package simonepassarelli.spark.dataimport.control;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import simonepassarelli.spark.dataimport.entity.BidAskPrice;
import simonepassarelli.spark.filter.InvalidQueryException;

import java.time.LocalDateTime;
import java.util.List;

class BidAskPriceRepositoryTest {

    @BeforeEach
    void setUp() {
        BidAskPriceRepository.clear();
        BidAskPriceRepository.persist(List.of(
                new BidAskPrice("reuters", "EURUSD", LocalDateTime.now(), 1.0, 1.12),
                new BidAskPrice("citi", "EURUSD", LocalDateTime.now().minusDays(1), 1.14, 1.456),
                new BidAskPrice("citi", "USDEUR", LocalDateTime.now().minusHours(1), 1.15, 1.457)
        ));
    }

    @Test
    void symbolEurUsd() {
        var result = BidAskPriceRepository.filter("symbol=EURUSD");
        Assertions.assertEquals(2, result.size());
        Assertions.assertTrue(result.stream().map(BidAskPrice::getSymbol).allMatch(symbol -> symbol.equals("EURUSD")));
    }

    @Test
    void symbolEurUsdWithSpaces() {
        var result = BidAskPriceRepository.filter("   symbol   =   EURUSD          ");
        Assertions.assertEquals(2, result.size());
        Assertions.assertTrue(result.stream().map(BidAskPrice::getSymbol).allMatch(symbol -> symbol.equals("EURUSD")));
    }

    @Test
    void symbolUsdEur() {
        var result = BidAskPriceRepository.filter("symbol=USDEUR");
        Assertions.assertEquals(1, result.size());
        Assertions.assertEquals("USDEUR", result.get(0).getSymbol());
    }

    @Test
    void sourceCiti() {
        var result = BidAskPriceRepository.filter("source=citi");
        Assertions.assertEquals(2, result.size());
        Assertions.assertTrue(result.stream().map(BidAskPrice::getSource).allMatch(s -> s.equals("citi")));
    }

    @Test
    void filterSourceAgeHigher() {
        var result = BidAskPriceRepository.filter("age>100");
        Assertions.assertEquals(2, result.size());
        Assertions.assertTrue(() -> {
            // since we are filtering by age in descending order,
            // we expect the first record to be newer than the second one
            var t0 = result.get(0).getTimestamp();
            var t1 = result.get(1).getTimestamp();
            return t0.isAfter(t1);
        });
    }

    @Test
    void filterSourceAgeLower() {
        var result = BidAskPriceRepository.filter("age<1000");
        Assertions.assertEquals(1, result.size());
    }

    @Test
    void filterInvalidOperator() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> BidAskPriceRepository.filter("age=<1000"));
    }

    @Test
    void invalidLeftIdentifier() {
        var exception = Assertions.assertThrows(InvalidQueryException.class, () -> BidAskPriceRepository.filter("suorce=citi"));
        Assertions.assertEquals("Invalid query specified: suorce=citi", exception.getMessage());
    }

    @Test
    void filterAskPrice() {
        var result = BidAskPriceRepository.filter("askPrice<1.13");
        Assertions.assertEquals(1, result.size());
        Assertions.assertTrue(result.stream().map(BidAskPrice::getAskPrice).allMatch(price -> price < 1.13));
    }

    @Test
    void filterAskPriceHigher() {
        var result = BidAskPriceRepository.filter("askPrice>1.13");
        Assertions.assertEquals(2, result.size());
        Assertions.assertTrue(result.stream().map(BidAskPrice::getAskPrice).allMatch(price -> price > 1.13));
        Assertions.assertTrue(() -> {
            // since we are filtering by askPrice in descending order,
            // we expect the first record to have a higher askPrice than the second one
            var p0 = result.get(0).getAskPrice();
            var p1 = result.get(1).getAskPrice();
            return p0 > p1;
        });
    }

    @Test
    void filterAskPriceHigherEqual() {
        var result = BidAskPriceRepository.filter("askPrice>=1.456");
        Assertions.assertEquals(2, result.size());
        Assertions.assertTrue(result.stream().map(BidAskPrice::getAskPrice).allMatch(price -> price >= 1.456));
        Assertions.assertTrue(() -> {
            // since we are filtering by askPrice in descending order,
            // we expect the first record to have a higher askPrice than the second one
            var p0 = result.get(0).getAskPrice();
            var p1 = result.get(1).getAskPrice();
            return p0 > p1;
        });
    }

    @Test
    void filterBidPrice() {
        var result = BidAskPriceRepository.filter("bidPrice=1.0");
        Assertions.assertEquals(1, result.size());
        Assertions.assertEquals(1.0, result.get(0).getBidPrice());
    }

    @Test
    void filterBidPriceHigher() {
        var result = BidAskPriceRepository.filter("bidPrice>1.0");
        Assertions.assertEquals(2, result.size());
        Assertions.assertTrue(result.stream().map(BidAskPrice::getBidPrice).allMatch(price -> price > 1.0));
        Assertions.assertTrue(() -> {
            // since we are filtering by bidPrice in descending order,
            // we expect the first record to have a higher bidPrice than the second one
            var p0 = result.get(0).getBidPrice();
            var p1 = result.get(1).getBidPrice();
            return p0 > p1;
        });
    }

    @Test
    void compoundFilter() {
        var result = BidAskPriceRepository.filter("""
                bidPrice>1.0
                symbol=EURUSD""");
        Assertions.assertEquals(1, result.size());
        Assertions.assertEquals("citi", result.get(0).getSource());
    }

    @Test
    void emptyFilter() {
        var result = BidAskPriceRepository.filter("");
        Assertions.assertEquals(0, result.size());
    }
}

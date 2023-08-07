package simonepassarelli.spark.dataimport.control;

import simonepassarelli.spark.dataimport.entity.BidAskPrice;
import simonepassarelli.spark.dataimport.entity.QueryResult;
import simonepassarelli.spark.filter.Filter;

import java.util.Collection;
import java.util.HashSet;

/**
 * Repository to store in-memory all BidAskPrice objects.
 */
public final class BidAskPriceRepository {

    private BidAskPriceRepository() {}

    private static final HashSet<BidAskPrice> prices = new HashSet<>();

    public static void persist(Collection<BidAskPrice> bidAskPrices) {
        prices.addAll(bidAskPrices);
    }

    public static void clear() {
        prices.clear();
    }

    public static QueryResult filter(String query) {
        Filter filter = Filter.createQueryFilter(query);
        var result = prices.stream()
                           .filter(filter.predicate())
                           .sorted(filter.comparator())
                           .toList();
        return new QueryResult(result);
    }
}

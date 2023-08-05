package simonepassarelli.spark.filter;

import simonepassarelli.spark.dataimport.entity.BidAskPrice;

import java.util.Comparator;
import java.util.function.Predicate;

final class AskPriceFilter implements Filter {
    private final Operator operator;
    private final double price;

    AskPriceFilter(Operator operator, double price) {
        this.operator = operator;
        this.price = price;
    }

    @Override
    public Predicate<BidAskPrice> predicate() {
        return bidAskPrice -> operator.compareDouble(bidAskPrice.getAskPrice(), price);
    }

    @Override
    public Comparator<BidAskPrice> comparator() {
        return Comparator.comparing(BidAskPrice::getAskPrice).reversed();
    }
}

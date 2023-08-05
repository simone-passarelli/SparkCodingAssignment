package simonepassarelli.spark.filter;

import simonepassarelli.spark.dataimport.entity.BidAskPrice;

import java.util.Comparator;
import java.util.function.Predicate;

final class AgeFilter implements Filter {
    private final Operator operator;
    private final int ageInMs;

    AgeFilter(Operator operator, int ageInMs) {
        this.operator = operator;
        this.ageInMs = ageInMs;
    }

    @Override
    public Predicate<BidAskPrice> predicate() {
        return bidAskPrice -> operator.compareTimestamp(bidAskPrice.getTimestamp(), ageInMs);
    }

    @Override
    public Comparator<BidAskPrice> comparator() {
        return Comparator.comparing(BidAskPrice::getTimestamp).reversed();
    }
}

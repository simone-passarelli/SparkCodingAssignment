package simonepassarelli.spark.filter;

import simonepassarelli.spark.dataimport.entity.BidAskPrice;

import java.util.Comparator;
import java.util.function.Predicate;

final class SourceFilter implements Filter {
    private final String source;

    SourceFilter(String source) {
        this.source = source;
    }

    @Override
    public Predicate<BidAskPrice> predicate() {
        return bidAskPrice -> bidAskPrice.getSource().equalsIgnoreCase(source);
    }

    @Override
    public Comparator<BidAskPrice> comparator() {
        return Comparator.comparing(BidAskPrice::getSource).reversed();
    }
}

package simonepassarelli.spark.filter;

import simonepassarelli.spark.dataimport.entity.BidAskPrice;

import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;

public final class CompoundFilter implements Filter {

    private final List<Filter> filters;

    public CompoundFilter(List<Filter> filters) {
        this.filters = filters;
    }

    @Override
    public Predicate<BidAskPrice> predicate() {
        if (filters.isEmpty()) {
            return __ -> false; //empty query matches nothing
        }

        Predicate<BidAskPrice> p = filters.get(0).predicate();
        for (int i = 1; i < filters.size(); i++) {
            p = p.and(filters.get(i).predicate());
        }
        return p;
    }

    @Override
    public Comparator<BidAskPrice> comparator() {
        if (filters.isEmpty()) {
            return Comparator.comparing(BidAskPrice::getSource).reversed();
        }

        Comparator<BidAskPrice> c = filters.get(0).comparator();
        for (int i = 1; i < filters.size(); i++) {
            c = c.thenComparing(filters.get(i).comparator());
        }
        return c;
    }
}

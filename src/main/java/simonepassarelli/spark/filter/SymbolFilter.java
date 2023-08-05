package simonepassarelli.spark.filter;

import simonepassarelli.spark.dataimport.entity.BidAskPrice;

import java.util.Comparator;
import java.util.function.Predicate;

final class SymbolFilter implements Filter {
    private final String symbol;

    SymbolFilter(String symbol) {
        this.symbol = symbol;
    }

    @Override
    public Predicate<BidAskPrice> predicate() {
        return bidAskPrice -> bidAskPrice.getSymbol().equalsIgnoreCase(symbol);
    }

    @Override
    public Comparator<BidAskPrice> comparator() {
        return Comparator.comparing(BidAskPrice::getSymbol).reversed();
    }
}

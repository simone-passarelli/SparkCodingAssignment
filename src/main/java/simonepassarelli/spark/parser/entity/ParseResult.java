package simonepassarelli.spark.parser.entity;

import simonepassarelli.spark.dataimport.entity.BidAskPrice;

import java.util.Collection;

public class ParseResult {
    private final Collection<BidAskPrice> bidAskPrices;
    private final Collection<String> parseErrors;

    public ParseResult(Collection<BidAskPrice> bidAskPrices, Collection<String> parseErrors) {
        this.bidAskPrices = bidAskPrices;
        this.parseErrors = parseErrors;
    }

    public Collection<BidAskPrice> getBidAskPrices() {
        return bidAskPrices;
    }

    public Collection<String> getErrors() {
        return parseErrors;
    }

    public String getErrorsAsString() {
        return String.join("\n", parseErrors);
    }

    public boolean hasErrors() {
        return !this.parseErrors.isEmpty();
    }
}

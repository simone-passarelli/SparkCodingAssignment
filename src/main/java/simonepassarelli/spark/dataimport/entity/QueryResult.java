package simonepassarelli.spark.dataimport.entity;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public record QueryResult(List<BidAskPrice> result) {

    private static final String HEADER = "bidSource,bidTimestamp,bidprice,symbol,askPrice";

    public Stream<BidAskPrice> stream() {
        return result.stream();
    }

    public int size() {
        return result.size();
    }

    public BidAskPrice get(int i) {
        return result.get(i);
    }

    @Override
    public String toString() {
        return Stream.concat(Stream.of(HEADER),                        // the header
                             this.stream().map(BidAskPrice::toString)) // the rest of the lines
                     .collect(Collectors.joining("\n"));
    }
}

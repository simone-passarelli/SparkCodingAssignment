package simonepassarelli.spark.filter;

import simonepassarelli.spark.dataimport.entity.BidAskPrice;

import java.util.Comparator;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public sealed interface Filter permits AgeFilter, AskPriceFilter, BidPriceFilter, CompoundFilter, SourceFilter, SymbolFilter {

    Predicate<BidAskPrice> predicate();

    Comparator<BidAskPrice> comparator();

    static Filter toQueryFilter(String queryString) {
        var filters = queryString.lines()
                                 .map(Filter::removeAllWhiteSpaces)
                                 .map(Filter::parseSingleLine)
                                 .toList();
        return new CompoundFilter(filters);
    }

    private static String removeAllWhiteSpaces(String query) {
        return query.chars()
                    .filter(c -> !Character.isWhitespace(c))
                    .mapToObj(Filter::charToString)
                    .collect(Collectors.joining());
    }

    private static Filter parseSingleLine(String query) {
        if (query.startsWith("symbol=")) {
            return new SymbolFilter(query.substring(query.indexOf("=") + 1));
        }
        if (query.startsWith("source=")) {
            return new SourceFilter(query.substring(query.indexOf("=") + 1));
        }
        if (query.startsWith("age")) {
            String operator = parseOperator(query.substring(3));
            String value = query.substring(3 + operator.length());
            return new AgeFilter(Operator.fromString(operator), Integer.parseInt(value));
        }
        if (query.startsWith("askPrice")) {
            String operator = parseOperator(query.substring(8));
            String value = query.substring(8 + operator.length());
            return new AskPriceFilter(Operator.fromString(operator), Double.parseDouble(value));
        }
        if (query.startsWith("bidPrice")) {
            String operator = parseOperator(query.substring(8));
            String value = query.substring(8 + operator.length());
            return new BidPriceFilter(Operator.fromString(operator), Double.parseDouble(value));
        }
        throw new InvalidQueryException(query);
    }

    private static String parseOperator(String substring) {
        var allowedValues = Set.of("<", "=", ">");
        return substring.chars()
                        .mapToObj(Filter::charToString)
                        .takeWhile(allowedValues::contains)
                        .collect(Collectors.joining());

    }

    private static String charToString(int i) {
        return String.valueOf((char) i);
    }
}

package simonepassarelli.spark.parser.control;

import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.exceptions.CsvException;
import simonepassarelli.spark.parser.entity.ParseResult;
import simonepassarelli.spark.dataimport.entity.BidAskPrice;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

class CsvParser implements Parser {
    public ParseResult parse(Path csvFile) throws IOException {
        var csvToBean = new CsvToBeanBuilder<BidAskPrice>(Files.newBufferedReader(csvFile))
                .withType(BidAskPrice.class)
                .withThrowExceptions(false)
                .build();
        var parse = csvToBean.parse();
        var errors = csvToBean.getCapturedExceptions();
        return new ParseResult(parse, mapErrorsToPlainStrings(errors));
    }

    private static Collection<String> mapErrorsToPlainStrings(List<CsvException> errors) {
        return errors.stream()
                     .map(e -> "Line " + e.getLineNumber() + ": " + e.getMessage())
                     .collect(Collectors.toList());
    }
}

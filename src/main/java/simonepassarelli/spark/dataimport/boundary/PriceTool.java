package simonepassarelli.spark.dataimport.boundary;

import simonepassarelli.spark.dataimport.control.BidAskPriceRepository;
import simonepassarelli.spark.parser.control.Parser;
import simonepassarelli.spark.parser.control.ParserNotFoundException;
import simonepassarelli.spark.parser.entity.ParseResult;

import java.io.IOException;
import java.nio.file.Path;
import java.util.logging.Logger;

public class PriceTool {

    private static final Logger logger = Logger.getLogger("PriceTool");

    private PriceTool() {}

    public static PriceTool init(Path path) throws ParserNotFoundException, IOException {
        Parser parser = Parser.determineParserForFileType(path);
        ParseResult parseResult = parser.parse(path);
        if (parseResult.hasErrors()) {
            logger.warning("The following records have been rejected: \n" + parseResult.getErrorsAsString());
        }
        BidAskPriceRepository.clear();
        BidAskPriceRepository.persist(parseResult.getBidAskPrices());
        return new PriceTool();
    }

    public void query(String query) {
        var queryResult = BidAskPriceRepository.filter(query);
        System.out.println(queryResult);
    }
}

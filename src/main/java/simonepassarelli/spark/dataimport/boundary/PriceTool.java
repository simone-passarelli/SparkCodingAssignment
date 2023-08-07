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

    /**
     * API entrypoint. Call this method to interact with the PriceTool facility.
     *
     * @param path a path to a file containing ask & bid prices
     * @return a PriceTool instance, which allows the user to run queries on the persisted data
     * @throws ParserNotFoundException if no parser is found to properly ingest the data pointed by the {@code path} param
     * @throws IOException             if the parser fails during file read
     */
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

    /**
     * Runs a specified query against the ask&bid prices database. The query can be a multiline String consisting of several statements, i.e.:
     * <pre>
     * source=citi
     * age<=100
     * symbol=EURUSD
     * </pre>
     *
     * @param query the input query. It can be empty but cannot be null
     */
    public void query(String query) {
        var queryResult = BidAskPriceRepository.filter(query);
        System.out.println(queryResult);
    }
}

package simonepassarelli.spark.parser.control;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Path;

public class CsvParserTest {

    @Test
    public void parse() throws IOException {
        var parse = new CsvParser().parse(Path.of("data/input.csv"));
        Assertions.assertEquals(3, parse.getBidAskPrices().size());
        Assertions.assertEquals(0, parse.getErrors().size());
    }

    /**
     * Tests an empty file. In this case the program should not break, just return an empty collection of parsed
     * entities.
     *
     * @throws IOException
     *         in case the provided {@code path} doesn't exist, or it cannot be accessed
     */
    @Test
    public void parseEmpty() throws IOException {
        var parse = new CsvParser().parse(Path.of("data/input_empty.csv"));
        Assertions.assertEquals(0, parse.getBidAskPrices().size());
        Assertions.assertEquals(0, parse.getErrors().size());
    }
}

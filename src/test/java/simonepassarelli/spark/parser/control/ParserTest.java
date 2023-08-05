package simonepassarelli.spark.parser.control;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;

public class ParserTest {

    @Test
    public void determineCorrectParser() throws ParserNotFoundException {
        var parser = Parser.determineParserForFileType(Path.of("file.csv"));
        Assertions.assertEquals(CsvParser.class, parser.getClass());
    }

    @Test
    public void noParserFoundForXmlFileType() {
        Assertions.assertThrows(ParserNotFoundException.class, () -> Parser.determineParserForFileType(Path.of("file.xml")));
    }
}

package simonepassarelli.spark.parser.control;

import simonepassarelli.spark.parser.entity.ParseResult;

import java.io.IOException;
import java.nio.file.Path;

@FunctionalInterface
public interface Parser {

    ParseResult parse(Path filePath) throws IOException;

    static Parser determineParserForFileType(Path path) throws ParserNotFoundException {
        String extension = Parser.getFileExtension(path);
        return switch (extension) {
            case ".csv" -> new CsvParser();
            default -> throw new ParserNotFoundException("Cannot find a valid parser for file " + path + " with extension " + extension);
        };
    }

    private static String getFileExtension(Path path) {
        var pathAsString = path.toString();
        var lastIndexOfDot = pathAsString.lastIndexOf('.');
        if (lastIndexOfDot > -1) {
            return pathAsString.substring(lastIndexOfDot).toLowerCase();
        }
        return "";
    }
}

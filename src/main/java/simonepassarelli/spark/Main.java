package simonepassarelli.spark;

import simonepassarelli.spark.dataimport.boundary.PriceTool;
import simonepassarelli.spark.parser.control.ParserNotFoundException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Main {

    public static void main(String[] args) throws ParserNotFoundException, IOException {
        var path = validateInputParam(args);
        var priceTool = PriceTool.init(path);
        priceTool.query("source=citi");
        System.out.println();
        priceTool.query("""
                source=citi
                symbol=EURUSD""");
    }

    private static Path validateInputParam(String[] args) {
        if (args.length != 1) {
            throw new IllegalArgumentException("""
                    Please provide a valid file path as a program argument. It can be a local or an absolute path. Examples:
                    java -jar SparkCodingAssignment.jar myfile.csv
                    java -jar SparkCodingAssignment.jar /home/data/myfile.csv
                    """);
        }
        var path = Path.of(args[0]);
        if (Files.notExists(path)) {
            throw new IllegalArgumentException("The file you provided does not exist. Please provide a valid filename");
        }
        return path;
    }
}

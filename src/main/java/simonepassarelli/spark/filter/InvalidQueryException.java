package simonepassarelli.spark.filter;

public class InvalidQueryException extends RuntimeException {
    public InvalidQueryException(String query) {
        super("Invalid query specified: " + query);
    }
}

package simonepassarelli.spark.dataimport.entity;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvDate;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Objects;

public class BidAskPrice {

    @CsvBindByName(column = "source", required = true)
    private String source;

    @CsvBindByName(column = "symbol", required = true)
    private String symbol;

    @CsvBindByName(column = "timestamp")
    @CsvDate("yyyyMMddHHmmssSSS")
    private LocalDateTime timestamp;

    @CsvBindByName(column = "bidPrice")
    private double bidPrice;

    @CsvBindByName(column = "askPrice")
    private double askPrice;

    /**
     * Needed by OpenCSV to instantiate the object
     */
    public BidAskPrice() {}

    public BidAskPrice(String source, String symbol, LocalDateTime timestamp, double bidPrice, double askPrice) {
        this.source = source;
        this.symbol = symbol;
        this.timestamp = timestamp;
        this.bidPrice = bidPrice;
        this.askPrice = askPrice;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public double getBidPrice() {
        return bidPrice;
    }

    public void setBidPrice(double bidPrice) {
        this.bidPrice = bidPrice;
    }

    public double getAskPrice() {
        return askPrice;
    }

    public void setAskPrice(double askPrice) {
        this.askPrice = askPrice;
    }

    @Override
    public String toString() {
        return "%s,%d,%s,%s,%s".formatted(source, timestamp.toEpochSecond(ZoneOffset.UTC), bidPrice, symbol, askPrice);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BidAskPrice that = (BidAskPrice) o;
        return Double.compare(bidPrice, that.bidPrice) == 0 && Double.compare(askPrice, that.askPrice) == 0 && Objects.equals(source, that.source) && Objects.equals(symbol, that.symbol) && Objects.equals(timestamp, that.timestamp);
    }

    @Override
    public int hashCode() {
        return Objects.hash(source, symbol, timestamp, bidPrice, askPrice);
    }
}

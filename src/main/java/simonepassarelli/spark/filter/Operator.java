package simonepassarelli.spark.filter;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public enum Operator {
    LESS_THAN {
        @Override
        public boolean compareTimestamp(LocalDateTime timestamp, int ageInMs) {
            var now = LocalDateTime.now();
            return timestamp.plus(ageInMs, ChronoUnit.MILLIS).isAfter(now);
        }

        @Override
        public boolean compareDouble(double double1, double double2) {
            return Double.compare(double1, double2) < 0;
        }
    },
    LESS_OR_EQUAL {
        @Override
        public boolean compareTimestamp(LocalDateTime timestamp, int ageInMs) {
            var now = LocalDateTime.now();
            return timestamp.plus(ageInMs, ChronoUnit.MILLIS).isAfter(now) ||
                    timestamp.plus(ageInMs, ChronoUnit.MILLIS).isEqual(now);
        }

        @Override
        public boolean compareDouble(double double1, double double2) {
            return Double.compare(double1, double2) <= 0;
        }
    },
    EQUAL {
        @Override
        public boolean compareTimestamp(LocalDateTime timestamp, int ageInMs) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean compareDouble(double double1, double double2) {
            return Double.compare(double1, double2) == 0;
        }
    },
    HIGHER_THAN {
        @Override
        public boolean compareTimestamp(LocalDateTime timestamp, int ageInMs) {
            var now = LocalDateTime.now();
            return timestamp.plus(ageInMs, ChronoUnit.MILLIS).isBefore(now);
        }

        @Override
        public boolean compareDouble(double double1, double double2) {
            return Double.compare(double1, double2) > 0;
        }
    },
    HIGHER_OR_EQUAL {
        @Override
        public boolean compareTimestamp(LocalDateTime timestamp, int ageInMs) {
            var now = LocalDateTime.now();
            return timestamp.plus(ageInMs, ChronoUnit.MILLIS).isBefore(now) ||
                    timestamp.plus(ageInMs, ChronoUnit.MILLIS).isEqual(now);
        }

        @Override
        public boolean compareDouble(double double1, double double2) {
            return Double.compare(double1, double2) >= 0;
        }
    };

    public static Operator fromString(String operator) {
        return switch (operator) {
            case "<" -> LESS_THAN;
            case "<=" -> LESS_OR_EQUAL;
            case "=" -> EQUAL;
            case ">=" -> HIGHER_OR_EQUAL;
            case ">" -> HIGHER_THAN;
            default -> throw new IllegalArgumentException("Invalid Operator!!: " + operator);
        };
    }

    public abstract boolean compareTimestamp(LocalDateTime timestamp, int ageInMs);

    public abstract boolean compareDouble(double double1, double double2);
}

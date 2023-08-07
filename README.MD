## Spark Systems Coding Assignment - Simone Passarelli - 06/08/2023

### Assumptions:
* Given the limited scope of the application, I decided to create a simple command line tool, instead of a full-fledged "server-side" application;
* Java 17 (the current LTS version) or higher is required to compile, test, and run the application;
* For the CSV parsing I used the well-known [OpenCSV](https://opencsv.sourceforge.net) library, instead of hand-crafting a (potentially complex?) parsing logic, which would have been cumbersome and error-prone;
* I decided to use the standard `java.util.logging` infrastructure for logging. In a real, production environment I would probably use Logback with Slf4j, but for the scope of this assignment I think it's good enough;

### Limitations:
* Currently, only CSV files can be handled. It's easy to add a parser for another file type, though: just implement the interface `simonepassarelli.spark.parser.control.Parser` for the desired file type.
* Discarded records are just logged; in a real environment I would probably save them in a "rejected.csv" file (or in a database), to give the possibility to a maintainer to look at the file contents, fix them, and run the program again.
* Since all records are kept in-memory, the number of records the application can handle is effectively determined by the max heap size. If we want to operate on billions of records, we should either ensure a proper maxHeap value (through testing), use a "real" database, or use a Java library which stores data off-heap (i.e. [Terracotta off-heap store](https://github.com/Terracotta-OSS/offheap-store) or [ChronicleMap](https://github.com/OpenHFT/Chronicle-Map)).

### Testing:
`mvn test`

### Usage
`mvn clean package`

`java -jar target/SparkCodingAssignment.jar data/input.csv`

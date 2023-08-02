# Order Book Matching System

This is a simple command-line application that implements a limit order book matching system. It accepts orders from standard input, matches them, and emits the trades as they occur. Once the standard input ends, the program prints the final contents of the order book.

## Prerequisites

Before running the application, make sure you have the following installed on your system:

- Java Development Kit (JDK) 20 or higher
- Apache Maven (for building the project)

## Getting Started

1. Clone the repository to your local machine:

```
git clone https://github.com/a2ankitrai/order-book.git
```

2. Navigate to the project directory:

```
cd order-book
```

3. Build the project using Maven:

```
mvn clean install
```

## Usage

To use the order book matching system, you can run the JAR file from the command line. The system accepts orders from standard input and emits trades as they occur to standard output. Once the standard input ends, the program prints the final contents of the order book.

To execute the JAR file and pass input from a file, you can use the provided shell script `exchange.sh`. The script checks if the target folder exists, and if not, it runs `mvn clean install` to build the project.

Here's how to use the shell script:

```shell
./exchange.sh test.txt
```

Replace test.txt with the path to your input file containing orders in comma-separated format.

## Input Format

The order inputs should be given as comma-separated values, one order per line of the input, delimited by a new line character. The fields are:

```
order-id,side,price,quantity
```

- `order-id`: A string representing the order identifier (e.g., "10001").
- `side`: A value of 'B' for Buy or 'S' for Sell.
- `price`: An integer representing the order price.
- `quantity`: An integer representing the order quantity.

Example:

```
10001,B,98,25500
10002,S,100,500
```

## Output

The application will emit trades  that occur as orders are processed. The trades are displayed in the format:
```
trade <aggressing-order-id>,<resting-order-id>,<price>,<quantity>
```

After all trades are processed, the final contents of the order book are displayed.

The order book output is formatted as follows:

```
000,000,000 000000 | 000000 000,000,000
```

If a value is too small to cover the whole reserved area, it is left padded with spaces.
The output also includes the hash of the order book contents, provided as an additional feature:

```
50,000 99 | 100 500
25,500 98 | 100 10,000
| 103 100
| 105 20,000

Hash: [hash]
```

Please note that the current implementation is designed for single-threaded execution. The data structures used to store and manage orders are not thread-safe. 

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

The application will emit trades as they occur in the following format:

```
trade <aggressing-order-id>,<resting-order-id>,<price>,<quantity>
```

Additionally, once standard input ends, the program will print the final contents of the order book with the following format:

```
Buy Orders           | Sell Orders
000,000,000 000000   | 000000 000,000,000
000,000,000 000000   | 000000 000,000,000
...
```

The output will also include the MD5 hash of the order book for validation:

```
Hash: 8ff13aad3e61429bfb5ce0857e846567
```

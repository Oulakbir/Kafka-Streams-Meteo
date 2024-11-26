# Kafka Streams - Weather Data Processing

This project demonstrates the use of Kafka Streams for processing weather data in real-time. The application reads weather data from a Kafka topic, performs transformations, and writes aggregated results to another Kafka topic.

## Features

1. **Real-Time Data Processing**:
   - Reads weather data in the format: `station, temperature, humidity`.
   - Example: `Station1,25.3,60`.

2. **High-Temperature Filtering**:
   - Filters records with temperatures greater than 30°C.

3. **Temperature Conversion**:
   - Converts temperatures from Celsius (°C) to Fahrenheit (°F).

4. **Aggregation**:
   - Groups data by station to compute average temperature and humidity.

5. **Kafka Integration**:
   - Publishes processed results to the `station-averages` topic.

---

## Prerequisites

1. **Java 8 or later**  
   Ensure you have Java Development Kit (JDK) installed.

2. **Apache Kafka**  
   Install and run Kafka locally or on a server.

3. **Maven**  
   Build the project using Maven.

---

## Getting Started

### Clone the Repository

```bash
git clone https://github.com/Oulakbir/Kafka-Streams-Meteo.git
cd Kafka-Streams-Meteo
```

### Install Dependencies

Ensure the project dependencies are downloaded and compiled:

```bash
mvn clean install
```

### Set Up Kafka Topics

Run the following commands to create the necessary Kafka topics:

```bash
kafka-topics --bootstrap-server localhost:9092 --create --topic weather-data --partitions 1 --replication-factor 1
kafka-topics --bootstrap-server localhost:9092 --create --topic station-averages --partitions 1 --replication-factor 1
```

### Run the Application

Start the Kafka Streams application:

```bash
mvn exec:java -Dexec.mainClass="ma.enset.Main"
```

---

## Testing

### Send Test Data

Use the Kafka console producer to send test data to the `weather-data` topic:

```bash
kafka-console-producer --bootstrap-server localhost:9092 --topic weather-data
```

Input example:

```
Station1,25.3,60
Station2,35.0,50
Station2,40.0,45
Station1,32.0,70
```

### Consume Processed Data

Consume the results from the `station-averages` topic:

```bash
kafka-console-consumer --bootstrap-server localhost:9092 --topic station-averages --from-beginning
```

Expected output:

```
Station1: Température Moyenne = 31.65°F, Humidité Moyenne = 65%
Station2: Température Moyenne = 37.5°F, Humidité Moyenne = 47.5%
```

---

## Results

### Docker containers executed succeffully
![Screenshot 2024-11-26 231145](https://github.com/user-attachments/assets/57d03b93-a13b-4c38-aae7-b3b36e5f6a6c)
![Screenshot 2024-11-26 231322](https://github.com/user-attachments/assets/0c532247-86af-4834-8b2c-5a0a92dc2973)

### Topics created
![Screenshot 2024-11-26 231728](https://github.com/user-attachments/assets/c58bd036-665b-471f-8230-027396f8598b)


---

## Folder Structure

```
Kafka-Streams-Meteo/
│
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── ma.enset/
│   │   │       └── Main.java
│   │   └── resources/
│   │       └── application.properties
│
├── pom.xml
├── README.md
└── results/
    ├── console_output.png
    ├── filtered_data.png
    └── aggregated_results.png
```# Kafka Streams - Weather Data Processing

This project demonstrates the use of Kafka Streams for processing weather data in real-time. The application reads weather data from a Kafka topic, performs transformations, and writes aggregated results to another Kafka topic.

## Features

1. **Real-Time Data Processing**:
   - Reads weather data in the format: `station, temperature, humidity`.
   - Example: `Station1,25.3,60`.

2. **High-Temperature Filtering**:
   - Filters records with temperatures greater than 30°C.

3. **Temperature Conversion**:
   - Converts temperatures from Celsius (°C) to Fahrenheit (°F).

4. **Aggregation**:
   - Groups data by station to compute average temperature and humidity.

5. **Kafka Integration**:
   - Publishes processed results to the `station-averages` topic.

---

## Prerequisites

1. **Java 8 or later**  
   Ensure you have Java Development Kit (JDK) installed.

2. **Apache Kafka**  
   Install and run Kafka locally or on a server.

3. **Maven**  
   Build the project using Maven.

---

## Getting Started

### Clone the Repository

```bash
git clone https://github.com/Oulakbir/Kafka-Streams-Meteo.git
cd Kafka-Streams-Meteo
```

### Install Dependencies

Ensure the project dependencies are downloaded and compiled:

```bash
mvn clean install
```

### Set Up Kafka Topics

Run the following commands to create the necessary Kafka topics:

```bash
kafka-topics --bootstrap-server localhost:9092 --create --topic weather-data --partitions 1 --replication-factor 1
kafka-topics --bootstrap-server localhost:9092 --create --topic station-averages --partitions 1 --replication-factor 1
```

### Run the Application

Start the Kafka Streams application:

```bash
mvn exec:java -Dexec.mainClass="ma.enset.Main"
```

---

## Testing

### Send Test Data

Use the Kafka console producer to send test data to the `weather-data` topic:

```bash
kafka-console-producer --bootstrap-server localhost:9092 --topic weather-data
```

Input example:

```
Station1,25.3,60
Station2,35.0,50
Station2,40.0,45
Station1,32.0,70
```

### Consume Processed Data

Consume the results from the `station-averages` topic:

```bash
kafka-console-consumer --bootstrap-server localhost:9092 --topic station-averages --from-beginning
```

Expected output:

```
Station1: Température Moyenne = 31.65°F, Humidité Moyenne = 65%
Station2: Température Moyenne = 37.5°F, Humidité Moyenne = 47.5%
```

---

## Results

### Sample Output in Console
![Processed Data in Console](results/console_output.png)

### Example of Filtered Data
![Filtered Data](results/filtered_data.png)

### Aggregated Results Visualization
![Aggregated Data](results/aggregated_results.png)

*(Replace the above placeholders with actual screenshots of your test results.)*

---

## Folder Structure

```
Kafka-Streams-Meteo/
│
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── ma.enset/
│   │   │       └── Main.java
│   │   └── resources/
│   │       └── application.properties
│
├── pom.xml
├── README.md
└── results/
    ├── console_output.png
    ├── filtered_data.png
    └── aggregated_results.png
```

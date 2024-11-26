package ma.enset;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.*;

import java.util.Properties;

public class Main {
    public static void main(String[] args) {

        // Configuration des propriétés
        Properties props = new Properties();
        props.put("application.id", "weather-data-streams-app");
        props.put("bootstrap.servers", "localhost:9092");
        props.put("default.key.serde", Serdes.String().getClass());
        props.put("default.value.serde", Serdes.String().getClass());
        props.put("state.dir", "C:/kafka-streams/state-weather-data");

        // Création du StreamBuilder
        StreamsBuilder builder = new StreamsBuilder();

        // Lecture des données météo depuis le topic "weather-data"
        KStream<String, String> weatherStream = builder.stream("weather-data");

        // Filtrage des données où la température est supérieure à 30°C
        KStream<String, String> filteredStream = weatherStream.filter((key, value) -> {
            try {
                String[] fields = value.split(",");
                double temperature = Double.parseDouble(fields[1]);
                return temperature > 30.0;
            } catch (Exception e) {
                System.err.println("Erreur lors du traitement des données : " + value);
                return false;
            }
        });

        // Conversion des températures en Fahrenheit et affichage dans le terminal
        KStream<String, String> transformedStream = filteredStream.mapValues(value -> {
            String[] fields = value.split(",");
            double temperatureCelsius = Double.parseDouble(fields[1]);
            double temperatureFahrenheit = (temperatureCelsius * 9 / 5) + 32;
            return fields[0] + "," + temperatureFahrenheit + "," + fields[2];
        });

        // Groupement par station et calcul des moyennes
        KGroupedStream<String, String> groupedStream = transformedStream.groupBy((key, value) -> value.split(",")[0]);

        KTable<String, String> averageTable = groupedStream.aggregate(
                () -> "0,0,0", // Valeur initiale : "totalTemp,totalHum,count"
                (station, newValue, aggValue) -> {
                    String[] newFields = newValue.split(",");
                    String[] aggFields = aggValue.split(",");

                    double newTemp = Double.parseDouble(newFields[1]);
                    double newHum = Double.parseDouble(newFields[2]);

                    double totalTemp = Double.parseDouble(aggFields[0]) + newTemp;
                    double totalHum = Double.parseDouble(aggFields[1]) + newHum;
                    long count = Long.parseLong(aggFields[2]) + 1;

                    return totalTemp + "," + totalHum + "," + count;
                },
                Materialized.with(Serdes.String(), Serdes.String())
        );

        // Conversion des agrégats en moyennes et envoi vers un topic de sortie
        averageTable.toStream().mapValues(value -> {
            String[] fields = value.split(",");
            double totalTemp = Double.parseDouble(fields[0]);
            double totalHum = Double.parseDouble(fields[1]);
            long count = Long.parseLong(fields[2]);

            double avgTemp = totalTemp / count;
            double avgHum = totalHum / count;
            System.out.println("Température Moyenne = " + avgTemp + "°F, Humidité Moyenne = " + avgHum + "%");
            return "Température Moyenne = " + avgTemp + "°F, Humidité Moyenne = " + avgHum + "%";
        }).to("station-averages", Produced.with(Serdes.String(), Serdes.String()));

        // Création et démarrage de Kafka Streams
        KafkaStreams streams = new KafkaStreams(builder.build(), props);
        streams.start();

        // Hook pour arrêter proprement l'application
        Runtime.getRuntime().addShutdownHook(new Thread(streams::close));
    }
}

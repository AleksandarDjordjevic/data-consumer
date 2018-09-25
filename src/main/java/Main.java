import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

public class Main {

    public static void main(String[] args) throws IOException {
        Properties props = new Properties();
        Classifier classifier = new Classifier();
        props.load(Main.class.getResourceAsStream("application.properties"));
        props.put("bootstrap.servers", props.getProperty("remote-machine.address") + ":" + props.getProperty("kafka.port"));

        ElasticsearchPublisher elasticsearchPublisher = new ElasticsearchPublisher(props);
        KafkaPublisher kafkaPublisher = new KafkaPublisher(props);
        props.put("group.id", "test");
        props.put("enable.auto.commit", "false");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.ByteArrayDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        try (KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props)) {
            consumer.subscribe(Arrays.asList(props.getProperty("data.stream.topic")));
            final int minBatchSize = 200;
            List<ConsumerRecord<String, String>> buffer = new ArrayList<>();
            while (true) {
                ConsumerRecords<String, String> records = consumer.poll(100);
                for (ConsumerRecord<String, String> record : records) {
                    buffer.add(record);
                }

                for (ConsumerRecord<String, String> cr : buffer) {
                    String value = cr.value();
                    System.out.println("Received value: " + value);
                    ClassAttributeValue classAttributeValue = classifier.classify(value);
                    Document document = new Document(value.split("\t")[22], value.split("\t")[100], value.split("\t")[101], value.split("\t")[102], value.split("\t")[0], value.split("\t")[106]);
                    document.setActualClassAttribute(classAttributeValue.name());
                    //Sending the document to Elasticsearch
                    elasticsearchPublisher.indexDocumentHighLevelApi(document);
                    //Sending classification result to Kafka
                    kafkaPublisher.sendData(document, props.getProperty("result.stream.topic"));
                }

            }
        }

    }
}


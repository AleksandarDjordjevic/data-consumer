import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Properties;

public class KafkaPublisher {
    private Properties properties;

    public KafkaPublisher(Properties properties) {
        this.properties = properties;
    }

    public void sendData(Document document, String topic) throws JsonProcessingException {
        Properties configProperties = new Properties();
        configProperties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, properties.getProperty("remote-machine.address")
                + ":" + properties.getProperty("kafka.port"));
        configProperties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.ByteArraySerializer");
        configProperties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");

        try (org.apache.kafka.clients.producer.Producer producer = new KafkaProducer(configProperties)) {
            if (document != null) {
                producer.send(new ProducerRecord(topic, new ObjectMapper().writeValueAsString(document)));
                System.out.println("Sending data:" + new ObjectMapper().writeValueAsString(document));
            }
        }
    }
}






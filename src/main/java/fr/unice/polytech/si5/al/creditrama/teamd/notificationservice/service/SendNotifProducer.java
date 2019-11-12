package fr.unice.polytech.si5.al.creditrama.teamd.notificationservice.service;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Properties;

public class SendNotifProducer {
    private Properties properties;

    public SendNotifProducer(Properties props) {
        this.properties = props;
    }

    public void produce(String topicToProduceIn) {

        try (Producer<String, String> producer = new KafkaProducer<>(this.properties)) {
            producer.send(new ProducerRecord<>(topicToProduceIn, "theKey", "Notif from CreditRama, si ce message s'envoie c'est que j'ai réussi à le lire depuis Kafka"));
            System.out.println("Message sent !!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

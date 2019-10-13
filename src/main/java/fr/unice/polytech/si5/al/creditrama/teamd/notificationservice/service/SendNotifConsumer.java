package fr.unice.polytech.si5.al.creditrama.teamd.notificationservice.service;

import fr.unice.polytech.si5.al.creditrama.teamd.notificationservice.controller.NotificationsController;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

public class SendNotifConsumer {
    private Properties properties;

    public SendNotifConsumer(Properties props) {
        this.properties = props;
    }

    public void consume(String topicToConsume) {
        try (KafkaConsumer<String, String> consumer = new KafkaConsumer<>(this.properties)) {
            consumer.subscribe(Collections.singletonList(topicToConsume));

            while (true) {
                ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));
                for (ConsumerRecord<String, String> record : records) {
                    System.out.println(record.value());
                    NotificationsController notificationsController = new NotificationsController();
                    notificationsController.testMail(record.value());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

package fr.unice.polytech.si5.al.creditrama.teamd.notificationservice;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import fr.unice.polytech.si5.al.creditrama.teamd.notificationservice.controller.NotificationsController;
import fr.unice.polytech.si5.al.creditrama.teamd.notificationservice.service.NotificationProcessor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;

import java.io.IOException;
import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

@EnableBinding(NotificationProcessor.class)
@SpringBootApplication
public class NotificationServiceApplication {

    @StreamListener("CreditRama.SendNotif")
    public void sendNotif(@Payload String val) throws IOException {
        /*System.out.println("NOTIF OK " + val);
        JsonObject jsonObject = new JsonObject().getAsJsonObject(val);
        JsonElement emailClient = jsonObject.get("email");
        if (emailClient != null) {
            NotificationsController notificationsController = new NotificationsController();
            System.out.println("ici parce que le mail est reconnu");
            notificationsController.testMail(emailClient.toString(), "Bonjour,\n Nous vous remercions d'avoir créé votre compte chez CreditRama.\n " +
                    "Ceci est un mail de confirmation, merci de ne pas répondre.\n \n Cordialement,\n L'équipe CreditRama");
        }*/
    }

    public static void main(String[] args) {
        SpringApplication.run(NotificationServiceApplication.class, args);
        Properties props = new Properties();
        props.setProperty("group.id", "Laura.Test");
        props.setProperty("enable.auto.commit", "true");
        props.setProperty("bootstrap.servers", "localhost:9092");
        props.setProperty("auto.commit.interval.ms", "100");
        props.setProperty("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.setProperty("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");

        try (KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props)) {
            consumer.subscribe(Collections.singletonList("CreditRama.SendNotif"));

            while (true) {
                ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));
                for (ConsumerRecord<String, String> record : records) {
                    JSONObject jsonObj = new JSONObject(record.value().toString());
                    if (jsonObj.get("email") != null) {
                        NotificationsController notificationsController = new NotificationsController();
                        notificationsController.testMail(jsonObj.get("email").toString(), "Bonjour,\n Nous vous remercions d'avoir créé votre compte chez CreditRama.\n " +
                                "Ceci est un mail de confirmation, merci de ne pas répondre.\n \n Cordialement,\n L'équipe CreditRama");
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

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
import org.springframework.boot.configurationprocessor.json.JSONException;
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
    public void sendNotif(@Payload String val) throws IOException, JSONException {
        JSONObject jsonObj = new JSONObject(val);
        if (jsonObj.get("email") != null) {
            NotificationsController notificationsController = new NotificationsController();
            notificationsController.testMail(jsonObj.get("email").toString(), "Bonjour,\n Nous vous remercions d'avoir créé votre compte chez CreditRama.\n " +
                    "Ceci est un mail de confirmation, merci de ne pas répondre.\n \n Cordialement,\n L'équipe CreditRama");
        }
    }

    public static void main(String[] args) {
        SpringApplication.run(NotificationServiceApplication.class, args);
    }
}

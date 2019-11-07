package fr.unice.polytech.si5.al.creditrama.teamd.notificationservice;

import fr.unice.polytech.si5.al.creditrama.teamd.notificationservice.controller.NotificationsController;
import fr.unice.polytech.si5.al.creditrama.teamd.notificationservice.models.Notification;
import fr.unice.polytech.si5.al.creditrama.teamd.notificationservice.service.NotificationProcessor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.context.annotation.Profile;
import org.springframework.messaging.handler.annotation.Payload;

@EnableBinding(NotificationProcessor.class)
@SpringBootApplication
@Profile("!disable-kafka")
public class NotificationServiceApplication {

    @StreamListener("CreditRama.SendNotif")
    public void sendNotif(@Payload Notification val) {
        new NotificationsController().testMail(val);
    }

    public static void main(String[] args) {
        SpringApplication.run(NotificationServiceApplication.class, args);
    }
}

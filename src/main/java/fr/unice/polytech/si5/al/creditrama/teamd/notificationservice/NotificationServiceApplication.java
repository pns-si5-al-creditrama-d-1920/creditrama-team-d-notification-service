package fr.unice.polytech.si5.al.creditrama.teamd.notificationservice;

import fr.unice.polytech.si5.al.creditrama.teamd.notificationservice.service.NotificationProcessor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;

@EnableBinding(NotificationProcessor.class)
@SpringBootApplication
public class NotificationServiceApplication {

    @StreamListener("CreditRama.SendNotif")
    public void sendNotif(@Payload String val) {
        System.out.println("NOTIF OK " + val);
    }

    public static void main(String[] args) {
        SpringApplication.run(NotificationServiceApplication.class, args);
        /*SendNotifConsumer sendNotifConsumer = ConsumerConfig.configConsumer("CreditRama.NotificationService", "127.0.0.1:9092");
        sendNotifConsumer.consume("CreditRama.SendNotif");*/
    }

}

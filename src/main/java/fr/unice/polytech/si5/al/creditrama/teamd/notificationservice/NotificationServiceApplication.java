package fr.unice.polytech.si5.al.creditrama.teamd.notificationservice;

import fr.unice.polytech.si5.al.creditrama.teamd.notificationservice.service.SendNotifConsumer;
import fr.unice.polytech.si5.al.creditrama.teamd.notificationservice.service.SendNotifProducer;
import fr.unice.polytech.si5.al.creditrama.teamd.notificationservice.utils.ConsumerConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class NotificationServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(NotificationServiceApplication.class, args);
        /*SendNotifConsumer sendNotifConsumer = ConsumerConfig.configConsumer("CreditRama.NotificationService", "127.0.0.1:9092");
        sendNotifConsumer.consume("CreditRama.SendNotif");*/
    }

}

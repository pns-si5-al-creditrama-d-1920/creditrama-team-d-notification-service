package fr.unice.polytech.si5.al.creditrama.teamd.notificationservice.controller;

import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Arrays;

@RestController
public class NotificationsController {
    @Value("${SEND_GRID_APIKEY}")
    private String sendgridkey;

    @CrossOrigin(origins = "*")
    @GetMapping("/testMail")
    public void testMail(String message) throws IOException {
        Email from = new Email("noreply@creditrama.com");
        String subject = "Creditrama la meilleur banque en ligne";
        //Content content = new Content("text/plain", "J'envoie ce mail suite à un évènement consommé dans Kafka, et ça, c'est vraiment fort.");
        Content content = new Content("text/plain", message);

        SendGrid sg = new SendGrid(sendgridkey);
        Arrays.asList("alexisgardin@gmail.com", "lauraalop7@gmail.com", "nathan.strobbe@gmail.com").stream().forEach(email -> {
            Email to = new Email(email);
            Mail mail = new Mail(from, subject, to, content);

            Request request = new Request();
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            Response response = null;
            try {
                request.setBody(mail.build());
                response = sg.api(request);
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println(response.getStatusCode());
            System.out.println(response.getBody());
            System.out.println(response.getHeaders());
        });


    }
}

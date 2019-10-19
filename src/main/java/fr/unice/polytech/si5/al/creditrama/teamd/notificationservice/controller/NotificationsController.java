package fr.unice.polytech.si5.al.creditrama.teamd.notificationservice.controller;

import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import com.sendgrid.helpers.mail.objects.Personalization;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.stream.Stream;

@RestController
public class NotificationsController {
    @Value("${SEND_GRID_APIKEY}")
    private String sendgridkey;

    @CrossOrigin(origins = "*")
    @GetMapping("/testMail")
    public void testMail(String emails, String message) throws IOException {
        Email from = new Email("noreply@creditrama.com");

        String subject = "Creditrama, la meilleur banque en ligne !";
        Content content = new Content("text/plain", message);

        SendGrid sg = new SendGrid(sendgridkey);
        Stream.of(emails).forEach(email -> {
            Email to = new Email(email);
            Mail mail = new Mail(from, subject, to, content);
            //mail.setTemplateId("d-e05e286316694c3a92fd9a8c6e6112e8");

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
            assert response != null;
            System.out.println(response.getStatusCode());
            System.out.println(response.getBody());
            System.out.println(response.getHeaders());
        });
    }
}

package fr.unice.polytech.si5.al.creditrama.teamd.notificationservice.controller;

import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Email;
import com.sendgrid.helpers.mail.objects.Personalization;
import fr.unice.polytech.si5.al.creditrama.teamd.notificationservice.models.Notification;
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
    public void testMail(Notification notification) throws Exception {
        Email from = new Email("noreply@creditrama.com");
        if (!notification.hasKey("message")) {
            throw new Exception("Wrong params : message is needed.");
        }

        SendGrid sg = new SendGrid("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");
        notification.getTo().forEach(email -> {
            Email to = new Email(email);
            Mail mail = new Mail();
            mail.setFrom(from);
            if ("TRANSFER".equals(notification.getAction())) {
                mail.setTemplateId("d-e05e286316694c3a92fd9a8c6e6112e8");
            }
            Personalization personalization = new Personalization();
            personalization.addTo(to);
            notification.getParams().forEach(param -> {
                personalization.addDynamicTemplateData(param.getKey(), param.getValue());
            });
            mail.addPersonalization(personalization);
            try {
                Request request = new Request();
                request.setMethod(Method.POST);
                request.setEndpoint("mail/send");
                Response response = null;
                request.setBody(mail.build());
                response = sg.api(request);
                assert response != null;
                System.out.println(response.getStatusCode());
                System.out.println(response.getBody());
                System.out.println(response.getHeaders());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}

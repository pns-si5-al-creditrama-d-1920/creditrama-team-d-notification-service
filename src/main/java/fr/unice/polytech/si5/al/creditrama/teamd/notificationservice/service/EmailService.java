package fr.unice.polytech.si5.al.creditrama.teamd.notificationservice.service;

import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Email;
import com.sendgrid.helpers.mail.objects.Personalization;
import fr.unice.polytech.si5.al.creditrama.teamd.notificationservice.client.ClientServiceClient;
import fr.unice.polytech.si5.al.creditrama.teamd.notificationservice.models.ClientDTO;
import fr.unice.polytech.si5.al.creditrama.teamd.notificationservice.models.Notification;
import fr.unice.polytech.si5.al.creditrama.teamd.notificationservice.models.NotificationMetaData;
import fr.unice.polytech.si5.al.creditrama.teamd.notificationservice.models.TransactionDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.context.annotation.Profile;

import java.io.IOException;
import java.util.ArrayList;


@Profile("!disable-kafka")
@EnableBinding(NotificationProcessor.class)
public class EmailService {

    private ClientServiceClient clientServiceClient;

    @Autowired
    public EmailService(ClientServiceClient clientServiceClient) {
        this.clientServiceClient = clientServiceClient;
    }

    @Value("sendgrid.api-key")
    String sendgridkey;

    public void sendMail(TransactionDTO transaction) throws Exception {
        ClientDTO dest = clientServiceClient.getClient(transaction.getDest().getClient());
        Notification notification = new Notification();
        notification.setType("EMAIL");
        notification.setAction("TRANSFER");
        notification.setTo(new ArrayList<>());
        notification.getTo().add(dest.getEmail());
        notification.setParams(new ArrayList<>());
        notification.getParams().add(new NotificationMetaData("username", dest.getUsername()));
        notification.getParams().add(new NotificationMetaData("amount", String.valueOf(transaction.getAmount())));
        if (transaction.getCode() != 0) {
            notification.getParams().add(new NotificationMetaData("code", String.valueOf(transaction.getCode())));
        }
        System.out.println("transaction.getCode() = " + transaction.getCode());
        sendMail(notification);
    }

    public void sendMail(Notification notification) throws Exception {
        Email from = new Email("noreply@creditrama.com");
        System.out.println("sendgridkey = " + sendgridkey);
        SendGrid sg = new SendGrid(sendgridkey);
        notification.getTo().forEach(email -> {
            Email to = new Email(email);
            Mail mail = new Mail();
            mail.setFrom(from);
            if ("TRANSFER".equals(notification.getAction())) {
                if (notification.getParams().get(2).getValue().equals("0")) {
                    mail.setTemplateId("d-4e0d0487000a4d19aa3feeb1d3c4f721");
                } else {
                    mail.setTemplateId("d-c097cff0c7c64c38a98469886f2bf407");
                }
            }
            Personalization personalization = new Personalization();
            personalization.addTo(to);
            notification.getParams().forEach(param -> personalization.addDynamicTemplateData(param.getKey(), param.getValue()));
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

    @StreamListener(NotificationProcessor.EMAIL_TRANSACTION)
    public void transactionDone(TransactionDTO transactionDTO) throws Exception {
        sendMail(transactionDTO);
    }
}

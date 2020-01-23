package fr.unice.polytech.si5.al.creditrama.teamd.notificationservice.client;

import fr.unice.polytech.si5.al.creditrama.teamd.notificationservice.models.ClientDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "client", url = "${service.client}", configuration = FeignConfiguration.class)
public interface ClientServiceClient {

    @GetMapping("/clients/{id}")
    public ClientDTO getClient(@PathVariable long id);

}
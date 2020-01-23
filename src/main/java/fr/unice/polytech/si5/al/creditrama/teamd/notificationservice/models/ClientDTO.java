package fr.unice.polytech.si5.al.creditrama.teamd.notificationservice.models;

import lombok.*;

import java.io.Serializable;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class ClientDTO {

    private Long userId;

    private String username;

    private String firstName;

    private String lastName;

    private String email;

}
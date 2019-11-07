package fr.unice.polytech.si5.al.creditrama.teamd.notificationservice.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Notification {
    private String type;
    private String action;
    private List<String> to;
    private List<NotificationMetaData> params;

    public boolean hasKey(String key) {
        return params.stream().filter(notif -> notif.getKey().equals(key) && !notif.getValue().isEmpty()).count() == 1;
    }
}

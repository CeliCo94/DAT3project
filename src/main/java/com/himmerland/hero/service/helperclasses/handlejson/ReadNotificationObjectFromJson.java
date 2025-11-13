package com.himmerland.hero.service.helperclasses.handlejson;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.himmerland.hero.domain.notifications.Notification;

import java.io.File;

public class ReadNotificationObjectFromJson {

    public static Notification readNoticationObjectFromJson() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            Notification notification = mapper.readValue(new File("src/main/resources/json/notifications.json"),
                    Notification.class);
            return notification;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
package com.himmerland.hero.domain.notifications;

import com.himmerland.hero.service.helperclasses.enums.Criticality;
import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;

public class NotificationTest {

    @Test
    void allArgsConstructor_setsFieldsAndDefaults() {
        Notification notification = new Notification("Dept A", "addr", "cause", "rule", Criticality.High);

        assertEquals("Dept A", notification.getDepartmentName());
        assertEquals("addr", notification.getAddress());
        assertEquals("cause", notification.getCause());
        assertEquals("rule", notification.getRule());
        assertEquals(Criticality.High, notification.getCriticality());
        assertTrue(notification.isActive());
        assertFalse(notification.isSent());
        assertNotNull(notification.getTimeStamp());
        assertDoesNotThrow(() -> Instant.parse(notification.getTimeStamp()));
        assertNotNull(notification.getId());
    }

    @Test
    void setters_UpdateFieldsAndPreserveIdentity() {
        Notification notification = new Notification();
        String originalId = notification.getId();
        notification.setDepartmentName("Dept B");
        notification.setAddress("old");
        notification.setCause("old");
        notification.setRule("old");
        notification.setCriticality(Criticality.Low);
        notification.setTimeStamp("2000-01-01T00:00:00Z");
        notification.setActive(false);
        notification.setSent(true);

        // mutate again to ensure setters keep working
        notification.setDepartmentName("Dept C");
        notification.setAddress("new");
        notification.setCause("new");
        notification.setRule("new");
        notification.setCriticality(Criticality.Medium);
        notification.setTimeStamp("2000-01-01T00:00:00Z");
        notification.setActive(true);
        notification.setSent(false);

        assertEquals("Dept C", notification.getDepartmentName());
        assertEquals("new", notification.getAddress());
        assertEquals("new",notification.getCause());
        assertEquals("new", notification.getRule());
        assertEquals(Criticality.Medium, notification.getCriticality());
        assertEquals("2000-01-01T00:00:00Z", notification.getTimeStamp());
        assertTrue(notification.isActive());
        assertFalse(notification.isSent());
        assertEquals(originalId, notification.getId(), "Identity should not change after mutations");

    }
    
}

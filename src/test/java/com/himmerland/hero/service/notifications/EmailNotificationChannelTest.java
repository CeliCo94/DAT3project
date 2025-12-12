package com.himmerland.hero.service.notifications;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.Mock;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import com.himmerland.hero.config.EmailProperties;
import com.himmerland.hero.domain.notifications.Notification;
import com.himmerland.hero.service.helperclasses.enums.Criticality;

@ExtendWith(MockitoExtension.class)
class EmailNotificationChannelTest {

    @Mock
    private JavaMailSender mailSender;

    @Mock
    private EmailProperties emailProperties;

    private EmailNotificationChannel channel;

    @BeforeEach
    void setUp() {
        channel = new EmailNotificationChannel(mailSender, emailProperties);
    }

    @Test
    void getName_returnsEMAIL() {
        assertEquals("EMAIL", channel.getName());
    }

    @Test
    void send_noOpsWhenAutosendIsFalse() {
        when(emailProperties.autosend()).thenReturn(false);
        
        Notification notification = new Notification();
        notification.setAddress("Test Address");
        notification.setCause("Test Cause");
        
        channel.send(notification);
        
        verify(mailSender, never()).send(any(SimpleMailMessage.class));
    }

    @Test
    void send_buildsAndSendsMessageWhenAutosendIsTrue_detailedMode() {
        when(emailProperties.autosend()).thenReturn(true);
        when(emailProperties.from()).thenReturn("sender@example.com");
        when(emailProperties.recipient()).thenReturn("recipient@example.com");

        Notification notification = new Notification();
        notification.setAddress("Building A, Floor 2");
        notification.setCause("High consumption detected");
        notification.setRule("Daily Usage Rule");
        notification.setCriticality(Criticality.High);
        notification.setTimeStamp("2025-12-11T10:30:00");
        notification.setActive(true);

        channel.send(notification);

        ArgumentCaptor<SimpleMailMessage> messageCaptor = ArgumentCaptor.forClass(SimpleMailMessage.class);
        verify(mailSender).send(messageCaptor.capture());

        SimpleMailMessage sentMessage = messageCaptor.getValue();
        assertEquals("sender@example.com", sentMessage.getFrom());
        assertArrayEquals(new String[]{"recipient@example.com"}, sentMessage.getTo());
        assertEquals("HERO notifikation - Building A, Floor 2", sentMessage.getSubject());
        
        String body = sentMessage.getText();
        assertTrue(body.contains("Der er en ny notifikation fra HERO-systemet"));
        assertTrue(body.contains("Adresse: Building A, Floor 2"));
        assertTrue(body.contains("Årsag: High consumption detected"));
        assertTrue(body.contains("Regel: Daily Usage Rule"));
        assertTrue(body.contains("Kritikalitet: High"));
        assertTrue(body.contains("Tidspunkt: 2025-12-11T10:30:00"));
        assertTrue(body.contains("Aktiv: Ja"));
    }

    @Test
    void send_buildsAndSendsMessageWhenAutosendIsTrue_summaryMode() {
        when(emailProperties.autosend()).thenReturn(true);
        when(emailProperties.from()).thenReturn("sender@example.com");
        when(emailProperties.recipient()).thenReturn("recipient@example.com");

        Notification notification = new Notification();
        notification.setAddress(null);
        notification.setRule(null);
        notification.setCriticality(null);
        notification.setTimeStamp(null);
        notification.setCause("5 nye notifikationer er tilgængelige");

        channel.send(notification);

        ArgumentCaptor<SimpleMailMessage> messageCaptor = ArgumentCaptor.forClass(SimpleMailMessage.class);
        verify(mailSender).send(messageCaptor.capture());

        SimpleMailMessage sentMessage = messageCaptor.getValue();
        assertEquals("sender@example.com", sentMessage.getFrom());
        assertArrayEquals(new String[]{"recipient@example.com"}, sentMessage.getTo());
        assertEquals("HERO - Daglige notifikationer", sentMessage.getSubject());
        
        String body = sentMessage.getText();
        assertTrue(body.contains("Der er nye notifikationer fra HERO-systemet"));
        assertTrue(body.contains("5 nye notifikationer er tilgængelige"));
    }

    @Test
    void send_detailedModeWithNullCriticality() {
        when(emailProperties.autosend()).thenReturn(true);
        when(emailProperties.from()).thenReturn("sender@example.com");
        when(emailProperties.recipient()).thenReturn("recipient@example.com");

        Notification notification = new Notification();
        notification.setAddress("Building B");
        notification.setCause("Test cause");
        notification.setRule("Test rule");
        notification.setCriticality(null);
        notification.setTimeStamp("2025-12-11T12:00:00");
        notification.setActive(false);

        channel.send(notification);

        ArgumentCaptor<SimpleMailMessage> messageCaptor = ArgumentCaptor.forClass(SimpleMailMessage.class);
        verify(mailSender).send(messageCaptor.capture());

        SimpleMailMessage sentMessage = messageCaptor.getValue();
        String body = sentMessage.getText();
        assertTrue(body.contains("Kritikalitet: \n"));
        assertTrue(body.contains("Aktiv: Nej"));
    }

    @Test
    void send_detailedModeWithAllCriticalityLevels() {
        when(emailProperties.autosend()).thenReturn(true);
        when(emailProperties.from()).thenReturn("sender@example.com");
        when(emailProperties.recipient()).thenReturn("recipient@example.com");

        for (Criticality criticality : Criticality.values()) {
            Notification notification = new Notification();
            notification.setAddress("Test Address");
            notification.setCause("Test Cause");
            notification.setRule("Test Rule");
            notification.setCriticality(criticality);
            notification.setTimeStamp("2025-12-11T10:30:00");

            channel.send(notification);

            ArgumentCaptor<SimpleMailMessage> messageCaptor = ArgumentCaptor.forClass(SimpleMailMessage.class);
            verify(mailSender, atLeastOnce()).send(messageCaptor.capture());

            SimpleMailMessage sentMessage = messageCaptor.getValue();
            String body = sentMessage.getText();
            assertTrue(body.contains("Kritikalitet: " + criticality.name()));
        }
    }
}

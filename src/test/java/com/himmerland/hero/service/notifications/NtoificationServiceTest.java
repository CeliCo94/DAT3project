package com.himmerland.hero.service.notifications;

import com.himmerland.hero.domain.notifications.Notification;
import com.himmerland.hero.service.helperclasses.enums.Criticality;
import com.himmerland.hero.service.repositories.NotificationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class NtoificationServiceTest {

    private NotificationRepository repository;
    private NotificationService service;

    @BeforeEach
    void setUp() {
        repository = mock(NotificationRepository.class);
        service = new NotificationService(repository);
    }
    
    @Test
    void findAll_delegatesToRepository() {
        List<Notification> expected = Collections.singletonList(sample(true, false));
        when(repository.findAll()).thenReturn(expected);

        List<Notification> result = service.findAll();

        assertSame(expected, result);
        verify(repository).findAll();
        verifyNoMoreInteractions(repository);
    }

    @Test
    void save_delegatesToRepository() {
        Notification notif = sample(true, false);
        when(repository.save(notif)).thenReturn(notif);

        Notification result = service.save(notif);

        assertSame(notif, result);
        ArgumentCaptor<Notification> captor = ArgumentCaptor.forClass(Notification.class);
        verify(repository).save(captor.capture());
        assertSame(notif, captor.getValue());
        verifyNoMoreInteractions(repository);
    }

    @Test
    void findCurrentNotification_returnsFirstActiveAndUnsent() {
        Notification inactive = sample(false, false);
        Notification sent = sample(true, true);
        Notification activeUnsent = sample(true, false);
        when(repository.findAll()).thenReturn(Arrays.asList(inactive, sent, activeUnsent));

        Notification result = service.findCurrentNotification();

        assertSame(activeUnsent, result);
    }

    @Test
    void findCurrentNotification_returnsNullWhenNoneMatch() {
        Notification inactive = sample(false, false);
        Notification sent = sample(true, true);
        when(repository.findAll()).thenReturn(Arrays.asList(inactive, sent));

        Notification result = service.findCurrentNotification();

        assertNull(result);
    }

    @Test
    void findAllActiveAndUnsent_returnsAllActiveAndUnsent() {
        Notification inactive = sample(false, false);
        Notification sent = sample(true, true);
        Notification activeUnsent1 = sample(true, false);
        Notification activeUnsent2 = sample(true, false);
        when(repository.findAll()).thenReturn(Arrays.asList(inactive, sent, activeUnsent1, activeUnsent2));

        List<Notification> result = service.findAllActiveAndUnsent();

        assertEquals(List.of(activeUnsent1, activeUnsent2), result);
    }
    private Notification sample(boolean active, boolean sent) {
        Notification n = new Notification("Dept", "Addr", "Cause", "Rule", Criticality.Low);
        n.setActive(active);
        n.setSent(sent);
        return n;
    }
}

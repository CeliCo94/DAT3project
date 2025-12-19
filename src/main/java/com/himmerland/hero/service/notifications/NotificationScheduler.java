package com.himmerland.hero.service.notifications;

import com.himmerland.hero.domain.notifications.Notification;
import com.himmerland.hero.config.EmailProperties;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@EnableScheduling
public class NotificationScheduler implements SchedulingConfigurer {

    private final NotificationService notificationService;
    private final NotificationNotifier notifier;
    private final EmailProperties props;

    public NotificationScheduler(
            NotificationService notificationService,
            NotificationNotifier notifier,
            EmailProperties props
    ) {
        this.notificationService = notificationService;
        this.notifier = notifier;
        this.props = props;
    }

    // Time Management for sending nofication
    @Override
    public void configureTasks(ScheduledTaskRegistrar registrar) {

        int hour = props.time();
        String cron = String.format("0 0 %d * * *", hour);

        registrar.addTriggerTask(
            this::sendDailyNotifications,
            triggerContext -> {
                CronTrigger trigger = new CronTrigger(
                    cron,
                    java.time.ZoneId.of("Europe/Copenhagen")
                );
                return trigger.nextExecution(triggerContext);
            }
        );
    }

    public void sendDailyNotifications() {

        if (!props.autosend()) {
            return; // autosend = false -> do nothing
        }

       List<Notification> unsent = notificationService.findAllActiveAndUnsent();
    
       if (unsent.isEmpty()) {
            return; // no active nofications -> do nothing
        }

        // NEED TO ADD: GROUP BY DEPARTMENT
        // NEED TO ADD: LOOP BY DEPARTMENT AND SEND NOFICATION THROUGH ALL CHANNELS
        // For each department, send each notification through all channels

        StringBuilder body = new StringBuilder();
        body.append("Daglige Notifikationer:\n\n");

        for (Notification n : unsent) {
        body.append("Adresse: ").append(n.getAddress()).append("\n");
        body.append("Regel: ").append(n.getRule()).append("\n");
        body.append("Grund: ").append(n.getCause()).append("\n");
        body.append("Kritighed: ").append(n.getCriticality()).append("\n");
        body.append("Tid: ").append(n.getTimeStamp()).append("\n");
        body.append("-------------------------------------\n\n");
        }

        Notification dailySummary = new Notification();
        dailySummary.setCause(body.toString());

        notifier.sendDailySummary(dailySummary); 

        unsent.forEach(n -> {
            n.setSent(true);
            n.setActive(false);
            notificationService.save(n);
            });

    }
}

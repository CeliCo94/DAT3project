package com.himmerland.hero;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.himmerland.hero.service.rules.*;
import com.himmerland.hero.service.notifications.*;

import static com.himmerland.hero.service.helperclasses.handlejson.WriteObjectToJson.writeObjectToJson;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);

		RuleThresholdHeat rule1 = new RuleThresholdHeat("Heat Rule 1", 60, 30, 50, 15);
		writeObjectToJson("src/main/resources/json/rules.json", rule1);

		Notification notification1 = new Notification("123 Main St", "High Temperature", "Heat Rule 1", 1, "2024-10-01T10:00:00Z", true, false);
		writeObjectToJson("src/main/resources/json/notifications.json", notification1);
	}
}
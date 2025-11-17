package com;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.test.context.TestPropertySource;

import com.himmerland.hero.Application;
import com.himmerland.hero.domain.rules.ruleThresholdHeat;

@SpringBootTest(
    classes = Application.class
    )
@TestPropertySource(properties = "spring.autoconfigure.exclude=org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration,org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration")
public class RuleTest {
    @Test
    public void testRule() {
        ruleThresholdHeat rule = new ruleThresholdHeat();
        //Assertions.assertEquals(0, rule.testRule());
    }
}
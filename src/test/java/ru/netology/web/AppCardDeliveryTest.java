package ru.netology.web;


import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;


import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Calendar;
import java.util.Date;

import static com.codeborne.selenide.Selenide.*;


public class AppCardDeliveryTest {


    String date;

    @BeforeEach
    public void setup() {
        Date dt = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(dt);
        c.add(Calendar.DATE, 4);
        dt = c.getTime();
        date = new SimpleDateFormat("dd.MM.yyyy").format(dt);
    }

    @Test
    void shouldTestWithInput() {

        open("http://localhost:9999");
        $("[data-test-id=city] input").setValue("Самара");
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.DELETE));
        $("[data-test-id=date] input").setValue(date);
        $("[data-test-id=name] input").setValue("Борисов Иван");
        $("[data-test-id=phone] input").setValue("+79012345678");
        $("[data-test-id=agreement]").click();
        $x("//button//span[contains(text(),'Забронировать')]").click();
        $x("//div[contains(text(),'Успешно!')]").shouldHave(Condition.visible, Duration.ofSeconds(15));

    }

    @Test
    void shouldTestWithSelect() {

        open("http://localhost:9999");
        $("[data-test-id=city] input").setValue("Са");
        $x("//span[contains(text(),'Самара')]").click();
        $("[data-test-id=date] input").click();
        $("td.calendar__day_state_current").click();
        $("[data-test-id=name] input").setValue("Борисов Иван");
        $("[data-test-id=phone] input").setValue("+79012345678");
        $("[data-test-id=agreement]").click();
        $x("//button//span[contains(text(),'Забронировать')]").click();
        $x("//div[contains(text(),'Успешно!')]").shouldHave(Condition.visible, Duration.ofSeconds(15));

    }

}

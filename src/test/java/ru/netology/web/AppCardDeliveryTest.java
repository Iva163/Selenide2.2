package ru.netology.web;


import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;


import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Condition.exactText;


public class AppCardDeliveryTest {

    public String generateDate(int days) {
        return LocalDate.now().plusDays(days).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }


    @Test
    void shouldTestWithInput() {
        String planningDate = generateDate(4);
        open("http://localhost:9999");
        $("[data-test-id=city] input").setValue("Самара");
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.DELETE));
        $("[data-test-id=date] input").setValue(planningDate);
        $("[data-test-id=name] input").setValue("Борисов Иван");
        $("[data-test-id=phone] input").setValue("+79012345678");
        $("[data-test-id=agreement]").click();
        $x("//button//span[contains(text(),'Забронировать')]").click();
        $x("//div[contains(text(),'Успешно!')]").shouldHave(Condition.visible, Duration.ofSeconds(15));
        $(".notification__content")
                .shouldHave(Condition.text("Встреча успешно забронирована на " + planningDate), Duration.ofSeconds(15))
                .shouldBe(Condition.visible);

    }

    @Test
    void shouldTestWithSelect() {
        String planningDate = generateDate(7);
        LocalDate planDate = LocalDate.now().plusDays(7);
        String day = String.valueOf(planDate.getDayOfMonth());
        LocalDate defaultDate = LocalDate.now().plusDays(3);

        open("http://localhost:9999");

        $("[data-test-id=city] input").setValue("Са");
        $x("//span[contains(text(),'Самара')]").click();

        $("[data-test-id=date] input").click();
        if (planDate.getMonthValue() > defaultDate.getMonthValue() | planDate.getYear() > defaultDate.getYear()) {
            $(".calendar__arrow_direction_right[data-step='1']").click();
        }
        $$("td.calendar__day").find(exactText(day)).click();

        $("[data-test-id=name] input").setValue("Борисов Иван");
        $("[data-test-id=phone] input").setValue("+79012345678");
        $("[data-test-id=agreement]").click();
        $x("//button//span[contains(text(),'Забронировать')]").click();

        $x("//div[contains(text(),'Успешно!')]").shouldHave(Condition.visible, Duration.ofSeconds(15));
        $(".notification__content")
                .shouldHave(Condition.text("Встреча успешно забронирована на " + planningDate), Duration.ofSeconds(15))
                .shouldBe(Condition.visible);

    }

}

package ru.netology.web.test;

import lombok.val;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;
import ru.netology.web.data.DataGenerator;
import java.time.Duration;
import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static java.time.Duration.ofSeconds;
public class CardDeliveryTest {

    @Test
    void shouldFillingApplication() {
        open("http://localhost:9999");
        val validUser = DataGenerator.FillingForm.generateCustomerData("ru");
        val firstMeet = DataGenerator.FillingForm.generateRandomNumber(4, 7);
        val oldMeetingDate = DataGenerator.FillingForm.generateMeetingDay(firstMeet);
        val secondMeet = DataGenerator.FillingForm.generateRandomNumber(firstMeet + 5, firstMeet + 14);
        val newMeetingDate = DataGenerator.FillingForm.generateMeetingDay(secondMeet);

        $("[data-test-id='city'] input").setValue(validUser.getCity());
        $("[placeholder='Дата встречи']").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[placeholder='Дата встречи']").setValue(oldMeetingDate);
        $("[data-test-id='name'] input").setValue(validUser.getName());
        $("[data-test-id='phone'] input").setValue(validUser.getPhone());
        $("[data-test-id=agreement] .checkbox__box").click();
        $(withText("Запланировать")).click();
        $("[data-test-id='success-notification']").shouldBe(visible, ofSeconds(15));
        $("[data-test-id='success-notification']>.notification__content")
                .shouldHave(text("Встреча успешно запланирована на " + (oldMeetingDate)));
        $("[placeholder='Дата встречи']").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[placeholder='Дата встречи']").setValue(newMeetingDate);
        $(withText("Запланировать")).click();
        $("[data-test-id='replan-notification']").shouldBe(visible);
        $("[data-test-id='replan-notification']>.notification__content").
                shouldHave(text("У вас уже запланирована встреча на другую дату. Перепланировать?"));
        $(withText("Перепланировать")).click();
        $("[data-test-id='success-notification']>.notification__content").shouldBe(visible)
                .shouldHave(exactText("Встреча успешно запланирована на " + (newMeetingDate)), Duration.ofSeconds(15));
    }
}

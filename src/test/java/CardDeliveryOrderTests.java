import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selectors;
import com.codeborne.selenide.Selenide;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static org.openqa.selenium.Keys.BACK_SPACE;

public class CardDeliveryOrderTests {

    @BeforeAll
    static void setUpAll() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    void setUp() {
        Selenide.open("http://localhost:9999/");

    }

    public String dataGenerate(int days) {
        return LocalDate.now().plusDays(days).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }

    @Test
    void fieldsAreFilledCorrectly() {
        $$("[placeholder='Город']").find(visible).setValue("Волгоград");
        //$("[data-test-id='date'] .input__control").click();
        $$("[data-test-id='date'] .input__control").find(visible).press(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $$("[data-test-id='date'] .input__control").find(visible).setValue(dataGenerate(3));
        $$("[data-test-id='name'] .input__control").find(visible).setValue("Иванов Иван");
        $$("[data-test-id='phone'] .input__control").find(visible).setValue("+79876543210");
        $$("[data-test-id='agreement']").find(visible).click();
        $$(".button__text").find(visible).click();
        $("div .notification__content").should(visible, Duration.ofSeconds(15));
        $("div .notification__content").shouldHave(exactText("Встреча успешно забронирована на " + dataGenerate(3)));

    }

}

package org.example.tests;

import io.qameta.allure.*;
import org.example.pages.FormFieldsPage;
import org.junit.jupiter.api.Test;

import static org.example.config.Config.FORM_FIELDS_URL;
import static org.junit.jupiter.api.Assertions.*;

@Epic("Автоматизация веб-форм")
@Feature("Взаимодействие с полями формы")
public class FormFieldsTest extends BaseTest {

    @Test
    @Story("Заполнение всех полей формы и отправка")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Тест проверяет успешное заполнение формы и появление алерта 'Message received!'")
    void testFillAndSubmitForm() {
        driver.get(FORM_FIELDS_URL);
        FormFieldsPage formPage = new FormFieldsPage(driver);

        takeScreenshot("Страница формы загружена");

        formPage.fillName("Иван Иванов")
                .fillPassword("securePassword123")
                .selectDrinks("Milk", "Coffee")
                .selectYellowColor()
                .selectAutomation("Yes")
                .fillEmail("test@example.com")
                .fillMessageWithToolsInfo()
                .clickSubmit();

        assertTrue(formPage.isAlertPresentWithText("Message received!"),
                "Алерт с текстом 'Message received!' не появился");

        takeScreenshot("Форма заполнена и отправлена");
    }
}
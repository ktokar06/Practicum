package org.example.tests;

import io.qameta.allure.*;
import org.example.pages.FormFieldsPage;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

@Epic("Автоматизация веб-форм")
@Feature("Взаимодействие с полями формы")
public class FormFieldsTest extends BaseTest {

    @Test
    @Story("Заполнение всех полей формы и отправка")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Тест проверяет успешное заполнение формы и появление алерта 'Message received!'")
    public void testFillAndSubmitForm() {
        FormFieldsPage formPage = new FormFieldsPage(driver);

        formPage
                .fillName("Test")
                .fillPassword("test123")
                .selectDrinks("Milk", "Coffee")
                .selectColor("Yellow")
                .selectAutomation("Yes")
                .fillEmail("test@example.com")
                .fillMessageWithToolsInfo()
                .clickSubmit();

        assertTrue(formPage.isAlertPresentWithText(),
                "Алерт с текстом не появился или текст не совпадает."
        );
    }
}
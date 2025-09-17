package org.example.pages;

import io.qameta.allure.Step;
import org.example.utils.WaitUtils;
import org.openqa.selenium.Alert;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

import java.util.ArrayList;
import java.util.List;

import static org.example.config.Config.EXPECTED_ALERT_TEXT;

/**
 * Класс страницы с полями формы для автоматизированного тестирования веб-интерфейса.
 */
public class FormFieldsPage extends HomePage {

    @FindBy(id = "name-input") private WebElement nameField;
    @FindBy(id = "email") private WebElement emailField;
    @FindBy(id = "message") private WebElement messageField;
    @FindBy(id = "submit-btn") private WebElement submitButton;
    @FindBy(xpath = "//input[@type='password']") private WebElement passwordField;
    @FindBy(xpath = "//*[@id=\"feedbackForm\"]/ul") private List<WebElement> automationToolsList;
    @FindBy(css = "#drink2") private WebElement milkCheckbox;
    @FindBy(css = "#drink3") private WebElement coffeeCheckbox;
    @FindBy(css = "#color3") private WebElement yellowRadioButton;
    @FindBy(css = "select[name='automation']") private WebElement automationDropdown;

    public FormFieldsPage(WebDriver driver) {
        super(driver);
    }

    /**
     * Заполняет поле имени заданным значением
     * @param name имя для ввода в поле
     * @return текущий экземпляр страницы FormFieldsPage
     */
    @Step("Заполнение поля имени значением: {name}")
    public FormFieldsPage fillName(String name) {
        WaitUtils.waitForElementVisible(driver, nameField, 10).sendKeys(name);
        return this;
    }

    /**
     * Заполняет поле пароля заданным значением
     * @param password пароль для ввода в поле
     * @return текущий экземпляр страницы FormFieldsPage
     */
    @Step("Заполнение поля пароля значением: {password}")
    public FormFieldsPage fillPassword(String password) {
        WaitUtils.waitForElementVisible(driver, passwordField, 10).sendKeys(password);
        return this;
    }

    /**
     * Выбирает указанные напитки из доступных чекбоксов
     * @param drinks массив названий напитков для выбора (например, "Milk", "Coffee")
     * @return текущий экземпляр страницы FormFieldsPage
     */
    @Step("Выбор напитков: {drinks}")
    public FormFieldsPage selectDrinks(String... drinks) {
        for (String drink : drinks) {
            if ("Milk".equalsIgnoreCase(drink)) {
                WaitUtils.waitForElementClickable(driver, milkCheckbox, 10).click();
            } else if ("Coffee".equalsIgnoreCase(drink)) {
                WaitUtils.waitForElementClickable(driver, coffeeCheckbox, 10).click();
            }
        }
        return this;
    }

    /**
     * Выбирает цвет из радио-кнопок
     * @param color название цвета для выбора (например, "Yellow")
     * @return текущий экземпляр страницы FormFieldsPage
     */
    @Step("Выбор цвета: {color}")
    public FormFieldsPage selectColor(String color) {
        if ("Yellow".equalsIgnoreCase(color)) {
            WaitUtils.waitForElementClickable(driver, yellowRadioButton, 10).click();
        }
        return this;
    }

    /**
     * Выбирает значение из выпадающего списка "Do you like automation?"
     * @param value значение для выбора: "Yes" или "No"
     * @return текущий экземпляр страницы FormFieldsPage
     */
    @Step("Выбор значения из списка 'Do you like automation?': {value}")
    public FormFieldsPage selectAutomation(String value) {
        WaitUtils.waitForElementVisible(driver, automationDropdown, 10);
        Select select = new Select(automationDropdown);
        select.selectByVisibleText(value);
        return this;
    }

    /**
     * Заполняет поле email заданным значением
     * @param email email для ввода в поле
     * @return текущий экземпляр страницы FormFieldsPage
     */
    @Step("Заполнение поля email значением: {email}")
    public FormFieldsPage fillEmail(String email) {
        WaitUtils.waitForElementVisible(driver, emailField, 10).sendKeys(email);
        return this;
    }

    /**
     * Заполняет поле сообщения информацией о количестве инструментов и самом длинном названии
     */
    @Step("Заполнение поля сообщения информацией о инструментах автоматизации")
    public FormFieldsPage fillMessageWithToolsInfo() {
        List<WebElement> visibleTools = WaitUtils.waitForAllElementsVisible(driver, automationToolsList, 10);

        List<String> tools = new ArrayList<>();
        for (WebElement element : visibleTools) {
            String text = element.getText().trim();
            if (!text.isEmpty()) {
                tools.add(text);
            }
        }

        int count = tools.size();

        String longestTool = "";
        if (!tools.isEmpty()) {
            longestTool = tools.get(0);
            for (String tool : tools) {
                if (tool.length() > longestTool.length()) {
                    longestTool = tool;
                }
            }
        }

        String messageText = String.format("Количество: %d, Самый длинный: %s", count, longestTool);

        WaitUtils.waitForElementVisible(driver, messageField, 10).sendKeys(messageText);
        return this;
    }

    /**
     * Нажимает кнопку отправки формы
     */
    @Step("Нажатие кнопки отправки формы")
    public void clickSubmit() {
        WebElement element = WaitUtils.waitForElementClickable(driver, submitButton, 10);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
    }

    /**
     * Проверяет наличие алерта с ожидаемым текстом
     *
     * @return true если алерт присутствует и содержит ожидаемый текст, false в противном случае
     */
    @Step("Проверка наличия алерта с текстом: Message received!")
    public boolean isAlertPresentWithText() {
        try {
            boolean isAlertPresent = WaitUtils.waitForAlertPresence(driver);
            if (isAlertPresent) {
                Alert alert = driver.switchTo().alert();
                String actualText = alert.getText();
                alert.accept();
                return EXPECTED_ALERT_TEXT.equals(actualText);
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }
}
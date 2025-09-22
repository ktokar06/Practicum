package org.example.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

/**
 * Базовый класс для всех страниц приложения.
 * Содержит WebDriver и WebDriverWait, а также инициализирует элементы страницы с помощью PageFactory.
 */
public class HomePage {

    protected WebDriver driver;

    /**
     * Конструктор базовой страницы.
     *
     * @param driver WebDriver, используемый для взаимодействия с браузером
     */
    public HomePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }
}
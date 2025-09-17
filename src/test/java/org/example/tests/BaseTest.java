package org.example.tests;

import io.qameta.allure.Allure;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.time.Duration;

import static org.example.config.Config.URL_SITE;

/**
 * Базовый класс для всех тестов, содержащий общие настройки и методы.
 */
public class BaseTest {

    protected WebDriver driver;

    /**
     * Метод, выполняемый перед каждым тестом.
     * Инициализирует ChromeDriver, максимизирует окно браузера и устанавливает неявные ожидания.
     */
    @BeforeEach
    public void setUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.get(URL_SITE);
    }

    /**
     * Метод, выполняемый после каждого теста.
     * Делает скриншот, добавляет его в Allure-отчет и закрывает браузер.
     */
    @AfterEach
    public void tearDown() {
        if (driver != null) {
            try {
                byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
                Allure.getLifecycle().addAttachment(
                        "Скриншот после теста",
                        "image/png",
                        "png",
                        screenshot
                );
            } catch (Exception e) {
                System.err.println("Не удалось сделать скриншот: " + e.getMessage());
            } finally {
                driver.quit();
            }
        }
    }
}
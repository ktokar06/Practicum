package org.example.tests;


import io.qameta.allure.Allure;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.time.Duration;

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
    }

    /**
     * Метод, выполняемый после каждого теста.
     * Закрывает браузер.
     */
    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }

    /**
     * Метод для создания скриншота с ожиданием
     */
    protected void takeScreenshot(String screenshotName) {
        if (driver != null) {
            try {
                Thread.sleep(1000);
                byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
                Allure.getLifecycle().addAttachment(
                        screenshotName,
                        "image/png",
                        "png",
                        screenshot
                );
            } catch (Exception e) {
                System.err.println("Не удалось сделать скриншот: " + e.getMessage());
            }
        }
    }
}
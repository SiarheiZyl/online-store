package com.online_market.selenium;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import static org.junit.Assert.assertEquals;

/**
 * Class for testing sign up process
 *
 * @author Siarhei
 * @version 1.0
 */
public class SignUpSeleniumTest {

    private WebDriver driver;

    @Before
    public void init() {

        System.setProperty("webdriver.chrome.driver", "C:\\chrome_driver\\chromedriver.exe");
        driver = new ChromeDriver();
    }

    @Test
    public void TestSignUpWithUniqueParameters() {

        driver.get(Url.LOGIN);
        driver.findElement(By.id("notReg")).click();
        assertEquals(Url.SIGN_UP, driver.getCurrentUrl());

        String firstName = "Mike";
        String lastName = "Brown";
        String email = "test@gmail.com";
        String login = "testt";
        String password = "1230";
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate localDate = LocalDate.now();

        driver.findElement(By.id("firstName")).sendKeys(firstName);
        driver.findElement(By.id("lastName")).sendKeys(lastName);
        driver.findElement(By.id("email")).sendKeys(email);
        driver.findElement(By.id("birthdate")).sendKeys(dtf.format(localDate));
        driver.findElement(By.id("username")).sendKeys(login);
        driver.findElement(By.id("password")).sendKeys(password);

        driver.findElement(By.id("register")).click();

        assertEquals(Url.USER_INFO+24, driver.getCurrentUrl());
    }

    @Test
    public void TestSignUpWithNotAvailableUsername() {

        driver.get(Url.SIGN_UP);

        String firstName = "Mike";
        String lastName = "Brown";
        String email = "test@gmail.com";
        String login = "szbee";
        String password = "1230";

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate localDate = LocalDate.now();
        String birthdate = "1999-01-01";

        driver.findElement(By.id("firstName")).sendKeys(firstName);
        driver.findElement(By.id("lastName")).sendKeys(lastName);
        driver.findElement(By.id("email")).sendKeys(email);
        driver.findElement(By.id("birthdate")).sendKeys(dtf.format(localDate));
        driver.findElement(By.id("username")).sendKeys(login);
        driver.findElement(By.id("password")).sendKeys(password);

        driver.findElement(By.id("register")).click();

        assertEquals(Url.SIGN_UP_ERROR, driver.getCurrentUrl());
    }

    @After
    public void destroy() {
        driver.close();
    }
}
package br.com.sysmap;


import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.List;

public class IniciandoProjetoTest {

    @Test
    public void iniciar() {

        System.setProperty("webdriver.chrome.driver", "C:\\Dev\\chromedriver_win32\\chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        driver.get("http://google.com");

    }
}

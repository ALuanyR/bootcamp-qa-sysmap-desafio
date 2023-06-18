package br.com.sysmap.driver;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class Browser extends DriverFactory {

    public static boolean elementoExiste(By element) {
        WebDriver driver = null;
        return driver.findElements(element).size() != 0;
    }

    public static void digitar(By element, String valor) {
        driver.findElement(element).sendKeys(valor);
    }

    public static void clicar(By element) {
        driver.findElement(element).click();
    }

    public static void aguardar(Integer Segundos) {
        try {
            Thread.sleep(Segundos * 1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public static String lerTexto(By element) {
        return driver.findElement(element).getText();
    }
}
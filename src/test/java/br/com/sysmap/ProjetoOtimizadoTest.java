package br.com.sysmap;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)

public class ProjetoOtimizadoTest {
    static String cidade = "Barueri";
    static String latitude = "-23.5112184";
    static String longitude = "-46.8764612";
    static String apiKey = "cf654284e62b3d8a90119029dae149b9";
    private static WebDriver driver;

    @BeforeAll
    static void inicio() {
        System.setProperty("webdriver.chrome.driver", "C:\\Dev\\chromedriver_win32\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.get("https://openweathermap.org/");
    }

    @AfterAll
    static void fim() {
        driver.quit();
    }

    @Test
    @DisplayName("Consultar nome de Cidade")
    @Order(1)
    void validarNomeDeCidade() throws InterruptedException {
        By searchBox = By.xpath("//input[@placeholder='Search city']");
        driver.findElement(searchBox).sendKeys("Barueri");
        By searchButton = By.xpath("//button[@type='submit' and @class='button-round dark']");
        Thread.sleep(4000);
        driver.findElement(searchButton).click();
        By searchCity = By.xpath("//span[contains(text(), 'Barueri, BR')]");
        Thread.sleep(4000);
        driver.findElement(searchCity).click();
        Thread.sleep(4000);
        By h2 = By.xpath("//h2[@data-v-3e6e9f12]");
        String cidadeEPais = driver.findElement(h2).getText();
        String[] nomeCidade = cidadeEPais.split(",");
        assertEquals("Barueri", nomeCidade[0]);
        System.out.printf("Validado que retornou o nome da cidade: %s solicitado\n", cidade);
    }


    @Test
    @DisplayName("Validar Temperatura Celsius")
    @Order(2)
    void validarTempCelsius() {
        By div = By.xpath("//div[@data-v-cccaf224 and contains(text(), 'Metric')]");
        driver.findElement(div).click();

        By searchCity = By.xpath("//span[@data-v-3e6e9f12 and contains(text(), '°C')]");
        String tempSiteSource = driver.findElement(searchCity).getText();

        By h2 = By.xpath("//h2[@data-v-3e6e9f12]");
        String tempSite = tempSiteSource.replaceAll("[^\\d]", "");

        try (CloseableHttpClient client = HttpClients.createDefault()) {
            HttpGet get = new HttpGet("https://api.openweathermap.org/data/2.5/weather?lat=" + latitude + "&lon=" + longitude + "&appid=" + apiKey + "&units=metric");
            HttpResponse response = client.execute(get);
            String resp = EntityUtils.toString(response.getEntity());
            JSONObject jsonResponse = new JSONObject(resp);

            JSONObject mainObject = jsonResponse.getJSONObject("main");
            double apiResultadoTemperatura = mainObject.getDouble("temp");
            long tempApi = Math.round(apiResultadoTemperatura);
            assertEquals(Long.toString(tempApi), tempSite);
            System.out.println("Temp. Api: " + apiResultadoTemperatura + " - Temp. Site: " + tempSite + " - Temp. Arredondada: " + tempApi + "ºC");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    @DisplayName("Validar Temperatura Fahrenheit")
    @Order(3)
    void validarTempFahrenheit() throws InterruptedException {
        By div = By.xpath("//div[@data-v-cccaf224 and contains(text(), 'Imperial')]");
        driver.findElement(div).click();

        Thread.sleep(4000);
        By searchCity = By.xpath("//span[@data-v-3e6e9f12 and contains(text(), '°F')]");
        Thread.sleep(4000);
        String tempSiteSource = driver.findElement(searchCity).getText();
        Thread.sleep(4000);

        String tempSite = tempSiteSource.replaceAll("[^\\d]", "");

        try (CloseableHttpClient client = HttpClients.createDefault()) {
            HttpGet get = new HttpGet("https://api.openweathermap.org/data/2.5/weather?lat=" + latitude + "&lon=" + longitude + "&appid=" + apiKey + "&units=imperial");
            HttpResponse response = client.execute(get);
            String resp = EntityUtils.toString(response.getEntity());
            JSONObject jsonResponse = new JSONObject(resp);

            JSONObject mainObject = jsonResponse.getJSONObject("main");
            double apiResultadoTemperatura = mainObject.getDouble("temp");
            long tempApi = Math.round(apiResultadoTemperatura);
            assertEquals(Long.toString(tempApi), tempSite);
            System.out.println("Temp. Api: " + apiResultadoTemperatura + " - Temp. Site: " + tempSite + " - Temp. Arredondada: " + tempApi + "ºF");

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }


    }


}

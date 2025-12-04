package tests;

import com.codeborne.selenide.Configuration;
import io.restassured.RestAssured;
import models.AuthResponse;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import spec.RestApiSpecs;
import utils.BrowserUtils;

import static com.codeborne.selenide.Selenide.closeWebDriver;
import static com.codeborne.selenide.Selenide.open;

public class TestBase {

    @BeforeAll
    static void setupEnvironment() {
        // Исправлено: https:// → https://
        Configuration.baseUrl = "https://demoqa.com";
        RestAssured.baseURI = "https://demoqa.com";
        Configuration.pageLoadStrategy = "eager";
        Configuration.remote = System.getProperty("remote", "https://user1:1234@selenoid.autotests.cloud/wd/hub");
        RestAssured.requestSpecification = RestApiSpecs.baseSpec();
    }

    @AfterEach
    void shutDown() {
        closeWebDriver();
    }

    // Новый метод: унифицированная настройка браузера с авторизацией
    protected void setupBrowserWithAuth(AuthResponse authResponse) {
        open("/favicon.ico");
        BrowserUtils.setCookiesFromAuthResponse(authResponse);
        open("/profile");
    }
}

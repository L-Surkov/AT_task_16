package utils;

import models.AuthResponse;
import org.openqa.selenium.Cookie;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;

public class BrowserUtils {
    public static void setCookiesFromAuthResponse(AuthResponse authResponse) {
        if (authResponse != null && authResponse.getUserId() != null) {
            getWebDriver().manage().addCookie(new Cookie("userID", authResponse.getUserId()));
            getWebDriver().manage().addCookie(new Cookie("expires", authResponse.getExpires()));
            getWebDriver().manage().addCookie(new Cookie("token", authResponse.getToken()));
        }
    }
}

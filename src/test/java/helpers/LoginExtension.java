package helpers;

import models.accountmodels.LoginResponseModel;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.platform.commons.support.AnnotationSupport;
import org.openqa.selenium.Cookie;
import pages.ProfilePage;

import static api.AuthApi.authorizeRequest;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;

public class LoginExtension implements BeforeEachCallback {

    @Override
    public void beforeEach(ExtensionContext context) throws Exception {
        WithLogin annotation = AnnotationSupport.findAnnotation(context.getRequiredTestMethod(),
                WithLogin.class
        ).orElse(AnnotationSupport.findAnnotation(
                        context.getRequiredTestClass(),
                        WithLogin.class
                ).orElse(null)
        );
        if (annotation != null) {
            LoginResponseModel authorizationResponse = authorizeRequest();
            ProfilePage page = new ProfilePage();
            page.openBrowserOnSimplePage();
            getWebDriver().manage().addCookie(new Cookie("token", authorizationResponse.getToken()));
            getWebDriver().manage().addCookie(new Cookie("expires", authorizationResponse.getExpires()));
            getWebDriver().manage().addCookie(new Cookie("userName", authorizationResponse.getUsername()));
            getWebDriver().manage().addCookie(new Cookie("userID", authorizationResponse.getUserId()));
        }
    }
}
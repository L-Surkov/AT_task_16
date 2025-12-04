package tests;

import models.AuthResponse;
import models.BookOperationRequest;
import models.LoginRequest;
import testData.TestData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import io.qameta.allure.Description;
import io.qameta.allure.Step;
import utils.BrowserUtils;
import spec.RestApiSpecs;

import static com.codeborne.selenide.Condition.not;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.*;
import static io.restassured.RestAssured.given;

public class BookApiTest extends TestBase {

    private AuthResponse authResponse;

    @BeforeEach
    @Step("Получение токена авторизации")
    void authenticateUser() {
        final var loginRequest = new LoginRequest(TestData.LOGIN, TestData.PASSWORD);
        authResponse = given(RestApiSpecs.baseSpec())
                .body(loginRequest)
                .when()
                .post("/Account/v1/Login")
                .then()
                .statusCode(200)
                .extract().as(AuthResponse.class);
    }

    @Test
    @Description("Проверка операций с книгой: добавление и удаление из спика")
    @DisplayName("Успешное добавление и удаление книги из списка в профиле")
    @Tag("ApiTests")
    void bookLifecycle_DeleteAddDelete_Test() {
        final String isbn = TestData.ISBN;
        final BookOperationRequest bookRequest = new BookOperationRequest(
                authResponse.getUserId(),
                isbn
        );

        removeBookIfExists(bookRequest);

        addBook(bookRequest);
        assertBookPresent();

        removeBook(bookRequest);
        assertBookAbsent();
    }

   @Step("Удаление книги (если она есть в списке)")
    private void removeBookIfExists(BookOperationRequest request) {
        try {
            given(RestApiSpecs.baseSpec())
                    .header("Authorization", "Bearer " + authResponse.getToken())
                    .body(request)
                    .when()
                    .delete("/BookStore/v1/Book")
                    .then()
                    .statusCode(204)
                    .log().ifValidationFails();
        } catch (Exception e) {
            System.out.println("Книга не найдена для удаления (возможно, уже удалена).");
        }
    }

    @Step("Добавление книги в коллекцию")
    private void addBook(BookOperationRequest request) {
        given(RestApiSpecs.baseSpec())
                .header("Authorization", "Bearer " + authResponse.getToken())
                .body(request)
                .when()
                .post("/BookStore/v1/Books")
                .then()
                .statusCode(201)
                .log().ifValidationFails();
    }

    @Step("Проверка наличия книги на странице профиля")
    private void assertBookPresent() {
        open("/favicon.ico");
        BrowserUtils.setCookiesFromAuthResponse(authResponse);
        open("/profile");
        $(".ReactTable").shouldHave(text(TestData.BOOK_TITLE));
    }

    @Step("Удаление книги из списка")
    private void removeBook(BookOperationRequest request) {  // Добавлен метод!
        given(RestApiSpecs.baseSpec())
                .header("Authorization", "Bearer " + authResponse.getToken())
                .body(request)
                .when()
                .delete("/BookStore/v1/Book")
                .then()
                .statusCode(204)
                .log().ifValidationFails();
    }

    @Step("Проверка отсутствия книги на странице профиля")
    private void assertBookAbsent() {
        open("/favicon.ico");
        BrowserUtils.setCookiesFromAuthResponse(authResponse);
        open("/profile");
        $(".ReactTable").should(not(text(TestData.BOOK_TITLE)));
    }
}

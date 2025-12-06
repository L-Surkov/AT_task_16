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
    @Description("Проверка операций с книгой: добавление и удаление из списка")
    @DisplayName("Успешное добавление и удаление книги из списка в профиле")
    @Tag("ApiTests")
    void bookLifecycle_DeleteAddDelete_Test() {
        final String isbn = TestData.ISBN;

        removeAllUserBooks();

        BookOperationRequest addRequest = new BookOperationRequest(
                authResponse.getUserId(),
                isbn
        );
        addBook(addRequest);

        assertBookPresent();

        BookOperationRequest deleteRequest = new BookOperationRequest(
                authResponse.getUserId(),
                isbn
        );
        removeBook(deleteRequest);

        assertBookAbsent();
    }

    @Step("Удаление всех книг пользователя")
    private void removeAllUserBooks() {
        given(RestApiSpecs.baseSpec())
                .header("Authorization", "Bearer " + authResponse.getToken())
                .queryParam("UserId", authResponse.getUserId())
                .when()
                .delete("/BookStore/v1/Books")
                .then()
                .statusCode(204)
                .log().ifValidationFails();
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

    @Step("Удаление конкретной книги")
    private void removeBook(BookOperationRequest request) {
        given(RestApiSpecs.baseSpec())
                .header("Authorization", "Bearer " + authResponse.getToken())
                .body(request)  // передаём модель напрямую!
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

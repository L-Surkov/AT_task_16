package tests;

import api.BookApi;
import helpers.WithLogin;
import io.qameta.allure.Description;
import org.junit.jupiter.api.*;
import pages.ProfilePage;

import static api.AuthApi.authorizeRequest;

public class BooksApiTests extends TestBase {
    ProfilePage profilePage = new ProfilePage();
    BookApi bookApi = new BookApi();

    @Test
    @Description("Проверка операций с книгой: предварительная очистка, " +
            "добавление и удаление из списка")
    @DisplayName("Успешное добавление и удаление книги из списка в профиле")
    @Tag("ApiTests")
    @WithLogin
    void bookLifecycle_DeleteAddDelete_Test() {
        bookApi.deleteAllBooks(authorizeRequest().getUserId());
        bookApi.addBooks();
        profilePage.openBrowserOnTheProfile()
                .assertSuccessLogin(authorizeRequest().getUsername())
                .assertThatCartIsNotEmpty();
        profilePage.deleteAllBooks()
                .confirmDelete()
                .assertThatCartIsEmpty();
    }
}
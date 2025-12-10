package pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import helpers.Attachments;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Selenide.*;

public class ProfilePage {
    SelenideElement userName = $("#userName-value"),
            noDataInProfile = $(".rt-noData"),
            deleteAllBooksButton = $$("#submit").findBy(Condition.text("Delete All Books")),
            confirmPopUpButton = $("#closeSmallModal-ok");

    @Step("Открыть браузер на странице профиля")
    public ProfilePage openBrowserOnTheProfile() {
        open("/profile");
        return this;
    }

    @Step("Открыть браузер для добавления cookies")
    public ProfilePage openBrowserOnSimplePage() {
        open("/images/Toolsqa.jpg");
        return this;
    }

    @Step("Проверить, что авторизация прошла успешно")
    public ProfilePage assertSuccessLogin(String userNameActual) {
        userName.shouldHave(Condition.text(userNameActual));
        Attachments.screenshotAs("Profile is opened");
        return this;
    }

    @Step("Обновить страницу")
    public ProfilePage refreshPage() {
        refresh();
        return this;
    }

    @Step("Проверить, что в профиле пустой список книг")
    public ProfilePage assertThatCartIsEmpty() {
        noDataInProfile.shouldHave(Condition.text("No rows found"));
        noDataInProfile.shouldBe(Condition.visible);
        Attachments.screenshotAs("Profile is empty");
        return this;
    }

    @Step("Проверить, что список книг не пустой на Web")
    public ProfilePage assertThatCartIsNotEmpty() {
        noDataInProfile.shouldNotBe(Condition.visible);
        Attachments.screenshotAs("Profile is not empty");
        return this;
    }

    @Step("Удалить все книги из списка")
    public ProfilePage deleteAllBooks() {
        deleteAllBooksButton
                .scrollTo()
                .click();
        return this;
    }

    @Step("Подтвердить операцию удаления")
    public ProfilePage confirmDelete() {
        confirmPopUpButton.click();
        Selenide.switchTo().alert().accept();
        return this;
    }
}
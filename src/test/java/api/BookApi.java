package api;

import io.qameta.allure.Step;
import models.booksmodels.*;

import static api.AuthApi.authorizeRequest;
import static io.restassured.RestAssured.given;
import static specs.Specifications.*;

public class BookApi {
    @Step("Delete all books from the cart")
    public void deleteAllBooks(String userId) {
        given(sendRequestWithoutBodySpec)
                .queryParams("UserId", userId)
                .when()
                .delete("/BookStore/v1/Books")
                .then()
                .spec(delete204ResponseSuccessSpec);
    }

    @Step("List all books in the catalog")
    public AllBooksModel listOfBooks() {
        return given(sendRequestWithoutBodySpec)
                .when()
                .get("/BookStore/v1/Books")
                .then()
                .spec(noCreate200ResponseSuccess)
                .extract().as(AllBooksModel.class);
    }

    @Step("Add books to the cart")
    public void addBooks() {
        AddBooksRequestModel addBooksRequest = new AddBooksRequestModel();
        addBooksRequest.setUserId(authorizeRequest().getUserId());
        IsbnModel book1 = new IsbnModel();
        IsbnModel book2 = new IsbnModel();
        book1.setIsbn(listOfBooks().getBooks()[0].getIsbn());
        book2.setIsbn(listOfBooks().getBooks()[2].getIsbn());
        addBooksRequest.setCollectionOfIsbns(new IsbnModel[]{book1, book2});
        given(sendRequestWithBodySpec)
                .body(addBooksRequest)
                .when()
                .post("/BookStore/v1/Books")
                .then()
                .spec(create201ResponseSuccess)
                .extract().as(AddBooksResponse.class);
    }

}
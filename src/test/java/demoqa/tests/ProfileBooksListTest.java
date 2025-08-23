package demoqa.tests;

import demoqa.api.AuthorizationApi;
import demoqa.api.BooksApi;
import demoqa.models.CollectionIsbnModel;
import demoqa.models.IsbnModel;
import demoqa.models.LoginRequestModel;
import demoqa.models.LoginResponseModel;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Cookie;

import java.util.ArrayList;
import java.util.List;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;
import static demoqa.tests.TestData.login;
import static demoqa.tests.TestData.password;
import static io.qameta.allure.Allure.step;

public class ProfileBooksListTest extends TestBase {
    AuthorizationApi authorization = new AuthorizationApi();
    BooksApi booksApi = new BooksApi();
    IsbnModel isbnModel = new IsbnModel();

    @Test
    @Tag("basket")
    void AddBookToProfileTest() {
        LoginResponseModel loginResponse = step("Authorization user", () ->
        authorization.login(new LoginRequestModel(login, password)));

        step("Сlear collection", () -> {
            booksApi.deleteAllBooks(loginResponse);
        });

        isbnModel.setIsbn("9781449325862");
        List<IsbnModel> isbnList = new ArrayList<>();

        step("Add book to profile", () -> {
            isbnList.add(isbnModel);
        });

        step("", () -> {
            CollectionIsbnModel booksList = new CollectionIsbnModel(loginResponse.getUserId(), isbnList);
            booksList.setUserId(loginResponse.getUserId());
            booksList.setCollectionOfIsbns(isbnList);
            booksApi.addBook(loginResponse, booksList);
        });

        step("Authorization user in UI via cookies", () -> {
            open("/favicon.ico");
            getWebDriver().manage().addCookie(new Cookie("userID", loginResponse.getUserId()));
            getWebDriver().manage().addCookie(new Cookie("expires", loginResponse.getExpires()));
            getWebDriver().manage().addCookie(new Cookie("token", loginResponse.getToken()));
        });

        step("Delete book in profile", () -> {
            open("/profile");
            $("#delete-record-undefined").click();
            $("#closeSmallModal-ok").click();
        });

        step("Check profile, should not have book", () -> {
            $(".ReactTable").shouldHave(text("No rows found"));
        });
    }
}
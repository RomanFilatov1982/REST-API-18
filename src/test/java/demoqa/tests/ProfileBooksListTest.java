package demoqa.tests;

import demoqa.api.AuthorizationApi;
import demoqa.api.BooksApi;
import demoqa.models.CollectionIsbnModel;
import demoqa.models.IsbnModel;
import demoqa.models.LoginRequestModel;
import demoqa.models.LoginResponseModel;
import demoqa.pages.AuthUtils;
import demoqa.pages.ProfilePage;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static demoqa.tests.TestData.login;
import static demoqa.tests.TestData.password;
import static io.qameta.allure.Allure.step;

public class ProfileBooksListTest extends TestBase {
    AuthorizationApi authorizationApi = new AuthorizationApi();
    AuthUtils authUtils = new AuthUtils();
    LoginRequestModel userData = new LoginRequestModel(login, password);
    BooksApi booksApi = new BooksApi();
    IsbnModel isbnModel = new IsbnModel();
    ProfilePage profilePage = new ProfilePage();

    @Test
    @Tag("basket")
    void AddBookToProfileTest() {
        LoginResponseModel loginResponse = step("Authorization user", () ->
                authorizationApi.login(userData));

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
            authUtils.authByCookies();
        });

        step("Delete book in profile", () -> {
            profilePage.deleteBook();
        });

        step("Check profile, should not have book", () -> {
            profilePage.checkPage();
        });
    }
}
package demoqa.tests;

import demoqa.api.AuthorizationApi;
import demoqa.api.BooksApi;
import demoqa.models.CollectionIsbnModel;
import demoqa.models.IsbnModel;
import demoqa.models.LoginRequestModel;
import demoqa.models.LoginResponseModel;
import demoqa.Utils.AuthUtils;
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

    ProfilePage profilePage = new ProfilePage();

    @Test
    @Tag("basket")
    void deleteBookFromProfileTest() {
        IsbnModel isbnModel = new IsbnModel();
        isbnModel.setIsbn("9781449325862");
        List<IsbnModel> isbnList = new ArrayList<>();
        isbnList.add(isbnModel);

        LoginResponseModel loginResponse = authorizationApi.login(userData);

        booksApi.deleteAllBooks(loginResponse);

        step("Add book to profile", () -> {
            CollectionIsbnModel booksList = new CollectionIsbnModel(loginResponse.getUserId(), isbnList);
            booksList.setUserId(loginResponse.getUserId());
            booksList.setCollectionOfIsbns(isbnList);
            booksApi.addBook(loginResponse, booksList);
        });

        step("Open/profile with authorized user", () -> {
            authUtils.authByCookies();
            profilePage.openPage();

        });
        profilePage.deleteBook();
        profilePage.checkNoRowsFound();
    }
}
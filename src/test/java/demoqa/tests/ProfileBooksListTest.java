package demoqa.tests;

import demoqa.api.AuthorizationApi;
import demoqa.api.BooksApi;
import demoqa.models.AddBooksListModel;
import demoqa.models.IsbnModel;
import demoqa.models.LoginRequestModel;
import demoqa.models.LoginResponseModel;
import io.restassured.response.Response;
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
import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static java.lang.String.format;

public class ProfileBooksListTest extends TestBase {
    @Test
    void successfulLoginWithUiTest() {

        open("/login");
        $("#userName").setValue(login);
        $("#password").setValue(password).pressEnter();
        $("#userName-value").shouldHave(text(login));
    }

    /* @Test
     void deleteBookFromProfileTest() {
         String authData = "{\"userName\":\"" + login + "\",\"password\":\"" + password + "\"}";

         LoginResponseModel authResponse = given()
                 .log().uri()
                 .log().method()
                 .log().body()
                 .contentType(JSON)
                 .body(authData)
                 .when()
                 .post("/Account/v1/Login")
                 .then()
                 .log().status()
                 .log().body()
                 .statusCode(200)
                 .extract().as(LoginResponseModel.class);

         given()
                 .log().uri()
                 .log().method()
                 .log().body()
                 .contentType(JSON)
                 .header("Authorization", "Bearer " + authResponse.path("token"))
                 .queryParams("UserId", authResponse.path("userId"))
                 .when()
                 .delete("/BookStore/v1/Books")
                 .then()
                 .log().status()
                 .log().body()
                 .statusCode(204);

         String isbn = "9781449365035";
         String bookData = format("{\"userId\":\"%s\",\"collectionOfIsbns\":[{\"isbn\":\"%s\"}]}",
                 authResponse.path("userId"), isbn);

         given()
                 .log().uri()
                 .log().method()
                 .log().body()
                 .contentType(JSON)
                 .header("Authorization", "Bearer " + authResponse.path("token"))
                 .body(bookData)
                 .when()
                 .post("/BookStore/v1/Books")
                 .then()
                 .log().status()
                 .log().body()
                 .statusCode(201);

         open("/favicon.ico");
         getWebDriver().manage().addCookie(new Cookie("userID", authResponse.path("userId")));
         getWebDriver().manage().addCookie(new Cookie("expires", authResponse.path("expires")));
         getWebDriver().manage().addCookie(new Cookie("token", authResponse.path("token")));

         open("/profile");
         $("#delete-record-undefined").click();
         $("#closeSmallModal-ok").click();
         $(".ReactTable").shouldHave(text("No rows found"));
     }
 */
    @Test
    void AddBookToProfileTest() {
        AuthorizationApi authorization = new AuthorizationApi();
        LoginResponseModel loginResponse = authorization.login(new LoginRequestModel(login, password));

        BooksApi booksApi = new BooksApi();
        booksApi.deleteAllBooks(loginResponse);

        IsbnModel isbnModel = new IsbnModel();
        isbnModel.setIsbn("9781449325862");
        List<IsbnModel> isbnList = new ArrayList<>();
        isbnList.add(isbnModel);

        AddBooksListModel booksList = new AddBooksListModel(loginResponse.getUserId(), isbnList);
        booksList.setUserId(loginResponse.getUserId());
        booksList.setCollectionOfIsbns(isbnList);
        booksApi.addBook(loginResponse, booksList);

        open("/favicon.ico");
        getWebDriver().manage().addCookie(new Cookie("userID", loginResponse.getUserId()));
        getWebDriver().manage().addCookie(new Cookie("expires", loginResponse.getExpires()));
        getWebDriver().manage().addCookie(new Cookie("token", loginResponse.getToken()));

        open("/profile");
        $("#delete-record-undefined").click();
        $("#closeSmallModal-ok").click();
        $(".ReactTable").shouldHave(text("No rows found"));
    }

}
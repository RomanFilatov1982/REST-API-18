package demoqa.api;

import demoqa.models.CollectionIsbnModel;
import demoqa.models.LoginResponseModel;

import static demoqa.specs.RequestSpec.requestSpec;
import static demoqa.specs.ResponseSpec.responseSpec;
import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;

public class BooksApi {
    public void deleteAllBooks(LoginResponseModel loginResponse) {
        given(requestSpec)
                .contentType(JSON)
                .header("Authorization", "Bearer " + loginResponse.getToken())
                .queryParams("UserId", loginResponse.getUserId())
                .when()
                .delete("/BookStore/v1/Books")
                .then()
                .spec(responseSpec(204));
    }

    public void addBook(LoginResponseModel loginResponse, CollectionIsbnModel booksList) {
        given(requestSpec)
                .contentType(JSON)
                .header("Authorization", "Bearer " + loginResponse.getToken())
                .body(booksList)
                .when()
                .post("/BookStore/v1/Books")
                .then()
                .spec(responseSpec(201));
    }
}
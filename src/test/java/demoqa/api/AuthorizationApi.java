package demoqa.api;

import demoqa.models.LoginRequestModel;
import demoqa.models.LoginResponseModel;
import io.qameta.allure.Step;

import static demoqa.specs.BaseSpecs.requestSpec;
import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;

public class AuthorizationApi {

    @Step("Authorization as user")
    public LoginResponseModel login(LoginRequestModel loginRequest) {

        return given(requestSpec)
                .contentType(JSON)
                .body(loginRequest)
                .when()
                .post("/Account/v1/Login")
                .then()
                .statusCode(200)
                .extract().as(LoginResponseModel.class);
    }
}
package demoqa.specs;

import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.with;

public class RequestSpec {
    public static RequestSpecification requestSpec = with()
            .log().uri()
            .log().method()
            .log().body();
}

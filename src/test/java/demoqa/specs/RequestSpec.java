package demoqa.specs;

import io.restassured.specification.RequestSpecification;

import static demoqa.helpers.CustomAllureListener.withCustomTemplates;
import static io.restassured.RestAssured.with;

public class RequestSpec {
    public static RequestSpecification requestSpec = with()
            .filter(withCustomTemplates())
            .log().uri()
            .log().method()
            .log().body();
}

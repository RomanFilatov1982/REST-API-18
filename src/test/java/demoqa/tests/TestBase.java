package demoqa.tests;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.logevents.SelenideLogger;
import demoqa.api.AuthorizationApi;
import demoqa.api.BooksApi;
import demoqa.helpers.Attach;
import demoqa.models.LoginRequestModel;
import demoqa.models.LoginResponseModel;
import demoqa.utils.AuthUtils;
import io.qameta.allure.selenide.AllureSelenide;
import io.restassured.RestAssured;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.util.Map;

import static com.codeborne.selenide.Selenide.closeWebDriver;
import static demoqa.tests.TestData.login;
import static demoqa.tests.TestData.password;

public class TestBase {

    public static final AuthorizationApi AUTHORIZATION_API = new AuthorizationApi();
    public static final BooksApi BOOKS_API = new BooksApi();
    public static LoginRequestModel userData;
    public static LoginResponseModel userInfo;

    @BeforeAll
    static void setup() {
        selenideConfiguration();
        userData = new LoginRequestModel(login, password);
        userInfo = AUTHORIZATION_API.login(userData);
    }

    @BeforeEach
    void addListener() {
        SelenideLogger.addListener("AllureSelenide", new AllureSelenide());
    }

    @AfterEach
    void shutDown() {
        Attach.screenshotAs("Last screenshot");
        Attach.pageSource();
        Attach.browserConsoleLogs();
        Attach.addVideo();
        closeWebDriver();
        closeWebDriver();
    }

    static void selenideConfiguration() {
         /*Configuration.baseUrl = System.getProperty("baseUrl", "https://demoqa.com");
        RestAssured.baseURI = System.getProperty("baseUrl", "https://demoqa.com");
        Configuration.browser = System.getProperty("browser", "chrome");
        Configuration.browserVersion = System.getProperty("browserVersion", "138.0");
        Configuration.browserSize = System.getProperty("browserSize", "1920x1080");
        Configuration.remote = System.getProperty("remoteUrl");*/


        Configuration.pageLoadTimeout = 60000;
        Configuration.baseUrl = "https://demoqa.com";
        RestAssured.baseURI = "https://demoqa.com";
        Configuration.browserSize = "1920x1080";
        Configuration.pageLoadStrategy = "eager";
        Configuration.holdBrowserOpen = true;
        //Configuration.remote = "https://user1:1234@selenoid.autotests.cloud/wd/hub";

        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("selenoid:options", Map.<String, Object>of(
                "enableVNC", true,
                "enableVideo", true
        ));
        Configuration.browserCapabilities = capabilities;
    }

    public static AuthUtils getAuthUtils() {
        return new AuthUtils();
    }
}
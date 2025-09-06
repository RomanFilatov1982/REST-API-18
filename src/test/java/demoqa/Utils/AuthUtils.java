package demoqa.Utils;

import demoqa.models.LoginResponseModel;
import io.qameta.allure.Step;
import org.openqa.selenium.Cookie;

import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;

public class AuthUtils {
    LoginResponseModel loginResponse = new LoginResponseModel();

    @Step("Authorization user in UI via cookies")
    public void authByCookies() {
        open("/favicon.ico");
        getWebDriver().manage().addCookie(new Cookie("userID", loginResponse.getUserId()));
        getWebDriver().manage().addCookie(new Cookie("expires", loginResponse.getExpires()));
        getWebDriver().manage().addCookie(new Cookie("token", loginResponse.getToken()));
    }

}

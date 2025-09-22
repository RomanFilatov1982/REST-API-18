package demoqa.pages;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;


public class ProfilePage {
    private final SelenideElement DELETE_ICON = $("#delete-record-undefined"),
            CLOSE_FORM = $("#closeSmallModal-ok"),
            CHECK = $(".ReactTable");

    @Step("Open/Profile")
    public ProfilePage openPage() {
        open("/profile");
        return this;
    }

    @Step("Delete book in profile")
    public ProfilePage deleteBook() {
        DELETE_ICON.click();
        CLOSE_FORM.click();
        return this;
    }

    @Step("Check profile, should not have book")
    public ProfilePage checkNoRowsFound() {
        CHECK.shouldHave(text("No rows found"));
        return this;
    }
}
package demoqa.pages;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;


public class ProfilePage {
    private final SelenideElement deleteIcon = $("#delete-record-undefined"),
            closeForm = $("#closeSmallModal-ok"),
            check = $(".ReactTable");

    @Step("Open/Profile")
    public ProfilePage openPage() {
        open("/profile");
        return this;
    }

    @Step("Delete book in profile")
    public ProfilePage deleteBook() {
        open("/profile");
        deleteIcon.click();
        closeForm.click();
        return this;
    }

    @Step("Check profile, should not have book")
    public ProfilePage checkNoRowsFound() {
        check.shouldHave(text("No rows found"));
        return this;
    }
}
package demoqa.pages;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;


public class ProfilePage {
    private final SelenideElement deleteIcon = $("#delete-record-undefined"),
            closeForm = $("#closeSmallModal-ok"),
            check = $(".ReactTable");

    public ProfilePage deleteBook() {
        open("/profile");
        deleteIcon.click();
        closeForm.click();
        return this;
    }

    public ProfilePage checkPage() {
        check.shouldHave(text("No rows found"));
        return this;
    }
}
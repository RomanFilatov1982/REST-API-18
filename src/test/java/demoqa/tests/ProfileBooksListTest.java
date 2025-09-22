package demoqa.tests;

import demoqa.models.CollectionIsbnModel;
import demoqa.models.IsbnModel;
import demoqa.pages.ProfilePage;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.qameta.allure.Allure.step;

public class ProfileBooksListTest extends TestBase {

    ProfilePage profilePage = new ProfilePage();

    @Test
    @Tag("basket")
    void deleteBookFromProfileTest() {

        List<IsbnModel> isbnList = TestData.getIsbnModelList("9781449325862");

        BOOKS_API.deleteAllBooks(userInfo);

        step("Add book to profile", () -> {
            BOOKS_API.addBook(userInfo, new CollectionIsbnModel(userInfo.getUserId(), isbnList));
        });

        step("Open/profile with authorized user", () -> {
            getAuthUtils().authByCookies(userInfo);
            profilePage.openPage();
        });
        profilePage.deleteBook();
        profilePage.checkNoRowsFound();
    }
}
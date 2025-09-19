package demoqa.tests;

import com.codeborne.selenide.Configuration;
import demoqa.models.IsbnModel;

import java.util.ArrayList;
import java.util.List;

public class TestData {
    public static final String login = System.getProperty("login");
    public static final String password = System.getProperty("password");

    /*public static String login = "test123456",
            password = "Test123456@";*/
    public static List<IsbnModel> getIsbnModelList(String isbnCode) {
        List<IsbnModel> isbnList = new ArrayList<>();
        IsbnModel isbnModel = new IsbnModel();
        isbnModel.setIsbn(isbnCode);

        isbnList.add(isbnModel);
        return isbnList;
    }

}
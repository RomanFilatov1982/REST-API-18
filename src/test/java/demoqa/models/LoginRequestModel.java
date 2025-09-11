package demoqa.models;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LoginRequestModel {
    private String login;
    private String password;


}

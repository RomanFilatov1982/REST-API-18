package demoqa.models;

import lombok.Data;

@Data
public class TokenResponseModel {
    String token;
    String expires;
    String created_date;
    String isActive;
}

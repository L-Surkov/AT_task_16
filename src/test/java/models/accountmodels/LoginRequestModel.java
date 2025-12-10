package models.accountmodels;

import lombok.Data;

@Data
public class LoginRequestModel {
    String userName, password;
}
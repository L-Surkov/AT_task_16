package api;

import lombok.EqualsAndHashCode;
import models.accountmodels.LoginRequestModel;
import models.accountmodels.LoginResponseModel;
import tests.TestBase;

import static io.restassured.RestAssured.given;
import static specs.Specifications.sendSimpleRequest;


@EqualsAndHashCode(callSuper = true)
public class AuthApi extends TestBase {
    public static LoginResponseModel authorizeRequest() {
        LoginRequestModel loginRequest = new LoginRequestModel();
        loginRequest.setUserName("MarkTester");
        loginRequest.setPassword("h7N2QREv97BMSM!");

        return given(sendSimpleRequest)
                .body(loginRequest)
                .when()
                .post("/Account/v1/Login")
                .then()
                .extract().as(LoginResponseModel.class);

    }
}
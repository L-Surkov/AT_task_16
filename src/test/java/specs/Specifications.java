package specs;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static api.AuthApi.authorizeRequest;
import static helpers.CustomAllureListener.withCustomTemplates;
import static io.restassured.RestAssured.with;

public class Specifications {

    public static RequestSpecification sendSimpleRequest = with()
            .contentType(ContentType.JSON);
    public static RequestSpecification sendRequestWithBodySpec = new RequestSpecBuilder()
            .addFilter(withCustomTemplates())
            .setContentType(ContentType.JSON)
            .addHeader("Authorization", "Bearer " + authorizeRequest().getToken())
            .log(LogDetail.METHOD)
            .log(LogDetail.URI)
            .log(LogDetail.BODY)
            .build();

    public static RequestSpecification sendRequestWithoutBodySpec = new RequestSpecBuilder()
            .addFilter(withCustomTemplates())
            .addHeader("Authorization", "Bearer " + authorizeRequest().getToken())
            .log(LogDetail.METHOD)
            .log(LogDetail.URI)
            .build();

    public static ResponseSpecification delete204ResponseSuccessSpec = new ResponseSpecBuilder()
            .expectStatusCode(204)
            .log(LogDetail.STATUS)
            .build();
    public static ResponseSpecification create201ResponseSuccess = new ResponseSpecBuilder()
            .expectStatusCode(201)
            .log(LogDetail.STATUS)
            .log(LogDetail.BODY)
            .build();

    public static ResponseSpecification noCreate200ResponseSuccess = new ResponseSpecBuilder()
            .expectStatusCode(200)
            .log(LogDetail.STATUS)
            .log(LogDetail.BODY)
            .build();
}
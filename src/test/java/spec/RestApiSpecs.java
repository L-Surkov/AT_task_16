package spec;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import static helpers.CustomAllureListener.withCustomTemplates;

public class RestApiSpecs {
    public static RequestSpecification baseSpec() {
        RequestSpecBuilder builder = new RequestSpecBuilder()
                .setContentType(ContentType.JSON)
                .setAccept(ContentType.JSON)
                .log(LogDetail.URI)
                .log(LogDetail.HEADERS)
                .log(LogDetail.BODY);

        return builder.build().filter(withCustomTemplates());
    }
}

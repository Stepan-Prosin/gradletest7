import com.github.javafaker.Faker;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import lombok.Value;
import org.junit.jupiter.api.Test;

import java.util.Locale;

import static io.restassured.RestAssured.given;

class DataGenerator {
    private static final RequestSpecification requestSpec = new RequestSpecBuilder()
            .setBaseUri("http://localhost")
            .setPort(9999)
            .setAccept(ContentType.JSON)
            .setContentType(ContentType.JSON)
            .log(LogDetail.ALL)
            .build();

    private static final Faker FAKER = new Faker(new Locale("en"));

    private DataGenerator() {
    }

    public static RegistrationDto sendRequest(RegistrationDto user) {
                given()
                .spec(requestSpec)
                .body(user)
                .when()
                .post("/api/system/users")
                .then()
                .statusCode(200);
                return user;
    }

    public static String generateName() {
        return  FAKER.name().username();

    }
    public static String generatepassword() {
        return  FAKER.internet().password();

    }
    public static  class Registration{

        private Registration() {
        }
        public static  RegistrationDto getUser(String status){
            return  new RegistrationDto(generateName(),generatepassword(),status);
        }
        public static  RegistrationDto getRegistredUser(String status){
            return sendRequest(getUser(status)) ;
        }

    }
    @Value
    public static class RegistrationDto{
        String login;
        String password;
        String status;
    }
}
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class BookStoreTests {
    @BeforeAll
    static void setUp() {
        RestAssured.baseURI = "https://demoqa.com";
    }

    @Test
    void noLogsTest() {
        // curl -X GET "https://demoqa.com/BookStore/v1/Books" -H "accept: application/json"
        get("/BookStore/v1/Books")
                .then()
                .body("books", hasSize(greaterThan(0)));
    }

    @Test
    void withAllLogsTest() {
        given()
                .log().all()
//                .log().uri()
//                .log().body()
                .get("/BookStore/v1/Books")
                .then()
                .log().all()
//                .log().body()
                .body("books", hasSize(greaterThan(0)));
    }

}

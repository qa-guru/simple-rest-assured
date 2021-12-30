package tests;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static filters.CustomLogFilter.customLogFilter;
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

    @Test
    void authorizeTest() {
        String data = "{\"userName\": \"alex\", \"password\": \"asdsad#frew_DFS2\"}";

        given()
                .contentType("application/json")
                .body(data)
                .when()
                .log().uri()
                .log().body()
                .post("/Account/v1/GenerateToken")
                .then()
                .log().body()
                .body("status", is("Success"))
                .body("result", is("User authorized successfully."));
    }

    @Test
    void authorizeWithAllureTest() {
        String data = "{\"userName\": \"alex\", \"password\": \"asdsad#frew_DFS2\"}";

//        RestAssured.filters(new AllureRestAssured());
        given()
                .filter(new AllureRestAssured())
                .contentType("application/json")
                .body(data)
                .when()
                .log().uri()
                .log().body()
                .post("/Account/v1/GenerateToken")
                .then()
                .log().body()
                .body("status", is("Success"))
                .body("result", is("User authorized successfully."));
    }

    @Test
    void authorizeWithTemplateTest() {
        String data = "{\"userName\": \"alex\", \"password\": \"asdsad#frew_DFS2\"}";

        given()
                .filter(customLogFilter().withCustomTemplates())
                .contentType("application/json")
                .body(data)
                .when()
                .log().uri()
                .log().body()
                .post("/Account/v1/GenerateToken")
                .then()
                .log().body()
                .body("status", is("Success"))
                .body("result", is("User authorized successfully."));
    }
}

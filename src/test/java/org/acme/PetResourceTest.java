package org.acme;

import com.example.petstore.models.PetType;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.CoreMatchers.is;


@QuarkusTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PetResourceTest {

	@Test
    @Order(1)
    public void testPetsRetrievalEndpointSuccess() {
        given()
          .when().get("/api/pets")
          .then()
                .assertThat()
                .statusCode(200)
                .body("size()", notNullValue());
    }

    @Test
    @Order(2)
    public void testPetsAddEndpointSuccess() {
        given()
                .header("Content-Type", "application/json")
                .body("{\r\n    \"petName\": \"Dane\",\r\n    \"petAge\": 4,\r\n    \"petTypeId\": 1\r\n}")
                .when().post("/api/pets/add")
                .then()
                    .assertThat()
                    .statusCode(200)
                    .body("petId", notNullValue())
                    .body("petAge", equalTo(4))
                    .body("petName", equalTo("Dane"))
                    .body("petType", notNullValue());
    }

    @Test
    @Order(3)
    public void testPetsUpdateEndpointSuccess() {
        given()
                .header("Content-Type", "application/json")
                .body("{\r\n    \"petName\": \"Woofy\"  \r\n}")
                .when().put("/api/pets/update/4")
                .then()
                    .assertThat()
                    .statusCode(200)
                    .body("petId", notNullValue())
                    .body("petName", equalTo("Woofy"))
                    .body("petType", notNullValue());
    }

    @Test
    @Order(4)
    public void testPetsUpdateEndpointFailure() {
        given()
                .header("Content-Type", "application/json")
                .body("{\r\n    \"petAge\": \"abc\"  \r\n}")
                .when().put("/api/pets/update/4")
                .then()
                    .assertThat()
                    .statusCode(400);
    }

    @Test
    @Order(5)
    public void testPetsDeleteEndpointSuccess() {
        given()
                .header("Content-Type", "application/json")
                .when().delete("/api/pets/delete/4")
                .then()
                    .assertThat()
                    .statusCode(200);
    }

    @Test
    @Order(6)
    public void testPetsDeleteEndpointFailure() {
        given()
                .header("Content-Type", "application/json")
                .when().delete("/api/pets/delete/4")
                .then()
                    .assertThat()
                    .statusCode(404);
    }

}
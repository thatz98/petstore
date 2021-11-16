package org.acme;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;


@QuarkusTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PetTypeResourceTest {

	@Test
    @Order(1)
    public void testPetTypesRetrievalEndpointSuccess() {
        given()
          .when().get("/api/petTypes")
          .then()
                .assertThat()
                .statusCode(200)
                .body("size()", notNullValue());
    }

    @Test
    @Order(2)
    public void testPetTypesAddEndpointSuccess() {
        given()
                .header("Content-Type", "application/json")
                .body("{\r\n    \"petTypeName\": \"Reptile\"    \r\n}")
                .when().post("/api/petTypes/add")
                .then()
                    .assertThat()
                    .statusCode(200)
                    .body("petTypeId", notNullValue())
                    .body("petTypeName", equalTo("Reptile"));
    }

    @Test
    @Order(3)
    public void testPetTypesUpdateEndpointSuccess() {
        given()
                .header("Content-Type", "application/json")
                .body("{\r\n    \"petTypeName\": \"Mammoth\"  \r\n}")
                .when().put("/api/petTypes/update/5")
                .then()
                    .assertThat()
                    .statusCode(200)
                    .body("petTypeName", equalTo("Mammoth"))
                    .body("petTypeId", notNullValue());
    }

    @Test
    @Order(4)
    public void testPetTypesUpdateEndpointFailure() {
        given()
                .header("Content-Type", "application/json")
                .body("{\r\n    \"petTypeName\": \"Mammoth\"  \r\n}")
                .when().put("/api/petTypes/update/1234")
                .then()
                    .assertThat()
                    .statusCode(404);
    }

    @Test
    @Order(5)
    public void testPetTypesDeleteEndpointSuccess() {
        given()
                .header("Content-Type", "application/json")
                .when().delete("/api/petTypes/delete/5")
                .then()
                    .assertThat()
                    .statusCode(200);
    }

    @Test
    @Order(6)
    public void testPetTypesDeleteEndpointFailure() {
        given()
                .header("Content-Type", "application/json")
                .when().delete("/api/petTypes/delete/5")
                .then()
                    .assertThat()
                    .statusCode(404);
    }

}
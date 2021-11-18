package org.acme;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.*;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.notNullValue;


@QuarkusTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PetResourceTest {

	@Test
    @Order(1)
    @DisplayName("Test the successful retrieval of pets - should return 200 with all the pets in the datastore")
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
    @DisplayName("Test the successful adding new pets - should return 200 and the newly created pet")
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
    @DisplayName("Test the successful getting pet by ID - should return 200 and the pet that matches with the ID")
    public void testPetsGetByIdEndpointSuccess() {
        given()
                .header("Content-Type", "application/json")
                .when().get("/api/pets/1")
                .then()
                .assertThat()
                .statusCode(200)
                .body("petId", notNullValue())
                .body("petAge", notNullValue())
                .body("petName", notNullValue())
                .body("petType", notNullValue());
    }

    @Test
    @Order(4)
    @DisplayName("Test the failure getting pet by ID which is invalid and not available - should return 404")
    public void testPetsGetByIdEndpointFailure() {
        given()
                .header("Content-Type", "application/json")
                .when().get("/api/pets/-24")
                .then()
                .assertThat()
                .statusCode(404);
    }

    @Test
    @Order(5)
    @DisplayName("Test the successful searching pet by name - should return 200 and the pet that matches with the name")
    public void testPetsSearchByNameEndpointSuccess() {
        given()
                .header("Content-Type", "application/json")
                .when().get("/api/pets/searchByName/Scooby")
                .then()
                .assertThat()
                .statusCode(200)
                .body("petId", notNullValue())
                .body("petAge", notNullValue())
                .body("petName", equalTo("Scooby"))
                .body("petType", notNullValue());
    }

    @Test
    @Order(6)
    @DisplayName("Test the failure getting pet by name which is not available in the datastore - should return 404")
    public void testPetsSearchByNameEndpointFailure() {
        given()
                .header("Content-Type", "application/json")
                .when().get("/api/pets/searchByName/Random")
                .then()
                .assertThat()
                .statusCode(404);
    }

    @Test
    @Order(7)
    @DisplayName("Test the successful searching pet by age - should return 200 and the pet that matches with the age")
    public void testPetsSearchByAgeEndpointSuccess() {
        given()
                .header("Content-Type", "application/json")
                .when().get("/api/pets/searchByAge/2")
                .then()
                .assertThat()
                .statusCode(200)
                .body("petId", notNullValue())
                .body("petAge", equalTo(2))
                .body("petName", notNullValue())
                .body("petType", notNullValue());
    }

    @Test
    @Order(8)
    @DisplayName("Test the failure getting pet by age which is not available in the datastore - should return 404")
    public void testPetsSearchByAgeEndpointFailure() {
        given()
                .header("Content-Type", "application/json")
                .when().get("/api/pets/searchByAge/-12")
                .then()
                .assertThat()
                .statusCode(404);
    }

    @Test
    @Order(9)
    @DisplayName("Test the successful update pet - should return 200 and the updated pet")
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
    @Order(10)
    @DisplayName("Test the failure update pet with invalid data type - should return 400")
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
    @Order(11)
    @DisplayName("Test the successful delete pet - should return 200")
    public void testPetsDeleteEndpointSuccess() {
        given()
                .header("Content-Type", "application/json")
                .when().delete("/api/pets/delete/4")
                .then()
                    .assertThat()
                    .statusCode(200);
    }

    @Test
    @Order(12)
    @DisplayName("Test the failure delete pet with non existent ID - should return 404")
    public void testPetsDeleteEndpointFailure() {
        given()
                .header("Content-Type", "application/json")
                .when().delete("/api/pets/delete/4")
                .then()
                    .assertThat()
                    .statusCode(404);
    }

}
package org.acme;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.*;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;


@QuarkusTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PetTypeResourceTest {

	@Test
    @Order(1)
    @DisplayName("Test the successful retrieval of pet types - should return 200 with all the pet type in the datastore")
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
    @DisplayName("Test the successful adding new pet type - should return 200 and the newly created pet type")
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
    @DisplayName("Test the successful searching pet type by name - should return 200 and the pet type that matches with the name")
    public void testPetTypesSearchByNameEndpointSuccess() {
        given()
                .header("Content-Type", "application/json")
                .when().get("/api/petTypes/searchByName/Dog")
                .then()
                .assertThat()
                .statusCode(200)
                .body("petTypeId", notNullValue())
                .body("petTypeName", equalTo("Dog"));
    }

    @Test
    @Order(4)
    @DisplayName("Test the failure getting pet type by name which is not available in the datastore - should return 404")
    public void testPetTypesSearchByNameEndpointFailure() {
        given()
                .header("Content-Type", "application/json")
                .when().get("/api/petTypes/Scooby")
                .then()
                .assertThat()
                .statusCode(404);
    }

    @Test
    @Order(5)
    @DisplayName("Test the successful getting pet type by ID - should return 200 and the pet type that matches with the ID")
    public void testPetTypesGetByIdEndpointSuccess() {
        given()
                .header("Content-Type", "application/json")
                .when().get("/api/petTypes/1")
                .then()
                .assertThat()
                .statusCode(200)
                .body("petTypeId", notNullValue())
                .body("petTypeName", notNullValue());
    }

    @Test
    @Order(6)
    @DisplayName("Test the failure getting pet type by ID which is invalid and not available - should return 404")
    public void testPetTypesGetByIdEndpointFailure() {
        given()
                .header("Content-Type", "application/json")
                .when().get("/api/petTypes/-20")
                .then()
                .assertThat()
                .statusCode(404);
    }

    @Test
    @Order(7)
    @DisplayName("Test the successful update pet type - should return 200 and the updated pet type")
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
    @Order(8)
    @DisplayName("Test the failure update pet type with invalid data type - should return 400")
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
    @Order(9)
    @DisplayName("Test the successful delete pet type - should return 200")
    public void testPetTypesDeleteEndpointSuccess() {
        given()
                .header("Content-Type", "application/json")
                .when().delete("/api/petTypes/delete/5")
                .then()
                    .assertThat()
                    .statusCode(200);
    }

    @Test
    @Order(10)
    @DisplayName("Test the failure delete pet type with non existent ID - should return 404")
    public void testPetTypesDeleteEndpointFailure() {
        given()
                .header("Content-Type", "application/json")
                .when().delete("/api/petTypes/delete/5")
                .then()
                    .assertThat()
                    .statusCode(404);
    }

}
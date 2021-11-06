package com.example.petstore.api;

import java.util.List;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.example.petstore.datastore.PetTypeList;
import com.example.petstore.models.Pet;
import com.example.petstore.datastore.PetList;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;

@Path("/api/pets")
@Produces("application/json")
public class PetResource {

	private PetList petList = PetList.getInstance();
	private PetTypeList petTypeList = PetTypeList.getInstance();

	@APIResponses(value = {
			@APIResponse(responseCode = "200", description = "All Pets", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(ref = "Pet"))) })
	@GET
	public Response getPets() {
		List<Pet> pets = petList.getPetList();

		return Response.ok(pets).build();
	}

	@APIResponses(value = {
			@APIResponse(responseCode = "200", description = "Pet for id", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(ref = "Pet"))),
			@APIResponse(responseCode = "404", description = "No Pet found for the id.") })
	@GET
	@Path("{petId}")
	public Response getPet(@PathParam("petId") Integer petId) {

		Pet pet = petList.getPetById(petId);

		if(pet == null){
			return Response.status(Status.NOT_FOUND).build();
		}

		return Response.ok(pet).build();
		
	}

	@APIResponses(value = {
			@APIResponse(responseCode = "200", description = "Newly added pet", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(ref = "Pet"))),
			@APIResponse(responseCode = "404", description = "No Pet found for the id.") })
	@POST
	@Path("add")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response addPet(Pet newPet) {
		newPet.setPetType(petTypeList.getPetTypeById(newPet.getPetTypeId()));
		newPet.setPetTypeId(null);
		Pet addedPet = petList.addPet(newPet);
		return Response.ok(addedPet).build();
	}

	@APIResponses(value = {
			@APIResponse(responseCode = "200", description = "Updated pet", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(ref = "Pet"))),
			@APIResponse(responseCode = "404", description = "No Pet found for the id.") })
	@PUT
	@Path("update/{petId}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updatePet(Pet toBeUpdated, @PathParam("petId") Integer petId) {

		if(!petList.petExists(petId)){
			return Response.status(Status.NOT_FOUND).build();
		}

		Pet updatedPet = petList.updatePet(toBeUpdated, petId);

		return Response.ok(updatedPet).build();
	}

	@APIResponses(value = {
			@APIResponse(responseCode = "200", description = "Deleted pet", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(ref = "Pet"))),
			@APIResponse(responseCode = "404", description = "No Pet found for the id.") })
	@DELETE
	@Path("delete/{petId}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response deletePet(@PathParam("petId") Integer petId) {

		if(!petList.petExists(petId)){
			return Response.status(Status.NOT_FOUND).build();
		}

		boolean result = petList.deletePet(petId);

		return Response.ok(result).build();
	}
}

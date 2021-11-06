package com.example.petstore.api;

import java.util.List;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.example.petstore.datastore.PetTypeList;
import com.example.petstore.models.PetType;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;

@Path("/api/petTypes")
@Produces("application/json")
public class PetTypeResource {

    private PetTypeList petTypesList = PetTypeList.getInstance();

    @APIResponses(value = {
            @APIResponse(responseCode = "200", description = "All Pet Types", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(ref = "Pet"))) })
    @GET
    public Response getPetTypes() {
        List<PetType> petTypes = petTypesList.getPetTypesList();

        return Response.ok(petTypes).build();
    }

    @APIResponses(value = {
            @APIResponse(responseCode = "200", description = "Pet Type for id", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(ref = "Pet"))),
            @APIResponse(responseCode = "404", description = "No Pet Type found for the id.") })
    @GET
    @Path("{petTypeId}")
    public Response getPetType(@PathParam("petTypeId") Integer petTypeId) {

        PetType petType = petTypesList.getPetTypeById(petTypeId);

        if(petType == null){
            return Response.status(Status.NOT_FOUND).build();
        }

        return Response.ok(petType).build();

    }

    @APIResponses(value = {
            @APIResponse(responseCode = "200", description = "Newly added pet", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(ref = "Pet"))),
            @APIResponse(responseCode = "404", description = "No Pet found for the id.") })
    @POST
    @Path("add")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addPetType(PetType newPetType) {
        PetType addedPetType = petTypesList.addPetType(newPetType);
        return Response.ok(addedPetType).build();
    }

    @APIResponses(value = {
            @APIResponse(responseCode = "200", description = "Updated pet type", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(ref = "Pet"))),
            @APIResponse(responseCode = "404", description = "No Pet Type found for the id.") })
    @PUT
    @Path("update/{petTypeId}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updatePetType(PetType toBeUpdated, @PathParam("petTypeId") Integer petTypeId) {

        if(!petTypesList.petTypeExists(petTypeId)){
            return Response.status(Status.NOT_FOUND).build();
        }

        PetType updatedPetType = petTypesList.updatePetType(toBeUpdated, petTypeId);

        return Response.ok(updatedPetType).build();
    }

    @APIResponses(value = {
            @APIResponse(responseCode = "200", description = "Deleted pet type", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(ref = "Pet"))),
            @APIResponse(responseCode = "404", description = "No Pet Type found for the id.") })
    @DELETE
    @Path("delete/{petTypeId}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response deletePetType(@PathParam("petTypeId") Integer petTypeId) {

        if(!petTypesList.petTypeExists(petTypeId)){
            return Response.status(Status.NOT_FOUND).build();
        }

        boolean result = petTypesList.deletePetType(petTypeId);

        return Response.ok(result).build();
    }
}

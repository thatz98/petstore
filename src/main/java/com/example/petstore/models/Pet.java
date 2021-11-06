package com.example.petstore.models;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

import com.fasterxml.jackson.annotation.JsonProperty;

@Schema(name = "Pet")
public class Pet {

	@Schema(required = true, description = "Pet id")
	@JsonProperty("pet_id")
	private Integer petId;

	@Schema(description = "Pet type")
	@JsonProperty("pet_type")
	private PetType petType;

	@Schema(description = "Temp pet type ID")
	@JsonProperty("pet_type_id")
	private Integer petTypeId;

	@Schema(required = true, description = "Pet name")
	@JsonProperty("pet_name")
	private String petName;

	@Schema(required = true, description = "Pet age")
	@JsonProperty("pet_age")
	private Integer petAge;

	public Pet() {
	}

	public Pet(Integer petId, PetType petType, String petName, Integer petAge) {
		this.petId = petId;
		this.petType = petType;
		this.petName = petName;
		this.petAge = petAge;
	}

	public Integer getPetId() {
		return petId;
	}

	public void setPetId(Integer petId) {
		this.petId = petId;
	}

	public PetType getPetType() {
		return petType;
	}

	public void setPetType(PetType petType) {
		this.petType = petType;
	}

	public Integer getPetTypeId() {
		return petTypeId;
	}

	public void setPetTypeId(Integer petTypeId) {
		this.petTypeId = petTypeId;
	}

	public String getPetName() {
		return petName;
	}

	public void setPetName(String petName) {
		this.petName = petName;
	}

	public Integer getPetAge() {
		return petAge;
	}

	public void setPetAge(Integer petAge) {
		this.petAge = petAge;
	}

}

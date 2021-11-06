package com.example.petstore.datastore;

import com.example.petstore.models.PetType;

import java.util.ArrayList;
import java.util.List;

public class PetTypeList {

    private List<PetType> petTypesList = new ArrayList<PetType>();

    private static PetTypeList ourInstance = new PetTypeList();

    private  PetTypeList() {
        this.addPetType(new PetType(1, "Dog"));
        this.addPetType(new PetType(2, "Cat"));
        this.addPetType(new PetType(3, "Bird"));
        this.addPetType(new PetType(3, "Fish"));
    }

    public static PetTypeList getInstance() {
        return ourInstance;
    }

    public PetType addPetType(PetType newPetType) {
        Integer newId = petTypesList.size() + 1;
        newPetType.setPetTypeId(newId);
        petTypesList.add(newPetType);
        return newPetType;
    }

    public List<PetType> getPetTypesList() {
        return petTypesList;
    }

    public PetType getPetTypeById(Integer id) {
        for (PetType petType : petTypesList) {
            if (petType.getPetTypeId().equals(id)) {
                return petType;
            }
        }

        return null;
    }

    public boolean petTypeExists(Integer id) {
        for (PetType petType : petTypesList) {
            if (petType.getPetTypeId().equals(id)) {
                return true;
            }
        }
        return false;
    }

    public PetType updatePetType(PetType toBeUpdated, Integer petId) {
        for (PetType pet : petTypesList) {
            if (pet.getPetTypeId().equals(petId)) {
                if(toBeUpdated.getPetTypeName() != null){
                    pet.setPetTypeName(toBeUpdated.getPetTypeName());
                }
                return pet;
            }
        }

        return null;
    }

    public boolean deletePetType(Integer id) {
        for (PetType petType : petTypesList) {
            if (petType.getPetTypeId().equals(id)) {
                petTypesList.remove(petType);
                return true;
            }
        }

        return false;
    }

}


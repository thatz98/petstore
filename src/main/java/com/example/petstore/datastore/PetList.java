package com.example.petstore.datastore;

import com.example.petstore.models.Pet;

import java.util.ArrayList;
import java.util.List;

public class PetList {

    private List<Pet> petsList = new ArrayList<Pet>();

    private static PetList ourInstance = new PetList();

    public static PetList getInstance() {
        return ourInstance;
    }

    public Pet addPet(Pet newPet) {
        Integer newId = petsList.size() + 1;
        newPet.setPetId(newId);
        petsList.add(newPet);
        return newPet;
    }

    public List<Pet> getPetList() {
        return petsList;
    }

    public Pet getPetById(Integer id) {
        for (Pet pet : petsList) {
            if (pet.getPetId().equals(id)) {
                return pet;
            }
        }

        return null;
    }

    public boolean petExists(Integer id) {
        for (Pet pet : petsList) {
            if (pet.getPetId().equals(id)) {
                return true;
            }
        }
        return false;
    }

    public Pet updatePet(Pet toBeUpdated, Integer petId) {
        for (Pet pet : petsList) {
            if (pet.getPetId().equals(petId)) {
                if(toBeUpdated.getPetAge() != null){
                    pet.setPetAge(toBeUpdated.getPetAge());
                }
                if(toBeUpdated.getPetName() != null){
                    pet.setPetName(toBeUpdated.getPetName());
                }
                if(toBeUpdated.getPetType() != null){
                    pet.setPetType(toBeUpdated.getPetType());
                }
                return pet;
            }
        }

        return null;
    }

    public boolean deletePet(Integer id) {
        for (Pet pet : petsList) {
            if (pet.getPetId().equals(id)) {
                petsList.remove(pet);
                return true;
            }
        }

        return false;
    }

}

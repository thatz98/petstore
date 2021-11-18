package com.example.petstore.datastore;

import com.example.petstore.models.Pet;
import com.example.petstore.models.PetType;

import java.util.ArrayList;
import java.util.List;

public class PetList {

    private List<Pet> petsList = new ArrayList<Pet>();

    private static PetList ourInstance = new PetList();

    private  PetList() {
        this.addPet(new Pet(1, new PetType(1, "Dog"), "Scooby", 7));
        this.addPet(new Pet(2, new PetType(2, "Cat"), "Kitty", 4));
        this.addPet(new Pet(3, new PetType(3, "Bird"), "Peththappu", 2));
        this.addPet(new Pet(4, new PetType(4, "Ksusma"), "Moli", 1));
    }

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

    public Pet getPetByName(String name) {
        for (Pet pet : petsList) {
            if (pet.getPetName().equals(name)) {
                return pet;
            }
        }

        return null;
    }

    public Pet getPetByAge(Integer age) {
        for (Pet pet : petsList) {
            if (pet.getPetAge().equals(age)) {
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

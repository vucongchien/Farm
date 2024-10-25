package io.github.Farm.animal;

import io.github.Farm.animal.Buffalo.BuffaloManager;
import io.github.Farm.animal.Chicken.ChickenManager;
import io.github.Farm.animal.Pig.PigManager;

import java.util.ArrayList;
import java.util.List;

public class PetManager {
    private ArrayList<Pet> pet;
    private static PetManager petManager;

    private PetManager() {
        pet = new ArrayList<>();
    }

    public static PetManager getPetmanager(){
        if(petManager ==null){
            petManager =new PetManager();
        }
        return petManager;
    }

    public void update(BuffaloManager buffaloManager, ChickenManager chickenManager, PigManager pigManager){
        if(pet!=null) {
            pet.clear();
        }
        pet.addAll(buffaloManager.getBuffaloManager());
        pet.addAll(chickenManager.getChickenManager());
        pet.addAll(pigManager.getPigManager());
    }

    public List<Pet> getPetManager(){
        return pet;
    }
}

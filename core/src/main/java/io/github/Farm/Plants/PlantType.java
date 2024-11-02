package io.github.Farm.Plants;

public enum PlantType {
    potato,
    carrot,
    pumpkin,
    wheat,
    radish,
    sunflower,
    cauliflower,
    cabbage,
    beetroot,
    kale,
    parsnip;
    public static String Seed(PlantType plantType){
        String path="Crops/"+ plantType.toString()+"_00.png";
        return path;
    }
    public static String Sprout(PlantType plantType){
        String path="Crops/"+ plantType.toString()+"_01.png";
        return path;
    }
    public static String Young(PlantType plantType){
        String path="Crops/"+ plantType.toString()+"_02.png";
        return path;
    }
    public static String Mature(PlantType plantType){
        String path="Crops/"+ plantType.toString()+"_03.png";
        return path;
    }
    public static String Harvested(PlantType plantType){
        String path="Crops/"+ plantType.toString()+"_04.png";
        return path;
    }
    public static String item(PlantType plantType){
        String path="Crops/"+ plantType.toString()+"_05.png";
        return path;
    }
}

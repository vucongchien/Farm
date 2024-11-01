package io.github.Farm.Materials;

public enum MaterialType {
    mushroom("mushroom"),
    tree("wood"),
    tree_02("wood"),
    rock("rock")
    ;
    private final String typeMaterial;
    MaterialType(String typeMaterial){
        this.typeMaterial=typeMaterial;
    }

    public String getTypeMaterial() {
        return typeMaterial;
    }
}

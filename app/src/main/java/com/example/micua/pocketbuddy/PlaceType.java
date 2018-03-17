package com.example.micua.pocketbuddy;

public class PlaceType {
    private String t = "amusement_park aquarium art_gallery casino hindu_temple mosque museum park " +
            "zoo";
    private String[] touristAttractions;

    public PlaceType() {
        touristAttractions = t.split(" ");
    }

    public String[] getTouristAttractions() {
        return touristAttractions;
    }
}

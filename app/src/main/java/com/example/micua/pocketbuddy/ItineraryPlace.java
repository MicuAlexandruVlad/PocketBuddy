package com.example.micua.pocketbuddy;

public class ItineraryPlace {
    private String description;
    private String title;
    private String rating;
    private String pricing;
    private String placeID;
    private String latitude;
    private String longitude;

    public ItineraryPlace(String description, String title, String rating, String pricing, String imgUrl, String placeID, String latitude, String longitude) {
        this.description = description;
        this.title = title;
        this.rating = rating;
        this.pricing = pricing;
        this.imgUrl = imgUrl;
        this.placeID = placeID;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getPlaceID() {
        return placeID;
    }

    public void setPlaceID(String placeID) {
        this.placeID = placeID;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    private String imgUrl;


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getPricing() {
        return pricing;
    }

    public void setPricing(String pricing) {
        this.pricing = pricing;
    }
}

package com.example.micua.pocketbuddy;

public class ItineraryPlace {
    private String description;
    private String title;
    private String rating;
    private String pricing;

    public ItineraryPlace(String description, String title, String rating, String pricing, String imgUrl) {
        this.description = description;
        this.title = title;
        this.rating = rating;
        this.pricing = pricing;
        this.imgUrl = imgUrl;
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

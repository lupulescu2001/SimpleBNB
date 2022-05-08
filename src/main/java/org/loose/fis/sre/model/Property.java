package org.loose.fis.sre.model;

import org.dizitart.no2.objects.Id;

import java.util.Objects;

public class Property {
    @Id
    private String cityName;
    private String description;
    private String username;
    private String name;
    private int sum_of_reviews;
    private int nr_of_reviews;

    public Property(String cityName, String description, String username, String name) {
        this.cityName = cityName;
        this.description = description;
        this.username = username;
        this.name = name;
        this.sum_of_reviews=0;
        this.nr_of_reviews=0;
    }
    public Property() {
    }
    public float getReview(){
        return (float)this.sum_of_reviews/this.nr_of_reviews;
    }
    public void setSum_of_reviews(int newSum){
        this.sum_of_reviews=newSum;
    }
    public void setNr_of_reviews(int newNr){
        this.nr_of_reviews=newNr;
    }
    public int getSum_of_reviews(){
        return this.sum_of_reviews;
    }

    public int getNr_of_reviews() {
        return this.nr_of_reviews;
    }

    public String getUsername() {
        return username;
    }

    public String getCityName() {
        return cityName;
    }

    public String getDescription() {
        return description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Property property = (Property) o;
        return Objects.equals(cityName, property.cityName) && Objects.equals(description, property.description) && Objects.equals(username, property.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cityName, description, username);
    }

    @Override
    public String toString() {
        return "Property{" +
                "cityName='" + cityName + '\'' +
                ", description='" + description + '\'' +
                ", username='" + username + '\'' +
                '}';
    }
}

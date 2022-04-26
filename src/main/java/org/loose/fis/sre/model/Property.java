package org.loose.fis.sre.model;

import org.dizitart.no2.objects.Id;

import java.util.Objects;

public class Property {
    @Id
    private String cityName;
    private String description;
    private String username;
    private String name;

    public Property(String cityName, String description, String username, String name) {
        this.cityName = cityName;
        this.description = description;
        this.username = username;
        this.name = name;
    }
    public Property() {
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

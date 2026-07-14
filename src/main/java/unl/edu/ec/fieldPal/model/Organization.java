package com.fieldPal.model;

import com.fieldPal.model.enums.Zone;
import java.io.Serializable;

public class Organization implements Serializable {
    private static final long serialVersionUID = 1L;

    private String id;
    private String name;
    private Zone zone;
    private String address;
    private String phone;
    private String image;
    private double rating;
    private String description;
    private int courtCount;
    private double latitude;
    private double longitude;

    public Organization() {}

    public Organization(String id, String name, Zone zone, String address, String phone,
                        String image, double rating, String description, int courtCount,
                        double latitude, double longitude) {
        this.id = id;
        this.name = name;
        this.zone = zone;
        this.address = address;
        this.phone = phone;
        this.image = image;
        this.rating = rating;
        this.description = description;
        this.courtCount = courtCount;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public Zone getZone() { return zone; }
    public void setZone(Zone zone) { this.zone = zone; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getImage() { return image; }
    public void setImage(String image) { this.image = image; }

    public double getRating() { return rating; }
    public void setRating(double rating) { this.rating = rating; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public int getCourtCount() { return courtCount; }
    public void setCourtCount(int courtCount) { this.courtCount = courtCount; }

    public double getLatitude() { return latitude; }
    public void setLatitude(double latitude) { this.latitude = latitude; }

    public double getLongitude() { return longitude; }
    public void setLongitude(double longitude) { this.longitude = longitude; }
}


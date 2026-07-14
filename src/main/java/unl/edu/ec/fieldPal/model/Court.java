package com.fieldPal.model;

import com.fieldPal.model.enums.CourtType;
import java.io.Serializable;

public class Court implements Serializable {
    private static final long serialVersionUID = 1L;

    private String id;
    private String orgId;
    private String name;
    private CourtType type;
    private double pricePerHour;
    private boolean hasLighting;
    private boolean isCovered;
    private String surface;
    private String imageUrl;

    public Court() {}

    public Court(String id, String orgId, String name, CourtType type, double pricePerHour,
                 boolean hasLighting, boolean isCovered, String surface, String imageUrl) {
        this.id = id;
        this.orgId = orgId;
        this.name = name;
        this.type = type;
        this.pricePerHour = pricePerHour;
        this.hasLighting = hasLighting;
        this.isCovered = isCovered;
        this.surface = surface;
        this.imageUrl = imageUrl;
    }

    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getOrgId() { return orgId; }
    public void setOrgId(String orgId) { this.orgId = orgId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public CourtType getType() { return type; }
    public void setType(CourtType type) { this.type = type; }

    public double getPricePerHour() { return pricePerHour; }
    public void setPricePerHour(double pricePerHour) { this.pricePerHour = pricePerHour; }

    public boolean isHasLighting() { return hasLighting; }
    public void setHasLighting(boolean hasLighting) { this.hasLighting = hasLighting; }

    public boolean isCovered() { return isCovered; }
    public void setCovered(boolean covered) { isCovered = covered; }

    public String getSurface() { return surface; }
    public void setSurface(String surface) { this.surface = surface; }

    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
}


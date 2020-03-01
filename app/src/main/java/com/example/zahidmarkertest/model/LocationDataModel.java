package com.example.zahidmarkertest.model;

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.clustering.ClusterItem;

public class LocationDataModel implements ClusterItem {

    private String image;
    private String id;
    private String name;
    private String latitude;
    private String longitude;
    private String addressOne;
    private String addressTwo;
    private String state;
    private String country;
    private String userLocation;
    private String companyType;
    private String category;
    private boolean newJoined;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getAddressOne() {
        return addressOne;
    }

    public void setAddressOne(String addressOne) {
        this.addressOne = addressOne;
    }

    public String getAddressTwo() {
        return addressTwo;
    }

    public void setAddressTwo(String addressTwo) {
        this.addressTwo = addressTwo;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getUserLocation() {
        return userLocation;
    }

    public void setUserLocation(String userLocation) {
        this.userLocation = userLocation;
    }

    public String getCompanyType() {
        return companyType;
    }

    public void setCompanyType(String companyType) {
        this.companyType = companyType;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public boolean isNewJoined() {
        return newJoined;
    }

    public void setNewJoined(boolean newJoined) {
        this.newJoined = newJoined;
    }

    private Double getLat() {

        if (getLatitude() != null && !getLatitude().isEmpty()) {
            try {
                double d = Double.parseDouble(getLatitude());
                return d;

            } catch (NumberFormatException e) {
                return null;
            }
        }
        return null;
    }

    private Double getLongi() {
        if (getLongitude() != null && !getLongitude().isEmpty()) {
            try {
                double d = Double.parseDouble(getLongitude());
                return d;
            } catch (NumberFormatException e) {
                return null;
            }
        }
        return null;

    }

    public LatLng getLatLng() {
        LatLng latLng = null;
        if (getLat() != null && getLongi() != null) {
            latLng = new LatLng(getLat(), getLongi());
        }
        return latLng;

    }

    @Override
    public LatLng getPosition() {
        return getLatLng();
    }

    @Override
    public String getTitle() {
        return getName();
    }

    @Override
    public String getSnippet() {
        return getAddressOne();
    }

}

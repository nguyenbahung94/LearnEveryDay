package com.ekakashi.greenhouse.map;

/**
 * Created by nbhung on 12/28/2017.
 */

public class PlaceObject {
    private String placeName;
    private String placeId;
    private String titleName;

    public PlaceObject(String placeId, String placeName, String titleName) {
        this.placeName = placeName;
        this.placeId = placeId;
        this.titleName = titleName;
    }

    public PlaceObject(String placeName, String placeId) {
        this.placeName = placeName;
        this.placeId = placeId;
    }

    public String getPlaceName() {
        return placeName;
    }

    public void setPlaceName(String placeName) {
        this.placeName = placeName;
    }

    public String getPlaceId() {
        return placeId;
    }

    public void setPlaceId(String placeId) {
        this.placeId = placeId;
    }

    public String getTitleName() {
        return titleName;
    }

    public void setTitleName(String titleName) {
        this.titleName = titleName;
    }

}

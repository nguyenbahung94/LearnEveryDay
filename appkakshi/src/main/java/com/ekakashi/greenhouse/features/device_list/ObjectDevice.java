package com.ekakashi.greenhouse.features.device_list;

/**
 * Created by nbhung on 12/1/2017.
 */

public class ObjectDevice {
    private String link;
    private String title;
    private String subTitle;
    private String codeDeviceId;
    private String dateRegistration;

    public ObjectDevice(String link, String title, String subTitle, String codeDeviceId, String dateRegistration) {
        this.link = link;
        this.title = title;
        this.subTitle = subTitle;
        this.codeDeviceId = codeDeviceId;
        this.dateRegistration = dateRegistration;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public String getCodeDeviceId() {
        return codeDeviceId;
    }

    public void setCodeDeviceId(String codeDeviceId) {
        this.codeDeviceId = codeDeviceId;
    }

    public String getDateRegistration() {
        return dateRegistration;
    }

    public void setDateRegistration(String dateRegistration) {
        this.dateRegistration = dateRegistration;
    }
}

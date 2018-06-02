package com.ekakashi.greenhouse.database.model_server_response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by nbhung on 1/18/2018.
 */

public class ResponseAddField {

    @SerializedName("id")
    public int id;
    @SerializedName("clientSystemInfo")
    public ClientSystemInfo clientSystemInfo;
    @SerializedName("createdAt")
    public String createdAt;
    @SerializedName("ekUser")
    public EkUser ekUser;
    @SerializedName("name")
    public String name;
    @SerializedName("startDate")
    public String startDate;
    @SerializedName("updatedAt")
    public String updatedAt;
    @SerializedName("fieldType")
    public int fieldType;
    @SerializedName("polygon")
    public List<myLatLong> polygon;

    public int getId() {
        return id;
    }

    public ClientSystemInfo getClientSystemInfo() {
        return clientSystemInfo;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public EkUser getEkUser() {
        return ekUser;
    }

    public String getName() {
        return name;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public int getFieldType() {
        return fieldType;
    }

    public List<myLatLong> getPolygon() {
        return polygon;
    }

    public static class Client {
        @SerializedName("id")
        public int id;
        @SerializedName("addressBlock")
        public String addressBlock;
        @SerializedName("addressCity")
        public String addressCity;
        @SerializedName("addressPref")
        public String addressPref;
        @SerializedName("addressZip")
        public String addressZip;
        @SerializedName("capital")
        public int capital;
        @SerializedName("clientName")
        public String clientName;
        @SerializedName("createdAt")
        public String createdAt;
        @SerializedName("documentDelivery")
        public int documentDelivery;
        @SerializedName("documentMethod")
        public int documentMethod;
        @SerializedName("email")
        public String email;
        @SerializedName("paymentTerms")
        public String paymentTerms;
        @SerializedName("phone")
        public String phone;
        @SerializedName("picName")
        public String picName;
        @SerializedName("productDelivery")
        public int productDelivery;
        @SerializedName("updatedAt")
        public String updatedAt;

        public int getId() {
            return id;
        }

        public String getAddressBlock() {
            return addressBlock;
        }

        public String getAddressCity() {
            return addressCity;
        }

        public String getAddressPref() {
            return addressPref;
        }

        public String getAddressZip() {
            return addressZip;
        }

        public int getCapital() {
            return capital;
        }

        public String getClientName() {
            return clientName;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public int getDocumentDelivery() {
            return documentDelivery;
        }

        public int getDocumentMethod() {
            return documentMethod;
        }

        public String getEmail() {
            return email;
        }

        public String getPaymentTerms() {
            return paymentTerms;
        }

        public String getPhone() {
            return phone;
        }

        public String getPicName() {
            return picName;
        }

        public int getProductDelivery() {
            return productDelivery;
        }

        public String getUpdatedAt() {
            return updatedAt;
        }
    }

    public static class ClientSystemInfo {
        @SerializedName("id")
        public int id;
        @SerializedName("client")
        public Client client;
        @SerializedName("createdAt")
        public String createdAt;
        @SerializedName("initialEkUserId")
        public int initialEkUserId;
        @SerializedName("initialEkUserName")
        public String initialEkUserName;
        @SerializedName("initialEkUserPassword")
        public String initialEkUserPassword;
        @SerializedName("isAvailableApi")
        public boolean isAvailableApi;
        @SerializedName("isAvailableWeb")
        public boolean isAvailableWeb;
        @SerializedName("updatedAt")
        public String updatedAt;

        public int getId() {
            return id;
        }

        public Client getClient() {
            return client;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public int getInitialEkUserId() {
            return initialEkUserId;
        }

        public String getInitialEkUserName() {
            return initialEkUserName;
        }

        public String getInitialEkUserPassword() {
            return initialEkUserPassword;
        }

        public boolean isAvailableApi() {
            return isAvailableApi;
        }

        public boolean isAvailableWeb() {
            return isAvailableWeb;
        }

        public String getUpdatedAt() {
            return updatedAt;
        }
    }
}

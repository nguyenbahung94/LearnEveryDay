package com.ekakashi.greenhouse.common;


import com.ekakashi.greenhouse.database.model_server_response.BaseResponse;
import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;

import okhttp3.ResponseBody;

public class BasePresenter {

    protected String getErrorMessage(ResponseBody errorBody) {
        Gson gson = new Gson();
        try {
            BaseResponse message = gson.fromJson(errorBody.charStream(), BaseResponse.class);
            if (message == null) {
                return "";
            }
            return (message.getMessage() == null || message.getMessage().isEmpty()) ? message.getErrorDescription() + "" : message.getMessage() + "";//sometimes message.getErrorDescription() return null
        } catch (JsonIOException e) {
            e.printStackTrace();
        } catch (JsonSyntaxException e1) {
            e1.printStackTrace();
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        return "";
    }

    protected int getErrorCode(ResponseBody errorBody) {
        Gson gson = new Gson();
        try {
            BaseResponse message = gson.fromJson(errorBody.charStream(), BaseResponse.class);
            if (message == null) {
                return 0;
            }
            return message.getCode();
        } catch (JsonIOException e) {
            e.printStackTrace();
        } catch (JsonSyntaxException e1) {
            e1.printStackTrace();
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        return 0;
    }
}

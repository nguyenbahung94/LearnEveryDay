package com.ekakashi.greenhouse.features.weather;

/**
 * Created by nbhung on 3/5/2018.
 */

public class CommonWeather {

    public static String getWeatherConversion(int number) {
        String conversion;
        switch (number) {
            case 0:
                conversion = "contents";
                break;

            case 1:
                conversion = "Sunny";
                break;

            case 2:
                conversion = "Slightly cloudy weather";
                break;
            case 3:
                conversion = "Cloudiness";
                break;
            case 4:
                conversion = "Cloudiness";
                break;
            case 5:
                conversion = "Strong rain";
                break;
            case 6:
                conversion = "Sleet storm";
                break;
            case 7:
                conversion = "Weak wet snow";
                break;
            case 8:
                conversion = "Strong wet snow";
                break;
            case 9:
                conversion = "weak dry snow";
                break;
            case 10:
                conversion = "String dry now";
                break;
            case 21:
                conversion = "Not Clear";
                break;
            default:
                conversion = "";
                break;
        }
        return conversion;
    }

    public static double getAirTemperature(int number) {
        return (-50 + (number * 0.5));
    }

    public static int getWindSpeed(int number) {
        if (number <= 254) {
            return number;
        }
        return -1;
    }

    public static int getContentConversion(int number) {
        if (number > 254) {
            return -1;
        }
        return number;
    }

    public static String getWindDirection(int number) {
        String result;
        switch (number) {
            case 0:
                result = "Calm";
                break;
            case 1:
                result = "North-northeast";
                break;
            case 2:
                result = "Northeast";
                break;
            case 3:
                result = "East-northeast";
                break;
            case 4:
                result = "East-northeast";
                break;
            case 5:
                result = "East-southeast";
                break;
            case 6:
                result = "Southeast";
                break;
            case 7:
                result = "South-southeast";
                break;
            case 8:
                result = "South";
                break;
            case 9:
                result = "South-Southwest";
                break;
            case 10:
                result = "Southwest";
                break;
            case 11:
                result = "West-southwest";
                break;
            case 12:
                result = "West-northwest";
                break;
            case 13:
                result = "West-northwest";
                break;
            case 14:
                result = "Northwest";
                break;
            case 15:
                result = "North-northwest";
                break;
            case 16:
                result = "North";
                break;
            case 17:
                result = "Not Clear";
                break;
            default:
                result = "";
                break;
        }
        return result;
    }

    public static String getAmountConversion(int number) {
        String result;
        switch (number) {
            case 0:
                result = "0~2";
                break;
            case 1:
                result = "3~7";
                break;
            case 2:
                result = "8~12";
                break;
            case 255:
                result = "out of estimated range";
                break;
            default:
                result = ((number - 1) * 5) + 3 + "~" + (((number - 1) * 5) + 7);
                break;
        }
        return result;
    }

}

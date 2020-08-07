package com.example.a3.API;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class MapsAPI {
    public static final String map_key = "";

    public static String searchLocation(String address) {
        address = address.trim().replace(" ", "");
        URL url = null;
        HttpURLConnection connect = null;
        String result = "";

        try {
//            url = new URL();
            connect = (HttpURLConnection) url.openConnection();
            connect.setReadTimeout(10000);
            connect.setConnectTimeout(15000);
            connect.setRequestMethod("GET");
            connect.setRequestProperty("Content-Type", "application/json");
            connect.setRequestProperty("Accept", "application/json");
            Scanner scanner = new Scanner(connect.getInputStream());
            while (scanner.hasNextLine()) {
                result += scanner.nextLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            connect.disconnect();
        }
        return result;
    }

}

package org.example;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) throws IOException {

        String JSON = getJSON("https://api.monobank.ua/bank/currency");
        System.out.println(getExhangeRate(JSON));
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        Currency[] currency = gson.fromJson(JSON, Currency[].class);
        System.out.println(Arrays.toString(currency));
    }

    public static String getJSON(String spec) throws IOException {
        String json = "";
        URL url = new URL(spec);
        URLConnection urlc = (HttpURLConnection) url.openConnection();

            try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlc.getInputStream()))) {
                json = bufferedReader.readLine();
            } catch (IOException e ) {
                e.printStackTrace();
            }

        return json;
    }

    public static double getExhangeRate(String json){
        double rate = 0.0;
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        Currency[] currency = gson.fromJson(json, Currency[].class);
        for (Currency currencies : currency) {
            if(currencies.getCurrencyCodeA() == 840 && currencies.getCurrencyCodeB() == 980) {
               rate = currencies.getRateBuy();
            }
        }
        return rate;
    }

}
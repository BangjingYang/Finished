package com.example.a3.API;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class MoiveSearchAPI {
    private static final String tmdb_key ="837137c834a5c728df83b6d258268a7c";

    public static String searchMoive(String keyword){
        keyword = keyword.trim().replace(" ","%20");
        URL url = null;
        HttpURLConnection connect = null;
        String result = "";
        try{
            url = new URL ("https://api.themoviedb.org/3/search/movie?api_key="+ tmdb_key +"&language=en-US&query="+ keyword + "&page=1&include_adult=false");
            connect = (HttpURLConnection) url.openConnection();
            connect.setReadTimeout(10000);
            connect.setConnectTimeout(15000);
            connect.setRequestMethod("GET");
            connect.setRequestProperty("Content-Type","application/json");
            connect.setRequestProperty("Accept","application/json");
            Scanner scanner = new Scanner(connect.getInputStream());
            while (scanner.hasNextLine()){
                result += scanner.nextLine();
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        finally {
            connect.disconnect();
        }
        return result;
    }

    public static List<String> getSnippet(String result){
        List<String> snippet = new ArrayList<>();
        try{
            JSONObject jsonObject = new JSONObject(result);
            JSONArray jsonArray1 = new JSONArray(jsonObject.getString("results"));
            if (jsonArray1.length() > 5) {
                for (int i = 0; i < 5; i++) {
                    JSONObject jsonObject1 = (JSONObject) jsonArray1.get(i);
                    snippet.add(jsonObject1.getString("title"));
                    snippet.add(jsonObject1.getString("release_date"));
                    snippet.add(jsonObject1.getString("poster_path"));
                    snippet.add(jsonObject1.getString("id"));
                }
            }else{
                for (int i = 0; i < jsonArray1.length(); i++) {
                    JSONObject jsonObject1 = (JSONObject) jsonArray1.get(i);
                    snippet.add(jsonObject1.getString("title"));
                    snippet.add(jsonObject1.getString("release_date"));
                    snippet.add(jsonObject1.getString("poster_path"));
                    snippet.add(jsonObject1.getString("id"));
                }
            }
        }catch (JSONException e){
            e.printStackTrace();
        }
        return snippet;
    }


    public static HashMap<String, Object> getMovieDetail(String movieID){
        URL url = null;
        HttpURLConnection connect = null;
        String detail = "";
        String cast = "";
        try{
            url = new URL ("https://api.themoviedb.org/3/movie/" + movieID + "?api_key=" + tmdb_key + "&language=en-US");
            connect = (HttpURLConnection) url.openConnection();
            connect.setReadTimeout(10000);
            connect.setConnectTimeout(15000);
            connect.setRequestMethod("GET");
            connect.setRequestProperty("Content-Type","application/json");
            connect.setRequestProperty("Accept","application/json");
            Scanner scanner = new Scanner(connect.getInputStream());
            while (scanner.hasNextLine()){
                detail += scanner.nextLine();
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        finally {
            connect.disconnect();
        }
        cast = getMovieCast(movieID);
        HashMap<String, Object> map = new HashMap<>();
        try {
            JSONObject details = new JSONObject(detail);
            map.put("overview", details.getString("overview"));
            map.put("imgURL", details.getString("poster_path"));
            map.put("rate score", details.getString("vote_average"));

            JSONArray genres = details.getJSONArray("genres");
            JSONObject genre = genres.getJSONObject(0);
            map.put("genre", genre.getString("name"));

            JSONArray production_countries = details.getJSONArray("production_countries");
            JSONObject production_country = production_countries.getJSONObject(0);
            map.put("country", production_country.getString("name"));

            JSONObject casts = new JSONObject(cast);
            JSONArray allcast = casts.getJSONArray("cast");
            JSONArray crew = casts.getJSONArray("crew");
            List<String> castList = new ArrayList<>();
            if (allcast.length() < 5){
                for (int i = 0; i < casts.length(); i++){
                    JSONObject jsonObject = allcast.getJSONObject(i);
                    castList.add(jsonObject.getString("name"));
                }
            }else {
                for (int i = 0; i < 5; i ++){
                    JSONObject jsonObject = allcast.getJSONObject(i);
                    castList.add(jsonObject.getString("name"));
                }
            }
            map.put("castList", castList);

            List<String> directorList = new ArrayList<>();
            for(int i = 0; i < crew.length(); i++){
                JSONObject jsonObject = crew.getJSONObject(i);
                if(jsonObject.getString("job").equals("Director")){
                    directorList.add(jsonObject.getString("name"));
                }
            }
            map.put("directorList", directorList);
        }catch(Exception e){
            e.printStackTrace();
        }
        return map;
    }

    public static String getMovieCast(String movieID){
        URL url = null;
        HttpURLConnection connect = null;
        String result = "";
        try{
            url = new URL("https://api.themoviedb.org/3/movie/" + movieID + "/credits?api_key=" + tmdb_key);
            connect = (HttpURLConnection) url.openConnection();
            connect.setReadTimeout(10000);
            connect.setConnectTimeout(15000);
            connect.setRequestMethod("GET");
            connect.setRequestProperty("Content-Type","application/json");
            connect.setRequestProperty("Accept","application/json");
            Scanner scanner = new Scanner(connect.getInputStream());
            while (scanner.hasNextLine()){
                result += scanner.nextLine();
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        finally {
            connect.disconnect();
        }
        return result;
    }

    public static HashMap<String, String> searchMovieDeteil(String keyword, String release){
        String result = searchMoive(keyword);
        List<String> movieIDAndName = getSnippet(result);
        HashMap<String, String> movieDetial = new HashMap<>();
        for(int i = 0; i < movieIDAndName.size(); i += 4) {
            String name = movieIDAndName.get(i).toLowerCase();
            String date = movieIDAndName.get(i + 1);
            if (keyword.toLowerCase().equals(name) || release.equals(date)) {

                    movieDetial.put("movieID", movieIDAndName.get(i + 3));
                    movieDetial.put("moviename", movieIDAndName.get(i));
                    movieDetial.put("moviepost", movieIDAndName.get(i + 2));
                    break;

            }
        }
        HashMap<String, Object> allMovieDetail= getMovieDetail( movieDetial.get("movieID"));
        movieDetial.put("overview", allMovieDetail.get("overview").toString());
        movieDetial.put("publicscore", allMovieDetail.get("rate score").toString());
        movieDetial.put("genre", allMovieDetail.get("genre").toString());
        movieDetial.put("country", allMovieDetail.get("country").toString());


        return movieDetial;
    }
}

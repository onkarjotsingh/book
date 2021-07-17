package com.example.android.book;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public final class Utils {

    private Utils() {
    }
    public static ArrayList<book> fetchbooksdata(String request_url){
        Log.v("Utils",request_url);
        URL url=createURL(request_url);
        String JASONResponce=null;
        try{
            JASONResponce=makeHttpRequest(url);
        }catch (Exception e){
            Log.e("Utils","Error creating Jason Responce",e);
        }
        ArrayList<book> books=extractbooks(JASONResponce);
        return books;
    }

    private static ArrayList<book> extractbooks(String jasonResponce) {
        ArrayList<book> books=new ArrayList<>();
        try {
            JSONObject jsonObject=new JSONObject(jasonResponce);
            JSONArray items=jsonObject.getJSONArray("items");
            for(int i=0;i<items.length();i++) {
                JSONObject book=items.getJSONObject(i);
                JSONObject volumeinfo = book.getJSONObject("volumeInfo");
                String title = volumeinfo.getString("title");
                JSONArray authorsarray = volumeinfo.getJSONArray("authors");
                StringBuilder authors = new StringBuilder();
                int j;
                for (j = 0; j < authorsarray.length() - 1; j++) {
                    authors.append(authorsarray.get(j));
                    authors.append(",");
                }
                authors.append(authorsarray.get(j));
                JSONObject imagelink = volumeinfo.getJSONObject("imageLinks");
                String img = imagelink.getString("thumbnail");
                String previewLink = volumeinfo.getString("previewLink");
                books.add(new book(title, authors.toString(), img, previewLink));
            }
        } catch (JSONException e) {
            Log.e("Utils","error parsing json response",e);
        }
        return books;
    }

    private static String makeHttpRequest(URL url) throws  IOException{
        String JASONResponce="";
        if(url==null){
            return JASONResponce;
        }
        HttpURLConnection urlConnection=null;
        InputStream inputStream=null;
        try {
            urlConnection=(HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(100000);
            urlConnection.setConnectTimeout(150000);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();
            if(urlConnection.getResponseCode()==200){
                inputStream=urlConnection.getInputStream();
                JASONResponce=readFromStream(inputStream);
            }
            else{
                Log.e("Utils","error response code"+urlConnection.getResponseCode());
            }
        }catch (IOException e) {
            Log.e("Utils", "Problem retrieving the JSON results.", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return JASONResponce;
    }

    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

    private static URL createURL(String request_url) {
        URL url=null;
        try {
            url=new URL(request_url);
        }catch (MalformedURLException e){
            Log.e("Utils","error in creating url",e);
        }
        return url;
    }


}

package com.example.asus.jsonsqlite;

import android.os.AsyncTask;

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

/**
 * Created by ASUS on 5.09.2016.
 */
public class JsonGetter extends AsyncTask<String, String,String> {
    public float lat, lng;
    public String formattedAddress;
    public String longName;

    @Override
    protected String doInBackground(String... params) {
        HttpURLConnection connection = null; // url connection instance created from  HttpURLConnection class
        BufferedReader reader = null;        // this reader will be used below as json data reader

        final String finalJson;
        try {
            URL url = new URL(params[0]); // the instructor gets the params[] parametres array's first value
                                          // "http://maps.google.com/maps/api/geocode/json?address=%22%20+%20"+urlCity+"%20+%20%22&sensor=false"

            connection = (HttpURLConnection) url.openConnection(); // the connection opened with the url above
            connection.connect();

            InputStream stream = connection.getInputStream(); //  json data receipt sketchily into stream variable

            reader = new BufferedReader(new InputStreamReader(stream)); // will be used while json data as mentioned above
            StringBuffer buffer = new StringBuffer();  //will hold the parsed and appended json data
            String line = "";                           //standart stream reading
            while ((line = reader.readLine()) != null) {
                buffer.append(line); // json data appended
            }
            finalJson = buffer.toString(); // finally json data parsed and converted to String value as finalJson
            JSONObject parentObject = new JSONObject(finalJson); // hereafter json data analyzed accordint to hieararchy in the json file
            JSONArray resultsArray=parentObject.getJSONArray("results"); // the outmost array

            JSONObject mainobject=resultsArray.getJSONObject(0);  // the first object in the resultArray

            SehirModel sehirModel=new SehirModel(); // instance for the model class

            //--------------------------- similar solving json data operations--------------------------------
            sehirModel.setFormattedAdress(mainobject.getString("formatted_address"));
            sehirModel.setLat((float) mainobject.getJSONObject("geometry").getJSONObject("location").optDouble("lat")); //schematized in the SehirModel class comments
                                                                                                    // JsonObject(ResultsArray(object(0).object(geometry(object(location).lat))))
            sehirModel.setLng((float) mainobject.getJSONObject("geometry").getJSONObject("location").getDouble("lng")); // instead of getDouble optDouble can be used
            SehirModel.AddComp addComp=new SehirModel.AddComp();
            addComp.setLongName(mainobject.getJSONArray("address_components").getJSONObject(0).getString("long_name"));

            //----------------------JsonObject(ResultsArray(object(0).object(geometry(object(location).lat))))-------------------------
            longName=addComp.getLongName();
            formattedAddress=sehirModel.getFormattedAdress();
            lat=sehirModel.getLat();
            lng=sehirModel.getLng();
            //------------------------JsonObject(ResultsArray(object(0).object(geometry(object(location).lat))))---------------------------

            //-------------------finalBufferedData holds the longname, lat and lng values---------------------
            StringBuffer finalBufferedData=new StringBuffer();
            finalBufferedData.append(longName+"\n"+lat+"-"+lng);
            return finalBufferedData.toString();
            //-------------------- finalbuffereddata's string value will be sent to onPostExecute Method as parametre named result
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }


    @Override
    protected void onPostExecute(String result){
        //MainActivity.tvSehir.setText(result);
        MainActivity mainActivity=new MainActivity();
        mainActivity.tvResult.setText(result); // result will be displayed in the main activity

    }
}

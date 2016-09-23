package com.example.asus.jsonsqlite;

import java.util.List;

/**
 * Created by ASUS on 5.09.2016.
 */

//The Model class helps the JsonGetter class to parse Json Data to Java Objects
    // Json data defined as as java variables

public class SehirModel {
    private List<AddComp> addCompList; // "address_components" : [] -> JsonObject(resultsArray(object(0).address_components))
    private String formattedAdress;    // "formatted_address" :     -> JsonObject(resultsArray(object(0).address_components))
    private float lat;                 //   "location": object      -> JsonObject(ResultsArray(object(0).object(geometry(object(location).lat))))
    private float lng;                  // ""location"": object      -> JsonObject(ResultsArray(object(0).object(geometry(object(location).lng))))

    public List<AddComp> getAddCompList() {
        return addCompList;
    } // list instance from AddComp class which defined to keep JsonArray(Address_component) place

    public void setAddCompList(List<AddComp> addCompList) {
        this.addCompList = addCompList;
    }

    //-------------------------------------------------

    public String getFormattedAdress() {
        return formattedAdress;
    }

    public void setFormattedAdress(String formattedAdress) {
        this.formattedAdress = formattedAdress;
    }
    //----------------------------------------------------------------
    public float getLat() {
        return lat;
    } // defined lat ve lng jason values as java float variables

    public void setLat(float lat) {
        this.lat = lat;
    }

    public float getLng() {
        return lng;
    }

    public void setLng(float lng) {
        this.lng = lng;
    }


    //--------------------------------------------------------------------
   public static class AddComp{  //Address_component Json array defined as java class

        private String longName;// the key Word long_name in the address_component
        // json array defined as java string variable

        public String getLongName() {
            return longName;
        }

        public void setLongName(String longName) {
            this.longName = longName;
        }
    }

}
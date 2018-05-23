/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cryptojoint;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Laurens
 */
public class Updater {
    private Map<String,API> CurrentPairs;
    private APIFetcher fetcher;
    
    //Updater is created 
    public Updater(){
        this.CurrentPairs = new HashMap<String, API>();
        this.fetcher = new APIFetcher();
    }
    
    // Add a pair to the current list
    public void AddPair(String pair, String Exchange){
        if (!CurrentPairs.containsKey(pair)){
            this.CurrentPairs.put(pair, fetcher.GetFormat(pair, Exchange));
        }
    }
    
    //TODO needs to handle json correctly and put it in the map
    public HashMap GetUpdate() throws MalformedURLException, IOException{
        HashMap ReturnMap = new HashMap<String, Double>();
        for (API value : CurrentPairs.values()){
            ReturnMap.put(value.getPair(), value.makeCall());
        }
        return ReturnMap;
    }
    
}


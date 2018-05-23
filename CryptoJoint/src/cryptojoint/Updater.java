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
    private Map<PairTradeType,API> CurrentPairs;
    private APIFetcher fetcher;
    
    //Updater is created 
    public Updater(){
        this.CurrentPairs = new HashMap<PairTradeType, API>();
        this.fetcher = new APIFetcher();
    }
    
    // Add a pair to the current list
    // Add support for different endppoints
    public void AddPair(String Endpoint, String Pair, String Exchange){
        PairTradeType endPoint = new PairTradeType(Pair, Endpoint);
        if (!CurrentPairs.containsKey(endPoint)){
            this.CurrentPairs.put(endPoint, fetcher.GetFormat(endPoint, Exchange));
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


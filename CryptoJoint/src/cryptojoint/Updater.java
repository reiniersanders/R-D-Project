/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cryptojoint;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Laurens
 */
public class Updater {
    private APIFetcher fetcher;
    
    //Updater is created 
    public Updater(){
        this.fetcher = new APIFetcher();
    }
    
    

    
    //only binance supported at the moment
    public ArrayList<CurrencyTuple> GetUpdate(ArrayList<CurrencyTuple> toUpdate, String Exchange) throws MalformedURLException, IOException{
        ArrayList<CurrencyTuple> returnArray = new ArrayList<CurrencyTuple>();
        for (int i = 0; i < toUpdate.size(); i++) {
            APIBinance api = fetcher.GetFormat(toUpdate.get(i).getOwned(),
                        toUpdate.get(i).getNotOwned(), Exchange);
            Double result = api.makeCall();
            CurrencyTuple tupleBuffer = toUpdate.get(i);
            tupleBuffer.setPrice(result);
            returnArray.add(tupleBuffer);
        }
        return returnArray;
    }
    
}


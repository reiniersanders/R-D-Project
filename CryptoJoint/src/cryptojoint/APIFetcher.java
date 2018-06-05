/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cryptojoint;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Laurens
 */
public class APIFetcher {
    
    public APIFetcher(){
    }
    
    public APIBinance GetFormat(Currency toBuy, Currency toSell, String Exchange){
        switch (Exchange) {
            case "Binance": return MakeBinanceAPI((toSell.getName() + toBuy.getName()));
            default: return null;
        }
        
    }
    
    public APIBinance MakeBinanceAPI(String Endpoint){
        return new APIBinance(Endpoint.toUpperCase(),  "https://api.binance.com/api/v1/");
    }
    
    
}


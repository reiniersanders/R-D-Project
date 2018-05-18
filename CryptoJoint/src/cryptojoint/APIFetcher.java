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
    
    public API GetFormat(String Pair, String Exchange){
        switch (Exchange) {
            case "Binance": return MakeBinanceAPI(Pair);
            default: return null;
        }
        
    }
    
    public API MakeBinanceAPI(String Pair){
        return new API(Pair,  "https://api.binance.com/api/v1/depth");
    }
    
    
}


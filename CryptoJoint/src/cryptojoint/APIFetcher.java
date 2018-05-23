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
    
    public API GetFormat(PairTradeType PairEndpoint, String Exchange){
        switch (Exchange) {
            case "Binance": return MakeBinanceAPI(PairEndpoint);
            default: return null;
        }
        
    }
    
    public API MakeBinanceAPI(PairTradeType PairEndpoint){
        return new API(PairEndpoint,  "https://api.binance.com/api/v1/");
    }
    
    
}


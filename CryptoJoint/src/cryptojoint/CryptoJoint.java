/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cryptojoint;

import java.io.IOException;
import java.util.HashMap;

/**
 *
 * @author Laurens
 */
public class CryptoJoint {

    /**
     * Example of how to call Updater, currently supports 
     * depth, trades, aggTrades
     * NOT SUPPORTED: KLINE data, 24h ticker, symbol price ticker and 
     * orderbook ticker
     * see:
     * https://github.com/binance-exchange/binance-official-api-docs/blob/master/rest-api.md 
     * for more info on data types
     */
    public static void main(String[] args) throws IOException {
        Updater updater = new Updater();
        updater.AddPair("ticker/24h", "ETHBTC", "Binance");
        HashMap test = updater.GetUpdate();
        System.out.println("testmap:");
        System.out.println(test.toString());
    }
    
}

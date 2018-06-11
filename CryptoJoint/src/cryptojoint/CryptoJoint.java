/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cryptojoint;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author Laurens
 */
public class CryptoJoint {

    /** 
     * main file for testing purposes
     * https://github.com/binance-exchange/binance-official-api-docs/blob/master/rest-api.md 
     * for more info on data types
     */
    public static void main(String[] args) throws IOException {
        Updater updater = new Updater();
        APIBinance api = new APIBinance("BTCUSDT", "https://api.binance.com/api/v3/ticker/price");
        api.getSymbols();
        Double result = api.makeCall();
        System.out.println(result);
    }
}

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
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        Updater updater = new Updater();
        updater.AddPair("ETHBTC", "Binance");
        HashMap test = updater.GetUpdate();
        System.out.println("testmap:");
        System.out.println(test.toString());
    }
    
}

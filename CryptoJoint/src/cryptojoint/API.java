/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cryptojoint;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.util.ArrayList;

/**
 *
 * @author Laurens
 */
public interface API { 
    public Double makeCall() throws UnsupportedEncodingException, MalformedURLException, IOException;
    public ArrayList<CurrencyTuple> getSymbols() throws MalformedURLException, IOException;
}
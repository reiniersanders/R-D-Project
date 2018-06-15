package com.example.wout.cryptojoint;

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

    public ArrayList<CurrencyTuple> GetSymbols(String Exchange) throws IOException{
        ArrayList<CurrencyTuple> retList = fetcher.getSymbols(Exchange);
        return retList;
    }

    /**
     * Returns an ArrayList of CurrencyTuple which contains the the price and two
     * currency objects, the price is how many notOwned Currencies the buyer gets
     * for one owned Currency
     * @param toUpdate    an ArrayList of all currencyTuples which need an update
     * @param Exchange    The exchange to use, A capitalized String
     * @return               An ArrayList of CurrencyTuple
     * @throws java.net.MalformedURLException wrong url, probably means symbols
     * in wrong order, Caller has to catch this
     */
    public ArrayList<CurrencyTuple> GetUpdate(ArrayList<CurrencyTuple> toUpdate, String Exchange){
        ArrayList<CurrencyTuple> returnArray = new ArrayList<>();
        for (int i = 0; i < toUpdate.size(); i++) {
            System.out.println(i + "/" + toUpdate.size());
            API api = fetcher.GetFormat(toUpdate.get(i).getNotOwned(),
                    toUpdate.get(i).getOwned(), Exchange);
            Double result = null;
            try {
                result = api.makeCall();
            } catch (IOException e) { }
            CurrencyTuple tupleBuffer = toUpdate.get(i);
            if (result == null)
                tupleBuffer.setPrice(0);
            else
                tupleBuffer.setPrice(result);
            returnArray.add(tupleBuffer);
        }
        return returnArray;
    }
}
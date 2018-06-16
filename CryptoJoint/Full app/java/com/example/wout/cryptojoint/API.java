package com.example.wout.cryptojoint;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.util.ArrayList;

public interface API {
    public Double makeCall() throws UnsupportedEncodingException, MalformedURLException, IOException;
    public ArrayList<CurrencyTuple> getSymbols() throws MalformedURLException, IOException;
}

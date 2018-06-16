package com.example.wout.cryptojoint;

/**
 *
 * @author Laurens
 */
public class PairTradeType {
    private String pair;
    private String endPoint;

    public PairTradeType(String pair, String endPoint) {
        this.pair = pair;
        this.endPoint = endPoint;
    }

    public String getPair() {
        return this.pair;
    }

    public String getEndPoint() {
        return this.endPoint;
    }
}
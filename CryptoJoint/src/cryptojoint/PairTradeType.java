/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cryptojoint;

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

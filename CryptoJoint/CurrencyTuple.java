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
public class CurrencyTuple {
    private Currency owned;
    private Currency notOwned;
    private double price;
    
    public CurrencyTuple(Currency owned, Currency notOwned) {
        this.owned = owned;
        this.notOwned = notOwned;
        this.price = 0.00;
    }
    
    public Currency getOwned() {
        return this.owned;
    }
    
    public Currency getNotOwned() {
        return this.notOwned;
    }
    
    public double getPrice() {
        return this.price;
    }
    
    public void setPrice(double price) {
        this.price = price;
    }
}

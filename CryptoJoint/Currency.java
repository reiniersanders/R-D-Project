/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package traderapp;

/**
 *
 * @author Ferran
 */
public class Currency {
    private String name;
    private double value;
    
    public Currency (String name, double value)
    {
        this.name=name;
        this.value=value;
    }
    
    public String getName()
    {
        return name;
    }
    
    public double getValue()
    {
        return value;
    }
    
    public void setValue(double value)
    {
        this.value=value;
    }
    
    public String toString()
    {
        return name;
    }
}

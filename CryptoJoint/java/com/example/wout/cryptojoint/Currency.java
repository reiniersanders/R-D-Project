package com.example.wout.cryptojoint;

import java.io.Serializable;

public class Currency implements Serializable{
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
}
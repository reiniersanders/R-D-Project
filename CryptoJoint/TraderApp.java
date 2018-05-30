/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package traderapp;

import java.util.ArrayList;



/**
 *
 * @author Ferran
 */
public class TraderApp {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Currency btc = new Currency("btc", 1);
        Currency ete = new Currency ("ete", 2);
        Trader trader = new Trader();
        ArrayList<Currency> list = new ArrayList();
        list.add(btc);
        list.add(ete);
        trader.updateTrader(list);
        Trade trade = new Trade(trader, btc, 2, ete, 1, 1);
        Trade tradetwo = new Trade (trader, btc, 10, ete, 1, 2);
        trader.Trademaker(btc, 2, ete, 0);
        trader.Trademaker(btc, 10, ete, 2);
        trader.Trademaker(btc, 12, ete, 2);
        trader.Trademaker(btc, 20, ete, 1);
    }


}

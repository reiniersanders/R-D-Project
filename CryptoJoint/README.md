# R-D-Project


TO IMPLEMENT

UML discussie

 

API updater en API fetcher: Laurens

Client: Wout

Trader: Ferran

 

API updater:

Lijst (Map) van vooraf opgevraagde currencies

 

Van updater naar client/trader worden Maps van

<currency (String), prijs (double), tradeid>

Of

<currency (String), aantal (double), tradeid>

 

Update alleen als er een request komt van de trader.

 

API fetcher:

Vertaalt de .json packages of iets anders wat we krijgen van de exchanges naar een Map.

 

Client:

Doet alle communicatie met de front-end.

Houd een wallet bij met wat voor currencies je hebt en dat wordt uitgedrukt in dollars of andere currencies.

Intern een Map met alle currencies en prijswaardes.

Met functies zoals getHoldings en getViewings.

Vraagt aan Trader met tradeid en stuurt de currency naar de trader.

Krijgt conformatie of het gelukt is of niet.

 

Trader:

Elke 5 seconden een request voor update.

Doet alle verhandelingen.

Goede afhandeling voor korte prijsverschillen.

Zoals ik wil 1 btc voor 5 eth, en dat kan.

Maar nadat het gekeken is of het kan is het voor 1,002 btc voor 5 eth.

Heeft interne wallet.

Krijgt trade requests van Client en krijgt de currency en stuurt conformatie terug.

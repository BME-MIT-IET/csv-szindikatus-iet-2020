# Statikus kód analízis

Első lépésnek elindítottuk a SonarLint ellenőrzőt a Visual Studio Code-ban, amely automatikusan végigfutott a projekten és a talált code smelleket jelezte nekünk.
Ezek után ellenőriztük a kódot a javaslatok alapján és módosításokat eszközöltük a kódban.

* Készítettünk egy logger-t amivel kiváltottuk a standard outputra való információ kiírást, és a kivétel kezelésnél is a loggert állítottuk be az információk kiírására.
* Néhány függvénynél a static és a final kulcsszót fel kellett cserélni hogy a Java előírásainak megfelelő legyen, mert a kezdeti kódban "final static"-ot írtak a "static final" helyett
* A run függvényt át kellett struktúrálni, egy try-with-resources struktúrát kellett létrehozni, hogy le tudjuk zárni a definiált író és olvasó osztályokat.

Későbbiekben refaktoráltuk a kódot, így szükségessé vált egy újabb SonarLint futtatás. Ezt már a SonarCloud ellenőrzővel együttesen készítettem.
A javaslatokat ellenőriztük és azoknak a többségét javítottuk.
Három security hotspotot talált a Sonarcloud, ezek közül feloldottunk egyet és javítottunk egyet.
* Ezek közül az egyik egy biztonságos hash-elés elkészítése volt, ahol SHA-1 helyett BCrypt-tel kódoljuk az információkat, a másik pedig az volt hogy a main függvény argumentumjait validálni kell, ezeket különféle függvényekkel oldottuk meg.

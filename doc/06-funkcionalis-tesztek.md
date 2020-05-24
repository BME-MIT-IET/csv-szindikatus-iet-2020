# Funkcionális tesztek tervezése, végrehajtása és tesztautomatizálás

A csv2rdf projekt kezdetben nem tartalmazott semmiféle tesztelési lehetőséget. Munkánk során többféle megközelítésben igyekeztük javítani tesztek megalkotásával a projekt minőségét. A megtervezett tesztek között a funkcionális tesztek szerepe a projekt átfogó helyes működésének bizonyítása.

A csapatunk úgy döntött, hogy a maximális kódfedettség elérése helyett egy egyedi, hatékony automatizált tesztelési környezetet dolgozunk ki. Erről a későbbiekben bővebben szó fog esni.

Első körben manuális tesztelést végeztünk. Ez lényegében annyit takart, hog y futtattuk az alkalmazást a projekt eredetileg specifikált lehetséges bemeneteivel. Minden bemenetre (input fájlok, parancssori argumentumok) megvizsgáltuk, hogy a program megfelelően működik-e. A tesztelés során információgyűjtést is végeztünk. Amennyiben egy teszt lefuttatása során más tesztfuttatási lehetőséget fedeztünk fel, alkottunk egy új tesztesetet az újonnan felfedezett funkció teszteléséhez.

## Néhány fontosabb tesztfuttatás

### A program futtatása argumentumok nélkül.<br>
>`rm -r out; mkdir out; java -jar ../../build/CSV2RDF.jar convert examples/cars/template.ttl examples/cars/cars.csv out/cars.ttl`<br>

Elvárt kimenet:<br>
>`<timestamp> com.complexible.common.csv.logger.ProcessBehaviourLogger logInfo<br>
INFO: Usage: java -jar CSV2RDF.jar convert <template.ttl> <input.csv> <output.ttl>`

### A program futtatása nem megfelelő paraméterezéssel.<br>
>`rm -r out; mkdir out; java -jar ../../build/CSV2RDF.jar too few arguments`<br>

Elvárt kimenet:<br>
>`<timestamp> com.complexible.common.csv.logger.ProcessBehaviourLogger logInfo<br>
SEVERE: Invalid parameters. Make sure the first file is a template with format .ttl, the input file is a .csv, the output file is a '.ttl'.
`

### Help parancs kipróbálása.<br>
>`rm -r out; mkdir out; java -jar ../../build/CSV2RDF.jar help`<br>

Elvárt kimenet:<br>
>`Egy általános súgónak kell megjelennie.
`

### A convert parancshoz tartozó súgó megjelenítése.<br>
>`rm -r out; mkdir out; java -jar ../../build/CSV2RDF.jar help convert`<br>

Elvárt kimenet:<br>
>`A convert parancs használatával kapcsolatos információknak kell megjelennie.
`

### Convert parancs kipróbálása.
A convert parancs hatására a .CSV frmátumú bemeneti adathalmaz és egy .TTL formátumú sablon segítsével a program előállítja a kimeneti ontológiát (.TTL). A .TTL formátum mellett az .RDF és .NT formátumok is támogatottak.<br>
>`rm -r out; mkdir out; java -jar ../../build/CSV2RDF.jar convert examples/cars/template.ttl examples/cars/cars.csv out/cars.ttl`<br>

Elvárt kimenet:<br>
>A megfelelő séma szerint és kiterjeszéssel létrehozott ontológia.

A következő fejezetben bemutatjuk a funkcionális teszteléshez használt automatizált tesztkeretrendszer felépítését.
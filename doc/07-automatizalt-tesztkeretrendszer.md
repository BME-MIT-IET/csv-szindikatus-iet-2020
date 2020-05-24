### Automatizált tesztfuttató keretrendszer kialakítása funkcionális tesztek alkalmazásához

A folytonos integráció során lehetőségünk van többek között egységtesztek futtatására, kódfedettség mérésre és a szoftver automatikus kiadására is. A fejezet bemutatja a csapat által kifejlesztett python nyelvű keretrendszert, amit az automatizált funkcionális teszteléshez használtunk.

Kezdjük el az elképzeléstől. Funkcionális tesztelés során az automatizálást valamilyen szkriptelési környezetben kívántuk megvalósítani. Fontos szempont volt a tesztek egyedi kialakítása, hiszen különféle működéseket teszteltünk. Arra a megoldásra jutottunk, hogy minden teszthez egyedi konfigurációs fájlt és ellenőrző szkriptet készítünk.

## Konfigurációs fájl

Minden funkcionális teszt saját konfigurációs fájlt kapott. A konfiogurációs fájl egy JSON állomány, amely a teszteléshez használt parancsokat, valamint a tesztelésben részt vevő fájlok neveit tartalmazza.

<div style="text-align:center"><img src="images/configuration-file.png" />
    <p style="text-align:center"><em>Szétszórt módon szervezett lib mappa. Példa konfigurációs fájl.</em></p>
</div>

A fenti konfigur
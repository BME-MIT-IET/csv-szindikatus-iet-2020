# Egységtesztek végrehajtása

Egységtesztek készítése során 3 érdemi osztályt hoztunk létre, amelyekben találhatók az egységtesztek.

- **ProviderTest** 
- **GeneratorTest**
- **DependencyTest**

<hr>

**ProviderTest**

<b>ValueProvider</b> absztrakt osztály leszármazottjainak metódusait teszteli.

1. <b>RowNumberProviderTest</b> - RowNumberProvider  osztály <b>provide</b> metódusát teszteli. A metódus a paraméterben átadott sor indexet adja vissza stringben. A tesztben vizsgáljuk, hogy a metódus visszatérési értéke egyenlő-e az általunk elvárt string értékkel.

2. <b>RowValueProviderTest</b> - RowValueProvider osztály <b>provideValue</b> metódusát teszteli. A metódus vár egy oszlopindexet és egy string típusú tömböt. Vissza kell, hogy adja a tömb azon elemét, amelyet paraméterben átadtunk. A tesztben is ezt vizsgáljuk, elvárt eredményt <code>tomb[index]</code> formában lementettük és összehasonlítottuk a kapott eredménnyel.

3. <b>UUIDProviderTest</b> - UUIDProvider osztály <b>provide</b> metódusát teszteli. A metódus egy tetszőleges UUID-t ad vissza. Azt vizsgáljuk, hogy a kapott eredmény nem null-e.




**ProviderTest**

1. <b>ConstantValueGeneratorTest</b> - ConstantValueGenerator osztály <b>generate</b> metódusát teszteli. A konstruktorban átadott URI-nak meg kell egyeznie a <b>generate</b> metódus által generált URI-val.
2. <b>BNodeGeneratorOnSameRowTest</b> - BNodeGenerator osztály <b>generate</b> metódusát teszteli. BNodeGenerator osztály üres csomópont létrehozásáért felelős. Ha a megadott sorindexen már generáltak csomópontot, akkor újbóli generálás esetén a már meglévő értéket kell, hogy visszaadja. Tehát a tesztben kétszer generálunk ugyanarra a csomópontra és egyezniük kell.
3. <b>BNodeGeneratorOnDifferentRowTest</b> - Szintén a <b>generate</b> metódust teszteli, viszont egy másik esetet ellenőriz. Két különböző sorra generál üres csomópontot és ezeknek különbözniük kell.
4. <b>TemplateURIGeneratorWithRowNumberProviderTest</b>
5. <b>TemplateURIGeneratorWithMoreProvidersTest</b>
6. <b>TemplateLiteralGeneratorTest</b>
7. <b>TemplateLiteralGeneratorWithMoreProvidersTest</b>





<style>
    p {
    text-align: justify;
    text-justify: inter-word;
    }
</style>

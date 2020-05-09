CSV2RDF
=======

CSV2RDF is a simple tool for **generating RDF** output **from CSV/TSV** files.<br>
Conversion is done by a **template file** describing one row of the desired output.<br>
See [examples/cars](examples/cars) for details. 

Building
--------

**Build jar** file in the build directory
`mvn -B package`

Running
-------

List **available commands**:
`java -jar build/CSV2RDF.jar help convert`

**Run coversion** (assuming a directory named out has been created):
`java -jar build/CSV2RDF.jar convert examples/cars/template.ttl examples/cars/cars.csv out/cars.ttl`. 

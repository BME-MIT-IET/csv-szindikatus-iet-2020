CSV2RDF
=======

CSV2RDF is a simple tool for **generating RDF** output **from CSV/TSV** files.
Conversion is done by a **template file** descrbing one row of the desired output.
See [examples/cars](examples/cars) for details. 

Building
--------

**build jar** file in the build directory
`mvn -B package`

Running
-------

list **available commands**:
`java -jar build/CSV2RDF.jar help convert`.

**run coversion** (assuming a directory named out has been created):
`java -jar build/CSV2RDF.jar convert examples/cars/template.ttl examples/cars/cars.csv out/cars.ttl`. 

csv2rdf
=======

csv2rdf is a simple tool for generating RDF output from CSV/TSV files. The conversion is done by a template file
that shows how the RDF output will look for one row. See [examples/cars](examples/cars) for details. 

Building
--------

`mvn -B package` will create a local build in the `build` sub-directory.

Running
-------

list **available commands**:
`java -jar build/CSV2RDF.jar help convert`.

**run coversion** (assuming a directory named out has been created):
`java -jar build/CSV2RDF.jar convert examples/cars/template.ttl examples/cars/cars.csv out/cars.ttl`. 

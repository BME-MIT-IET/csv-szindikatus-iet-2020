> This project was updated by team **csv-szindikatus** for the Integration and Verification Techniques course at the Budapest University of Technology and Economics. The documentation is available inside the **doc** folder in **Hungarian** language.<br>Original work: https://github.com/clarkparsia/csv2rdf

CSV2RDF
=======

CSV2RDF is a simple tool for **generating RDF** output **from CSV/TSV** files.<br>
Conversion is done using a **template file** describing one row of the desired output.<br>
See [examples/cars](examples/cars) for details. 

### Building

**Build jar** file in the build directory:<br>
`mvn -B package`

### Running

**Run conversion** (assuming a directory named out has been created):<br>
`java -jar convert build/CSV2RDF.jar examples/cars/template.ttl examples/cars/cars.csv out/cars.ttl`

**Run conversion using docker image**<br>
The following command can be executed from the root directory of the repository (might require root):<br>
``docker run -v `pwd`/examples/cars:/input -v `pwd`/out:/output tmsbrndz/csv-indikatus java -jar /app/build/CSV2RDF.jar convert /input/template.ttl /input/cars.csv /output/cars.ttl``

package com.complexible.common.csv;


import org.junit.Test;
import org.openrdf.rio.RDFFormat;
import org.openrdf.rio.RDFWriter;
import org.openrdf.rio.Rio;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * This class contains tests for the project dependencies.
 */
public class DependencyTest {

    private String templateFilename = "examples/cars/template.ttl";

    /**
     * Check if tests run properly.
     */
    @Test
    public void sanityCheck() {

        assertEquals(true, true);

    }

     /**
     * Check if the Rio.getParserFormatForFileName() method works properly.
     */
    @Test
    public void formatDetectionTest() {

        Optional<RDFFormat> format = Rio.getParserFormatForFileName(templateFilename);

        assertEquals(Optional.of(RDFFormat.TURTLE), format);

    }

    /**
     * Check if the Rio.createWriter() method creates the writer properly.
     */
    @Test
    public void createWriterTest() {

        RDFFormat format = RDFFormat.TURTLE;
        RDFWriter writer = Rio.createWriter(RDFFormat.TURTLE, System.out);

        /* test passes if no exceptions were thrown */

    }

    /**
     * Check how it works
     */
    @Test
    public void testTest(){
        assertEquals(1, 1);
    }
}

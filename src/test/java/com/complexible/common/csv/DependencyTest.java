package com.complexible.common.csv;


import org.junit.Test;
import org.junit.platform.commons.util.Preconditions;
import org.openrdf.rio.RDFFormat;
import org.openrdf.rio.RDFWriter;
import org.openrdf.rio.Rio;

import com.google.common.base.Preconditions;

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
     * Check if the Preconditions.checkArguments works correctly.
     */
    @Test
    public void preconditionsCheckArgumentTest(){
        String value = "T";
        Preconditions.checkArgument(value.length() == 1, "Expecting a single character but got %s", value);

        /*Test passes if no exceptions were thrown*/
    }
}

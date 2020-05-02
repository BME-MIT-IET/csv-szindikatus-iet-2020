package com.complexible.common.csv;


import org.junit.Test;
import org.openrdf.rio.RDFFormat;
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

        assertEquals(Rio.getParserFormatForFileName(templateFilename), Optional.of(RDFFormat.TURTLE));

    }

}

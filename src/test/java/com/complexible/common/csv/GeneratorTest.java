package com.complexible.common.csv;

import org.junit.Test;
import com.complexible.common.csv.generator.*;
import com.complexible.common.csv.provider.*;
import static org.junit.jupiter.api.Assertions.*;
import org.openrdf.model.Value;
import com.complexible.common.csv.CSV2RDF;
import com.complexible.common.csv.provider.ValueProvider;
import org.openrdf.model.URI;
import org.openrdf.model.ValueFactory;
import org.openrdf.model.impl.ValueFactoryImpl;

public class GeneratorTest {

    /**
     * Tests the TemplateURIGenerator subclass of the TemplateValueGenerator
     */
    @Test
    public void TemplateURIGeneratorTest(){
        ValueProvider[] providers = new ValueProvider[1];
        RowNumberProvider rnp = new RowNumberProvider();
        providers[0] = rnp;
        String ph = rnp.getPlaceholder();
        String template = ph+"hello"+ph+ph;
        int rowIndex = 1;
        String[] array = new String[]{"Hello","this","is","a","test"};
        TemplateURIGenerator tug = new TemplateURIGenerator(template, providers);
        URI uri = tug.generate(rowIndex, array);
        String expectedString = "1hello11";
        ValueFactory FACTORY = ValueFactoryImpl.getInstance();
        URI expectedURI = FACTORY.createURI(expectedString);
        assertEquals(true, expectedURI.equals(uri));
    }
}

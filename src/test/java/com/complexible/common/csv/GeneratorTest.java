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
import org.openrdf.model.Literal;
import org.openrdf.model.Resource;
import org.openrdf.model.impl.IntegerLiteralImpl;

import javax.xml.stream.FactoryConfigurationError;
import java.math.BigInteger;

public class GeneratorTest {

    private int rowIndex = 1;
    private String[] array = new String[]{"Hello","this","is","a","test"};
    private ValueFactory FACTORY = ValueFactoryImpl.getInstance();

    /**
     * Tests the TemplateURIGenerator subclass of the TemplateValueGenerator
     */
    @Test
    public void TemplateURIGeneratorWithRowNumberProviderTest(){
        ValueProvider[] providers = new ValueProvider[1];
        RowNumberProvider rnp = new RowNumberProvider();
        providers[0] = rnp;
        String ph = rnp.getPlaceholder();
        String template = "http://" + ph + "hello" + ph + ph + ".com";
        TemplateURIGenerator tug = new TemplateURIGenerator(template, providers);
        URI uri = tug.generate(rowIndex, array);
        String expectedString = "http://1hello11.com";
        URI expectedURI = FACTORY.createURI(expectedString);
        assertEquals(true, expectedURI.equals(uri));
    }

    /**
     * Tests if the TemplateURIGenerator functions well with more than one given provider
     */
    @Test
    public void TemplateURIGeneratorWithMoreProvidersTest(){
        ValueProvider[] providers = new ValueProvider[2];
        RowNumberProvider rnp = new RowNumberProvider();
        RowValueProvider rvp = new RowValueProvider(1);
        providers[0] = rnp;
        providers[1] = rvp;
        String rnpPH = rnp.getPlaceholder();
        String rvpPH = rvp.getPlaceholder();
        String template = "http://" + rnpPH + rvpPH + "hello" + rnpPH + ".com";
        String expectedString = "http://1thishello1.com";
        TemplateURIGenerator tug = new TemplateURIGenerator(template, providers);
        URI uri = tug.generate(rowIndex, array);
        URI expectedURI = FACTORY.createURI(expectedString);
        assertEquals(true, expectedURI.equals(uri));
    }

    /**
     * Tests the TemplateLiteralGenerator subclass of the TemplateValueGenerator
     */
    @Test
    public void TemplateLiteralGeneratorTest(){
        Literal literal = new IntegerLiteralImpl(BigInteger.ONE);
        String template = literal.getLabel();
        ValueProvider[] providers = new ValueProvider[1];
        RowNumberProvider rnp = new RowNumberProvider();
        providers[0] = rnp;
        String expected = template.replace(rnp.getPlaceholder(), "1");
        TemplateLiteralGenerator tlg = new TemplateLiteralGenerator(literal, providers);
        Literal result = tlg.generate(rowIndex, array);
        Literal expectedLiteral;
        if(literal.getDataType() != null){
            expectedLiteral = FACTORY.createLiteral(expected, literal.getDataType());
        }
        else if(literal.getLanguage().orElse(null) != null){
            expectedLiteral = FACTORY.createLiteral(expected, literal.getLanguage().orElse(null));
        }
        else{
            expectedLiteral = FACTORY.createLiteral(expected);
        }
        assertEquals(true, expectedLiteral.equals(result));
    }

    /**
     * Tests the ConstantValueGenerator class 
     */

    @Test
    public void ConstantValueGeneratorTest(){
        URI uri = FACTORY.createURI("http://1thishello1.com");
        ConstantValueGenerator constantValueGenerator = new ConstantValueGenerator<URI>(uri);
        
        final int rowIndex = -1;
        final String[] rows = {"Not", "used", "by", "method", ":)"};

        Value generatedUri = constantValueGenerator.generate(rowIndex, rows);

        assertEquals(true, generatedUri.equals(uri));
    }

}
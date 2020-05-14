package com.complexible.common.csv;

import org.junit.Test;
import com.complexible.common.csv.provider.*;
import static org.junit.jupiter.api.Assertions.*;


public class ProviderTest {

    private String[] array = new String[]{"Hello", "this", "is", "a", "test"};
    private int rowIndex = 1;

    /**
     * Tests the RowNumberProvider subclass of the Provider abstract class
     */
    @Test
    public void RowNumberProviderTest(){
        RowNumberProvider rnp = new RowNumberProvider();
        String result = rnp.provide(rowIndex, array);
        assertEquals(result, String.valueOf(rowIndex));
    }

    /**
     * Tests the RowValueProvider subclass of the Provider abstract class
     */
    @Test
    public void RowValueProviderTest(){
        RowValueProvider rvp = new RowValueProvider(2);
        String result = rvp.provide(rowIndex, array);
        assertEquals(result, array[2]);
    }

    /**
     * Tests the UUIDProvider subclass of the Provider abstract class
     */
    @Test
    public void UUIDProviderTest(){
        UUIDProvider up = new UUIDProvider();
        String result = up.provide(rowIndex, array);
        assertNotEquals(null, result);
    }
}
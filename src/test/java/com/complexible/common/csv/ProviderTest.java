package com.complexible.common.csv;

import org.junit.Test;
import com.complexible.common.csv.provider.*;
import static org.junit.jupiter.api.Assertions.*;

public class ProviderTest {
    private final String[] array = new String[] { "Hello", "this", "is", "a", "test" };
    private final int rowIndex = 1;

    /**
     * Tests the RowNumberProvider subclass of the Provider abstract class
     */
    @Test
    public void RowNumberProviderTest() {
        RowNumberProvider rowNumberProvider = new RowNumberProvider();
        String result = rowNumberProvider.provide(rowIndex, array);
        String rowIndexToString = String.valueOf(rowIndex);

        assertEquals(result, rowIndexToString);
    }

    /**
     * Tests the RowValueProvider subclass of the Provider abstract class
     */
    @Test
    public void RowValueProviderTest() {
        final int colIndex = 2;
        RowValueProvider rowValueProvider = new RowValueProvider(colIndex);
        String result = rowValueProvider.provide(rowIndex, array);
        String expectedResult = array[colIndex];

        assertEquals(expectedResult, result);
    }

    /**
     * Tests the UUIDProvider subclass of the Provider abstract class
     */
    @Test
    public void UUIDProviderTest() {
        UUIDProvider uuidProvider = new UUIDProvider();
        String result = uuidProvider.provide(rowIndex, array);

        assertNotNull(result);
    }
}
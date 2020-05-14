package com.complexible.common.csv;

import org.junit.Test;
import com.complexible.common.csv.provider.*;
import static org.junit.jupiter.api.Assertions.*;


public class ProviderTest {

    @Test
    public void RowNumberProviderTest(){
        RowNumberProvider rnp = new RowNumberProvider();
        String[] array = new String[]{"Hello", "this", "is", "a", "test"};
        int rowIndex = 1;
        rnp.setHashed(true);
        String result = rnp.provide(rowIndex, array);
        assertEquals(result, array[1]);
    }

}
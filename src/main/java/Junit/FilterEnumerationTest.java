package Junit;

import io.kuy.infozilla.elements.enumeration.Enumeration;
import io.kuy.infozilla.filters.FilterEnumeration;
import org.junit.Before;
import org.junit.Test;
import java.util.List;

import static org.junit.Assert.assertEquals;


public class FilterEnumerationTest {

    private FilterEnumeration filterEnumeration;

    @Before
    public void setUp() {
        filterEnumeration = new FilterEnumeration();
    }

    @Test
    public void testGetCharEnums() {
        String inputText = "A. Item 1\nB. Item 2\nC. Item 3";
        List<Enumeration> charEnums = filterEnumeration.runFilter(inputText);
        assertEquals(1, charEnums.size());
    }

    @Test
    public void testGetNumEnums() {
        String inputText = "1. Item 1\n2) Item 2\n3- Item 3";
        List<Enumeration> numEnums = filterEnumeration.runFilter(inputText);
        assertEquals(1, numEnums.size());
        // Perform more assertions on the enumerations extracted
    }

    @Test
    public void testGetItemizations() {
        String inputText = "- Item 1\n- Item 2\n- Item 3";
        List<Enumeration> itemizations = filterEnumeration.runFilter(inputText);
        assertEquals(1, itemizations.size());
        // Perform more assertions on the enumerations extracted
    }

    @Test
    public void testRunFilter() {
        String inputText = "A Item 1\nB Item 2\n- Item 3";
        List<Enumeration> enumerations = filterEnumeration.runFilter(inputText);
        assertEquals(4, enumerations.size());
        // Perform more assertions on the enumerations extracted
    }

    @Test
    public void testGetOutputText() {
        String inputText = "A Item 1\nB Item 2\nC Item 3";
        filterEnumeration.runFilter(inputText);
        String outputText = filterEnumeration.getOutputText();
        // Perform assertions on the processed output text
    }

    // Add more tests for edge cases, error handling, and other methods as needed
}


package unit;

import io.kuy.infozilla.elements.enumeration.Enumeration;
import io.kuy.infozilla.filters.FilterEnumeration;
import io.kuy.infozilla.filters.FilterTextRemover;
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
    public void testGetWithoutEnums() {
        String inputText = "Input senza enumeration";
        List<Enumeration> charEnums = filterEnumeration.runFilter(inputText);
        assertEquals(0, charEnums.size());
    }

    @Test
    public void testEmptyInput() {
        String inputText = "";
        List<Enumeration> enumerations = filterEnumeration.runFilter(inputText);
        assertEquals(0, enumerations.size());
    }

    @Test
    public void testNonStandardEnumSymbols() {
        String inputText = "A)\n1-\nC]\n";
        List<Enumeration> enumerations = filterEnumeration.runFilter(inputText);
        assertEquals(0, enumerations.size());
    }

    @Test
    public void testMixedEnumsAndItemizations() {
        String inputText = "A. Item 1\n- Item 2\nB. Item 3\nC. Item 4\n";
        List<Enumeration> enumerations = filterEnumeration.runFilter(inputText);
        assertEquals(1, enumerations.size());
    }

    @Test
    public void testMixedCharAndIEmpty() {
        String inputText = "A. Item 1\nB. Item 2\nC. Item 3\n \n \nA. Item 2\nB. Item 2\n";
        List<Enumeration> enumerations = filterEnumeration.runFilter(inputText);
        assertEquals(2, enumerations.size());
    }

    @Test
    public void testMixedNumAndIEmpty() {
        String inputText = "1. Item 1\n2. Item 2\n3. Item 3\n \n \n1. Item 2\n2. Item 2\n";
        List<Enumeration> enumerations = filterEnumeration.runFilter(inputText);
        assertEquals(2, enumerations.size());
    }
    @Test
    public void testMixedItemizationsAndCharAndItemizations() {
        String inputText = "- Item 1\n- Item 2\n- Item 3\nA. Item 3\nB. Item 3\n- Item 2\n- Item 2\n";
        List<Enumeration> enumerations = filterEnumeration.runFilter(inputText);
        assertEquals(3, enumerations.size());
    }

    @Test
    public void testInterleavedEnumsAndItemizations() {
        String inputText = "A. Item 1\n- Item 2\nB. Item 3\n- Item 4\n";
        List<Enumeration> enumerations = filterEnumeration.runFilter(inputText);
        assertEquals(0, enumerations.size());
    }




    @Test
    public void testGetCharEnums() {
        String inputText = "A. Item 1\nB. Item 2\nC. Item 3";
        List<Enumeration> charEnums = filterEnumeration.runFilter(inputText);
        assertEquals(1, charEnums.size());

        Enumeration charEnumeration = charEnums.get(0);
        assertEquals("A. Item 1", charEnumeration.getEnumeration_items().get(0));
        assertEquals("B. Item 2", charEnumeration.getEnumeration_items().get(1));
        assertEquals("C. Item 3", charEnumeration.getEnumeration_items().get(2));
    }

    @Test
    public void testGetNumEnums() {
        String inputText = "1. Item 1\n2. Item 2\n3. Item 3";
        List<Enumeration> numEnums = filterEnumeration.runFilter(inputText);
        assertEquals(1, numEnums.size());

        Enumeration numEnumeration = numEnums.get(0);
        assertEquals("1. Item 1", numEnumeration.getEnumeration_items().get(0));
        assertEquals("2. Item 2", numEnumeration.getEnumeration_items().get(1));
        assertEquals("3. Item 3", numEnumeration.getEnumeration_items().get(2));
    }


    @Test
    public void testGetItemizations() {
        String inputText = "- Item 1\n- Item 2\n- Item 3";
        List<Enumeration> itemizations = filterEnumeration.runFilter(inputText);
        assertEquals(1, itemizations.size());

        Enumeration itemEnumeration = itemizations.get(0);
        assertEquals("- Item 1", itemEnumeration.getEnumeration_items().get(0));
        assertEquals("- Item 2", itemEnumeration.getEnumeration_items().get(1));
        assertEquals("- Item 3", itemEnumeration.getEnumeration_items().get(2));
    }

    @Test
    public void testRunFilterCharEnumsANDItemizations() {
        String inputText = "A. Item 1\nB. Item 2\nC. Item 3\n- Item 4\n- Item 5";
        List<Enumeration> enumerations = filterEnumeration.runFilter(inputText);
        assertEquals(2, enumerations.size());

        Enumeration enumeration = enumerations.get(0);
        assertEquals("A. Item 1", enumeration.getEnumeration_items().get(0));
        assertEquals("B. Item 2", enumeration.getEnumeration_items().get(1));
        assertEquals("C. Item 3", enumeration.getEnumeration_items().get(2));
        assertEquals(3, enumeration.getEnumeration_items().size());

        Enumeration enumeration1 = enumerations.get(1);
        assertEquals("- Item 4", enumeration1.getEnumeration_items().get(0));
        assertEquals("- Item 5", enumeration1.getEnumeration_items().get(1));
        assertEquals(2, enumeration1.getEnumeration_items().size());
    }

    @Test
    public void testRunFilterCharEnumsANDNumEnums() {
        String inputText = "A. Item 1\nB. Item 2\n1. Item 3\n2. Item 4\n3. Item 5";
        List<Enumeration> enumerations = filterEnumeration.runFilter(inputText);
        assertEquals(2, enumerations.size());

        Enumeration enumeration = enumerations.get(0);
        assertEquals("A. Item 1", enumeration.getEnumeration_items().get(0));
        assertEquals("B. Item 2", enumeration.getEnumeration_items().get(1));
        assertEquals(2, enumeration.getEnumeration_items().size());

        Enumeration enumeration1 = enumerations.get(1);
        assertEquals("1. Item 3", enumeration1.getEnumeration_items().get(0));
        assertEquals("2. Item 4", enumeration1.getEnumeration_items().get(1));
        assertEquals("3. Item 5", enumeration1.getEnumeration_items().get(2));
        assertEquals(3, enumeration1.getEnumeration_items().size());
    }

    @Test
    public void testRunFilterCharEnumsANDCharEnums() {
        String inputText = "A. Item 1\nB. Item 2\nC. Item 3\nA. Item 4\nB. Item 5";
        List<Enumeration> enumerations = filterEnumeration.runFilter(inputText);
        assertEquals(2, enumerations.size());

        Enumeration enumeration = enumerations.get(0);
        assertEquals("A. Item 1", enumeration.getEnumeration_items().get(0));
        assertEquals("B. Item 2", enumeration.getEnumeration_items().get(1));
        assertEquals("C. Item 3", enumeration.getEnumeration_items().get(2));
        assertEquals(3, enumeration.getEnumeration_items().size());

        Enumeration enumeration1 = enumerations.get(1);
        assertEquals("A. Item 4", enumeration1.getEnumeration_items().get(0));
        assertEquals("B. Item 5", enumeration1.getEnumeration_items().get(1));
        assertEquals(2, enumeration1.getEnumeration_items().size());
    }

    @Test

    public void testRunFilter2CharEnumsAND2CharEnums() {
        String inputText = "A. Item 1\nB. Item 2\nA. Item 3\nB. Item 4";
        List<Enumeration> enumerations = filterEnumeration.runFilter(inputText);
        assertEquals(2, enumerations.size());

        Enumeration enumeration = enumerations.get(0);
        assertEquals("A. Item 1", enumeration.getEnumeration_items().get(0));
        assertEquals("B. Item 2", enumeration.getEnumeration_items().get(1));
        assertEquals(2, enumeration.getEnumeration_items().size());

        Enumeration enumeration1 = enumerations.get(1);
        assertEquals("A. Item 3", enumeration1.getEnumeration_items().get(0));
        assertEquals("B. Item 4", enumeration1.getEnumeration_items().get(1));
        assertEquals(2, enumeration1.getEnumeration_items().size());
    }

    @Test
    public void testRunFilterItemizationsANDCharEnums() {
        String inputText = "A. Item 4\nB. Item 5\n- Item 1\n- Item 2\n- Item 3";
        List<Enumeration> enumerations = filterEnumeration.runFilter(inputText);
        assertEquals(2, enumerations.size());

        Enumeration enumeration = enumerations.get(0);
        assertEquals("A. Item 4", enumeration.getEnumeration_items().get(0));
        assertEquals("B. Item 5", enumeration.getEnumeration_items().get(1));
        assertEquals(2, enumeration.getEnumeration_items().size());

        Enumeration enumeration1 = enumerations.get(1);
        assertEquals("- Item 1", enumeration1.getEnumeration_items().get(0));
        assertEquals("- Item 2", enumeration1.getEnumeration_items().get(1));
        assertEquals("- Item 3", enumeration1.getEnumeration_items().get(2));
        assertEquals(3, enumeration1.getEnumeration_items().size());

    }


    @Test
    public void testRunFilterNumEnumsANDItemizations() {
        String inputText = "1. Item 1\n2. Item 2\n- Item 3\n- Item 4";
        List<Enumeration> enumerations = filterEnumeration.runFilter(inputText);
        assertEquals(2, enumerations.size());

        Enumeration enumeration = enumerations.get(0);
        assertEquals("1. Item 1", enumeration.getEnumeration_items().get(0));
        assertEquals("2. Item 2", enumeration.getEnumeration_items().get(1));
        assertEquals(2, enumeration.getEnumeration_items().size());

        Enumeration enumeration1 = enumerations.get(1);
        assertEquals("- Item 3", enumeration1.getEnumeration_items().get(0));
        assertEquals("- Item 4", enumeration1.getEnumeration_items().get(1));
        assertEquals(2, enumeration1.getEnumeration_items().size());
    }

    @Test
    public void testRunFilterNumEnumsANDCharEnums() {
        String inputText = "A. Item 1\nB. Item 2\nC. Item 3\n1. Item 1\n2. Item 2\n3. Item 3\nA. Item 1\nB. Item 2\nC. Item 3";
        List<Enumeration> enumerations = filterEnumeration.runFilter(inputText);
        assertEquals(3, enumerations.size());
;
        Enumeration enumeration = enumerations.get(0);
        assertEquals("A. Item 1", enumeration.getEnumeration_items().get(0));
        assertEquals("B. Item 2", enumeration.getEnumeration_items().get(1));
        assertEquals("C. Item 3", enumeration.getEnumeration_items().get(2));
        assertEquals(3, enumeration.getEnumeration_items().size());

        Enumeration enumeration1 = enumerations.get(2);
        assertEquals("1. Item 1", enumeration1.getEnumeration_items().get(0));
        assertEquals("2. Item 2", enumeration1.getEnumeration_items().get(1));
        assertEquals("3. Item 3", enumeration1.getEnumeration_items().get(2));
        assertEquals(3, enumeration1.getEnumeration_items().size());

        Enumeration enumeration2 = enumerations.get(1);
        assertEquals("A. Item 1", enumeration2.getEnumeration_items().get(0));
        assertEquals("B. Item 2", enumeration2.getEnumeration_items().get(1));
        assertEquals("C. Item 3", enumeration2.getEnumeration_items().get(2));
        assertEquals(3, enumeration2.getEnumeration_items().size());

    }

    @Test
    public void testRunFilterNumEnumsANDNumEnums() {
        String inputText = "1. Item 1\n2. Item 2\n3. Item 3\n1. Item 4\n2. Item 5";
        List<Enumeration> enumerations = filterEnumeration.runFilter(inputText);
        assertEquals(2, enumerations.size());

        Enumeration enumeration = enumerations.get(0);
        assertEquals("1. Item 1", enumeration.getEnumeration_items().get(0));
        assertEquals("2. Item 2", enumeration.getEnumeration_items().get(1));
        assertEquals("3. Item 3", enumeration.getEnumeration_items().get(2));
        assertEquals(3, enumeration.getEnumeration_items().size());

        Enumeration enumeration1 = enumerations.get(1);
        assertEquals("1. Item 4", enumeration1.getEnumeration_items().get(0));
        assertEquals("2. Item 5", enumeration1.getEnumeration_items().get(1));
        assertEquals(2, enumeration1.getEnumeration_items().size());
    }

    @Test
    public void testRunFilterCharEnumsANDNumEnumsANDItemizations() {
        String inputText = "A. Item 1\nB. Item 2\ntesto\n1. Item 1\n2. Item 2\n3. Item 3\n- Item 1\n- Item 2\n- Item 3";
        List<Enumeration> enumerations = filterEnumeration.runFilter(inputText);
        assertEquals(3, enumerations.size());

        Enumeration enumeration = enumerations.get(0);
        assertEquals("A. Item 1", enumeration.getEnumeration_items().get(0));
        assertEquals("B. Item 2", enumeration.getEnumeration_items().get(1));
        assertEquals(2, enumeration.getEnumeration_items().size());

        Enumeration enumeration2 = enumerations.get(1);

        assertEquals("1. Item 1", enumeration2.getEnumeration_items().get(0));
        assertEquals("2. Item 2", enumeration2.getEnumeration_items().get(1));
        assertEquals("3. Item 3", enumeration2.getEnumeration_items().get(2));
        assertEquals(3, enumeration2.getEnumeration_items().size());

        Enumeration enumeration3 = enumerations.get(2);
        assertEquals("- Item 1", enumeration3.getEnumeration_items().get(0));
        assertEquals("- Item 2", enumeration3.getEnumeration_items().get(1));
        assertEquals("- Item 3", enumeration3.getEnumeration_items().get(2));
        assertEquals(3, enumeration3.getEnumeration_items().size());

    }

    @Test
    public void testGetOutputText() {
        // Crea un'istanza della tua classe
        FilterEnumeration filter = new FilterEnumeration();
        String inputText = "1. Item 1\n2. Item 2\n- Item 3\n- Item 4";
        List<Enumeration> enums = filter.runFilter(inputText);
        // Chiama il metodo da testare
        String result = filter.getOutputText();

        // Verifica che il risultato sia corretto
        assertEquals("\n\n\n", result);
    }


}


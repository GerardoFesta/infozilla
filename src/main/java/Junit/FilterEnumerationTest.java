package Junit;

import io.kuy.infozilla.elements.enumeration.Enumeration;
import io.kuy.infozilla.filters.FilterEnumeration;
import io.kuy.infozilla.filters.FilterTextRemover;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

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
        String inputText = "1. Item 1\n2) Item 2\n3- Item 3";
        List<Enumeration> numEnums = filterEnumeration.runFilter(inputText);
        assertEquals(1, numEnums.size());

        Enumeration numEnumeration = numEnums.get(0);
        assertEquals("1. Item 1", numEnumeration.getEnumeration_items().get(0));
        assertEquals("2) Item 2", numEnumeration.getEnumeration_items().get(1));
        assertEquals("3- Item 3", numEnumeration.getEnumeration_items().get(2));
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
        assertEquals("- Item 4", enumeration.getEnumeration_items().get(3));
        assertEquals("- Item 5", enumeration.getEnumeration_items().get(4));
    }

    @Test
    public void testRunFilterCharEnumsANDNumEnums() {
        String inputText = "A. Item 1\nB. Item 2\n1. Item 3\n2. Item 4\n2. Item 5";
        List<Enumeration> enumerations = filterEnumeration.runFilter(inputText);
        assertEquals(2, enumerations.size());

        Enumeration enumeration = enumerations.get(0);
        assertEquals("A. Item 1", enumeration.getEnumeration_items().get(0));
        assertEquals("B. Item 2", enumeration.getEnumeration_items().get(1));
        assertEquals("1. Item 3", enumeration.getEnumeration_items().get(2));
        assertEquals("2. Item 4", enumeration.getEnumeration_items().get(3));
        assertEquals("2. Item 5", enumeration.getEnumeration_items().get(4));
    }

    @Test
    //ERRORE: ne trova solo 1
    public void testRunFilterCharEnumsANDCharEnums() {
        String inputText = "A. Item 1\nB. Item 2\nC. Item 3\nA. Item 4\nB. Item 5";
        List<Enumeration> enumerations = filterEnumeration.runFilter(inputText);
        assertEquals(2, enumerations.size());

        Enumeration enumeration = enumerations.get(0);
        assertEquals("A. Item 1", enumeration.getEnumeration_items().get(0));
        assertEquals("B. Item 2", enumeration.getEnumeration_items().get(1));
        assertEquals("C. Item 3", enumeration.getEnumeration_items().get(2));
        assertEquals("A. Item 4", enumeration.getEnumeration_items().get(3));
        assertEquals("B. Item 5", enumeration.getEnumeration_items().get(4));
    }

    @Test
    //ERRORE: ne trova solo 1
    public void testRunFilter2CharEnumsAND2CharEnums() {
        String inputText = "A. Item 1\nB. Item 2\nA. Item 3\n B. Item 4";
        List<Enumeration> enumerations = filterEnumeration.runFilter(inputText);
        assertEquals(2, enumerations.size());

        Enumeration enumeration = enumerations.get(0);
        assertEquals("A. Item 1", enumeration.getEnumeration_items().get(0));
        assertEquals("B. Item 2", enumeration.getEnumeration_items().get(1));
        assertEquals("A. Item 3", enumeration.getEnumeration_items().get(2));
        assertEquals("B. Item 4", enumeration.getEnumeration_items().get(3));
    }

    @Test
    //ERRORE: si aspetta A. Item 4 come il primo e non - Item 1 (2 enumeration le trova)
    public void testRunFilterItemizationsANDCharEnums() {
        String inputText = "- Item 1\n- Item 2\n- Item 3\nA. Item 4\nB. Item 5";
        List<Enumeration> enumerations = filterEnumeration.runFilter(inputText);
        assertEquals(2, enumerations.size());

        Enumeration enumeration = enumerations.get(0);
        assertEquals("- Item 1", enumeration.getEnumeration_items().get(0));
        assertEquals("- Item 2", enumeration.getEnumeration_items().get(1));
        assertEquals("- Item 3", enumeration.getEnumeration_items().get(2));
        assertEquals("A. Item 4", enumeration.getEnumeration_items().get(3));
        assertEquals("B. Item 5", enumeration.getEnumeration_items().get(4));
    }

    @Test
    //ERRORE: si aspetta 1. Item 4 come il primo e non - Item 1 (2 enumeration le trova)
    public void testRunFilterItemizationsANDNumEnums() {
        String inputText = "- Item 1\n- Item 2\n- Item 3\n1. Item 4\n2. Item 5\n3. Item 5\n4. Item 7";
        List<Enumeration> enumerations = filterEnumeration.runFilter(inputText);
        assertEquals(2, enumerations.size());

        Enumeration enumeration = enumerations.get(0);
        assertEquals("- Item 1", enumeration.getEnumeration_items().get(0));
        assertEquals("- Item 2", enumeration.getEnumeration_items().get(1));
        assertEquals("- Item 3", enumeration.getEnumeration_items().get(2));
        assertEquals("1. Item 4", enumeration.getEnumeration_items().get(3));
        assertEquals("2. Item 5", enumeration.getEnumeration_items().get(4));
        assertEquals("3. Item 6", enumeration.getEnumeration_items().get(5));
        assertEquals("4. Item 7", enumeration.getEnumeration_items().get(6));
    }

    @Test
    public void testRunFilterNumEnumsANDItemizations() {
        String inputText = "1. Item 1\n2. Item 2\n- Item 3\n- Item 4";
        List<Enumeration> enumerations = filterEnumeration.runFilter(inputText);
        assertEquals(2, enumerations.size());

        Enumeration enumeration = enumerations.get(0);
        assertEquals("1. Item 1", enumeration.getEnumeration_items().get(0));
        assertEquals("2. Item 2", enumeration.getEnumeration_items().get(1));
        assertEquals("- Item 3", enumeration.getEnumeration_items().get(2));
        assertEquals("- Item 4", enumeration.getEnumeration_items().get(3));
    }

    @Test
    //ERRORE: si aspetta A. Item 4 come il primo e non 1. Item 1 (2 enumeration le trova)
    public void testRunFilterNumEnumsANDCharEnums() {
        String inputText = "1. Item 1\n2. Item 2\n3. Item 3\nA. Item 4\nB. Item 5";
        List<Enumeration> enumerations = filterEnumeration.runFilter(inputText);
        assertEquals(2, enumerations.size());

        Enumeration enumeration = enumerations.get(0);
        assertEquals("1. Item 1", enumeration.getEnumeration_items().get(0));
        assertEquals("2. Item 2", enumeration.getEnumeration_items().get(1));
        assertEquals("3. Item 3", enumeration.getEnumeration_items().get(2));
        assertEquals("A. Item 4", enumeration.getEnumeration_items().get(3));
        assertEquals("B. Item 5", enumeration.getEnumeration_items().get(4));
    }

    @Test
    //ERRORE: ne trova solo 1
    public void testRunFilterNumEnumsANDNumEnums() {
        String inputText = "1. Item 1\n2. Item 2\n3. Item 3\n1. Item 4\n2. Item 5";
        List<Enumeration> enumerations = filterEnumeration.runFilter(inputText);
        assertEquals(2, enumerations.size());

        Enumeration enumeration = enumerations.get(0);
        assertEquals("1. Item 1", enumeration.getEnumeration_items().get(0));
        assertEquals("2. Item 2", enumeration.getEnumeration_items().get(1));
        assertEquals("3. Item 3", enumeration.getEnumeration_items().get(2));
        assertEquals("1. Item 4", enumeration.getEnumeration_items().get(3));
        assertEquals("2. Item 5", enumeration.getEnumeration_items().get(4));
    }

    @Test
    //ERRORE: non salta l'elemento errato
    public void testRunFilter2NumEnumsAND3NumEnums() {
        String inputText = "1. Item 1\n2. Item 2\nNonFaParteDiEnum\n1. Item 3\n2. Item 4\n3. Item 5";
        List<Enumeration> enumerations = filterEnumeration.runFilter(inputText);
        assertEquals(2, enumerations.size());

        Enumeration enumeration = enumerations.get(0);
        assertEquals("1. Item 1", enumeration.getEnumeration_items().get(0));
        assertEquals("2. Item 2", enumeration.getEnumeration_items().get(1));
        assertEquals("1. Item 3", enumeration.getEnumeration_items().get(2));
        assertEquals("2. Item 4", enumeration.getEnumeration_items().get(3));
        assertEquals("3. Item 5", enumeration.getEnumeration_items().get(4));
    }
    @Test
    //ERRORE: si aspetta A. Item 7 come primo, 1. Item 1 come secondo e - Item 5 come terzo
    public void testRunFilterNumEnumsANDItemizationsANDCharEnums() {
        String inputText = "1. Item 1\n2. Item 2\n3. Item 3\n4. Item 4\n- Item 5\n- Item 6\nA. Item 7\nB. Item 8";
        List<Enumeration> enumerations = filterEnumeration.runFilter(inputText);
        assertEquals(3, enumerations.size());

        Enumeration enumeration = enumerations.get(0);
        assertEquals("1. Item 1", enumeration.getEnumeration_items().get(0));
        assertEquals("2. Item 2", enumeration.getEnumeration_items().get(1));
        assertEquals("3. Item 3", enumeration.getEnumeration_items().get(2));
        assertEquals("4. Item 4", enumeration.getEnumeration_items().get(3));
        assertEquals("- Item 5", enumeration.getEnumeration_items().get(4));
        assertEquals("- Item 6", enumeration.getEnumeration_items().get(5));
        assertEquals("A. Item 7", enumeration.getEnumeration_items().get(6));
        assertEquals("B. Item 8", enumeration.getEnumeration_items().get(7));
    }

    @Test
    //ERRORE: ne trova solo 3 (stesso erroe di prima-se metto una in piu al secondo NumEnums funziona)
    public void testRunFilterCharEnumsANDNumEnumsANDItemizations() {
        String inputText = "A. Item 1\nB. Item 2\n6. Item 3\n7. Item 4\n3. Item 5\n4. Item 6\n- Item 7\n- Item 8";
        List<Enumeration> enumerations = filterEnumeration.runFilter(inputText);
        assertEquals(4, enumerations.size());

        Enumeration enumeration = enumerations.get(0);
        assertEquals("A. Item 1", enumeration.getEnumeration_items().get(0));
        assertEquals("B. Item 2", enumeration.getEnumeration_items().get(1));
        assertEquals("6. Item 3", enumeration.getEnumeration_items().get(2));
        assertEquals("7. Item 4", enumeration.getEnumeration_items().get(3));
        assertEquals("3. Item 5", enumeration.getEnumeration_items().get(4));
        assertEquals("4. Item 6", enumeration.getEnumeration_items().get(5));
        assertEquals("- Item 7", enumeration.getEnumeration_items().get(6));
        assertEquals("- Item 8", enumeration.getEnumeration_items().get(7));
    }

    private FilterTextRemover textRemover;
    @Test
    //ERRORE: se non c'è \n alla fine dell'ultimo enum lo elimina (l'ultimo resta)
    public void testGetOutputText() {
        // Crea un mock della classe textRemover
        textRemover = Mockito.mock(FilterTextRemover.class);

        // Crea un'istanza della tua classe
        FilterEnumeration filter = new FilterEnumeration();
        String inputText = "1. Item 1\n2. Item 2\n- Item 3\n- Item 4";
        List<Enumeration> enums = filter.runFilter(inputText);
        // Chiama il metodo da testare
        String result = filter.getOutputText();

        // Verifica che il risultato sia corretto
        assertEquals("", result);
    }


}


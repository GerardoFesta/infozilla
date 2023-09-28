package Junit;
import io.kuy.infozilla.filters.FilterTextRemover;
import org.junit.Assert;

import org.junit.*;


import io.kuy.infozilla.filters.FilterPatches;


import java.util.List;
import io.kuy.infozilla.elements.patch.Patch;


import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;


public class FilterPatchesTest {
    private FilterPatches filter ;

    @BeforeEach
    public void setUp() {
        filter = new FilterPatches();
    }

    @Test
    public void testRunFilterWith_TwoPatches() {
        String inputText = "Index: file_modificato.txt\n"
                + "===================================================================\n"
                + "--- file_modificato.txt\t(revision 123)\n"
                + "+++ file_modificato.txt\t(revision 124)\n"
                + "@@ -1,6 +1,6 @@\n\n"
                + "Riga originale 1\n"
                + "Riga originale 2\n"
                + "Riga originale 3\n"
                + "+Riga modificata 1\n"
                + "+Riga modificata 2\n"
                + "+Riga aggiunta 2.5\n"
                + "Riga originale 4\n"
                + "-Riga originale 5\n"
                + "+Riga modificata 3\n"
                + "Riga originale 6\n"
                + "\n"
                + "@@ -11,16 +11,16 @@\n"
                + "-Riga originale 11\n"
                + "-Riga originale 12\n"
                + "-Riga originale 13\n"
                + "+Riga modificata 11\n"
                + "+Riga modificata 12\n"
                + "+Riga aggiunta 12.5\n"
                + "Riga originale 14\n"
                + "-Riga originale 15\n"
                + "+Riga modificata 13\n"
                + "Riga originale 16\n"
                +"cssdfsfsfsfsfsfsfs\n"
                +"Index: file_modificato.txt\n"
                + "===================================================================\n"
                + "--- file_modificato.txt\t(revision 123)\n"
                + "+++ file_modificato.txt\t(revision 124)\n"
                + "@@ -1,6 +1,6 @@\n\n" ;


        List<Patch> patches = filter.runFilter(inputText);
        Assert.assertNotNull(patches);
        Assert.assertEquals(2, patches.size());
        Assert.assertEquals(2,patches.get(0).getHunks().size());
        Assert.assertEquals(1,patches.get(1).getHunks().size());
    }
    @Test
    public void testRunFilterWith_ThreePatches() {
        String inputText = "Index: file_modificato.txt\n"
                + "===================================================================\n"
                + "--- file_modificato.txt\t(revision 123)\n"
                + "+++ file_modificato.txt\t(revision 124)\n"
                + "@@ -1,6 +1,6 @@\n\n"
                + "Riga originale 1\n"
                + "Riga originale 2\n"
                + "Riga originale 3\n"
                + "+Riga modificata 1\n"
                + "+Riga modificata 2\n"
                + "+Riga aggiunta 2.5\n"
                + "Riga originale 4\n"
                + "-Riga originale 5\n"
                + "+Riga modificata 3\n"
                + "Riga originale 6\n"
                + "\n"
                + "@@ -11,16 +11,16 @@\n"
                + "-Riga originale 11\n"
                + "-Riga originale 12\n"
                + "-Riga originale 13\n"
                + "+Riga modificata 11\n"
                + "+Riga modificata 12\n"
                + "+Riga aggiunta 12.5\n"
                + "Riga originale 14\n"
                + "-Riga originale 15\n"
                + "+Riga modificata 13\n"
                + "Riga originale 16\n"
                +"cssdfsfsfsfsfsfsfs\n"
                +"Index: file_modificato.txt\n"
                + "===================================================================\n"
                + "--- file_modificato.txt\t(revision 123)\n"
                + "+++ file_modificato.txt\t(revision 124)\n"
                + "@@ -1,6 +1,6 @@\n\n"
                +"Index: file_modificato.txt\n"
                + "===================================================================\n"
                + "--- file_modificato.txt\t(revision 123)\n"
                + "+++ file_modificato.txt\t(revision 124)\n"
                + "@@ -1,6 +1,6 @@\n\n";


        List<Patch> patches = filter.runFilter(inputText);
        Assert.assertEquals(3, patches.size());
        Assert.assertEquals(2,patches.get(0).getHunks().size());
        Assert.assertEquals(1,patches.get(1).getHunks().size());
        Assert.assertEquals(1,patches.get(2).getHunks().size());
    }
    @Test
    public void testRunFilterWithoutPatches() {
        // Test input text without patches
        String inputText = "Index: file_modificato.txt\n"
                + "===================================================================\n";

        List<Patch> patches = filter.runFilter(inputText);

        //Assert.assertNotNull(patches);
        Assert.assertEquals(0, patches.size());
    }
    @Test
    public void testRunFilterWithoutPatches2() {
        // Test input text without patches
        String inputText = "Ihghghgh";
        List<Patch> patches = filter.runFilter(inputText);

        Assert.assertNotNull(patches);
        Assert.assertEquals(0, patches.size());
    }




        @Test
        public void testGetOutputText() {
            FilterPatches filter = new FilterPatches();
            String inputText = "Test output";
            List<Patch> patches = filter.runFilter(inputText);

            String result = filter.getOutputText();


            assertEquals("Test output", result);
        }



    @Test
    public void testGetOutputText2() {
        // Crea un'istanza della tua classe
        FilterPatches filter = new FilterPatches();
        String inputText = "Index: file_modificato.txt\n"
                + "===================================================================\n"
                + "--- file_modificato.txt\t(revision 123)\n"
                + "+++ file_modificato.txt\t(revision 124)\n"
                + "@@ -1,6 +1,6 @@\n\n"
                + " Riga originale 1\n"
                + " Riga originale 2\n"
                + " Riga originale 3\n"
                + "+Riga modificata 1\n"
                + "+Riga modificata 2\n"
                + "+Riga aggiunta 2.5\n"
                + " Riga originale 4\n"
                + "-Riga originale 5\n"
                + "+Riga modificata 3\n"
                + "Riga originale 6\n"
                + "\n"
                + "@@ -11,16 +11,16 @@\n"
                + "-Riga originale 11\n"
                + "-Riga originale 12\n"
                + "-Riga originale 13\n"
                + "+Riga modificata 11\n"
                + "+Riga modificata 12\n"
                + "+Riga aggiunta 12.5\n"
                + " Riga originale 14\n"
                + "-Riga originale 15\n"
                + "+Riga modificata 13\n"
                + " Riga originale 16\n"
                +"Index: file_modificato1.txt\n"
                + "===================================================================\n"
                + "--- file_modificato.txt\t(revision 123)\n"
                + "+++ file_modificato.txt\t(revision 124)\n"
                + "@@ -1,6 +1,6 @@\n\n"
                + " Riga originale 1\n"
                + " Riga originale 2\n"
                + " Riga originale 3\n"
                +"Index: file_modificato2.txt\n"
                + "===================================================================\n"
                + "--- file_modificato.txt\t(revision 123)\n"
                + "+++ file_modificato.txt\t(revision 124)\n"
                + "@@ -1,6 +1,6 @@\n\n"
                + " Riga originale 12\n"
                + " Riga originale 22\n"
                + " Riga originale 55\n"
                + "diff --git a/file_originale.txt b/file_modificato.txt\n"
                + "index a1b2c3d..e4f5g6h 100644\n"
                + "--- a/file_originale.txt\n"
                + "+++ b/file_modificato.txt\n"
                + "@@ -1,6 +1,6 @@\n"
                + " Questo è un file di esempio.\n"
                + "-Riga originale 1\n"
                + "-Riga originale 2\n"
                + "-Riga originale 3\n"
                + "+Riga modificata 1\n"
                + "+Riga modificata 2\n"
                + "+Riga aggiunta 2.5\n"
                + " Riga originale 4\n"
                + "-Riga originale 5\n"
                + "+Riga modificata 3\n"
                + " Riga originale 6\n"
                + " Riga originale 7\n"
                + " Riga originale 8\n";

        List<Patch> patches = filter.runFilter(inputText);
        // Chiama il metodo da testare
        String result = filter.getOutputText();

        // Verifica che il risultato sia corretto
        assertEquals("", result.replaceAll("\n",""));
    }

    @Test
    public void testGetOutputText3() {
        FilterPatches filter = new FilterPatches();
        String inputText = "Index: file_modificato.txt\n"
                + "===================================================================\n"
                + "--- file_modificato.txt\t(revision 123)\n"
                + "+++ file_modificato.txt\t(revision 124)\n"
                + "@@ -1,6 +1,6 @@\n\n"
                + " Riga originale 1\n"
                + " Riga originale 2\n"
                + " Riga originale 3\n"
                + "+Riga modificata 1\n"
                + "+Riga modificata 2\n"
                + "+Riga aggiunta 2.5\n"
                + " Riga originale 4\n"
                + "-Riga originale 5\n"
                + "+Riga modificata 3\n"
                + " Riga originale 6\n"
                + "\n";

        List<Patch> patches = filter.runFilter(inputText);

        String result = filter.getOutputText();


        assertEquals("", result.replaceAll("\n",""));
    }


}
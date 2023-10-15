package Junit;


import io.kuy.infozilla.elements.patch.Patch;
import io.kuy.infozilla.filters.PatchParser;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class PatchParserTest {
    private PatchParser patchParser;

    @Before
    public void setUp() {
        patchParser = new PatchParser();
    }


    @Test
    /*
       Test 1 patch with 2 hunk header
       RESULT: Identified the patch with the index and the original and modified files.
       Tested the text within the hunks.

       **Issue: If there is a space after a hunk's header, the subsequent text is not captured.


     */
    public void testParseForPatches() {
        String inputText = "Index: file_modificato.txt\n" +
                "===================================================================\n" +
                "--- file_modificato.txt\t \n" +
                "+++ file_modificato.txt\t\n" +
                "@@ -1,6 +1,6 @@\n\n" +
                " Riga originale 1\n" +
                " Riga originale 2\n" +
                " Riga originale 3\n" +
                "+Riga modificata 1\n" +
                "+Riga modificata 2\n" +
                "\n" +
                "@@ -11,16 +11,16 @@\n" +
                "-Riga originale 11\n" +
                "-Riga originale 12\n" +
                "-Riga originale 13\n" +
                "+Riga modificata 11\n" +
                "+Riga modificata 12";

        List<Patch> patches = patchParser.parseForPatches(inputText);

        // Perform assertions to test the results
        assertEquals(1, patches.size());


        assertEquals("file_modificato.txt", patches.get(0).getIndex());
        assertEquals("file_modificato.txt", patches.get(0).getOriginalFile());
        assertEquals("file_modificato.txt", patches.get(0).getModifiedFile());
        assertEquals(2, patches.get(0).getHunks().size()); // Assuming there is 1 hunk in the first patch
        assertEquals("\n-Riga originale 11\n" +
                        "\n-Riga originale 12\n" +
                        "\n-Riga originale 13\n" +
                        "\n+Riga modificata 11\n" +
                        "\n+Riga modificata 12"
                , patches.get(0).getHunks().get(1).getText().replaceAll("\r", ""));


        assertEquals("\n\n\n Riga originale 1\n" +
                "\n Riga originale 2\n" +
                "\n Riga originale 3\n" +
                "\n+Riga modificata 1\n" +
                "\n+Riga modificata 2\n\n", patches.get(0).getHunks().get(0).getText().replaceAll("\r", ""));

    }

    @Test
    public void testPatchWithLeadingTrailingWhitespace() {
        String inputText = "Index: file1.txt\n"
                +"================================\n"
                + "--- file1.txt\n"
                + "+++ file1.txt\n"
                + "  \t   \n" //  Leading whitespace
                + "@@ -1,5 +1,5 @@\n"
                + " This is a test.\n"
                + "+Added line.\n"
                + " More changes.\n"
                + " The end.\n"
                + "\n\t   \n"; // Trailing whitespace

        PatchParser patchParser = new PatchParser();
        List<Patch> patches = patchParser.parseForPatches(inputText);
        assertEquals(1, patches.size());

        Patch patch = patches.get(0);


        assertEquals("\n This is a test.\n"
                + "\n+Added line.\n"
                + "\n More changes.\n"
                + "\n The end.\n\n\n", patch.getHunks().get(0).getText().replaceAll("\r", ""));
    }

    @Test
    public void testDiffPatch1(){
        String inputText = "diff --git a/file_originale.txt b/file_modificato.txt\n"
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
        PatchParser patchParser = new PatchParser();
        List<Patch> patches = patchParser.parseForPatches(inputText);
        assertEquals(1,patches.size());
        Patch patch = patches.get(0);
        assertEquals(1,patch.getHunks().size());

    }

    @Test
    public void testDiffPatchHunks() {
        String inputText = "Index: file1.txt\n"
                +"===========================\n"
                + "--- file1.txt\n"
                + "+++ file1.txt\n"
                + "@@ -1,6 +1,6 @@\n"
                + " This is a test.\n"
                + "+Added line.\n"
                + " \n" // Whitespace inside the hunk
                + " More changes.\n"
                + "-Removed line.\n"
                + " The end.\n"
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
                + "+Riga modificata 3\n";

        PatchParser patchParser = new PatchParser();
        List<Patch> patches = patchParser.parseForPatches(inputText);


        Patch patch = patches.get(0);
        assertEquals(1,patch.getHunks().size());
        patch = patches.get(1);
        assertEquals(1,patch.getHunks().size());

    }



    @Test
    public void testParsePatchWithWhitespace() {
        String inputText = "Index: file1.txt\n"
                +"===========================\n"
                + "--- file1.txt\n"
                + "+++ file1.txt\n"
                + "@@ -1,6 +1,6 @@\n"
                + " This is a test.\n\n"
                + "+Added line.\n"
                + " More changes.\n"
                + "-Removed line.\n"
                + " The end.\n";
        PatchParser patchParser = new PatchParser();
        List<Patch> patches = patchParser.parseForPatches(inputText);


        // Verify the content of the patch
        Patch patch = patches.get(0);
        assertEquals("\n This is a test.\n\n\n"
                + "\n+Added line.\n"
                + "\n More changes.\n"
                + "\n-Removed line.\n"
                + "\n The end.",patch.getHunks().get(0).getText().replaceAll("\r", ""));
        //RESULT:"THIS IS A TEST "

    }

    @Test
    public void testParsePatch_TextWithoutLeadingWhitespace() {
        String inputText = "Index: file1.txt\n"
                +"===========================\n"
                + "--- file1.txt\n"
                + "+++ file1.txt\n"
                + "@@ -1,6 +1,6 @@\n"
                + " This is a test.\n"
                + "+Added line.\n"
                + "More changes.\n"
                + "-Removed line.\n"
                + " The end.\n";
        PatchParser patchParser = new PatchParser();
        List<Patch> patches = patchParser.parseForPatches(inputText);

        // Verify that exactly one patch has been found.
        assertEquals(1, patches.size());
        // Verify the content of the patch
        Patch patch = patches.get(0);
        assertEquals("\n This is a test.\n"
                + "\n+Added line.\n\n"
                + "\n-Removed line.\n"
                + "\n The end.",patch.getHunks().get(0).getText().replaceAll("\r", ""));

    }


    @Test
    public void testParsePatch_WithLeadingWhitespace_hunk() {
        String inputText = "Index: file1.txt\n"
                +"===========================\n"
                + "--- file1.txt\n"
                + "+++ file1.txt\n"
                + " @@ -1,6 +1,6 @@\n"
                + " This is a test.\n"
                + "+Added line.\n"
                + " More changes.\n"
                + "-Removed line.\n"
                + " The end.\n";
        PatchParser patchParser = new PatchParser();
        List<Patch> patches = patchParser.parseForPatches(inputText);

        // Verify that exactly one patch has been found.
        assertEquals(0, patches.size());


    }

    @Test
    public void testParsePatch_WithLeadingWhitespace_Index() {
        String inputText = " Index: file1.txt\n"
                +"===========================\n"
                + "--- file1.txt\n"
                + "+++ file1.txt\n"
                + "@@ -1,6 +1,6 @@\n"
                + " This is a test.\n"
                + "+Added line.\n"
                + " More changes.\n"
                + "-Removed line.\n"
                + " The end.\n";
        PatchParser patchParser = new PatchParser();
        List<Patch> patches = patchParser.parseForPatches(inputText);

        // Verify that exactly one patch has been found.
        assertEquals(0, patches.size());

    }

    @Test
    public void testParsePatch_WithLeadingWhitespace_OriginalFile(){
        String inputText = "Index: file1.txt\n"
                +"====\n"
                + " --- file1.txt\n"
                + "+++ file1.txt\n"
                + "@@ -1,6 +1,6 @@\n"
                + " This is a test.\n"
                + "+Added line.\n"
                + " More changes.\n"
                + "-Removed line.\n"
                + " The end.\n";
        PatchParser patchParser = new PatchParser();
        List<Patch> patches = patchParser.parseForPatches(inputText);

        // Verify that exactly one patch has been found.
        assertEquals(0, patches.size());


    }

    @Test
    public void testParsePatch_SeparationLines_Number(){
        String inputText = "Index: file1.txt\n"
                +"===\n"
                + "--- file1.txt\n"
                + "+++ file1.txt\n"
                + "@@ -1,6 +1,6 @@\n"
                + " This is a test.\n"
                + "+Added line.\n"
                + " More changes.\n"
                + "-Removed line.\n"
                + " The end.\n";
        PatchParser patchParser = new PatchParser();
        List<Patch> patches = patchParser.parseForPatches(inputText);

        // Verify that exactly one patch has been found.
        assertEquals(0, patches.size());

    }
    //: 4 separation lines "====\n"  the leading space
    @Test
    public void testParsePatch_SeparationLines_MinimumNumber(){
        String inputText = "Index: file1.txt\n"
                +"====\n"
                + "--- file1.txt\n"
                + "+++ file1.txt\n"
                + "@@ -1,6 +1,6 @@\n"
                + " This is a test.\n"
                + "+Added line.\n"
                + " More changes.\n"
                + "-Removed line.\n"
                + " The end.\n";
        PatchParser patchParser = new PatchParser();
        List<Patch> patches = patchParser.parseForPatches(inputText);

        // Verify that exactly one patch has been found.
        assertEquals(1, patches.size());

    }

}

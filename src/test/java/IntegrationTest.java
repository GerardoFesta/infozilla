import io.kuy.infozilla.elements.enumeration.Enumeration;
import io.kuy.infozilla.filters.*;
import org.junit.jupiter.api.Test;
import static org.junit.Assert.*;
import io.kuy.infozilla.elements.patch.Patch;
import io.kuy.infozilla.elements.sourcecode.java.CodeRegion;
import io.kuy.infozilla.elements.stacktrace.java.StackTrace;

import java.util.List;

public class IntegrationTest {


    private FilterPatches patchFilter;
    private FilterStackTraceJAVA stacktraceFilter;
    private FilterSourceCodeJAVA sourcecodeFilter;
    private FilterEnumeration enumFilter;

    private String inputText ="if (isJavaProject) {\n"
            + "IJavaProject jProject = JavaCore.create(project);\n"
            + "jProject.setRawClasspath(new IClasspathEntry[0], project.getFullPath(), monitor);\n"
            + "modelIds.add(model.getPluginBase().getId());\n"
            + "}\n"
            + "1) perform the import in only one operation as suggested by bug 31592\n"
            + "2) add a temporary .classpth to the project to avoid markers creation while\n"
            + "org.eclipse.core.internal.resources.ResourceException:\n"
            + "Resource /org.eclipse.debug.core/.classpath is not local.\n"
            + "at org.eclipse.core.internal.resources.Resource.checkLocal(Resource.java:313)\n"
            + "at org.eclipse.core.internal.resources.File.getContents(File.java:213)\n"
            + "at org.eclipse.jdt.internal.core.Util.getResourceContentsAsByteArray(Util.java:671)\n"
            +"Index: file_modificato.txt\n"
            + "===================================================================\n"
            + "--- file_modificato.txt\t(revision 123)\n"
            + "+++ file_modificato.txt\t(revision 124)\n"
            + "@@ -1,6 +1,6 @@\n"
            + " Riga originale 1\n"
            + " Riga originale 2\n"
            + " Riga originale 3\n"
            + "+Riga modificata 1\n"
            + "+Riga modificata 2\n"
            + "+Riga aggiunta 2.5\n"
            + " Riga originale 4\n"
            + "-Riga originale 5\n"
            + "+Riga modificata 3\n"
            + " Riga originale 6\n";
    private String outputText = "";

    @Test
    public void tcTraceSource() {
        FilterChainEclipse filterChain = new FilterChainEclipse(inputText, false , true, true, false);
        List<Patch> patches = filterChain.getPatches();
        List<StackTrace> traces = filterChain.getTraces();
        List<CodeRegion> regions = filterChain.getRegions();
        List<Enumeration> enumerations = filterChain.getEnumerations();

        // Verifica che le liste non siano nulle
        assertTrue(patches.isEmpty());
        assertTrue(enumerations.isEmpty());

        assertFalse(traces.isEmpty());
        assertFalse(regions.isEmpty());

        //source
        assertEquals(1,regions.size());
        assertEquals("if (isJavaProject) {\n"
                + "IJavaProject jProject = JavaCore.create(project);\n"
                + "jProject.setRawClasspath(new IClasspathEntry[0], project.getFullPath(), monitor);\n"
                + "modelIds.add(model.getPluginBase().getId());\n"
                + "}",regions.get(0).text);

        //trace
        assertEquals(1,traces.size());
        assertEquals("org.eclipse.core.internal.resources.ResourceException",traces.get(0).getException());
        assertEquals(":\n"+"Resource /org.eclipse.debug.core/.classpath is not local.",traces.get(0).getReason());
        assertEquals("org.eclipse.core.internal.resources.Resource.checkLocal(Resource.java:313)",traces.get(0).getFrames().get(0));
        assertEquals("org.eclipse.core.internal.resources.File.getContents(File.java:213)",traces.get(0).getFrames().get(1));
        assertEquals("org.eclipse.jdt.internal.core.Util.getResourceContentsAsByteArray(Util.java:671)",traces.get(0).getFrames().get(2));

         outputText ="\n1) perform the import in only one operation as suggested by bug 31592\n"
                 + "2) add a temporary .classpth to the project to avoid markers creation while\n\n"
                 +"Index: file_modificato.txt\n"
                 + "===================================================================\n"
                 + "--- file_modificato.txt\t(revision 123)\n"
                 + "+++ file_modificato.txt\t(revision 124)\n"
                 + "@@ -1,6 +1,6 @@\n"
                 + " Riga originale 1\n"
                 + " Riga originale 2\n"
                 + " Riga originale 3\n"
                 + "+Riga modificata 1\n"
                 + "+Riga modificata 2\n"
                 + "+Riga aggiunta 2.5\n"
                 + " Riga originale 4\n"
                 + "-Riga originale 5\n"
                 + "+Riga modificata 3\n"
                 + " Riga originale 6\n";

        assertEquals(outputText,filterChain.getOutputText());

    }



    @Test
    public void tcTraceEnum() {
        FilterChainEclipse filterChain = new FilterChainEclipse(inputText, false, true, false, true);
        List<Patch> patches = filterChain.getPatches();
        List<StackTrace> traces = filterChain.getTraces();
        List<CodeRegion> regions = filterChain.getRegions();
        List<Enumeration> enumerations = filterChain.getEnumerations();

        // Verifica che le liste non siano nulle
        assertTrue(patches.isEmpty());
        assertFalse(enumerations.isEmpty());
        assertFalse(traces.isEmpty());
        assertTrue(regions.isEmpty());


        //enumerations
        assertEquals(1,enumerations.size());
        assertEquals("1) perform the import in only one operation as suggested by bug 31592",enumerations.get(0).getEnumeration_items().get(0));
        assertEquals("2) add a temporary .classpth to the project to avoid markers creation while",enumerations.get(0).getEnumeration_items().get(1));

        //trace
        assertEquals(1,traces.size());
        assertEquals("org.eclipse.core.internal.resources.ResourceException",traces.get(0).getException());
        assertEquals(":\n"+"Resource /org.eclipse.debug.core/.classpath is not local.",traces.get(0).getReason());
        assertEquals("org.eclipse.core.internal.resources.Resource.checkLocal(Resource.java:313)",traces.get(0).getFrames().get(0));
        assertEquals("org.eclipse.core.internal.resources.File.getContents(File.java:213)",traces.get(0).getFrames().get(1));
        assertEquals("org.eclipse.jdt.internal.core.Util.getResourceContentsAsByteArray(Util.java:671)",traces.get(0).getFrames().get(2));

        outputText ="if (isJavaProject) {\n"
                + "IJavaProject jProject = JavaCore.create(project);\n"
                + "jProject.setRawClasspath(new IClasspathEntry[0], project.getFullPath(), monitor);\n"
                + "modelIds.add(model.getPluginBase().getId());\n"
                + "}\n\n\n\n"
                +"Index: file_modificato.txt\n"
                + "===================================================================\n"
                + "--- file_modificato.txt\t(revision 123)\n"
                + "+++ file_modificato.txt\t(revision 124)\n"
                + "@@ -1,6 +1,6 @@\n"
                + " Riga originale 1\n"
                + " Riga originale 2\n"
                + " Riga originale 3\n"
                + "+Riga modificata 1\n"
                + "+Riga modificata 2\n"
                + "+Riga aggiunta 2.5\n"
                + " Riga originale 4\n"
                + "-Riga originale 5\n"
                + "+Riga modificata 3\n"
                + " Riga originale 6\n";

        assertEquals(outputText,filterChain.getOutputText());

    }


    @Test
    public void tcTracePatch() {
        FilterChainEclipse filterChain = new FilterChainEclipse(inputText, true, true, false, false);
        List<Patch> patches = filterChain.getPatches();
        List<StackTrace> traces = filterChain.getTraces();
        List<CodeRegion> regions = filterChain.getRegions();
        List<Enumeration> enumerations = filterChain.getEnumerations();

        // Verifica che le liste non siano nulle
        assertFalse(patches.isEmpty());
        assertTrue(enumerations.isEmpty());

        assertFalse(traces.isEmpty());
        assertTrue(regions.isEmpty());

        //patch
        assertEquals(1,patches.size());
        assertEquals("file_modificato.txt",patches.get(0).getOriginalFile());
        assertEquals("file_modificato.txt",patches.get(0).getModifiedFile());
        assertEquals("file_modificato.txt",patches.get(0).getIndex());
        assertEquals(1,patches.get(0).getHunks().size());
        assertEquals(" Riga originale 1"+" Riga originale 2"+ " Riga originale 3"+"+Riga modificata 1"+"+Riga modificata 2"+"+Riga aggiunta 2.5"+ " Riga originale 4"+ "-Riga originale 5"+ "+Riga modificata 3"+" Riga originale 6"
                ,patches.get(0).getHunks().get(0).getText().replaceAll("\r", "").replaceAll("\n",""));

        //trace
        assertEquals(1,traces.size());
        assertEquals("org.eclipse.core.internal.resources.ResourceException",traces.get(0).getException());
        assertEquals(":\n"+"Resource /org.eclipse.debug.core/.classpath is not local.",traces.get(0).getReason());
        assertEquals("org.eclipse.core.internal.resources.Resource.checkLocal(Resource.java:313)",traces.get(0).getFrames().get(0));
        assertEquals("org.eclipse.core.internal.resources.File.getContents(File.java:213)",traces.get(0).getFrames().get(1));
        assertEquals("org.eclipse.jdt.internal.core.Util.getResourceContentsAsByteArray(Util.java:671)",traces.get(0).getFrames().get(2));

        outputText ="if (isJavaProject) {\n"
                + "IJavaProject jProject = JavaCore.create(project);\n"
                + "jProject.setRawClasspath(new IClasspathEntry[0], project.getFullPath(), monitor);\n"
                + "modelIds.add(model.getPluginBase().getId());\n"
                + "}\n"
                + "1) perform the import in only one operation as suggested by bug 31592\n"
                + "2) add a temporary .classpth to the project to avoid markers creation while\n\n\n";

        assertEquals(outputText,filterChain.getOutputText());


    }



    @Test
    public void tcSourcePatch(){

        FilterChainEclipse filterChain = new FilterChainEclipse(inputText, true, false, true, false);
        List<Patch> patches = filterChain.getPatches();
        List<StackTrace> traces = filterChain.getTraces();
        List<CodeRegion> regions = filterChain.getRegions();
        List<Enumeration> enumerations = filterChain.getEnumerations();

        // Verifica che le liste non siano nulle
        assertFalse(patches.isEmpty());
        assertTrue(enumerations.isEmpty());

        assertTrue(traces.isEmpty());
        assertFalse(regions.isEmpty());

        //patch
        assertEquals(1,patches.size());
        assertEquals("file_modificato.txt",patches.get(0).getOriginalFile());
        assertEquals("file_modificato.txt",patches.get(0).getModifiedFile());
        assertEquals("file_modificato.txt",patches.get(0).getIndex());
        assertEquals(1,patches.get(0).getHunks().size());
        assertEquals(" Riga originale 1"+" Riga originale 2"+ " Riga originale 3"+"+Riga modificata 1"+"+Riga modificata 2"+"+Riga aggiunta 2.5"+ " Riga originale 4"+ "-Riga originale 5"+ "+Riga modificata 3"+" Riga originale 6"
                ,patches.get(0).getHunks().get(0).getText().replaceAll("\r", "").replaceAll("\n",""));

        //source
        assertEquals(1,regions.size());
        assertEquals("if (isJavaProject) {\n"
                + "IJavaProject jProject = JavaCore.create(project);\n"
                + "jProject.setRawClasspath(new IClasspathEntry[0], project.getFullPath(), monitor);\n"
                + "modelIds.add(model.getPluginBase().getId());\n"
                + "}",regions.get(0).text);


        outputText ="\n1) perform the import in only one operation as suggested by bug 31592\n"
                + "2) add a temporary .classpth to the project to avoid markers creation while\n"
                + "org.eclipse.core.internal.resources.ResourceException:\n"
                + "Resource /org.eclipse.debug.core/.classpath is not local.\n"
                + "at org.eclipse.core.internal.resources.Resource.checkLocal(Resource.java:313)\n"
                + "at org.eclipse.core.internal.resources.File.getContents(File.java:213)\n"
                + "at org.eclipse.jdt.internal.core.Util.getResourceContentsAsByteArray(Util.java:671)\n\n";

        assertEquals(outputText,filterChain.getOutputText());

    }



}
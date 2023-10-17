package unit;

import static org.junit.Assert.*;

import io.kuy.infozilla.elements.sourcecode.java.CodeRegion;
import io.kuy.infozilla.filters.FilterSourceCodeJAVA;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.net.URISyntaxException;
import java.util.List;

public class FilterSourceCodeJavaTest {
    private FilterSourceCodeJAVA filter;

    @Before
    public void setUp() {
        filter = new FilterSourceCodeJAVA(FilterStackTraceJAVATest.class.getResource("/Java_CodeDB.txt"));;
    }

    @Test
    public void testDefaultConstructor() {
        assertNotNull(filter);
    }

    @Test
    public void testConstructorWithFileURL() {
        FilterSourceCodeJAVA filterWithFile = new FilterSourceCodeJAVA(FilterSourceCodeJavaTest.class.getResource("/Java_CodeDB.txt"));
        assertNotNull(filterWithFile);
    }



    @Test
    public void testConstructorWithFilename() {
        try {
            File file = new File(FilterSourceCodeJavaTest.class.getResource("/Java_CodeDB.txt").toURI());
            String path = file.toURI().getPath();
            FilterSourceCodeJAVA filterWithFile = new FilterSourceCodeJAVA(path);
            assertNotNull(filterWithFile);
        } catch (URISyntaxException e) {
            fail();
        }

    }

    @Test
    public void testRunFilterWithMinimalSet() {
        String inputText = "if (condition){\n" +
                "doSomething();\n" +
                "}\n" +
                "else{\n" +
                "doSomethingElse();\n" +
                "}";
        List<CodeRegion> codeRegions = filter.runFilter(inputText);
        assertNotNull(codeRegions);
        assertEquals(2, codeRegions.size());
        assertEquals("ifstatement", codeRegions.get(0).keyword);
        assertEquals("if (condition){\n" +
                "doSomething();\n" +
                "}", codeRegions.get(0).text);
        assertEquals("elsestatement", codeRegions.get(1).keyword);
        assertEquals("else{\n" +
                "doSomethingElse();\n" +
                "}", codeRegions.get(1).text);
        //Se metti sulla stessa riga, la regular expression per l'else statement prende anche ciò che c'è prima
    }

    @Test
    public void testRunFilterWithIncompleteSyntax() {
        String inputText = "import java.util.*;\npublic class Test { int x; ";
        List<CodeRegion> codeRegions = filter.runFilter(inputText);
        assertNotNull(codeRegions);
        assertEquals(2, codeRegions.size());
        assertEquals("import", codeRegions.get(0).keyword);
        assertEquals("import java.util.*;", codeRegions.get(0).text);
        assertEquals("class", codeRegions.get(1).keyword);
        assertEquals("public class Test {", codeRegions.get(1).text);
        //CORRETTO PERCHE' SE NON TROVA PARENTESI GRAFFA CHIUSA, RICONOSCE IL TOKEN MA METTE COME END LA POSIZIONE DELLA PAR. APERTA
    }

    @Test
    public void testRunFilterWithNestedCode() {
        String inputText = "import java.util.*;\n" +
                "public class Test {\n" +
                    "private int x;\n" +
                    "public void method() {\n" +
                        "if (condition) {\n" +
                            "doSomething();\n" +
                        "} else {\n" +
                            "doSomethingElse();\n" +
                        "}\n" +
                    "}\n" +
                "}\n";
        List<CodeRegion> codeRegions = filter.runFilter(inputText);
        assertNotNull(codeRegions);
        codeRegions.forEach(codeRegion -> {
            System.out.println(codeRegion.keyword);
            System.out.println(codeRegion.text);
        });
        assertEquals(2, codeRegions.size());
        assertEquals("import", codeRegions.get(0).keyword);
        assertEquals("import java.util.*;", codeRegions.get(0).text);
        assertEquals("class", codeRegions.get(1).keyword);
        assertEquals("public class Test {\n" +
                "private int x;\n" +
                "public void method() {\n" +
                "if (condition) {\n" +
                "doSomething();\n" +
                "} else {\n" +
                "doSomethingElse();\n" +
                "}\n" +
                "}\n" +
                "}", codeRegions.get(1).text);
       }

    @Test
    public void testPackageDeclaration() {
        String inputText = "package io.example;";
        List<CodeRegion> codeRegions = filter.runFilter(inputText);
        assertNotNull(codeRegions);
        assertEquals(1, codeRegions.size());
        assertEquals("package", codeRegions.get(0).keyword);
    }

    @Test
    public void testSingleLineComment() {
        String inputText = "//This is a comment \n abc";
        List<CodeRegion> codeRegions = filter.runFilter(inputText);
        assertNotNull(codeRegions);
        assertEquals(1, codeRegions.size());
        assertEquals("singlecomment", codeRegions.get(0).keyword);
        assertEquals("//This is a comment \n", codeRegions.get(0).text);
    }

    @Test
    public void testMultiLineComment() {
        String inputText = "/* This is a multi-line\n   comment */ and it's over";
        List<CodeRegion> codeRegions = filter.runFilter(inputText);
        assertNotNull(codeRegions);
        assertEquals(1, codeRegions.size());
        assertEquals("multicomment", codeRegions.get(0).keyword);
        assertEquals("/* This is a multi-line\n   comment */", codeRegions.get(0).text);
    }


    @Test
    public void testFunctionDefAndCall() {
        String inputText = "public void method() {\n" +
                "if (condition) {\n" +
                "doSomething();\n" +
                "} else {\n" +
                "doSomethingElse();\n" +
                "}\n" +
                "}\n" +
                "This is just random text. When i call \nmethod();\n it crashes\n" +
                "Then, here is an invalid method(); call. This happens because it is not at the end of the line \n" +
                "This other one is bad because it is missing the ; method()"+
                "Yet, it works with parameters \nmethod(a,b,c);\n" +
                "Just a reminder, the regex needs the call to be on a newline, otherwise it will take whatever is before the call as well.";
        List<CodeRegion> codeRegions = filter.runFilter(inputText);
        assertNotNull(codeRegions);
        assertEquals(3, codeRegions.size());
        assertEquals("functiondef", codeRegions.get(0).keyword);
        assertEquals("public void method() {\n" +
                "if (condition) {\n" +
                "doSomething();\n" +
                "} else {\n" +
                "doSomethingElse();\n" +
                "}\n" +
                "}", codeRegions.get(0).text);
        assertEquals("functioncall", codeRegions.get(1).keyword);
        assertEquals("method();", codeRegions.get(1).text);
        assertEquals("functioncall", codeRegions.get(2).keyword);
        assertEquals("method(a,b,c);", codeRegions.get(2).text);
    }

    @Test
    public void testWithAllSegments() {
        String inputText = "package java.io;\n" +
                "import java.lang.*;\n" +
                "//this is a test comment\n" +
                "public class randomClass{\n" +
                "//doesn't matter what's in there\n" +
                "}\n" +
                "Some break text\n" +
                "public void method() {\n" +
                "}\n" +
                "/*Some break text\n" +
                " for multiline comment */\n" +
                "if (condition) {\n" +
                "doSomething();\n" +
                "}\n" +
                "else {\n" +
                "doSomethingElse();\n" +
                "}\n" +

                "This is just random text.\n" +
                "method();";
        List<CodeRegion> codeRegions = filter.runFilter(inputText);
        assertNotNull(codeRegions);
        assertEquals(9, codeRegions.size());
        assertEquals("package", codeRegions.get(0).keyword);
        assertEquals("package java.io;", codeRegions.get(0).text);
        assertEquals("import", codeRegions.get(1).keyword);
        assertEquals("import java.lang.*;", codeRegions.get(1).text);
        assertEquals("singlecomment", codeRegions.get(2).keyword);
        assertEquals("//this is a test comment\n", codeRegions.get(2).text);
        assertEquals("class", codeRegions.get(3).keyword);
        assertEquals("public class randomClass{\n" +
                "//doesn't matter what's in there\n" +
                "}", codeRegions.get(3).text);
        assertEquals("functiondef", codeRegions.get(4).keyword);
        assertEquals("public void method() {\n" +
                "}", codeRegions.get(4).text);
        assertEquals("multicomment", codeRegions.get(5).keyword);
        assertEquals("/*Some break text\n" +
                " for multiline comment */", codeRegions.get(5).text);
        assertEquals("ifstatement", codeRegions.get(6).keyword);
        assertEquals("if (condition) {\n" +
                "doSomething();\n" +
                "}", codeRegions.get(6).text);
        assertEquals("elsestatement", codeRegions.get(7).keyword);
        assertEquals("else {\n" +
                "doSomethingElse();\n" +
                "}", codeRegions.get(7).text);
        assertEquals("functioncall", codeRegions.get(8).keyword);
        assertEquals("method();", codeRegions.get(8).text);
    }


}


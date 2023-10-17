package unit;

import io.kuy.infozilla.elements.stacktrace.java.StackTrace;
import io.kuy.infozilla.filters.FilterStackTraceJAVA;
import org.junit.jupiter.api.Test;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class FilterStackTraceJAVATest {

    @Test
    public void testFindStackTracesSingleDepth() {
        FilterStackTraceJAVA filter = new FilterStackTraceJAVA();
        String inputText = "Some text with a stack trace:\n" +
                "java.lang.TestException: TestException\n" +
                "    at com.example.TestClass.method(TestClass.java:42)";
        List<StackTrace> stackTraces = filter.runFilter(inputText);
        stackTraces.forEach(stackTrace -> {
            System.out.println(stackTrace.getException());
        });
        assertEquals(1, stackTraces.size());
        assertFalse(stackTraces.get(0).isCause());
        assertEquals("java.lang.TestException", stackTraces.get(0).getException());
        assertEquals("TestException", stackTraces.get(0).getReason());
        assertEquals(1, stackTraces.get(0).getFrames().size());
    }

    @Test
    public void testFindStackTracesMultipleDepth() {
        FilterStackTraceJAVA filter = new FilterStackTraceJAVA();
        String inputText = "Some text with a stack trace:\n"+
                "java.lang.TestException: TestException \n" +
                "    at com.example.TestClass.method1(TestClass.java:42)\n" +
                "    at com.example.TestClass.method2(TestClass.java:50)\n";
        List<StackTrace> stackTraces = filter.runFilter(inputText);
        assertEquals(1, stackTraces.size());
        assertFalse(stackTraces.get(0).isCause());
        assertEquals("java.lang.TestException", stackTraces.get(0).getException());
        assertEquals("TestException", stackTraces.get(0).getReason());
        assertEquals(2, stackTraces.get(0).getFrames().size());
    }

    @Test
    public void testFindStackTracesNoStackTrace() {
        FilterStackTraceJAVA filter = new FilterStackTraceJAVA();
        String inputText = "No stack trace here.";
        List<StackTrace> stackTraces = filter.runFilter(inputText);
        assertEquals(0, stackTraces.size());
    }

    @Test
    public void testFindStackTracesMultipleStackTraces() {
        FilterStackTraceJAVA filter = new FilterStackTraceJAVA();
        String inputText = "First stack trace:\n" +
                "java.lang.TestException: TestException1\n" +
                "    at com.example.TestClass.method1(TestClass.java:42)\n" +
                "   at com.example.TestClass.method4(TestClass.java:112)\n" +
                "Second stack trace:\n" +
                "java.lang.RuntimeException: TestException2\n" +
                "    at com.example.TestClass.method2(TestClass.java:50)";
        List<StackTrace> stackTraces = filter.runFilter(inputText);
        assertEquals(2, stackTraces.size());
        assertFalse(stackTraces.get(0).isCause());
        assertEquals("java.lang.TestException", stackTraces.get(0).getException());
        assertEquals("TestException1", stackTraces.get(0).getReason());
        assertEquals(2, stackTraces.get(0).getFrames().size());
        assertFalse(stackTraces.get(1).isCause());
        assertEquals("java.lang.RuntimeException", stackTraces.get(1).getException());
        assertEquals("TestException2", stackTraces.get(1).getReason());
        assertEquals(1, stackTraces.get(1).getFrames().size());
    }

    @Test
    public void testFindStackTracesWithCause() {
        FilterStackTraceJAVA filter = new FilterStackTraceJAVA();
        String inputText = "Root Exception:\n" +
                "java.lang.TestException: RootException\n" +
                "    at com.example.TestClass.method(TestClass.java:42)\n" +
                "Caused by:\n" +
                "java.lang.RuntimeException: CauseException\n" +
                "    at com.example.TestClass.method2(TestClass.java:50)";
        List<StackTrace> stackTraces = filter.runFilter(inputText);
        stackTraces.forEach(stackTrace -> {
            System.out.println(stackTrace.getFramesText());
        });
        assertFalse(stackTraces.get(0).isCause());
        assertTrue(stackTraces.get(1).isCause());
        assertEquals(2, stackTraces.size());
        assertEquals("java.lang.TestException", stackTraces.get(0).getException());
        assertEquals("RootException", stackTraces.get(0).getReason());
        assertEquals(1, stackTraces.get(0).getFrames().size());
        assertEquals("java.lang.RuntimeException", stackTraces.get(1).getException());
        assertEquals("CauseException", stackTraces.get(1).getReason());
        assertEquals(1, stackTraces.get(1).getFrames().size());
    }


    @Test
    public void testFindExceptionsMultipleStartingPoints() {
        String inputText = "This is a sample text with multiple exceptions.\n" +
                "java.lang.NullPointerException: Null pointer exception\n" +
                "    at com.example.MyClass.method1(MyClass.java:10)\n" +
                "Caused by:\n" +
                "java.lang.IllegalArgumentException: Illegal argument exception\n" +
                "    at com.example.MyClass.method2(MyClass.java:20)\n" +
                "    at com.example.MyClass.main(MyClass.java:30)\n" +
                "This \n are \n some \n break \n lines \n to \n force \n the \n execution"+
                "\n of \n the \n code \n linked \n to \n multiple \n starting \n points \n in \n the \n original \n class \n" +
                "java.lang.IndexOutOfBoundsException: Index out of bounds exception\n" +
                "    at com.example.AnotherClass.method3(AnotherClass.java:5)\n" +
                "    at com.example.AnotherClass.method4(AnotherClass.java:15)\n" +
                "    at com.example.AnotherClass.main(AnotherClass.java:25)\n";

        FilterStackTraceJAVA filter = new FilterStackTraceJAVA();
        List<StackTrace> stackTraces = filter.runFilter(inputText);
        assertEquals(3, stackTraces.size());
        stackTraces.forEach(stackTrace -> {
            System.out.println(stackTrace.getFramesText());
        });
        assertFalse(stackTraces.get(0).isCause());
        assertTrue(stackTraces.get(1).isCause());
        assertFalse(stackTraces.get(2).isCause());
        assertEquals("java.lang.NullPointerException", stackTraces.get(0).getException());
        assertEquals("Null pointer exception", stackTraces.get(0).getReason());
        assertEquals(1, stackTraces.get(0).getFrames().size());
        assertEquals("java.lang.IllegalArgumentException", stackTraces.get(1).getException());
        assertEquals("Illegal argument exception", stackTraces.get(1).getReason());
        assertEquals(2, stackTraces.get(1).getFrames().size());
        assertEquals("java.lang.IndexOutOfBoundsException", stackTraces.get(2).getException());
        assertEquals("Index out of bounds exception", stackTraces.get(2).getReason());
        assertEquals(3, stackTraces.get(2).getFrames().size());



    }
}

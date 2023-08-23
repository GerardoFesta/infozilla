import io.kuy.infozilla.cli.Main;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.xmlunit.builder.DiffBuilder;
import org.xmlunit.builder.Input;
import org.xmlunit.diff.Diff;
import picocli.CommandLine;
import javax.xml.transform.*;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.*;

import static org.junit.jupiter.api.Assertions.*;

public class SystemTest {



    @BeforeAll
    public static void beforeAll() {
        System.setProperty("javax.xml.bind.JAXBContextFactory", "com.sun.xml.bind.v2.ContextFactory");
    }


    @Test
    @Disabled
    public void tc1() {
        String[] args = {
                "--charset", "UTF-8",
                "./NonExistingFile.txt"
        };

        Main main = new Main();


        try {
            CommandLine cmd = new CommandLine(main);
            CommandLine.populateCommand(main, args);
            main.run();
            fail(); //Se arriva qui, significa che non c'è stata eccezione
        } catch (Exception e) {

            System.out.println("Execution crashed: " + e.getMessage());
        }
    }

    @Test
    public void tc2(){
        String[] args = {
                "-s=abc",
                "--charset", "UTF-8",
                "./system_testing_inputs/tc1.txt"
        };

        Main main = new Main();


        try {
            CommandLine cmd = new CommandLine(main);
            CommandLine.populateCommand(main, args);
            main.run();
            fail(); //Se arriva qui, significa che non c'è stata eccezione
        } catch (Exception e) {

            System.out.println("Execution crashed: " + e.getMessage());
        }



    }

    @Test
    public void tc3(){
        String[] args = {
                "-s=abc",
                "--charset", "UTF-8",
                "./system_testing_inputs/tc1.txt"
        };

        Main main = new Main();


        try {
            CommandLine cmd = new CommandLine(main);
            CommandLine.populateCommand(main, args);
            main.run();
            fail(); //Se arriva qui, significa che non c'è stata eccezione
        } catch (Exception e) {

            System.out.println("Execution crashed: " + e.getMessage());
        }



    }

    @Test
    public void tc4(){
        String[] args = {
                "-s=abc",
                "--charset", "UTF-8",
                "./system_testing_inputs/tc1.txt"
        };

        Main main = new Main();


        try {
            CommandLine cmd = new CommandLine(main);
            CommandLine.populateCommand(main, args);
            main.run();
            fail(); //Se arriva qui, significa che non c'è stata eccezione
        } catch (Exception e) {

            System.out.println("Execution crashed: " + e.getMessage());
        }



    }

    @Test
    public void tc5(){
        String[] args = {
                "-p=abc",
                "--charset", "UTF-8",
                "./system_testing_inputs/tc1.txt"
        };

        Main main = new Main();


        try {
            CommandLine cmd = new CommandLine(main);
            CommandLine.populateCommand(main, args);
            main.run();
            fail(); //Se arriva qui, significa che non c'è stata eccezione
        } catch (Exception e) {

            System.out.println("Execution crashed: " + e.getMessage());
        }



    }

    @Test
    public void tc6() {
        String[] args = {
                "--charset", "UTF-8",
                "./system_testing_inputs/tc6.txt"
        };

        Main main = new Main();
        CommandLine.run(main, args);

        File expectedFile = new File("./system_testing_oracles/tc6_oracle.xml");
        File actualFile = new File("./system_testing_inputs/tc6.txt.result.xml");
        try {
            // Apply XSLT transformation to expectedFile
            Source expectedXmlSource = new StreamSource(expectedFile);
            Result transformedExpectedXmlResult = new StreamResult(new File("./system_testing_oracles/tc6_oracle_transformed.xml"));
            applyXSLTTransformation(expectedXmlSource, transformedExpectedXmlResult);

            // Apply XSLT transformation to actualFile
            Source actualXmlSource = new StreamSource(actualFile);
            Result transformedActualXmlResult = new StreamResult(new File("./system_testing_inputs/tc6.txt.result_transformed.xml"));
            applyXSLTTransformation(actualXmlSource, transformedActualXmlResult);

            // Compare transformed files
            FileReader transformedExpectedReader = new FileReader("./system_testing_oracles/tc6_oracle_transformed.xml");
            FileReader transformedActualReader = new FileReader("./system_testing_inputs/tc6.txt.result_transformed.xml");

            Diff diff = DiffBuilder.compare(Input.fromReader(transformedExpectedReader))
                    .withTest(Input.fromReader(transformedActualReader))
                    .ignoreWhitespace()
                    .ignoreElementContentWhitespace()
                    .normalizeWhitespace()
                    .build();

            assertFalse(diff.hasDifferences(), "XML content does not match");

        } catch (Exception e) {
            System.out.println(e);

            fail();
        }
    }

    @Test
    public void tc7() {
        String[] args = {
                "-s=false",
                "--charset", "UTF-8",
                "./system_testing_inputs/tc7.txt"
        };

        Main main = new Main();
        CommandLine.run(main, args);

        File expectedFile = new File("./system_testing_oracles/tc7_oracle.xml");
        File actualFile = new File("./system_testing_inputs/tc7.txt.result.xml");
        try {
            // Apply XSLT transformation to expectedFile
            Source expectedXmlSource = new StreamSource(expectedFile);
            Result transformedExpectedXmlResult = new StreamResult(new File("./system_testing_oracles/tc7_oracle_transformed.xml"));
            applyXSLTTransformation(expectedXmlSource, transformedExpectedXmlResult);

            // Apply XSLT transformation to actualFile
            Source actualXmlSource = new StreamSource(actualFile);
            Result transformedActualXmlResult = new StreamResult(new File("./system_testing_inputs/tc7.txt.result_transformed.xml"));
            applyXSLTTransformation(actualXmlSource, transformedActualXmlResult);

            // Compare transformed files
            FileReader transformedExpectedReader = new FileReader("./system_testing_oracles/tc7_oracle_transformed.xml");
            FileReader transformedActualReader = new FileReader("./system_testing_inputs/tc7.txt.result_transformed.xml");

            Diff diff = DiffBuilder.compare(Input.fromReader(transformedExpectedReader))
                    .withTest(Input.fromReader(transformedActualReader))
                    .ignoreWhitespace()
                    .ignoreElementContentWhitespace()
                    .normalizeWhitespace()
                    .build();

            assertFalse(diff.hasDifferences(), "XML content does not match");

        } catch (Exception e) {
            System.out.println(e);

            fail();
        }
    }

    @Test
    public void tc8() {
        String[] args = {
                "-p=false",
                "--charset", "UTF-8",
                "./system_testing_inputs/tc8.txt"
        };

        Main main = new Main();
        CommandLine.run(main, args);

        File expectedFile = new File("./system_testing_oracles/tc8_oracle.xml");
        File actualFile = new File("./system_testing_inputs/tc8.txt.result.xml");
        try {
            // Apply XSLT transformation to expectedFile
            Source expectedXmlSource = new StreamSource(expectedFile);
            Result transformedExpectedXmlResult = new StreamResult(new File("./system_testing_oracles/tc8_oracle_transformed.xml"));
            applyXSLTTransformation(expectedXmlSource, transformedExpectedXmlResult);

            // Apply XSLT transformation to actualFile
            Source actualXmlSource = new StreamSource(actualFile);
            Result transformedActualXmlResult = new StreamResult(new File("./system_testing_inputs/tc8.txt.result_transformed.xml"));
            applyXSLTTransformation(actualXmlSource, transformedActualXmlResult);

            // Compare transformed files
            FileReader transformedExpectedReader = new FileReader("./system_testing_oracles/tc8_oracle_transformed.xml");
            FileReader transformedActualReader = new FileReader("./system_testing_inputs/tc8.txt.result_transformed.xml");

            Diff diff = DiffBuilder.compare(Input.fromReader(transformedExpectedReader))
                    .withTest(Input.fromReader(transformedActualReader))
                    .ignoreWhitespace()
                    .ignoreElementContentWhitespace()
                    .normalizeWhitespace()
                    .build();

            assertFalse(diff.hasDifferences(), "XML content does not match");

        } catch (Exception e) {
            System.out.println(e);

            fail();
        }
    }

    @Test
    public void tc9() {
        String[] args = {
                "-l=false",
                "--charset", "UTF-8",
                "./system_testing_inputs/tc9.txt"
        };

        Main main = new Main();
        CommandLine.run(main, args);

        File expectedFile = new File("./system_testing_oracles/tc9_oracle.xml");
        File actualFile = new File("./system_testing_inputs/tc9.txt.result.xml");
        try {
            // Apply XSLT transformation to expectedFile
            Source expectedXmlSource = new StreamSource(expectedFile);
            Result transformedExpectedXmlResult = new StreamResult(new File("./system_testing_oracles/tc9_oracle_transformed.xml"));
            applyXSLTTransformation(expectedXmlSource, transformedExpectedXmlResult);

            // Apply XSLT transformation to actualFile
            Source actualXmlSource = new StreamSource(actualFile);
            Result transformedActualXmlResult = new StreamResult(new File("./system_testing_inputs/tc9.txt.result_transformed.xml"));
            applyXSLTTransformation(actualXmlSource, transformedActualXmlResult);

            // Compare transformed files
            FileReader transformedExpectedReader = new FileReader("./system_testing_oracles/tc9_oracle_transformed.xml");
            FileReader transformedActualReader = new FileReader("./system_testing_inputs/tc9.txt.result_transformed.xml");

            Diff diff = DiffBuilder.compare(Input.fromReader(transformedExpectedReader))
                    .withTest(Input.fromReader(transformedActualReader))
                    .ignoreWhitespace()
                    .ignoreElementContentWhitespace()
                    .normalizeWhitespace()
                    .build();

            assertFalse(diff.hasDifferences(), "XML content does not match");

        } catch (Exception e) {
            System.out.println(e);

            fail();
        }
    }

    @Test
    public void tc10() {
        String[] args = {
                "-c=false",
                "--charset", "UTF-8",
                "./system_testing_inputs/tc10.txt"
        };

        Main main = new Main();
        CommandLine.run(main, args);

        File expectedFile = new File("./system_testing_oracles/tc10_oracle.xml");
        File actualFile = new File("./system_testing_inputs/tc10.txt.result.xml");
        try {
            // Apply XSLT transformation to expectedFile
            Source expectedXmlSource = new StreamSource(expectedFile);
            Result transformedExpectedXmlResult = new StreamResult(new File("./system_testing_oracles/tc10_oracle_transformed.xml"));
            applyXSLTTransformation(expectedXmlSource, transformedExpectedXmlResult);

            // Apply XSLT transformation to actualFile
            Source actualXmlSource = new StreamSource(actualFile);
            Result transformedActualXmlResult = new StreamResult(new File("./system_testing_inputs/tc10.txt.result_transformed.xml"));
            applyXSLTTransformation(actualXmlSource, transformedActualXmlResult);

            // Compare transformed files
            FileReader transformedExpectedReader = new FileReader("./system_testing_oracles/tc10_oracle_transformed.xml");
            FileReader transformedActualReader = new FileReader("./system_testing_inputs/tc10.txt.result_transformed.xml");

            Diff diff = DiffBuilder.compare(Input.fromReader(transformedExpectedReader))
                    .withTest(Input.fromReader(transformedActualReader))
                    .ignoreWhitespace()
                    .ignoreElementContentWhitespace()
                    .normalizeWhitespace()
                    .build();

            assertFalse(diff.hasDifferences(), "XML content does not match");

        } catch (Exception e) {
            System.out.println(e);

            fail();
        }
    }

    @Test
    public void tc11() {
        String[] args = {
                "--charset", "UTF-8",
                "./system_testing_inputs/tc11.txt"
        };

        Main main = new Main();
        CommandLine.run(main, args);

        File expectedFile = new File("./system_testing_oracles/tc11_oracle.xml");
        File actualFile = new File("./system_testing_inputs/tc11.txt.result.xml");
        try {
            // Apply XSLT transformation to expectedFile
            Source expectedXmlSource = new StreamSource(expectedFile);
            Result transformedExpectedXmlResult = new StreamResult(new File("./system_testing_oracles/tc11_oracle_transformed.xml"));
            applyXSLTTransformation(expectedXmlSource, transformedExpectedXmlResult);

            // Apply XSLT transformation to actualFile
            Source actualXmlSource = new StreamSource(actualFile);
            Result transformedActualXmlResult = new StreamResult(new File("./system_testing_inputs/tc11.txt.result_transformed.xml"));
            applyXSLTTransformation(actualXmlSource, transformedActualXmlResult);

            // Compare transformed files
            FileReader transformedExpectedReader = new FileReader("./system_testing_oracles/tc11_oracle_transformed.xml");
            FileReader transformedActualReader = new FileReader("./system_testing_inputs/tc11.txt.result_transformed.xml");

            Diff diff = DiffBuilder.compare(Input.fromReader(transformedExpectedReader))
                    .withTest(Input.fromReader(transformedActualReader))
                    .ignoreWhitespace()
                    .ignoreElementContentWhitespace()
                    .normalizeWhitespace()
                    .build();

            assertFalse(diff.hasDifferences(), "XML content does not match");

        } catch (Exception e) {
            System.out.println(e);

            fail();
        }
    }

    @Test
    public void tc12() {
        String[] args = {
                "--charset", "UTF-8",
                "./system_testing_inputs/tc12.txt"
        };

        Main main = new Main();
        CommandLine.run(main, args);

        File expectedFile = new File("./system_testing_oracles/tc12_oracle.xml");
        File actualFile = new File("./system_testing_inputs/tc12.txt.result.xml");
        try {
            // Apply XSLT transformation to expectedFile
            Source expectedXmlSource = new StreamSource(expectedFile);
            Result transformedExpectedXmlResult = new StreamResult(new File("./system_testing_oracles/tc12_oracle_transformed.xml"));
            applyXSLTTransformation(expectedXmlSource, transformedExpectedXmlResult);

            // Apply XSLT transformation to actualFile
            Source actualXmlSource = new StreamSource(actualFile);
            Result transformedActualXmlResult = new StreamResult(new File("./system_testing_inputs/tc12.txt.result_transformed.xml"));
            applyXSLTTransformation(actualXmlSource, transformedActualXmlResult);

            // Compare transformed files
            FileReader transformedExpectedReader = new FileReader("./system_testing_oracles/tc12_oracle_transformed.xml");
            FileReader transformedActualReader = new FileReader("./system_testing_inputs/tc12.txt.result_transformed.xml");

            Diff diff = DiffBuilder.compare(Input.fromReader(transformedExpectedReader))
                    .withTest(Input.fromReader(transformedActualReader))
                    .ignoreWhitespace()
                    .ignoreElementContentWhitespace()
                    .normalizeWhitespace()
                    .build();

            assertFalse(diff.hasDifferences(), "XML content does not match");

        } catch (Exception e) {
            System.out.println(e);

            fail();
        }
    }









    private void applyXSLTTransformation(Source xmlSource, Result transformedXmlResult) throws TransformerException {
        File xsltFile = new File("src/test/utils/xslToCompare.xsl");
        TransformerFactory factory = TransformerFactory.newInstance();
        Transformer transformer = factory.newTransformer(new StreamSource(xsltFile));
        transformer.transform(xmlSource, transformedXmlResult);
    }
}

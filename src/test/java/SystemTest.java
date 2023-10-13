import io.kuy.infozilla.cli.Main;
import org.junit.jupiter.api.*;
import org.xmlunit.builder.DiffBuilder;
import org.xmlunit.builder.Input;
import org.xmlunit.diff.Diff;
import picocli.CommandLine;
import javax.xml.transform.*;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.*;
import java.util.regex.Pattern;

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
                "-f", "./NonExistingFile.txt"
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
                "-f"," ./system_testing_inputs/tc1.txt"
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
                "-f","./system_testing_inputs/tc1.txt"
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
                "-f","./system_testing_inputs/tc1.txt"
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
                "-f","./system_testing_inputs/tc1.txt"
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
                "-f","./system_testing_inputs/tc6.txt"
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
                "-f","./system_testing_inputs/tc7.txt"
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
                "-f","./system_testing_inputs/tc8.txt"
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
                "-f","./system_testing_inputs/tc9.txt"
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
                "-f","./system_testing_inputs/tc10.txt"
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
                "-f","./system_testing_inputs/tc11.txt"
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
                "-f","./system_testing_inputs/tc12.txt"
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

    //TEST PER URL
    @Test
    public void tc18() {
        String[] args = {
                "--charset", "UTF-8",
                "-u", "https://github.com/gerardofesta/infozilla/issues/14"
        };


        Main main = new Main();
        CommandLine.run(main, args);

        File expectedFile = new File("./system_testing_oracles/tc18_oracle.xml");
        File actualFile = new File("./gerardofesta-infozilla-14Issue.txt.result.xml");
        try {
            // Apply XSLT transformation to expectedFile
            Source expectedXmlSource = new StreamSource(expectedFile);
            Result transformedExpectedXmlResult = new StreamResult(new File("./system_testing_oracles/tc18_oracle_transformed.xml"));
            applyXSLTTransformation(expectedXmlSource, transformedExpectedXmlResult);

            // Apply XSLT transformation to actualFile
            Source actualXmlSource = new StreamSource(actualFile);
            Result transformedActualXmlResult = new StreamResult(new File("./gerardofesta-infozilla-14Issue.txt.result_transformed.xml"));
            applyXSLTTransformation(actualXmlSource, transformedActualXmlResult);

            // Compare transformed files
            FileReader transformedExpectedReader = new FileReader("./system_testing_oracles/tc18_oracle_transformed.xml");
            FileReader transformedActualReader = new FileReader("./gerardofesta-infozilla-14Issue.txt.result_transformed.xml");

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
    public void tc19() {
        String[] args = {
                "--charset", "UTF-8",
                "-u", "https://just_a_random_link.com/issues/14"
        };

        try{
            Main main = new Main();
            CommandLine.run(main, args);
            fail();
        }catch (Exception e){
            System.out.println("Execution crashed: " + e.getMessage());

        }

    }

//TEST PER URL REPO


    @Test
    public void tc20() {
        String[] args = {
                "--charset", "UTF-8",
                "-a", "github.com/randomurl/nonexistingrepo"
        };

        Main main = new Main();
        try {
            CommandLine.run(main, args);
            fail();
        } catch (RuntimeException e) {
            System.out.println("Execution crashed: " + e.getMessage());
        }
    }

    @Test
    public void tc21() {
        String[] args = {
                "--charset", "UTF-8",
                "-a", "github.com/gerardofesta/infozilla"
        };

        Main main = new Main();
        CommandLine.run(main, args);
        assertTrue( new File("./gerardofesta-infozilla-14Issue.txt.result.xml").exists());
        assertTrue( new File("./gerardofesta-infozilla-13Issue.txt.result.xml").exists());
        assertTrue( new File("./gerardofesta-infozilla-12Issue.txt.result.xml").exists());
        assertTrue( new File("./gerardofesta-infozilla-11Issue.txt.result.xml").exists());
        assertTrue( new File("./gerardofesta-infozilla-10Issue.txt.result.xml").exists());
        assertTrue( new File("./gerardofesta-infozilla-9Issue.txt.result.xml").exists());
        assertTrue( new File("./gerardofesta-infozilla-8Issue.txt.result.xml").exists());
        assertTrue( new File("./gerardofesta-infozilla-7Issue.txt.result.xml").exists());
        assertTrue( new File("./gerardofesta-infozilla-6Issue.txt.result.xml").exists());
        assertTrue( new File("./gerardofesta-infozilla-5Issue.txt.result.xml").exists());
        assertTrue( new File("./gerardofesta-infozilla-4Issue.txt.result.xml").exists());
        assertTrue( new File("./gerardofesta-infozilla-3Issue.txt.result.xml").exists());
        assertTrue( new File("./gerardofesta-infozilla-2Issue.txt.result.xml").exists());
        assertTrue( new File("./gerardofesta-infozilla-1Issue.txt.result.xml").exists());
    }

    @Test
    public void tc22(){
        String[] args = {
                "--charset", "UTF-8",
                "-a", "https://github.com/gerardofesta/GRUPPO25-CleanWater/"
        };

        Main main = new Main();
        CommandLine.run(main, args);

        File directory = new File(".");
        String pattern = "gerardofesta-GRUPPO25-CleanWater-\\d+Issue\\.txt\\.result\\.xml";

        File[] matchingFiles = directory.listFiles(new FilenameFilter() {
            public boolean accept(File dir, String name) {
                return Pattern.matches(pattern, name);
            }
        });

        if (matchingFiles == null || matchingFiles.length == 0 || matchingFiles.length>3) {
            fail();
        }else{
            boolean flag1 = false, flag2 =false, flag3 =false;
            for (File file : matchingFiles) {
                if (!flag1 && file.getName().contains("gerardofesta-GRUPPO25-CleanWater-1Issue.txt.result.xml")) {
                    flag1=true;
                }else{
                    if(!flag2 && file.getName().contains("gerardofesta-GRUPPO25-CleanWater-2Issue.txt.result.xml")){
                        flag2=true;
                    }else{
                        if(!flag3 && file.getName().contains("gerardofesta-GRUPPO25-CleanWater-3Issue.txt.result.xml")){
                            flag3=true;
                        }
                    }
                }
            }
            if(!(flag1 && flag2 && flag3))
                fail();
        }
    }

    @Test
    public void tc23(){
        String[] args = {
                "--charset", "UTF-8",
                "-a", "https://github.com/gerardofesta/SongCommunity"
        };

        Main main = new Main();
        CommandLine.run(main, args);

        File directory = new File(".");
        String pattern = "gerardofesta-SongCommunity-\\d+Issue\\.txt\\.result\\.xml";

        File[] matchingFiles = directory.listFiles((dir, name) -> Pattern.matches(pattern, name));

        if (! (matchingFiles == null || matchingFiles.length == 0)) {
            fail();
        }
    }

    //filter by wrong ob
    @Test
    public void tc24() {
        String[] args = {
                "--charset", "UTF-8",
                "-a", "github.com/gerardofesta/infozilla",
                "-ob", "abcde"
        };

        Main main = new Main();
        assertThrows(picocli.CommandLine.ExecutionException.class, () -> CommandLine.run(main, args));

    }

    //filter by oa
    @Test
    public void tc25() {
        String[] args = {
                "--charset", "UTF-8",
                "-a", "github.com/gerardofesta/infozilla",
                "-oa", "abcde"
        };

        Main main = new Main();
        assertThrows(picocli.CommandLine.ExecutionException.class, () -> CommandLine.run(main, args));

    }

    //test by wrong cb
    @Test
    public void tc26() {
        String[] args = {
                "--charset", "UTF-8",
                "-a", "github.com/gerardofesta/infozilla",
                "-cb", "abcde"
        };

        Main main = new Main();
        assertThrows(picocli.CommandLine.ExecutionException.class, () -> CommandLine.run(main, args));

    }

    //test by wrong ca
    @Test
    public void tc27() {
        String[] args = {
                "--charset", "UTF-8",
                "-a", "github.com/gerardofesta/infozilla",
                "-ca", "abcde"
        };

        Main main = new Main();
        assertThrows(picocli.CommandLine.ExecutionException.class, () -> CommandLine.run(main, args));

    }

    //test with correct dates
    @Test
    public void tc28(){
        String[] args = {
                "--charset", "UTF-8",
                "-a", "github.com/gerardofesta/infozilla",
                "-oa", "2023-08-26",
                "-ob", "2023-09-19",
                "-cb", "2024-01-01",
                "-ca", "2023-01-01"
        };

        Main main = new Main();

        CommandLine.run(main, args);

        File directory = new File(".");
        String pattern = "gerardofesta-infozilla-\\d+Issue\\.txt\\.result\\.xml";

        File[] matchingFiles = directory.listFiles(new FilenameFilter() {
            public boolean accept(File dir, String name) {
                return Pattern.matches(pattern, name);
            }
        });

        if (matchingFiles == null || matchingFiles.length!=4) {
            fail();
        }else {
            assertTrue(matchingFiles[0].getName().equalsIgnoreCase("gerardofesta-infozilla-5Issue.txt.result.xml"));
            assertTrue(matchingFiles[1].getName().equalsIgnoreCase("gerardofesta-infozilla-6Issue.txt.result.xml"));
            assertTrue(matchingFiles[2].getName().equalsIgnoreCase("gerardofesta-infozilla-7Issue.txt.result.xml"));
            assertTrue(matchingFiles[3].getName().equalsIgnoreCase("gerardofesta-infozilla-8Issue.txt.result.xml"));

        }


    }

    //test by invalid state
    @Test
    public void tc29() {
        String[] args = {
                "--charset", "UTF-8",
                "-a", "github.com/gerardofesta/infozilla",
                "-st", "abcde"
        };

        Main main = new Main();
        assertThrows(picocli.CommandLine.ExecutionException.class, () -> CommandLine.run(main, args));

    }

    @Test
    public void tc30(){
        String[] args = {
                "--charset", "UTF-8",
                "-a", "github.com/gerardofesta/infozilla",
                "-as", "gerardofesta",
                "-st", "all"

        };

        Main main = new Main();
        CommandLine.run(main, args);

        File directory = new File(".");
        String pattern = "gerardofesta-infozilla-\\d+Issue\\.txt\\.result\\.xml";

        File[] matchingFiles = directory.listFiles(new FilenameFilter() {
            public boolean accept(File dir, String name) {
                return Pattern.matches(pattern, name);
            }
        });

        if (matchingFiles == null || matchingFiles.length<4) {
            fail();
        }else {
            boolean flag1 = false, flag5 =false, flag13 =false, flag14 =false;
            for (File file : matchingFiles) {
                if (!flag1 && file.getName().contains("gerardofesta-infozilla-1Issue.txt.result.xml")) {
                    flag1=true;
                }else{
                    if(!flag5 && file.getName().contains("gerardofesta-infozilla-5Issue.txt.result.xml")){
                        flag5=true;
                    }else{
                        if(!flag13 && file.getName().contains("gerardofesta-infozilla-13Issue.txt.result.xml")){
                            flag13=true;
                        }else{
                            if(!flag14 && file.getName().contains("gerardofesta-infozilla-14Issue.txt.result.xml")) {
                                flag14 = true;
                            }
                        }
                    }
                }
            }
            if(!(flag1 && flag5 && flag13 && flag14))
                fail();
        }

    }

    @Test
    public void tc31(){
        String[] args = {
                "--charset", "UTF-8",
                "-a", "github.com/gerardofesta/infozilla",
                "-la", "enhancement",
                "-st", "closed"

        };

        Main main = new Main();
        CommandLine.run(main, args);

        File directory = new File(".");
        String pattern = "gerardofesta-infozilla-\\d+Issue\\.txt\\.result\\.xml";

        File[] matchingFiles = directory.listFiles(new FilenameFilter() {
            public boolean accept(File dir, String name) {
                return Pattern.matches(pattern, name);
            }
        });

        if (matchingFiles == null || matchingFiles.length<3) {
            fail();
        }else {
            boolean flag3 = false, flag4 =false, flag5 =false;
            for (File file : matchingFiles) {
                if (!flag3 && file.getName().contains("gerardofesta-infozilla-3Issue.txt.result.xml")) {
                    flag3=true;
                }else{
                    if(!flag4 && file.getName().contains("gerardofesta-infozilla-4Issue.txt.result.xml")){
                        flag4=true;
                    }else{
                        if(!flag5 && file.getName().contains("gerardofesta-infozilla-5Issue.txt.result.xml")){
                            flag5=true;
                        }
                    }
                }
            }
            if(!(flag3 && flag4 && flag5))
                fail();
        }

    }

    @Test
    public void tc32(){
        String[] args = {
                "--charset", "UTF-8",
                "-a", "https://github.com/gerardofesta/GRUPPO25-CleanWater",
                "-la", "enhancement",
                "-la", "bug",
                "-st", "open"

        };

        Main main = new Main();
        CommandLine.run(main, args);

        File directory = new File(".");
        String pattern = "gerardofesta-GRUPPO25-CleanWater-\\d+Issue\\.txt\\.result\\.xml";

        File[] matchingFiles = directory.listFiles((dir, name) -> Pattern.matches(pattern, name));
        if (matchingFiles == null || matchingFiles.length!=2) {
            fail();
        }else {
            boolean flag1 = false, flag2 =false;
            for (File file : matchingFiles) {
                if (!flag1 && file.getName().contains("gerardofesta-GRUPPO25-CleanWater-1Issue.txt.result.xml")) {
                    flag1=true;
                }else{
                    if(!flag2 && file.getName().contains("gerardofesta-GRUPPO25-CleanWater-2Issue.txt.result.xml")){
                        flag2=true;
                    }
                }
            }
            if(!(flag1 && flag2))
                fail();
        }

    }

    @Test
    public void tc33() {
        String[] args = {
                "--charset", "UTF-8",
                "-a", "https://github.com/gerardofesta/GRUPPO25-CleanWater",
                "-la", "abcdefg",
                "-st", "open"

        };

        Main main = new Main();
        CommandLine.run(main, args);

        File directory = new File(".");
        String pattern = "gerardofesta-GRUPPO25-CleanWater-\\d+Issue\\.txt\\.result\\.xml";

        File[] matchingFiles = directory.listFiles((dir, name) -> Pattern.matches(pattern, name));
        if (!(matchingFiles == null || matchingFiles.length == 0)) {
            fail();
        }
    }

    @Test
    public void tc34() {
        String[] args = {
                "--charset", "UTF-8",
                "-a", "https://github.com/gerardofesta/infozilla",
                "-as", "abcde"

        };

        Main main = new Main();
        CommandLine.run(main, args);

        File directory = new File(".");
        String pattern = "gerardofesta-infozilla\\d+Issue\\.txt\\.result\\.xml";

        File[] matchingFiles = directory.listFiles((dir, name) -> Pattern.matches(pattern, name));
        if (!(matchingFiles == null || matchingFiles.length == 0)) {
            fail();
        }
    }











    private void applyXSLTTransformation(Source xmlSource, Result transformedXmlResult) throws TransformerException {
        File xsltFile = new File("src/test/utils/xslToCompare.xsl");
        TransformerFactory factory = TransformerFactory.newInstance();
        Transformer transformer = factory.newTransformer(new StreamSource(xsltFile));
        transformer.transform(xmlSource, transformedXmlResult);
    }

    @AfterEach
    public void deleteTempFiles() {
        try {
            // Elimina i file con nome "_oracle_transformed.xml" dalla cartella ./system_testing_oracles/
            File oracleDirectory = new File("./system_testing_oracles/");
            File[] oracleFiles = oracleDirectory.listFiles((dir, name) -> name.contains("_oracle_transformed.xml"));
            if (oracleFiles != null) {
                for (File file : oracleFiles) {
                    file.delete();
                }
            }

            // Elimina i file con nome ".cleared", ".result" e ".result_transformed" dalla cartella ./system_testing_inputs/
            File inputsDirectory = new File("./system_testing_inputs/");
            File[] inputFiles = inputsDirectory.listFiles((dir, name) -> name.contains(".cleaned") || name.contains(".result") || name.contains(".result_transformed"));
            if (inputFiles != null) {
                for (File file : inputFiles) {
                    file.delete();
                }
            }
            
            //Elimina i file scraped
            File scrapedDirectory = new File(".");
            String patternInfo = "gerardofesta-infozilla-(\\d+Issue\\.txt|\\d+Issue\\.txt\\.cleaned|\\d+Issue\\.txt\\.result\\.xml)";
            String patternSC = "gerardofesta-SongCommunity-(\\d+Issue\\.txt|\\d+Issue\\.txt\\.result\\.xml)";
            String patternCW = "gerardofesta-GRUPPO25-CleanWater-(\\d+Issue\\.txt|\\d+Issue\\.txt\\.result\\.xml|\\d+Issue\\.txt\\.cleaned)";

            File[] scrapedFiles = scrapedDirectory.listFiles((dir, name) -> Pattern.matches(patternInfo, name) || Pattern.matches(patternSC, name) || Pattern.matches(patternCW, name));

            if (scrapedFiles != null) {
                for (File file : scrapedFiles) {
                    file.delete();
                }
            }
            
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}

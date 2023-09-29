import io.kuy.infozilla.cli.Main;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.xmlunit.builder.DiffBuilder;
import org.xmlunit.builder.Input;
import org.xmlunit.diff.Diff;
import picocli.CommandLine;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import javax.xml.transform.*;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.*;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.StringJoiner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.poi.ss.usermodel.*;


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



    @Test
    public void tc13() {
        String[] args = {
                "--charset", "UTF-8",
                "./system_testing_inputs/tc13.txt",
                "-o=csv"
        };

        Main main = new Main();
        CommandLine.run(main, args);

        try {
            File expectedFileST = new File("./system_testing_oracles/tc13_ST_oracle.csv");
            File expectedFileSC = new File("./system_testing_oracles/tc13_SC_oracle.csv");
            File expectedFileE = new File("./system_testing_oracles/tc13_E_oracle.csv");
            File expectedFileP = new File("./system_testing_oracles/tc13_P_oracle.csv");
            File actualFileP = new File("./system_testing_inputs/tc13_txt_CSV/tc13_txt_patches.csv");
            File actualFileE = new File("./system_testing_inputs/tc13_txt_CSV/tc13_txt_enumerations.csv");
            File actualFileSC = new File("./system_testing_inputs/tc13_txt_CSV/tc13_txt_sourceCode.csv");
            File actualFileST = new File("./system_testing_inputs/tc13_txt_CSV/tc13_txt_stackTraces.csv");


            CSVParser expectedParserST = CSVParser.parse(expectedFileST, Charset.forName("UTF-8"), CSVFormat.DEFAULT);
            CSVParser expectedParserSC = CSVParser.parse(expectedFileSC, Charset.forName("UTF-8"), CSVFormat.DEFAULT);
            CSVParser expectedParserE = CSVParser.parse(expectedFileE, Charset.forName("UTF-8"), CSVFormat.DEFAULT);
            CSVParser expectedParserP = CSVParser.parse(expectedFileP, Charset.forName("UTF-8"), CSVFormat.DEFAULT);

            CSVParser actualParser = CSVParser.parse(actualFileST, Charset.forName("UTF-8"), CSVFormat.DEFAULT);

            List<CSVRecord> expectedRecordsST = expectedParserST.getRecords();
            List<CSVRecord> actualRecords = actualParser.getRecords();

            assertEquals(expectedRecordsST.size(), actualRecords.size(), "CSV record count does not match");





            for (int i = 0; i < expectedRecordsST.size(); i++) {
                CSVRecord expectedRecord = expectedRecordsST.get(i);
                CSVRecord actualRecord = actualRecords.get(i);

                // Rimuovi la cella in posizione due (indice 1, poiché l'indice è zero-based)
                StringJoiner updatedRow = new StringJoiner(",");
                StringJoiner expectedRow = new StringJoiner(",");
                for (int j = 0; j < actualRecord.size(); j++) {
                    if (j != 2) { // Salta la cella in posizione due
                        updatedRow.add(actualRecord.get(j));
                        expectedRow.add(expectedRecord.get(j));
                    }
                }

                // Convert the records to strings and remove whitespace for comparison
                String expectedRecordStr = expectedRow.toString().replaceAll("\\s+", "");
                String actualRecordStr = updatedRow.toString().replaceAll("\\s+", "");

                assertEquals(expectedRecordStr, actualRecordStr, "CSV content does not match at row " + (i + 1));
            }

            actualParser = CSVParser.parse(actualFileSC, Charset.forName("UTF-8"), CSVFormat.DEFAULT);
            List<CSVRecord> expectedRecordsSC = expectedParserSC.getRecords();
            actualRecords = actualParser.getRecords();
            assertEquals(expectedRecordsSC.size(), actualRecords.size(), "CSV record count does not match");

            for (int i = 0; i < expectedRecordsSC.size(); i++) {
                CSVRecord expectedRecord = expectedRecordsSC.get(i);
                CSVRecord actualRecord = actualRecords.get(i);

                // Convert the records to strings and remove whitespace for comparison
                String expectedRecordStr = expectedRecord.toString().replaceAll("\\s+", "");
                String actualRecordStr = actualRecord.toString().replaceAll("\\s+", "");

                assertEquals(expectedRecordStr, actualRecordStr, "CSV content does not match at row " + (i + 1));
            }

            actualParser = CSVParser.parse(actualFileE, Charset.forName("UTF-8"), CSVFormat.DEFAULT);
            List<CSVRecord> expectedRecordsE = expectedParserE.getRecords();
            actualRecords = actualParser.getRecords();

            assertEquals(expectedRecordsE.size(), actualRecords.size(), "CSV record count does not match");

            for (int i = 0; i < expectedRecordsE.size(); i++) {
                CSVRecord expectedRecord = expectedRecordsE.get(i);
                CSVRecord actualRecord = actualRecords.get(i);

                // Convert the records to strings and remove whitespace for comparison
                String expectedRecordStr = expectedRecord.toString().replaceAll("\\s+", "");
                String actualRecordStr = actualRecord.toString().replaceAll("\\s+", "");

                assertEquals(expectedRecordStr, actualRecordStr, "CSV content does not match at row " + (i + 1));
            }

            actualParser = CSVParser.parse(actualFileP, Charset.forName("UTF-8"), CSVFormat.DEFAULT);
            List<CSVRecord> expectedRecordsP = expectedParserP.getRecords();
            actualRecords = actualParser.getRecords();


            assertEquals(expectedRecordsP.size(), actualRecords.size(), "CSV record count does not match");

            for (int i = 0; i < expectedRecordsP.size(); i++) {
                CSVRecord expectedRecord = expectedRecordsP.get(i);
                CSVRecord actualRecord = actualRecords.get(i);

                // Convert the records to strings and remove whitespace for comparison
                String expectedRecordStr = expectedRecord.toString().replaceAll("\\s+", "");
                String actualRecordStr = actualRecord.toString().replaceAll("\\s+", "");

                assertEquals(expectedRecordStr, actualRecordStr, "CSV content does not match at row " + (i + 1));
            }


        } catch (Exception e) {
            System.out.println(e);

            fail();
        }
    }

    @Test
    public void tc14() {
        String[] args = {
                "--charset", "UTF-8",
                "./system_testing_inputs/tc14.txt",
                "-o=xls"
        };
        try {

            Main main = new Main();
            CommandLine.run(main, args);
            File expectedFileST = new File("./system_testing_oracles/tc14_ST_oracle.xls");
            File expectedFileSC = new File("./system_testing_oracles/tc14_SC_oracle.xls");
            File expectedFileE = new File("./system_testing_oracles/tc14_E_oracle.xls");
            File expectedFileP = new File("./system_testing_oracles/tc14_P_oracle.xls");
            File actualFileP = new File("./system_testing_inputs/tc14_txt_XLS/tc14_txt_patches.xls");
            File actualFileE = new File("./system_testing_inputs/tc14_txt_XLS/tc14_txt_enumerations.xls");
            File actualFileSC = new File("./system_testing_inputs/tc14_txt_XLS/tc14_txt_sourceCode.xls");
            File actualFileST = new File("./system_testing_inputs/tc14_txt_XLS/tc14_txt_stackTraces.xls");

            FileInputStream expectedFileInputStreamST = new FileInputStream(expectedFileST);
            FileInputStream expectedFileInputStreamSC = new FileInputStream(expectedFileSC);
            FileInputStream expectedFileInputStreamE = new FileInputStream(expectedFileE);
            FileInputStream expectedFileInputStreamP = new FileInputStream(expectedFileP);
            FileInputStream actualFileInputStreamP = new FileInputStream(actualFileP);
            FileInputStream actualFileInputStreamE = new FileInputStream(actualFileE);
            FileInputStream actualFileInputStreamSC = new FileInputStream(actualFileSC);
            FileInputStream actualFileInputStreamST = new FileInputStream(actualFileST);

            Workbook expectedWorkbookST = WorkbookFactory.create(expectedFileInputStreamST);
            Workbook expectedWorkbookSC = WorkbookFactory.create(expectedFileInputStreamSC);
            Workbook expectedWorkbookE = WorkbookFactory.create(expectedFileInputStreamE);
            Workbook expectedWorkbookP = WorkbookFactory.create(expectedFileInputStreamP);
            Workbook actualWorkbookP = WorkbookFactory.create(actualFileInputStreamP);
            Workbook actualWorkbookE = WorkbookFactory.create(actualFileInputStreamE);
            Workbook actualWorkbookSC = WorkbookFactory.create(actualFileInputStreamSC);
            Workbook actualWorkbookST = WorkbookFactory.create(actualFileInputStreamST);

            Sheet expectedSheetST = expectedWorkbookST.getSheetAt(0);
            Sheet expectedSheetSC = expectedWorkbookSC.getSheetAt(0);
            Sheet expectedSheetE = expectedWorkbookE.getSheetAt(0);
            Sheet expectedSheetP = expectedWorkbookP.getSheetAt(0);
            Sheet actualSheetP = actualWorkbookP.getSheetAt(0);
            Sheet actualSheetE = actualWorkbookE.getSheetAt(0);
            Sheet actualSheetSC = actualWorkbookSC.getSheetAt(0);
            Sheet actualSheetST = actualWorkbookST.getSheetAt(0);

            Iterator<Row> expectedRowIteratorST = expectedSheetST.iterator();
            Iterator<Row> expectedRowIteratorSC = expectedSheetSC.iterator();
            Iterator<Row> expectedRowIteratorE = expectedSheetE.iterator();
            Iterator<Row> expectedRowIteratorP = expectedSheetP.iterator();
            Iterator<Row> actualRowIteratorP = actualSheetP.iterator();
            Iterator<Row> actualRowIteratorE = actualSheetE.iterator();
            Iterator<Row> actualRowIteratorSC = actualSheetSC.iterator();
            Iterator<Row> actualRowIteratorST = actualSheetST.iterator();

            while (expectedRowIteratorST.hasNext() && actualRowIteratorST.hasNext()) {
                Row expectedRow = expectedRowIteratorST.next();
                Row actualRow = actualRowIteratorST.next();

                Iterator<Cell> expectedCellIterator = expectedRow.cellIterator();
                Iterator<Cell> actualCellIterator = actualRow.cellIterator();

                while (expectedCellIterator.hasNext() && actualCellIterator.hasNext()) {
                    Cell expectedCell = expectedCellIterator.next();
                    Cell actualCell = actualCellIterator.next();

                    //Ignoro i numeri così da evitare stackTrace timestamp
                    if (expectedCell.getCellType() == CellType.STRING && actualCell.getCellType() == CellType.STRING) {
                        String expectedCellValue = expectedCell.getStringCellValue().replaceAll("\\s+", "");
                        String actualCellValue = actualCell.getStringCellValue().replaceAll("\\s+", "");

                        if (!expectedCellValue.equals(actualCellValue)) {
                            System.out.println("XLS content does not match at row " + expectedRow.getRowNum() + ", column " + expectedCell.getColumnIndex());
                            fail();
                        }
                    }
                }
            }

            while (expectedRowIteratorP.hasNext() && actualRowIteratorP.hasNext()) {
                Row expectedRow = expectedRowIteratorP.next();
                Row actualRow = actualRowIteratorP.next();

                Iterator<Cell> expectedCellIterator = expectedRow.cellIterator();
                Iterator<Cell> actualCellIterator = actualRow.cellIterator();

                while (expectedCellIterator.hasNext() && actualCellIterator.hasNext()) {
                    Cell expectedCell = expectedCellIterator.next();
                    Cell actualCell = actualCellIterator.next();


                    if (expectedCell.getCellType() == CellType.STRING && actualCell.getCellType() == CellType.STRING) {
                        String expectedCellValue = expectedCell.getStringCellValue().replaceAll("\\s+", "");
                        String actualCellValue = actualCell.getStringCellValue().replaceAll("\\s+", "");

                        if (!expectedCellValue.equals(actualCellValue)) {
                            System.out.println("XLS content does not match at row " + expectedRow.getRowNum() + ", column " + expectedCell.getColumnIndex());
                            fail();
                        }
                    }
                }
            }

            while (expectedRowIteratorSC.hasNext() && actualRowIteratorSC.hasNext()) {
                Row expectedRow = expectedRowIteratorSC.next();
                Row actualRow = actualRowIteratorSC.next();

                Iterator<Cell> expectedCellIterator = expectedRow.cellIterator();
                Iterator<Cell> actualCellIterator = actualRow.cellIterator();

                while (expectedCellIterator.hasNext() && actualCellIterator.hasNext()) {
                    Cell expectedCell = expectedCellIterator.next();
                    Cell actualCell = actualCellIterator.next();


                    if (expectedCell.getCellType() == CellType.STRING && actualCell.getCellType() == CellType.STRING) {
                        String expectedCellValue = expectedCell.getStringCellValue().replaceAll("\\s+", "");
                        String actualCellValue = actualCell.getStringCellValue().replaceAll("\\s+", "");

                        if (!expectedCellValue.equals(actualCellValue)) {
                            System.out.println("XLS content does not match at row " + expectedRow.getRowNum() + ", column " + expectedCell.getColumnIndex());
                            fail();
                        }
                    }
                }
            }

            while (expectedRowIteratorE.hasNext() && actualRowIteratorE.hasNext()) {
                Row expectedRow = expectedRowIteratorE.next();
                Row actualRow = actualRowIteratorE.next();

                Iterator<Cell> expectedCellIterator = expectedRow.cellIterator();
                Iterator<Cell> actualCellIterator = actualRow.cellIterator();

                while (expectedCellIterator.hasNext() && actualCellIterator.hasNext()) {
                    Cell expectedCell = expectedCellIterator.next();
                    Cell actualCell = actualCellIterator.next();


                    if (expectedCell.getCellType() == CellType.STRING && actualCell.getCellType() == CellType.STRING) {
                        String expectedCellValue = expectedCell.getStringCellValue().replaceAll("\\s+", "");
                        String actualCellValue = actualCell.getStringCellValue().replaceAll("\\s+", "");

                        if (!expectedCellValue.equals(actualCellValue)) {
                            System.out.println("XLS content does not match at row " + expectedRow.getRowNum() + ", column " + expectedCell.getColumnIndex());
                            fail();
                        }
                    }
                }
            }



            expectedFileInputStreamST.close();
            expectedFileInputStreamSC.close();
            expectedFileInputStreamE.close();
            expectedFileInputStreamP.close();
            actualFileInputStreamP.close();
            actualFileInputStreamE.close();
            actualFileInputStreamSC.close();
            actualFileInputStreamST.close();

        } catch (Exception e) {
            e.printStackTrace();
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

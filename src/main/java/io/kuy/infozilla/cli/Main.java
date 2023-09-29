package io.kuy.infozilla.cli;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Date;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

import io.kuy.infozilla.filters.FilterChainEclipse;
import io.kuy.infozilla.helpers.DataExportUtility;
import org.json.JSONObject;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;

/**
 * Main
 */
@Command(name = "infozilla", version = "1.0")
public class Main implements Runnable{

  @Option(names = { "-s", "--with-stacktraces" }, description = "Process and extract stacktraces (default=true)")
  private boolean withStackTraces = true;

  @Option(names = { "-p", "--with-patches" }, description = "Process and extract patches (default=true)")
  private boolean withPatches = true;

  @Option(names = { "-c", "--with-source-code" }, description = "Process and extract source code regions (default=true)")
  private boolean withCode = true;

  @Option(names = { "-l", "--with-lists" }, description = "Process and extract lists (default=true)")
  private boolean withLists = true;

  @Option(names = "--charset", description = "Character Set of Input (default=ISO-8859-1)")
  private String inputCharset = "ISO-8859-1";

  @Parameters(arity = "1..*", paramLabel = "FILE", description = "File(s) to process.")
  private File[] inputFiles;

  @Option(names = { "-o", "--output-format" }, description = "Output format (json, xml, xls, etc.)")
  private String outputFormat = "xml"; // Valore predefinito

  @Override
  public void run() {

    for (File f : inputFiles) {
      try {
        process(f);
      } catch (Exception e) {
        e.printStackTrace();
        System.exit(1);
      }
    }

  }
  private static void writeCSVToFile(File folder, String filename, String csvData, String charset) {
    try {
      Files.writeString(Path.of(folder.getAbsolutePath(), filename), csvData, Charset.forName(charset), StandardOpenOption.CREATE);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }


  private void process(File f) throws Exception {
    // Read file
    String data = Files.readString(f.toPath(), Charset.forName(inputCharset));

    // Run infozilla
    FilterChainEclipse infozilla_filters = new FilterChainEclipse(data, withPatches, withStackTraces, withCode, withLists);

    // Infozilla remembers the original input text
    // String original_text = infozilla_filters.getInputText();

    // Infozilla can filter out all structural elements and output
    // the remaining (natural language text parts) of the input.
    String filtered_text = infozilla_filters.getOutputText();

    System.out.println("Extracted Structural Elements from " + f.getAbsolutePath());
    System.out.println(infozilla_filters.getPatches().size() + "\t Patches");
    System.out.println(infozilla_filters.getTraces().size() + "\t Stack Traces");
    System.out.println(infozilla_filters.getRegions().size() + "\t Source Code Fragments");
    System.out.println(infozilla_filters.getEnumerations().size() + "\t Enumerations");

    System.out.println("Writing Cleaned Output");
    Files.writeString(Path.of(f.getAbsolutePath() + ".cleaned"), filtered_text, Charset.forName(inputCharset), StandardOpenOption.CREATE );

    String p ="";
    String[] s;
    String modP ="";
    String dirp ="";
    String[] sdir;


    switch (outputFormat) {
      case "json":
        // Export as JSON

        JSONObject jsonOutput = new JSONObject();
        jsonOutput.put("Patches", DataExportUtility.getJSONExportOfPatches(infozilla_filters.getPatches(), true));
        jsonOutput.put("StackTraces", DataExportUtility.getJSONExportOfStackTraces(infozilla_filters.getTraces(), true, new Timestamp(new Date().getTime())));
        jsonOutput.put("SourceCodeRegions", DataExportUtility.getJSONExportOfSourceCode(infozilla_filters.getRegions(), true));
        jsonOutput.put("Enumerations", DataExportUtility.getJSONExportOfEnumerations(infozilla_filters.getEnumerations(), true));

        String jsonDoc = jsonOutput.toString(4);

        System.out.println("Writing JSON Output");
        Files.writeString(Path.of(f.getAbsolutePath() + ".result.json"), jsonDoc, Charset.forName(inputCharset), StandardOpenOption.CREATE );
        break;
      case "csv":
        // Export as CSV
         p = f.getAbsolutePath();
         if(p.contains("/")){
           s = p.split("/");
           s[s.length - 1] = s[s.length - 1].replace(".","_");
           s[s.length - 2] = s[s.length -2] + "/"+ s[s.length - 1 ]+"_CSV";
           modP = String.join("/", s);
           sdir = Arrays.copyOf(s,s.length-1);
           dirp = String.join("/", sdir);

         }else {
           s = p.split("\\\\");
           System.out.println(p);
           s[s.length - 1] = s[s.length - 1].replace(".", "_");
           s[s.length - 2] = s[s.length - 2] + "\\" + s[s.length - 1] + "_CSV";
           modP = String.join("\\\\", s);
           sdir = Arrays.copyOf(s, s.length - 1);
           dirp = String.join("\\\\", sdir);
         }

          String patchesCSV = DataExportUtility.exportPatchesToCSV(infozilla_filters.getPatches(), true);
          String stackTracesCSV = DataExportUtility.getCSVExportOfStackTraces(infozilla_filters.getTraces(), true, new Timestamp(new Date().getTime()));
          String sourceCodeCSV = DataExportUtility.getCSVExportOfSourceCode(infozilla_filters.getRegions(), true);
          String enumerationsCSV = DataExportUtility.getCSVExportOfEnumerations(infozilla_filters.getEnumerations(), true);

          if( !Files.exists(Path.of(dirp))){
            Files.createDirectories(Path.of(dirp));
          }

          Files.writeString(Path.of(modP+ "_patches.csv"), patchesCSV, Charset.forName(inputCharset), StandardOpenOption.CREATE );
          Files.writeString(Path.of(modP + "_stackTraces.csv"), stackTracesCSV, Charset.forName(inputCharset), StandardOpenOption.CREATE );
          Files.writeString(Path.of(modP + "_sourceCode.csv"), sourceCodeCSV, Charset.forName(inputCharset), StandardOpenOption.CREATE );
          Files.writeString(Path.of(modP + "_enumerations.csv"), enumerationsCSV, Charset.forName(inputCharset), StandardOpenOption.CREATE );
          break;


      case "xls":
        // Export as XLS
        p = f.getAbsolutePath();
        if(p.contains("/")){
          s = p.split("/");
          s[s.length - 1] = s[s.length - 1].replace(".","_");
          s[s.length - 2] = s[s.length -2] + "/"+ s[s.length - 1 ]+"_XLS";
          modP = String.join("/", s);
          sdir = Arrays.copyOf(s,s.length-1);
          dirp = String.join("/", sdir);

        }else {
          s = p.split("\\\\");
          s[s.length - 1] = s[s.length - 1].replace(".", "_");
          s[s.length - 2] = s[s.length - 2] + "\\" + s[s.length - 1] + "_XLS";
          modP = String.join("\\\\", s);
          sdir = Arrays.copyOf(s, s.length - 1);
          dirp = String.join("\\\\", sdir);
        }

        if( !Files.exists(Path.of(dirp))){
          Files.createDirectories(Path.of(dirp));
        }

        File outputFile;
        outputFile  = new File(modP+ "_patches.xls");
        DataExportUtility.getXLSExportOfPatches(infozilla_filters.getPatches(), true, outputFile);
        outputFile  = new File(modP+ "_stackTraces.xls");
        DataExportUtility.getXLSExportOfStackTrace(infozilla_filters.getTraces(), true, new Timestamp(new Date().getTime()),outputFile);
        outputFile  = new File(modP+ "_sourceCode.xls");
        DataExportUtility.getXLSExportOfSourceCode(infozilla_filters.getRegions(), true,outputFile);
        outputFile  = new File(modP+ "_enumerations.xls");
        DataExportUtility.getXLSExportOfEnumerations(infozilla_filters.getEnumerations(), true,outputFile);
        break;


      case "xml":
        // Export as XML
        Element rootE = new Element("infozilla-output");
        rootE.addContent(DataExportUtility.getXMLExportOfPatches(infozilla_filters.getPatches(), true));
        rootE.addContent(DataExportUtility.getXMLExportOfStackTraces(infozilla_filters.getTraces(), true, new Timestamp(new Date().getTime())));
        rootE.addContent(DataExportUtility.getXMLExportOfSourceCode(infozilla_filters.getRegions(), true));
        rootE.addContent(DataExportUtility.getXMLExportOfEnumerations(infozilla_filters.getEnumerations(), true));

        Document doc = new Document(rootE);
        XMLOutputter outputter = new XMLOutputter(Format.getPrettyFormat());
        String xmlDoc = outputter.outputString(doc);

        System.out.println("Writing XML Output");
        Files.writeString(Path.of(f.getAbsolutePath() + ".result.xml"), xmlDoc, Charset.forName(inputCharset), StandardOpenOption.CREATE );
        break;
      default:

        break;
    }






  }

  public static void main(String[] args) {
    CommandLine.run(new Main(), args);
  }

}
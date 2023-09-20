/**
 * DataExportUtility.java
 * This file is part of the infoZilla framework and tool.
 */
package io.kuy.infozilla.helpers;

import java.io.BufferedWriter;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;

import org.jdom.Attribute;
import org.jdom.Element;
import org.json.JSONObject;
import org.json.JSONArray;

import io.kuy.infozilla.elements.enumeration.Enumeration;
import io.kuy.infozilla.elements.patch.Patch;
import io.kuy.infozilla.elements.patch.PatchHunk;
import io.kuy.infozilla.elements.sourcecode.java.CodeRegion;
import io.kuy.infozilla.elements.stacktrace.java.StackTrace;

public class DataExportUtility {
	
	
	/**
	 * Get an XML Export of a list of Stack Traces
	 * @param traces a List of {@link StackTrace} that should be exported as a new XML node
	 * @return an Element "Stack Traces" containing an XML Export of the given stack traces.
	 */
	public static final Element getXMLExportOfStackTraces(List<StackTrace> traces, boolean withFrames, Timestamp ts) {
		Element rootE = new Element("Stacktraces");
		rootE.setAttribute(new Attribute("amount", Integer.toString(traces.size())));

		// Add each Stack Traces to the JDOM Tree
		for (StackTrace trace : traces) {
			
			// Distinguish between trace and cause
			Element traceE;
			if (trace.isCause()) 
				traceE = new Element("Cause");
			else 
				traceE = new Element("Stacktrace");
			
			traceE.setAttribute(new Attribute("timestamp", Long.toString(ts.getTime())));
			// Add the Originating Exception
			Element exceptionE = new Element("Exception");
			exceptionE.setText(trace.getException());
			traceE.addContent(exceptionE);
			
			// As well as the Reason
			Element reasonE = new Element("Reason");
			reasonE.setText(trace.getReason());
			traceE.addContent(reasonE);
			
			if (withFrames) {
				// And last the Stack Frames (call stack)
				Element framesE = new Element("Frames");
				int depth = 0;
				for (String frame : trace.getFrames()) {
					Element frameE = new Element("Frame");
					frameE.setAttribute(new Attribute("depth", (Integer.toString(depth))));
					frameE.setText(frame);
					framesE.addContent(frameE);
					depth++;
				}
				traceE.addContent(framesE);
			}
			
			// Add this Stack Trace to the Root Element
			rootE.addContent(traceE);
		}
		
		// Return the set of Stack Traces
		return rootE;
	}
	
	
	public static final Element getXMLExportOfStackTrace(StackTrace trace, boolean withFrames, Timestamp ts) {
		Element rootE = new Element("Stacktrace");
					
		// Distinguish between trace and cause
		Element traceE;
		if (trace.isCause()) 
			traceE = new Element("Cause");
		else 
			traceE = new Element("Stacktrace");
		
		traceE.setAttribute(new Attribute("timestamp", Long.toString(ts.getTime())));
		// Add the Originating Exception
		Element exceptionE = new Element("Exception");
		exceptionE.setText(trace.getException());
		traceE.addContent(exceptionE);
		
		// As well as the Reason
		Element reasonE = new Element("Reason");
		reasonE.setText(trace.getReason());
		traceE.addContent(reasonE);
		
		if (withFrames) {
			// And last the Stack Frames (call stack)
			Element framesE = new Element("Frames");
			int depth = 0;
			for (String frame : trace.getFrames()) {
				Element frameE = new Element("Frame");
				frameE.setAttribute(new Attribute("depth", (Integer.toString(depth))));
				frameE.setText(frame);
				framesE.addContent(frameE);
				depth++;
			}
			traceE.addContent(framesE);
		}
		
		// Add this Stack Trace to the Root Element
		rootE.addContent(traceE);
		
		
		// Return the set of Stack Traces
		return rootE;
	}
	
	
	/**
	 * Get an XML Export of a list of Patches
	 * @param patches a list of {@link Patch} that should be exported as new XML node
	 * @param withHunks a boolean value, true if we want complete Hunk output, false if this is not desired.
	 * @return an Element "Patches" containing an XML Export of the given patches.
	 */
	public static final Element getXMLExportOfPatches(List<Patch> patches, boolean withHunks) {
		Element rootE = new Element("Patches");
		rootE.setAttribute(new Attribute("amount", Integer.toString(patches.size())));
		
		// Add each Patch to the JDOM Tree
		for (Patch patch : patches) {
			Element patchE = new Element("Patch");
			
			// Add the index
			Element indexE = new Element("index");
			indexE.setText(patch.getIndex());
			patchE.addContent(indexE);
			
			// Add the original File
			Element origE = new Element("original_file");
			origE.setText(patch.getOriginalFile());
			patchE.addContent(origE);
			
			// Add the modified File
			Element modE = new Element("modified_file");
			modE.setText(patch.getModifiedFile());
			patchE.addContent(modE);
			
			// Add the original header   <---- This is meant for Debugging
			//Element oheadE = new Element("original_header");
			//oheadE.setText(patch.getHeader());
			//patchE.addContent(oheadE);
			
			// Add the list of Patch Hunks if we want so
			if (withHunks) {
				Element hunksE = new Element("Hunks");
				for (PatchHunk hunk : patch.getHunks()) {
					Element hunkE = new Element("hunk");
					hunkE.setText(hunk.getText());
					hunksE.addContent(hunkE);
				}
				patchE.addContent(hunksE);
			}
			
			// Finally add the patch to the root Element
			rootE.addContent(patchE);
		}
		
		// Return the export
		return rootE;
	}
	
	
	/**
	 * Get an XML Export of a list of Source Code Regions (as provided by FilterSourceCode classes)
	 * @param coderegions a list of {@CodeRegion}s that should be exported as new XML node
	 * @param withCode a boolean value, true if we want complete source code text, false if this is not desired.
	 * @return an Element "Source Code Regions" containing an XML Export of the given Code Regions.
	 */
	public static final Element getXMLExportOfSourceCode(List<CodeRegion> coderegions, boolean withCode) {
		Element rootE = new Element("SourceCodeRegions");
		rootE.setAttribute(new Attribute("amount", Integer.toString(coderegions.size())));
		
		for (CodeRegion region : coderegions) {
			Element regionE = new Element("source_code");
			regionE.setAttribute(new Attribute("type", region.keyword));
			
			Element locationE = new Element("location");
			locationE.setAttribute(new Attribute("start", Integer.toString(region.start)));
			locationE.setAttribute(new Attribute("end", Integer.toString(region.end)));
			
			regionE.addContent(locationE);
			
			if (withCode) {
				Element codeE = new Element("code");
				codeE.setText(region.text);
				regionE.addContent(codeE);
			}
			
			rootE.addContent(regionE);
		}
		return rootE;
	}

	
	/**
	 * Get an XML Export of a list of Enumeration (as provided by the FilterEnumeration classes)
	 * @param enumerations a list of {@link Enumeration}s that should be exported as new XML node
	 * @param withLines a boolean value, true if we want complete enumeration lines 
	 * @return an Element "Enumerations" containing an XML Export of the given Enumerations
	 */
	public static final Element getXMLExportOfEnumerations(List<Enumeration> enumerations, boolean withLines) {
		Element rootE = new Element("Enumerations");
		rootE.setAttribute(new Attribute("amount", Integer.toString(enumerations.size())));
		
		for (Enumeration enu : enumerations) {
			Element enumE = new Element("Enumeration");
			enumE.setAttribute(new Attribute("lines", Integer.toString(enu.getEnumeration_items().size())));
			
			if (withLines) {
				Element linesE = new Element("Lines");
				for (String line : enu.getEnumeration_items()) {
					Element lineE = new Element("Line");
					lineE.setText(line);
					linesE.addContent(lineE);
				}
				enumE.addContent(linesE);
			}
			
			rootE.addContent(enumE);
		}
		
		return rootE;
	}

	//JSON

	public static final JSONObject getJSONExportOfStackTraces(List<StackTrace> traces, boolean withFrames, Timestamp ts) {
		JSONObject rootJSON = new JSONObject();
		rootJSON.put("amount", traces.size());

		JSONArray stackTracesArray = new JSONArray();

		for (StackTrace trace : traces) {
			JSONObject traceJSON = new JSONObject();

			if (trace.isCause()) {
				traceJSON.put("type", "Cause");
			} else {
				traceJSON.put("type", "Stacktrace");
			}

			traceJSON.put("timestamp", ts.getTime());
			traceJSON.put("Exception", trace.getException());
			traceJSON.put("Reason", trace.getReason());

			if (withFrames) {
				JSONArray framesArray = new JSONArray();
				int depth = 0;
				for (String frame : trace.getFrames()) {
					JSONObject frameJSON = new JSONObject();
					frameJSON.put("depth", depth);
					frameJSON.put("frame", frame);
					framesArray.put(frameJSON);
					depth++;
				}
				traceJSON.put("Frames", framesArray);
			}

			stackTracesArray.put(traceJSON);
		}

		rootJSON.put("Stacktraces", stackTracesArray);

		return rootJSON;
	}


	public static final JSONObject getJSONExportOfEnumerations(List<Enumeration> enumerations, boolean withLines) {
		JSONObject rootJSON = new JSONObject();
		rootJSON.put("amount", enumerations.size());

		JSONArray enumerationsArray = new JSONArray();

		for (Enumeration enu : enumerations) {
			JSONObject enumJSON = new JSONObject();
			enumJSON.put("lines", enu.getEnumeration_items().size());

			if (withLines) {
				JSONArray linesArray = new JSONArray();
				for (String line : enu.getEnumeration_items()) {
					linesArray.put(line);
				}
				enumJSON.put("Lines", linesArray);
			}

			enumerationsArray.put(enumJSON);
		}

		rootJSON.put("Enumerations", enumerationsArray);

		return rootJSON;
	}


	public static final JSONObject getJSONExportOfSourceCode(List<CodeRegion> coderegions, boolean withCode) {
		JSONObject rootJSON = new JSONObject();
		rootJSON.put("amount", coderegions.size());

		JSONArray sourceCodeRegionsArray = new JSONArray();

		for (CodeRegion region : coderegions) {
			JSONObject regionJSON = new JSONObject();
			regionJSON.put("type", region.keyword);

			JSONObject locationJSON = new JSONObject();
			locationJSON.put("start", region.start);
			locationJSON.put("end", region.end);

			regionJSON.put("location", locationJSON);

			if (withCode) {
				regionJSON.put("code", region.text);
			}

			sourceCodeRegionsArray.put(regionJSON);
		}

		rootJSON.put("SourceCodeRegions", sourceCodeRegionsArray);

		return rootJSON;
	}

	public static final JSONObject getJSONExportOfPatches(List<Patch> patches, boolean withHunks) {
		JSONObject rootJSON = new JSONObject();
		rootJSON.put("amount", patches.size());

		JSONArray patchesArray = new JSONArray();

		for (Patch patch : patches) {
			JSONObject patchJSON = new JSONObject();
			patchJSON.put("index", patch.getIndex());
			patchJSON.put("original_file", patch.getOriginalFile());
			patchJSON.put("modified_file", patch.getModifiedFile());

			if (withHunks) {
				JSONArray hunksArray = new JSONArray();
				for (PatchHunk hunk : patch.getHunks()) {
					JSONObject hunkJSON = new JSONObject();
					hunkJSON.put("hunk", hunk.getText());
					hunksArray.put(hunkJSON);
				}
				patchJSON.put("Hunks", hunksArray);
			}

			patchesArray.put(patchJSON);
		}

		rootJSON.put("Patches", patchesArray);

		return rootJSON;
	}

	//CSV

	public static String exportPatchesToCSV(List<Patch> patches, boolean withHunks) {

		StringBuilder csvData = new StringBuilder();
		csvData.append("\"" + "Amount" + "\"");
		csvData.append(",");
		csvData.append("\"" + "Index" + "\"");
		csvData.append(",");
		csvData.append("\"" + "Original File" + "\"");
		csvData.append(",");
		csvData.append("\"" + "Modified File" + "\"");
		csvData.append(",");

		if (withHunks) {
			csvData.append("\"" + "Hunk Text" + "\"");
		}

		csvData.append("\n");


		for (Patch patch : patches) {
			if (withHunks) {
				for (PatchHunk hunk : patch.getHunks()) {
					csvData.append("\"" +  patches.size() + "\"");
					csvData.append(",");
					csvData.append( "\"" +  patch.getIndex() + "\"" );
					csvData.append(",");
					csvData.append("\"" + patch.getOriginalFile() + "\"");
					csvData.append(",");
					csvData.append("\"" + patch.getModifiedFile() + "\"");
					csvData.append(",");
					csvData.append("\"" + hunk.getText() + "\"");
					csvData.append("\n");
				}
			}else{
				csvData.append("\"" +  patches.size() + "\"");
				csvData.append(",");
				csvData.append( "\"" +  patch.getIndex() + "\"" );
				csvData.append(",");
				csvData.append("\"" + patch.getOriginalFile() + "\"");
				csvData.append(",");
				csvData.append("\"" + patch.getModifiedFile() + "\"");
				csvData.append("\n");
			}

		}

		return csvData.toString();
	}

	public static String getCSVExportOfSourceCode(List<CodeRegion> coderegions, boolean withCode) {
		StringBuilder csvData = new StringBuilder();
		csvData.append("\"" + "Type" + "\"");
		csvData.append(",");
		csvData.append("\"" + "Start" + "\"");
		csvData.append(",");
		csvData.append("\"" + "End" + "\"");

		if (withCode) {
			csvData.append(",");
			csvData.append("\"" + "Code" + "\"");

		}
		csvData.append("\n");

		for (CodeRegion region : coderegions) {
			if (withCode) {
				csvData.append("\"" + region.keyword + "\"");
				csvData.append(",");
				csvData.append("\"" + region.start + "\"");
				csvData.append(",");
				csvData.append("\"" + region.end+ "\"");
				csvData.append(",");
				csvData.append("\"" + region.text+ "\"");
				csvData.append("\n");
			}
			else{
				csvData.append("\"" + region.keyword + "\"");
				csvData.append(",");
				csvData.append("\"" + region.start + "\"");
				csvData.append(",");
				csvData.append("\"" + region.end+ "\"");
				csvData.append("\n");
			}
		}

		return csvData.toString();
	}


	public static String getCSVExportOfEnumerations(List<Enumeration> enumerations, boolean withLines) {
		StringBuilder csvData = new StringBuilder();
		csvData.append( "\"" + "Amount" + "\"");

		if (withLines) {
			csvData.append(",");
			csvData.append("\"" + "Enumeration" + "\"");
			csvData.append(",");
			csvData.append( "\"" + "Item" + "\"");
		}


		for (Enumeration enu : enumerations) {
			if (withLines) {
				for (String line : enu.getEnumeration_items()) {
					csvData.append("\n");
					csvData.append("\""+enumerations.size()+"\"").append(",");
					csvData.append("\""+enu.getEnumeration_items().size()+"\"").append(",");
					csvData.append("\""+line +"\"");
				}
			}
		}

		return csvData.toString();
	}

	public static String getCSVExportOfStackTraces(List<StackTrace> traces, boolean withFrames, Timestamp ts) {

		StringBuilder csvData = new StringBuilder();
		csvData.append("\"" + "Amount" + "\"");
		csvData.append(",");
		csvData.append("\"" + "Type" + "\"");
		csvData.append(",");
		csvData.append("\"" + "Timestamp" + "\"");
		csvData.append(",");
		csvData.append("\"" + "Exception" + "\"");
		csvData.append(",");
		csvData.append("\"" + "Reason" + "\"");



		if (withFrames) {
			csvData.append(",");
			csvData.append("\"" + "Depth" + "\"");
			csvData.append(",");
			csvData.append("\"" + "Frame" + "\"");


			csvData.append("\n");


			for (StackTrace trace : traces) {

				if (withFrames) {

					int depth = 0;
					for (String frame : trace.getFrames()) {
						csvData.append("\"" + traces.size() + "\"");
						csvData.append(",");
						if (trace.isCause()) {
							csvData.append("\"" + "Cause" + "\"");
						} else {
							csvData.append("\"" + "Stacktrace" + "\"");
						}
						csvData.append(",");
						csvData.append("\"" + ts.getTime() + "\"");
						csvData.append(",");
						csvData.append("\"" + trace.getException() + "\"");
						csvData.append(",");
						csvData.append("\"" + trace.getReason() + "\"");
						csvData.append(",");

						csvData.append("\"" + depth + "\"" ).append(",").append("\""  + frame + "\"" );
						depth++;
						csvData.append("\n");
					}
				}
			}
		}
		else {
			for (StackTrace trace : traces) {
				csvData.append("\"" + traces.size() + "\"");
				csvData.append(",");
				if (trace.isCause()) {
					csvData.append("\"" + "Cause" + "\"");
				} else {
					csvData.append("\"" + "Stacktrace" + "\"");
				}
				csvData.append(",");
				csvData.append("\"" + ts.getTime() + "\"");
				csvData.append(",");
				csvData.append("\"" + trace.getException() + "\"");
				csvData.append(",");
				csvData.append("\"" + trace.getReason() + "\"");
				csvData.append(",");
				csvData.append("\n");
			}
		}
		return csvData.toString();
	}





	/**
	 * Write a CSV line to a BufferedWriter stream
	 * @param writer	The writer to write to
	 * @param bug_id	the bug report id
	 * @param foundStackTrace	if a stacktrace was found
	 * @param foundPatch		if a patch was found
	 * @param foundSource		if source code was found
	 * @param foundEnum			if enumerations were found
	 */
	public static void writeCSV(BufferedWriter writer,
								int bug_id, 
								boolean foundStackTrace,
								boolean foundPatch,
								boolean foundSource,
								boolean foundEnum) {
		try {
			
			
			writer.write(Integer.toString(bug_id) + "," 
					+ getIntFromBoolean(foundStackTrace) + ","
					+ getIntFromBoolean(foundPatch) + ","
					+ getIntFromBoolean(foundSource) + ","
					+ getIntFromBoolean(foundEnum) + System.getProperty("line.separator"));
		} catch (IOException e) {
			System.out.println("Could not write CSV file!");
			System.out.println(e.getMessage());
			System.exit(1);
		}
	}
	
	/**
	 * Write the Results of the Duplicates analysis to a BufferedWriter Stream
	 * @param writer the writer to write to
	 * @param dupe_id the id of the duplicate report
	 * @param original_id the id of the coupled original report
	 * @param opa the amount of patches in the original report
	 * @param ost the amount of stack traces in the original report
	 * @param oso the amount of source code in the original report
	 * @param oen the amount of enumerations in the original report
	 * @param dpa the amount of patches in the duplicate report
	 * @param dst the amount of stack traces in the duplicate report
	 * @param dso the amount of source code in the duplicate report
	 * @param den the amount of enumerations in the duplicate report
	 */
	public static void writeCSVDuplicateAnalysis(BufferedWriter writer,
			int dupe_id, int original_id, int opa, int ost, int oso, int oen,
										  int dpa, int dst, int dso, int den) {
		try {
			
			writer.write(dupe_id + "," 
					+ original_id + ","
					+ opa + ","
					+ ost + ","
					+ oso + ","
					+ oen + ","
					+ dpa + ","
					+ dst + ","
					+ dso + ","
					+ den 
					+ System.getProperty("line.separator"));
			
		} catch (IOException e) {
			System.out.println("Could not write CSV file!");
			System.out.println(e.getMessage());
			System.exit(1);
		}
	}
	
	
	/**
	 * Helper Function to convert boolean to int
	 * @param b a boolean value
	 * @return 0 if false, 1 if true
	 */
	private static int getIntFromBoolean(boolean b) {
		if (b)
			return 1;
		else
			return 0;
	}
	
}

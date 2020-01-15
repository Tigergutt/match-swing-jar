package se.melsom.io.docx;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;

import org.apache.log4j.Logger;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.apache.xmlbeans.XmlCursor;
import org.apache.xmlbeans.impl.values.XmlValueDisconnectedException;

public abstract class WordDocument {
	private static Logger logger = Logger.getLogger(WordDocument.class);
	static final String HEADER_1_STYLE_NAME = "Rubrik1";
	static final String HEADER_2_STYLE_NAME = "Rubrik2";
	static final String HEADER_3_STYLE_NAME = "Rubrik3";
	static final String NORMAL_STYLE_NAME = "Normal";
	static final String TABLE_TEXT_STYLE_NAME = "Tabelltext";
	static final String COMMENT_STYLE_NAME = "Citat";
	
    private XWPFDocument document = null;

	public WordDocument(String template) {
		InputStream input = getClass().getResourceAsStream("/templates/" + template);
		try {
			document = new XWPFDocument(input);
		} catch (IOException e) {
			logger.warn("Failed to open template!", e);
		}	
	}
	
	public boolean isOk() {
		return document != null;
	}
	
	XWPFParagraph findParagraph(String text) {
		for (XWPFParagraph paragraph : document.getParagraphs()) {
			try {
//				System.out.println("<p>" + paragraph.getText() + "</p>");
				if (text.equals(paragraph.getText())) {
					return paragraph;
				}
			} catch (XmlValueDisconnectedException e) {
			}
		}

		return null;
	}

	void insertParagraph(String text, String style, XWPFParagraph successor) {
		XmlCursor cursor = successor.getCTP().newCursor();
		XWPFParagraph p1 = document.insertNewParagraph(cursor);
        XWPFRun r1 = p1.createRun();
        p1.setStyle(style);
        r1.setText(text);
        cursor.dispose();

	}
	
	void removeParagraph(XWPFParagraph paragraph) {
		XmlCursor cursor = paragraph.getCTP().newCursor();
		
        cursor.removeXml();
        cursor.dispose();
	}
	
	XWPFTable createTable(String[] tableHeaderText, String style, XWPFParagraph successor) {
		XmlCursor cursor = successor.getCTP().newCursor();		
        XWPFTable table = document.insertNewTbl(cursor);        
    	XWPFTableRow header = table.getRow(0);
    	
        for (int columnIndex = 0; columnIndex < tableHeaderText.length; columnIndex++) {
        	if (tableHeaderText[columnIndex] == null) {
        		continue;
        	}
        	
        	XWPFTableCell cell = columnIndex == 0 ? header.getCell(0) : header.createCell();
            XWPFParagraph para = cell.getParagraphs().get(0);

            para.setStyle(style);
            
            XWPFRun run = para.createRun();

            run.setBold(true);
            run.setText(tableHeaderText[columnIndex]);
        }
       
        cursor.dispose();
        
        return table;
	}
	
//    void addRecords(Collection<RecordElement> records, String style, XWPFTable table) {
//    	String color = "ffffff";
//    	boolean isStrikeThrough = false;
//    	
//    	for (RecordElement record : records) {
//			XWPFTableRow row = table.createRow();
//
//			setCellContent(record.getId(), color, isStrikeThrough, style, row.getCell(0));
//			setCellContent(record.getSummary(), color, isStrikeThrough, style, row.getCell(1));
//			setCellContent(record.getScope(), color, isStrikeThrough, style, row.getCell(2));
//    	}
//    }
    
//    void addDataElement(DataElement field, boolean isDiff, boolean isRemoved, String[] tableHeaderText, String style, XWPFTable table) {
//    	String color = isDiff ? "ffff00" : "ffffff";
//    	boolean isStrikeThrough = isRemoved;
//		XWPFTableRow row = table.createRow();
//		int columnIndex = 0;
//		int cellIndex = 0;
//		
//		if (tableHeaderText[columnIndex++] != null) {
//			setCellContent(field.getName(), color, isStrikeThrough, style, row.getCell(cellIndex++));
//		}
//
//		String dataType = "Unknown";
//		switch (field.getType()) {
//        case ALPHANUMERIC:
//        	dataType = "Text";
//        	break;
//        	
//        case INTEGER:
//        	dataType = "Heltal";            	
//        	break;
//        	
//        case DECIMAL:
//        	dataType = "Decimalt";
//        	break;
//        	
//        default:
//        	break;
//        }
//		
//		if (tableHeaderText[columnIndex++] != null) {
//			setCellContent(dataType, color, isStrikeThrough, style, row.getCell(cellIndex++));
//		}
//
//        //content += "<td style='text-align:right'>";
//		String size = "";
//    	if (field instanceof DecimalElement) {
//        	size = "" + (field.getSize() + field.getDecimals());
//            size += " (" + field.getSize();                 	
//            size += "," + field.getDecimals() + ")"; 
//    	} else {
//        	size = "" + field.getSize();
//    	}
//    	
//		if (tableHeaderText[columnIndex++] != null) {
//			setCellContent(size, color, isStrikeThrough, style, row.getCell(cellIndex++));
//		}
//
//        //content += "<td style='text-align:right'>";
//		if (tableHeaderText[columnIndex++] != null) {
//			setCellContent("" + field.getStart(), color, isStrikeThrough, style, row.getCell(cellIndex++));
//		}
//        
//        //content += "<td style='text-align:right'>";
//		if (tableHeaderText[columnIndex++] != null) {
//			setCellContent("" + field.getEnd(), color, isStrikeThrough, style, row.getCell(cellIndex++));
//		}
//
//		if (tableHeaderText[columnIndex++] != null) {
//			setCellContent(field.getSource(), color, isStrikeThrough, style, row.getCell(cellIndex++));
//		}
//
//		Vector<String> description = new Vector<>(field.getDescription());
//
//		if (field.getScope().length() > 0) {
//        	description.addElement("Avser: " + field.getScope());
//        }
//        
//		if (tableHeaderText[columnIndex++] != null) {
//			setCellContent(description, color, isStrikeThrough, style, row.getCell(cellIndex++));
//		}
//        
//		if (tableHeaderText[columnIndex++] != null) {
//			setCellContent(field.getComments(), color, isStrikeThrough, style, row.getCell(cellIndex++));
//		}
//    }
    
//	void createTable(Collection<DataElement> fields, String[] tableHeaderText, String style, XWPFParagraph successor) {
//		XWPFTable table = createTable(tableHeaderText, style, successor);
//        
//		for (DataElement field : fields) {
//			addDataElement(field, false, false, tableHeaderText, style, table);
//		}
//	}
	
	void setCellContent(String text, String color, boolean strikeThrough, String style, XWPFTableCell cell) {
        XWPFParagraph para = cell.getParagraphs().get(0);

        cell.setColor(color);        
        para.setStyle(style);
        
        XWPFRun run = para.createRun();
        
        run.setStrikeThrough(strikeThrough);
        run.setText(text);
	}
	
	void setCellContent(Collection<String> text, String color, boolean strikeThrough, String style, XWPFTableCell cell) {
        XWPFParagraph para = null;
        
        for (String line : text) {
        	if (para == null) {
        		para = cell.getParagraphs().get(0);
        	} else {
        		para = cell.addParagraph();
        	}

            para.setStyle(style);                        
            cell.setColor(color);

            XWPFRun run = para.createRun();
            run.setStrikeThrough(strikeThrough);
            run.setText(line);
        }
	}
	
	public void save(File file) {
        FileOutputStream out = null;
		try {
			out = new FileOutputStream(file);
	        document.write(out);
		} catch (IOException e) {
			logger.error("Could not save file '" + file.getPath() + "'", e);
			e.printStackTrace();
		} finally {
	        try {
	        	if (out != null) {
	        		out.close();
	        	}
			} catch (IOException e) {
				logger.error("Could not close file '" + file.getPath() + "'", e);
			}
		}
		
		logger.info("Saved file '" + file.getPath() + "'");
	}
}

package se.melsom.io.docx;

import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFTable;

public class WordRecordDocumentation extends WordDocument {
	static final String OVERVIEW_TAG = "<posttyper>";
	static final String STARTING_RECORDS_DESCRIPTION_TAG = "<startposter>";
	static final String HEADER_RECORDS_DESCRIPTION_TAG = "<huvudposter>";
	static final String DATA_RECORDS_DESCRIPTION_TAG = "<dataposter>";
	static final String ENDING_RECORDS_DESCRIPTION_TAG = "<avslutningsposter>";
	private String[] fieldNames = null;

	public WordRecordDocumentation(String template, String[] fieldNames) {
		super(template);
		this.fieldNames = fieldNames;
	}
	
//	@Override
//	public void setContent(RecordDefinitions records) {
//    	String[] header = {"Posttyp", "Beskrivning", "Avser"};
//    	XWPFParagraph taggedParagraph = findParagraph(OVERVIEW_TAG);
//    	XWPFTable table = createTable(header, TABLE_TEXT_STYLE_NAME, taggedParagraph);
//    	
//    	addRecords(records.getStartingRecord().getRecords(), TABLE_TEXT_STYLE_NAME, table);
//    	addRecords(records.getDataRecords().getRecords(), TABLE_TEXT_STYLE_NAME, table);
//    	addRecords(records.getEndingRecords().getRecords(), TABLE_TEXT_STYLE_NAME, table);
//        removeParagraph(taggedParagraph);
//		
//    	taggedParagraph = findParagraph(STARTING_RECORDS_DESCRIPTION_TAG);
//        setRecordsContent(records.getStartingRecord(), taggedParagraph);
//        removeParagraph(taggedParagraph);
//        
//    	taggedParagraph = findParagraph(HEADER_RECORDS_DESCRIPTION_TAG);
//        setRecordsContent(records.getHeaderRecords(), taggedParagraph);
//        removeParagraph(taggedParagraph);
//
//    	taggedParagraph = findParagraph(DATA_RECORDS_DESCRIPTION_TAG);
//        setRecordsContent(records.getDataRecords(), taggedParagraph);
//        removeParagraph(taggedParagraph);
//        
//    	taggedParagraph = findParagraph(ENDING_RECORDS_DESCRIPTION_TAG);
//        setRecordsContent(records.getEndingRecords(), taggedParagraph);        
//        removeParagraph(taggedParagraph);
//	}

//   void setRecordsContent(DeclarationModel records, XWPFParagraph descriptions) {
//    	for (RecordElement record : records.getRecords()) {
//    		
//    		insertParagraph("Posttyp " + record.getId() + " " + record.getSummary(), HEADER_2_STYLE_NAME, descriptions);
//
//    		if (record.getScope().length() > 0) {
//    			insertParagraph("Avser " + record.getScope() + ".", NORMAL_STYLE_NAME, descriptions);
//            }
//    		
//    		if (record.getComments().size() > 0) {
//    			for (String line : record.getComments()) {
//        			insertParagraph(line, COMMENT_STYLE_NAME, descriptions);
//    			}
//    			
//    		}
//            
//            Collection<DataElement> fields = DeclarationModel.getDataElements(record);
//    		
//    		records.calculateElementStartEnd(record, fields);
//            createTable(fields, fieldNames, TABLE_TEXT_STYLE_NAME, descriptions);
//    	}
//    }

//    @Override
//	public void saveContent(File file) {
//    	save(file);
//	}
}

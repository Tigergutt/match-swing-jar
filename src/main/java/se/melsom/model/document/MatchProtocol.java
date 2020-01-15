package se.melsom.model.document;

import java.util.Vector;

import org.apache.log4j.Logger;

import se.melsom.model.competitor.Competitor;
import se.melsom.model.document.element.DocumentElement;
import se.melsom.model.document.element.HeadingElement;
import se.melsom.model.document.element.PageElement;
import se.melsom.model.document.element.TableElement;
import se.melsom.model.document.element.table.ContentCell;
import se.melsom.model.document.element.table.FillerCell;
import se.melsom.model.document.element.table.Row;
import se.melsom.model.program.MatchProgram;
import se.melsom.model.program.ProgramStage;
import se.melsom.model.program.ProgramString;
import se.melsom.model.program.StageTeam;

public class MatchProtocol {
	private static Logger logger = Logger.getLogger(MatchProtocol.class);

	private Vector<DocumentElement> elements = new Vector<>();

	public MatchProtocol(ProgramStage stage) {
		MatchProgram program = (MatchProgram) stage.getParent();
		
		for (StageTeam team : stage.getTeams()) {
			PageElement page = new PageElement();
			
			elements.addElement(page);
			String headerText =  program.getTitle() + " " + program.getDate() + " - " + stage.getTitle();
			HeadingElement protocolHeader = new HeadingElement(16, headerText);

			protocolHeader.setMargins(0, 0, 0, 9);
			page.addElement(protocolHeader);

			HeadingElement teamHeader = new HeadingElement(12, team.getTitle());

			teamHeader.setMargins(0, 0, 6, 3);
			page.addElement(teamHeader);
			
			logger.trace("Page, " + stage.getTitle() + ", " + team.getTitle());
			
			TableElement table = new TableElement(9);
			Row upperHeader = new Row();
			Row lowerHeader = new Row();

			upperHeader.addCell(new ContentCell("", true));
			lowerHeader.addCell(new ContentCell("Bana", true));
			
			upperHeader.addCell(new ContentCell("", true));
			lowerHeader.addCell(new ContentCell("Skytt", true));

			for (ProgramString string : stage.getStrings()) {
				ContentCell stringHeader = new ContentCell(string.getTitle(), true);

				for (int shotIndex = 0; shotIndex < string.getShotCount(); shotIndex++) {
					if (shotIndex == 0) {
						upperHeader.addCell(stringHeader);
					} else {
						upperHeader.addCell(new FillerCell(stringHeader));
					}
					lowerHeader.addCell(new ContentCell("" + (shotIndex + 1), true));
				}
			}
				
			table.addRow(upperHeader);
			table.addRow(lowerHeader);

			for (int laneIndex : team.getLanes()) {
				Row row = new Row();
				Competitor competitor = team.getCompetitor(laneIndex);

				row.addCell(new ContentCell("" + (laneIndex + 1), false));
				row.addCell(new ContentCell(competitor == null ? "" : competitor.toString(), false));

				for (ProgramString programString : stage.getStrings()) {
					for (int shotIndex = 0; shotIndex < programString.getShotCount(); shotIndex++) {
						row.addCell(new ContentCell(10, 10));
					}
				}

				logger.trace("Row: " + row);
				table.addRow(row);
			}
			
			page.addElement(table);
		}
	}
	
	public void process(DocumentProcessor processor) {
		for (DocumentElement element : elements) {
			element.process(processor);
		}
	}
	
	public void render(DocumentRenderer renderer) {
		for (DocumentElement element : elements) {
			element.render(renderer);
		}
	}

}

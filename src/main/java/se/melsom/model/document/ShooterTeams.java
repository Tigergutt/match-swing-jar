package se.melsom.model.document;

import java.util.Vector;

import org.apache.log4j.Logger;

import se.melsom.model.Competition;
import se.melsom.model.competitor.Competitor;
import se.melsom.model.document.element.DocumentElement;
import se.melsom.model.document.element.HeadingElement;
import se.melsom.model.document.element.TableElement;
import se.melsom.model.document.element.table.ContentCell;
import se.melsom.model.document.element.table.Row;
import se.melsom.model.program.ProgramStage;
import se.melsom.model.program.StageTeam;

public class ShooterTeams {
	private Vector<DocumentElement> elements = new Vector<>();
	private static Logger logger = Logger.getLogger(ShooterTeams.class);

	public ShooterTeams(Competition match, ProgramStage stage) {
		if (stage == null) {
			logger.warn("Stage index " + stage + " is out of bounds.");
			return;
		}
		
		String title = match.getMatchProgram().getTitle();
		String date = match.getMatchProgram().getDate();
		
		elements.addElement(new HeadingElement(1, title + " " + date));
		elements.addElement(new HeadingElement(2, "Skjutlagsindelning: " + stage.getTitle()));

		for (StageTeam team : stage.getTeams()) {
			elements.addElement(new HeadingElement(3, team.getTitle()));
			
				TableElement table = new TableElement(12);
				Row header = new Row();
				
				header.addCell(new ContentCell("Bana", true));
				header.addCell(new ContentCell("Skytt", true));
				header.addCell(new ContentCell("Grupp/kompani", true));
				
				table.addRow(header);

				for (int laneIndex = 0; laneIndex < stage.getTeamSize(); laneIndex++) {
					Row row = new Row();
					Competitor competitor = team.getCompetitor(laneIndex);

					row.addCell(new ContentCell("" + (laneIndex + 1), false));
					row.addCell(new ContentCell(competitor == null ? "" : competitor.toString(), false));
					row.addCell(new ContentCell(competitor == null ? "" : competitor.getUnit(), false));
					
					table.addRow(row);
				}
				
				elements.addElement(table);
		}
	}

	
	public void render(DocumentRenderer renderer) {
		for (DocumentElement element : elements) {
			element.render(renderer);
		}
	}
}

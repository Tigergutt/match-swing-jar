package se.melsom.model.document;

import java.util.Vector;

import org.apache.log4j.Logger;

import se.melsom.model.Competition;
import se.melsom.model.ShooterDatabase;
import se.melsom.model.document.element.DocumentElement;
import se.melsom.model.document.element.TableElement;
import se.melsom.model.document.element.table.ContentCell;
import se.melsom.model.document.element.table.Row;
import se.melsom.model.program.MatchProgram;
import se.melsom.model.program.ProgramStage;
import se.melsom.model.program.ProgramString;
import se.melsom.model.score.Scoreboard;
import se.melsom.model.score.Scorecard;
import se.melsom.model.score.StageScore;
import se.melsom.model.score.StringScore;
import se.melsom.model.shooter.Shooter;

public class Leaderboard {
	private static Logger logger = Logger.getLogger(Leaderboard.class);

	private Vector<DocumentElement> elements = new Vector<>();

	public Leaderboard(Scoreboard scoreboard) {
		Competition competition = Competition.singleton();
		ShooterDatabase database = ShooterDatabase.singleton();
		MatchProgram matchProgram = competition.getMatchProgram();
		TableElement table = new TableElement(12);
		Row header = new Row();
		
		header.addCell(new ContentCell("Placering", true));
		header.addCell(new ContentCell("Skytt", true));

		for (ProgramStage programStage : matchProgram.getStages()) {
			for (ProgramString programString : programStage.getStrings()) {
				header.addCell(new ContentCell(programString.getTitle(), true));
			}
			
			header.addCell(new ContentCell(programStage.getTitle(), true));
		}

		header.addCell(new ContentCell("Totalt", true));
		
		table.addRow(header);
		
		logger.debug(header);

		int placering = 0;
		for (Scorecard scorecard : competition.getScoreboard().getScorecards()) {
			Row row = new Row();
			Shooter shooter = database.getShooter(scorecard.getShooterID());
			
			row.addCell(new ContentCell("" + ++placering, false));
			row.addCell(new ContentCell(shooter.getName(), false));
			
			for (ProgramStage programStage : matchProgram.getStages()) {
				StageScore stageScore = scorecard.getStageScore(programStage.getIndex());
				
				for (ProgramString programString : programStage.getStrings()) {
					if (stageScore == null) {
						row.addCell(new ContentCell("0", false));						
					} else {
						StringScore stringScore = stageScore.getStringScore(programString.getIndex());
						
						row.addCell(new ContentCell("" + stringScore.getTotalScore(), false));
					}
				}

				if (stageScore == null) {
					row.addCell(new ContentCell("0", false));						
				} else {
					row.addCell(new ContentCell("" + stageScore.getTotalScore(), false));
				}
			}
			
			row.addCell(new ContentCell("" + scorecard.getTotalScore(), false));
			table.addRow(row);
			logger.debug(row);
		}

		elements.addElement(table);
	}
	
	public void render(DocumentRenderer renderer) {
		for (DocumentElement element : elements) {
			element.render(renderer);
		}
	}
}

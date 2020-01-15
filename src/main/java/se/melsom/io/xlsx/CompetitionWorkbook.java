package se.melsom.io.xlsx;

import java.io.File;

public class CompetitionWorkbook {
	public static String[] headings = {
			"Namn",
			"Förband",
			"Poäng",
			"Placering"
	};
	
	private String name;
	
	public CompetitionWorkbook(String name) {
		this.name = name;
	}
	
	public CompetitionWorkbook(File file) {
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String value) {
		name = value;
	}
	
//	public List<Scorecard> getScores() {
//		return null;
//	}
//	
//	public void setScores(List<Scorecard> scores) {
//	}
	
	public void save(File file) {
	}
}

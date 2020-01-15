package se.melsom.presentation.importer;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileSystemView;
import javax.swing.table.TableModel;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellValue;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ImportScoreboardViewModel {
	private static Logger logger = Logger.getLogger(ImportScoreboardViewModel.class);
	private ImportScoreboardView view = null;
	private ScoreboardTableModel scoreboard = new ScoreboardTableModel();
	private List<List<String>> content = new ArrayList<>();

	public ImportScoreboardView createView() {
		view = new ImportScoreboardView(this);

		return view;
	}

	public TableModel getTableModel() {
		return scoreboard;
	}

	public void selectScoreboardFile() {
		File directory = FileSystemView.getFileSystemView().getHomeDirectory();
		JFileChooser fileChooser = new JFileChooser(directory);
		content.clear();

		int returnValue = fileChooser.showOpenDialog(view);
		// int returnValue = jfc.showSaveDialog(null);

		if (returnValue == JFileChooser.APPROVE_OPTION) {
			File selectedFile = fileChooser.getSelectedFile();

			view.setPath(selectedFile.getPath());

			try {
				peekXlsxFile(selectedFile, content);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		logger.debug("Number of rows: " + content.size());
		view.setLastRowNumber(content.size());

		showContent();
	}

	void peekXlsxFile(File file, List<List<String>> content) throws IOException {
		InputStream inp = new FileInputStream(file);

		Workbook wb = new XSSFWorkbook(inp);
		FormulaEvaluator evaluator = wb.getCreationHelper().createFormulaEvaluator();

		if (wb.getNumberOfSheets() > 0) {
			Sheet sheet = wb.getSheetAt(0);

			for (int rowNumber = sheet.getFirstRowNum(); rowNumber < sheet.getLastRowNum(); rowNumber++) {
				Row row = sheet.getRow(rowNumber);

				if (row == null) {
					logger.debug("Row #" + rowNumber + " is null");
					continue;
				}
				List<String> rowContent = new ArrayList<>();

				for (int cellNumber = row.getFirstCellNum(); cellNumber < row.getLastCellNum(); cellNumber++) {
					Cell cell = row.getCell(cellNumber);
					String cellContent = "";

					if (cell != null) {
						CellValue value = evaluator.evaluate(cell);

						if (value != null) {
							cellContent = value.formatAsString();
						}
					}

					logger.trace(rowContent);
					rowContent.add(cellContent);
				}

				content.add(rowContent);
			}
		}

		wb.close();
	}

	public void update() {
		showContent();
	}

	void showContent() {
		logger.debug("Show content");
		List<List<String>> scoreboardContent = new ArrayList<>();

		for (int rowNumber = 0; rowNumber < content.size(); rowNumber++) {
			if (rowNumber >= view.getFirstRowNumber() && rowNumber <= view.getLastRowNumber()) {
				List<String> row = content.get(rowNumber);
				List<String> score = new ArrayList<>();

				score.add("");
				score.add("");
				score.add("");

				for (int cellNumber = 0; cellNumber < row.size(); cellNumber++) {
					if (cellNumber == view.getNameColumnNumber()) {
						score.set(0, row.get(cellNumber));
					} else if (cellNumber == view.getUnitColumnNumber()) {
						score.set(1, row.get(cellNumber));
					} else if (cellNumber == view.getScoreColumnNumber()) {
						score.set(2, row.get(cellNumber));
					}
				}

				scoreboardContent.add(score);
			}
		}

		scoreboard.setContent(scoreboardContent);
	}
}

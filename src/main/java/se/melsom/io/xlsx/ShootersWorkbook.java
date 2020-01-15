package se.melsom.io.xlsx;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import se.melsom.model.shooter.Shooter;

public class ShootersWorkbook {
	private Logger logger = Logger.getLogger(this.getClass());
	public static String[] headings = { "ID", "Efternamn", "Förnamn", "Vänsterskytt", "Förband" };

	Workbook wb = null;

	public ShootersWorkbook() {
	}

	public List<Shooter> getShooters() {
		List<Shooter> shooters = new ArrayList<>();

		if (wb == null) {
			logger.warn("Woorkbook is null");
			return null;
		}

		Sheet sheet = wb.getSheet("Shooters");

		if (sheet == null) {
			logger.warn("No sheet named Shooters");
			return null;
		}

		for (int rowNumber = sheet.getFirstRowNum(); rowNumber < sheet.getLastRowNum(); rowNumber++) {
			if (rowNumber == sheet.getFirstRowNum()) {
				continue;
			}

			Row row = sheet.getRow(rowNumber);

			if (row == null) {
				logger.debug("Row #" + rowNumber + " is null");
				continue;
			}

			String id = "";
			String forename = "";
			String surname = "";
			String unit = "";
			String isLeftHandedShooter = "";

			for (int cellNumber = row.getFirstCellNum(); cellNumber < row.getLastCellNum(); cellNumber++) {
				Cell cell = row.getCell(cellNumber);

				switch (cellNumber) {
				case 0:
					id = getCellContent(cell);
					break;

				case 1:
					surname = getCellContent(cell);
					break;

				case 2:
					forename = getCellContent(cell);
					break;

				case 4:
					unit = getCellContent(cell);
					break;

				case 5:
					isLeftHandedShooter = getCellContent(cell);
					break;

				default:
					logger.warn("Unhandled column: #" + cellNumber + " : " + getCellContent(cell));
					break;
				}
			}

			// Shooter shooter = new Shooter(id, forename, surname, unit,
			// isLeftHandedShooter);
			//
			// shooters.add(shooter);
		}

		return shooters;
	}

	public void addShooter(Shooter shooter) {
	}

	public void setShooter(Shooter shooter) {
	}

	public void removeShooter(Shooter shooter) {
	}

	public void load(File file) throws IOException {
		logger.debug("Load shooters from: '" + file.getPath() + "'");
		InputStream inp = new FileInputStream(file);
		wb = new XSSFWorkbook(inp);

		wb.close();
	}

	public void save(File file) {
	}

	String getCellContent(Cell cell) {
		String content = "";

		if (cell != null) {
			CellType cellType = cell.getCellTypeEnum();

			switch (cellType) {
			case STRING:
				content = cell.getStringCellValue();
				break;

			case BOOLEAN:
				content = "" + cell.getBooleanCellValue();
				break;

			case NUMERIC:
				content = "" + cell.getNumericCellValue();
				break;

			default:
				logger.warn("Unkown cell type: " + cellType);
				break;
			}
		}

		return content;
	}
}

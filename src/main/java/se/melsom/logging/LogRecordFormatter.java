package se.melsom.logging;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;

//This custom formatter formats parts of a log record to a single line
class LogRecordFormatter extends Formatter
{
	// This method is called for every log records
	public String format(LogRecord rec)
	{
		StringBuffer buf = new StringBuffer(1000);
		buf.append(calcDate(rec.getMillis()));
		buf.append(' ');
		buf.append(rec.getLevel());
		buf.append(": ");
		buf.append(formatMessage(rec));
		buf.append('\n');
		return buf.toString();
	}

	private String calcDate(long millisecs)
	{
		SimpleDateFormat date_format = new SimpleDateFormat("yyyy-MM-dd:HH:mm");
		Date resultdate = new Date(millisecs);
		return date_format.format(resultdate);
	}
}

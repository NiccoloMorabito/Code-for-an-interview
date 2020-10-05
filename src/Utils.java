import java.time.Month;
import java.time.format.TextStyle;
import java.util.Locale;

public class Utils {
	
	public static String minutesToTime(Integer minutes) {
		/**
		 * Returns a conversion of minutes into a string in the form "H ore MM minuti"
		 */
		if (minutes == null)
			return "0 ore";
		
		int h = minutes / 60;
		int m = minutes % 60;
		
		String result = h + " ore";
		if ( m > 0 )
			return result + " " + m + " minuti";
		
		return result;
	}
	
	public static String getMonthAndYear(int month, int year) {
		/**
		 * Returns a string in the form "month YYYY" according to passed parameters.
		 */
		return Month.of(month).getDisplayName(TextStyle.FULL_STANDALONE, Locale.ITALIAN) + " " + year;
	}
	
	public static String getDateString(int day, int month, int year) {
		/**
		 * Returns a string in the form "DD month YYYY" according to passed parameters.
		 */
		return day + " " + getMonthAndYear(month, year);
	}
}

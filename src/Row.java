import java.util.Calendar;
import java.util.Date;

public class Row {

	private Date date;
	private int minutes;
	private String name;
	private String surname;
	private String activityType;
	private String job;
	
	public Row(Date date, int minutes, String name, String surname, String activityType, String job) {
		super();
		this.date = date;
		this.minutes = minutes;
		this.name = name;
		this.surname = surname;
		this.activityType = activityType;
		this.job = job;
	}
	
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public int getMinutes() {
		return minutes;
	}
	public void setMinutes(int minutes) {
		this.minutes = minutes;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSurname() {
		return surname;
	}
	public void setSurname(String surname) {
		this.surname = surname;
	}
	public String getActivityType() {
		return activityType;
	}
	public void setActivityType(String activityType) {
		this.activityType = activityType;
	}
	public String getJob() {
		return job;
	}
	public void setJob(String job) {
		this.job = job;
	}
	
	public int getDayNumber() {
		/**
		 * Returns the day of month of the row date as integer.
		 */
        Calendar calendar = Calendar.getInstance();
		calendar.setTime(this.date);
        return calendar.get(Calendar.DAY_OF_MONTH);
	}
	
	public int getMonthNumber() {
		/**
		 * Returns the month of the row date as integer.
		 */
        Calendar calendar = Calendar.getInstance();
		calendar.setTime(this.date);
        return calendar.get(Calendar.MONTH) + 1;
	}
	
	public int getYearNumber() {
		/**
		 * Returns the year of the row date as integer.
		 */
        Calendar calendar = Calendar.getInstance();
		calendar.setTime(this.date);
        return calendar.get(Calendar.YEAR);
	}
	
	public String getMonthAndYear() {
		/**
		 * Returns a string in the form "month YYYY" corresponding to the row date.
		 */
		return Utils.getMonthAndYear(this.getMonthNumber(), this.getYearNumber());
	}
	
	public String getDateString() {
		/**
		 * Returns a string in the form "DD month YYYY" corresponding to the row date.
		 */
		return Utils.getDateString(this.getDayNumber(), this.getMonthNumber(), this.getYearNumber());
	}
	
	public String getNameAndSurname() {
		return this.getName() + "\t" + this.getSurname();
	}
	
	public String getTime() {
		/**
		 * Returns a conversion of row minutes into a string in the form "H ore MM minuti"
		 */
		return Utils.minutesToTime(this.minutes);
	}
	

}

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Exercise {
	
	public static final String PATH = "worklog.csv";
	public static final String SEPARATOR = ";";
    public static final SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MM-dd");
	
	public static final int DATE_IND = 0;
	public static final int MIN_IND = 1;
	public static final int NAME_IND = 2;
	public static final int SURN_IND = 3;
	public static final int ACT_IND = 4;
	public static final int JOB_IND = 5;
	
	public List<Row> rows;
	
	public Exercise() {
		this.rows = new ArrayList<>();
        BufferedReader br = null;
        String line;

        try {
        	// opening file
            br = new BufferedReader(new FileReader(PATH));
            
            // for each line 
            while ((line = br.readLine()) != null) {
                String[] splitted = line.split(SEPARATOR);
                
                // add new Row to rows list
                Row row;
				try {
					row = new Row(
						SDF.parse(splitted[DATE_IND]),
						Integer.parseInt(splitted[MIN_IND]),
						splitted[NAME_IND],
						splitted[SURN_IND],
						splitted[ACT_IND],
						splitted[JOB_IND]
					);
					this.rows.add(row);
				} catch (NumberFormatException | ParseException e) {
					System.out.println("Problema nel leggere la seguente riga:");
					System.out.println(line);
					e.printStackTrace();
				}
            }
        } catch (IOException e) {
        	System.out.println("File non presente o illegibile.");
            e.printStackTrace();
		} finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
	}
	
	public void printTotalHoursOfMonth(int month, int year) {
		/**
		 * Prints the total working hours in month per user for the year passed.
		 */
		String requestedMonthAndYear = Utils.getMonthAndYear(month, year);
		
		String format = "%-15s%-10s%-10s%-20s\n";
		// header
		System.out.format(format, "Mese", "Nome", "Cognome", "Ore\n");
		// result lines
		rows.stream()
			.collect(Collectors.groupingBy(
											Row::getNameAndSurname, 
											Collectors.groupingBy(Row::getMonthAndYear,  Collectors.summingInt(Row::getMinutes))
										))
			.forEach( (a, b) -> {
			System.out.format(format, requestedMonthAndYear, a.split("\t")[0], a.split("\t")[1],
					Utils.minutesToTime(b.get(requestedMonthAndYear)));
			});	

	}
	
	public void printTotalHoursOfJob(String job, int year) {	
		/**
		 * Prints the total working hours for a certain job per month of the year passed.
		 */
		Map<String, Integer> monthToMinutes = rows
				.stream()
				.collect(Collectors.groupingBy(Row::getJob,
													Collectors.groupingBy(Row::getMonthAndYear,
																			Collectors.summingInt(Row::getMinutes)
																			)
												)
						)
				.get(job);
		
		// printing result for all the months of selected year
		String format = "%-15s%-20s%-20s\n";
		System.out.format(format, "Commessa", "Mese", "Ore\n");
		for (int m=1; m<=12; m++)
		{
			String monthAndYear = Utils.getMonthAndYear(m, year);
			System.out.format(format, job, monthAndYear, Utils.minutesToTime(monthToMinutes.get(monthAndYear)));
		}
		
	}
	
	public void printTotalHoursOfDay(Date date, String name, String surname) {
		/**
		 * Prints the total working hours for a certain day and for the user with name and surname passed.
		 */
		List<Row> filtered = rows.stream()
							.filter( r -> r.getDate().equals(date) && r.getName().equals(name) && r.getSurname().equals(surname))
							.collect(Collectors.toList());
		
		String format = "%-20s%-10s%-10s%-20s%-15s%-20s\n";
		System.out.format(format, "Data", "Nome", "Cognome", "Tipo", "Commessa", "Ore\n");
		for (Row r : filtered)		
			System.out.format(format, r.getDateString(), r.getName(), r.getSurname(), r.getActivityType(), r.getJob(), r.getTime());
	}
	
	public static void main(String[] args) {
		Exercise e = new Exercise();
		
		// 1
		System.out.println("1. il totale delle ore nel mese di giugno, raggruppate per utente");
		System.out.println("-----------------------------------------------------------------\n");
		e.printTotalHoursOfMonth(6, 2012);
		System.out.println("\n");
		
		// 2
		System.out.println("2. il totale delle ore della commessa 5435, raggruppate per mese");
		System.out.println("----------------------------------------------------------------\n");
		e.printTotalHoursOfJob("5435", 2012);
		System.out.println("\n");
		
		//3
		System.out.println("3. l'elenco delle ore di Davide Bianchi nel giorno 7 agosto 2012");
		System.out.println("-----------------------------------------------------------------\n");
		try {
			Date day = SDF.parse("2012-08-07");
			e.printTotalHoursOfDay(day, "Davide", "Bianchi");
		} catch (ParseException pe) {
			pe.printStackTrace();
		}
	}

}

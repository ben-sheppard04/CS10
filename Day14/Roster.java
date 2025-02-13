import java.io.*;
import java.util.*;

/**
 * Demo of file input (happens to be with PQ)
 * 
 * @author Tim Pierson, Dartmouth CS10, Winter 2024, based on prior term code
 */
public class Roster {

	/**
	 * Loads a roster from a comma-separated values format file
	 * Each line should be <student name>,<student year>; e.g. "Alice,20"
	 */
	public static List<SimpleStudent> readRoster(String fileName) throws IOException {
		List<SimpleStudent> roster = new ArrayList<SimpleStudent>();
		BufferedReader input;

		// Open the file, if possible
		// NOTE: input was declared outside try block, otherwise it would go out of scope
		try {
			input = new BufferedReader(new FileReader(fileName));
		} catch (FileNotFoundException e) {
			System.err.println("Cannot open file.\n" + e.getMessage());
			return roster;
		}

		// Read the file
		try {
			// Line by line
			String line;
			int lineNum = 0;
			while ((line = input.readLine()) != null) {
				System.out.println("read @"+lineNum+"`"+line+"'");
				// Comma separated
				String[] pieces = line.split(",");
				if (pieces.length != 2) {
					//did not get two elements in this line, output an error message
					System.err.println("bad separation in line "+lineNum+":"+line);
				}
				else {
					// got two elements for this line
					try {
						// Extract year as an integer, if possible
						SimpleStudent s = new SimpleStudent(pieces[0], Integer.parseInt(pieces[1]));
						System.out.println("=>"+s);
						roster.add(s); //good student, add to roster
					}
					catch (NumberFormatException e) {
						// couldn't parse second element as integer
						System.err.println("bad number in line "+lineNum+":"+line);
					}
				}
				lineNum++;
			}
		}
		catch (IOException e) {
			System.err.println("IO error while reading.\n" + e.getMessage());
		}

			// Close the file, if possible
			try {
				input.close();
			} catch (IOException e) {
				System.err.println("Cannot close file.\n" + e.getMessage());
			}

			return roster;
	}
	/**
	 * Variation using finally
	 */
	public static List<SimpleStudent> readRoster2(String fileName) throws IOException {
		List<SimpleStudent> roster = new ArrayList<SimpleStudent>();
		BufferedReader input;
		
		// Open the file, if possible
        try {
    			input = new BufferedReader(new FileReader(fileName));
        } 
        catch (FileNotFoundException e) {
            System.err.println("Cannot open file.\n" + e.getMessage());
            return roster;
        }
        
        // Read the file
		try {
			// Line by line
			String line;
			int lineNum = 0;
			while ((line = input.readLine()) != null) {
				System.out.println("read @"+lineNum+"`"+line+"'");
				// Comma separated
				String[] pieces = line.split(",");
				if (pieces.length != 2) {
					//did not get two elements in this line, output an error message
					System.err.println("bad separation in line "+lineNum+":"+line);
				}
				else {
					// got two elements for this line
					try {
						// Extract year as an integer, if possible
						SimpleStudent s = new SimpleStudent(pieces[0], Integer.parseInt(pieces[1]));
						System.out.println("=>"+s);
						roster.add(s); //good student, add to roster
					}
					catch (NumberFormatException e) {
						// couldn't parse second element as integer
						System.err.println("bad number in line "+lineNum+":"+line);
					}
				}
				lineNum++;
			}
		}
		finally {
			// Close the file, if possible
			try {
				input.close();
			}
			catch (IOException e) {
				System.err.println("Cannot close file.\n" + e.getMessage());
			}			
		}

		return roster;
	}

	public static void main(String[] args) throws Exception {
		List<SimpleStudent> roster = readRoster("inputs/roster.csv");
		PriorityQueue<SimpleStudent> pq = new PriorityQueue<SimpleStudent>(); //which comparator is used?
		pq.addAll(roster);
		System.out.println("\nsorted roster:");
		while (!pq.isEmpty()) System.out.println(pq.remove());

		// do it again with the other reader, 
		// uses a finally block to ensure file is close
		System.out.println("\n\n***repeat....");
		roster = readRoster2("inputs/roster.csv");
		pq = new PriorityQueue<SimpleStudent>();
		pq.addAll(roster);
		System.out.println("\nsorted roster:");
		while (!pq.isEmpty()) System.out.println(pq.remove());
	}
}

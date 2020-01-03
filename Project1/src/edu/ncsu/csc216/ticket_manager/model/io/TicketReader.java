package edu.ncsu.csc216.ticket_manager.model.io;

import java.io.*;
import java.util.*;

import edu.ncsu.csc216.ticket_manager.model.ticket.Ticket;

/**
 * Reads tickets from a .txt file. Creates an ArrayList of Tickets read from the
 * file.
 * 
 * @author Benson Liu
 *
 */
public class TicketReader {

	/**
	 * Reads tickets from a .txt file. If there are any errors in processing the
	 * file an IllegalArgumentException is thrown. Returns the tickets read in an
	 * ArrayList
	 * 
	 * @param fileName Name of the file being read
	 * @return ArrayList of Tickets read from the file
	 * @throws FileNotFoundException
	 */
	public static ArrayList<Ticket> readTicketFile(String fileName) {
		Scanner fileReader = null;
		try {
			fileReader = new Scanner(new FileInputStream(fileName));
		} catch (FileNotFoundException e) {
			throw new IllegalArgumentException("Unable to load file.");
		}
		ArrayList<Ticket> tickets = new ArrayList<Ticket>();
		while (fileReader.hasNextLine()) {
			try {
				Ticket ticket = readTicket(fileReader);
				tickets.add(ticket);
			} catch (IllegalArgumentException e) {
				// skip line
			}
		}
		fileReader.close();
		return tickets;
	}

	/**
	 * Private method for reading a line. Sorts tokens and creates a Ticket object
	 * with them, which is returned
	 * 
	 * @param fileReader Scanner for reading lines
	 * @return Ticket object created with scanned tokens
	 */
	private static Ticket readTicket(Scanner fileReader) {
		Scanner lineScan = new Scanner(fileReader.nextLine());
		lineScan.useDelimiter("#");
		try {
			int id = Integer.parseInt(String.valueOf(lineScan.next().charAt(1)));
			ArrayList<String> notes = new ArrayList<String>();
			String state = lineScan.next();
			String ticketType = lineScan.next();
			String subject = lineScan.next();
			String caller = lineScan.next();
			String category = lineScan.next();
			String priority = lineScan.next();
			String owner = null;
			if (lineScan.hasNext()) {
				owner = lineScan.next();
			}
			String code = null;
			if (lineScan.hasNext()) {
				code = lineScan.next();
			}
			Ticket ticket = new Ticket(id, state, ticketType, subject, caller, category, priority, owner, code, notes);
			lineScan.close();
			lineScan.close();
			return ticket;
		} catch (NoSuchElementException e) {
			throw new IllegalArgumentException();
		}
	}
}

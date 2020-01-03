package edu.ncsu.csc216.ticket_manager.model.io;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.List;

import edu.ncsu.csc216.ticket_manager.model.ticket.Ticket;

/**
 * Writes tickets onto a .txt file.
 * 
 * @author Benson Liu
 *
 */
public class TicketWriter {

	/**
	 * Writes tickets onto a .txt file using the Ticket.toString() method. If there
	 * are any errors during the processing, an IllegalArgumentException is thrown.
	 * 
	 * @param fileName Name of file tickets are being printed on
	 * @param tickets  The tickets being printed onto the file
	 * @throws IOException thrown if file cannot be written onto
	 */
	public static void writeTicketFile(String fileName, List<Ticket> tickets) {
		PrintStream fileWriter = null;
		try {
			fileWriter = new PrintStream(new File(fileName));
		} catch (IOException e) {
			throw new IllegalArgumentException("Unable to save file.");
		}
		for (int i = 0; i < tickets.size(); i++) {
			if (i == tickets.size() - 1) {
				fileWriter.print(tickets.get(i).toString());
			} else {
				fileWriter.print(tickets.get(i).toString());
			}

		}

		fileWriter.close();
	}
}

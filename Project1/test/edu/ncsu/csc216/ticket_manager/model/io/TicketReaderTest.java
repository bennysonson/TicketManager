package edu.ncsu.csc216.ticket_manager.model.io;

import static org.junit.Assert.assertEquals;

import java.io.FileNotFoundException;
import java.util.ArrayList;

import org.junit.Test;

import edu.ncsu.csc216.ticket_manager.model.ticket.Ticket;

/**
 * Class for testing TicketReader
 * 
 * @author Benson Liu
 *
 */
public class TicketReaderTest {

	/**
	 * Test for method readTicketFile()
	 */
	@Test
	public void testReadTicketFile() throws FileNotFoundException {
		ArrayList<Ticket> tickets = TicketReader.readTicketFile("test-files/ticket1.txt");
		assertEquals(tickets.get(0).getState(), "New");
		assertEquals(6, tickets.size());
		try {
			TicketReader.readTicketFile("8u24-982h=028umc =8uu");
		} catch (IllegalArgumentException e) {
			assertEquals(e.getMessage(), "Unable to load file.");
		}
	}
	

}

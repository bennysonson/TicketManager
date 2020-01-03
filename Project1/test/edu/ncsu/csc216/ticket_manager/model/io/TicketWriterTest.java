package edu.ncsu.csc216.ticket_manager.model.io;


import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.Test;

import edu.ncsu.csc216.ticket_manager.model.ticket.Ticket;

/**
 * Class for testing TicketWriter
 * @author Benson Liu
 *
 */
public class TicketWriterTest {

	/**
	 * Test for method writeTicketFile()
	 */
	@Test
	public void testWriteTicketFile() {
		ArrayList<Ticket> tickets = new ArrayList<Ticket>();
		ArrayList<String> notes = new ArrayList<String>();
		notes.add("note");
		Ticket ticket1 = new Ticket(1, "New", "Request", "Internet down", "Benson", "Network", "Urgent", "Ben", null, notes);
		Ticket ticket2 = new Ticket(2, "Working", "Incident", "Big problem", "Bender", "Database", "Low", "Bentonio", null, notes);
		tickets.add(ticket1);
		tickets.add(ticket2);
		TicketWriter.writeTicketFile("test-files/TicketWriterTest.txt", tickets);
		assertEquals(2, tickets.size());
		try {
			TicketWriter.writeTicketFile("skdlfjklsd.....394up9r8@@!#!*@()#", tickets);
		} catch (IllegalArgumentException e) {
			assertEquals("Unable to save file.", e.getMessage());
		}
		
	}

}

package edu.ncsu.csc216.ticket_manager.model.manager;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.Test;

import edu.ncsu.csc216.ticket_manager.model.command.Command;
import edu.ncsu.csc216.ticket_manager.model.ticket.Ticket;

/**
 * Class for testing TicketManager
 * 
 * @author Benson Liu
 *
 */
public class TicketManagerTest {
	ArrayList<String> notes = new ArrayList<String>();
	Ticket ticket1 = new Ticket(1, "New", "Request", "Internet down", "Benson", "Network", "Urgent", "Ben", null,
			notes);
	Ticket ticket2 = new Ticket(2, "Working", "Incident", "Big problem", "Bender", "Database", "Low", "Bentonio", null,
			notes);
	ArrayList<Ticket> tickets = new ArrayList<Ticket>();

	TicketManager tm = TicketManager.getInstance();

	/**
	 * Test for method addTicketToList()
	 */
	@Test
	public void testAddTicketToList() {
		tm.addTicketToList(Ticket.TicketType.INCIDENT, "subject", "caller", Ticket.Category.DATABASE,
				Ticket.Priority.HIGH, "notes");
		assertEquals(tm.getTickets().size(), 6);
	}

	/**
	 * Test for method saveTicketsToFile()
	 */
	@Test
	public void testSaveTicketsToFile() {
		tickets.add(ticket1);
		tickets.add(ticket2);
		tm.saveTicketsToFile("test-files/TicketManagerTest.txt");
		assertEquals(tickets.size(), 2);

	}

	/**
	 * Test for method loadTicketsFromFile()
	 */
	@Test
	public void testLoadTicketsFromFile() {
		tm.loadTicketsFromFile("test-files/ticket1.txt");
		assertEquals(tm.getTickets().size(), 6);
	}

	/**
	 * Test for method createNewTicketList()
	 */
	@Test
	public void testCreateNewTicketList() {
		tm.createNewTicketList();
		assertEquals(tickets.size(), 0);
	}

	/**
	 * Test for method getTicketsForDisplay()
	 */
	@Test
	public void testGetTicketsForDisplay() {
		tm.loadTicketsFromFile("test-files/ticket1.txt");
		tm.getTicketsForDisplay();
		assertEquals(tm.getTickets().size(), 6);
	}

	/**
	 * Test for method getTicketsForDisplayByType()
	 */
	@Test
	public void testGetTicketsForDisplayByType() {
		tm.loadTicketsFromFile("test-files/ticket1.txt");
		try {
			tm.getTicketsForDisplayByType(null);
		} catch (IllegalArgumentException e) {
			assertEquals(e.getMessage(), "Ticket type cannot be null.");
		}
		
		tm.getTicketsForDisplayByType(Ticket.TicketType.INCIDENT);
	}

	/**
	 * Test for method getTicketById()
	 */
	@Test
	public void testGetTicketById() {
		tm.loadTicketsFromFile("test-files/ticket1.txt");
		assertEquals("New", tm.getTicketById(1).getState());

	}

	/**
	 * Test for method executeCommand()
	 */
	@Test
	public void testExecuteCommand() {
		Command command = new Command(Command.CommandValue.PROCESS, "Benson", null, null, null, "note");
		tm.loadTicketsFromFile("test-files/ticket1.txt");
		tm.executeCommand(1, command);
		assertEquals(tm.getTicketById(1).getState(), "Working");
	}

	/**
	 * Test for method deleteTicketById()
	 */
	@Test
	public void testDeleteTicketById() {
		tm.loadTicketsFromFile("test-files/ticket1.txt");
		tm.deleteTicketById(3);
		assertEquals(tm.getTickets().size(), 5);
	}
}

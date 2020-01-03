package edu.ncsu.csc216.ticket_manager.model.manager;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.Test;

import edu.ncsu.csc216.ticket_manager.model.command.Command;
import edu.ncsu.csc216.ticket_manager.model.io.TicketReader;
import edu.ncsu.csc216.ticket_manager.model.ticket.Ticket;

/**
 * Class that tests TicketList
 * 
 * @author Benson Liu
 *
 */
public class TicketListTest {

	ArrayList<String> notes = new ArrayList<String>();
	Ticket ticket1 = new Ticket(1, "New", "Request", "Internet down", "Benson", "Network", "Urgent", "Ben", null,
			notes);
	Ticket ticket2 = new Ticket(2, "Working", "Incident", "Big problem", "Bender", "Database", "Low", "Bentonio", null,
			notes);
	ArrayList<Ticket> tickets = new ArrayList<Ticket>();

	/**
	 * Test for method addTicket()
	 */
	@Test
	public void testAddTicket() {
		TicketList ticketList = new TicketList();
		ticketList.addTicket(Ticket.TicketType.INCIDENT, "Internet down", "Benson", Ticket.Category.NETWORK,
				Ticket.Priority.URGENT, "note");
		assertEquals(ticketList.getTickets().size(), 1);
	}

	/**
	 * Test for method addTickets()
	 */
	@Test
	public void testAddTickets() {
		tickets.add(ticket1);
		tickets.add(ticket2);
		TicketList ticketList = new TicketList();
		ticketList.addTickets(tickets);
		assertEquals(ticketList.getTickets().size(), 2);
	}

	/**
	 * Test for method getTicketsByType()
	 */
	@Test
	public void testGetTicketsByType() {
		tickets.add(ticket1);
		tickets.add(ticket2);
		TicketList ticketList = new TicketList();
		ticketList.addTickets(tickets);
		assertEquals(1, ticketList.getTicketsByType(Ticket.TicketType.INCIDENT).size());
		assertEquals("Bentonio", ticketList.getTicketsByType(Ticket.TicketType.INCIDENT).get(0).getOwner());

		try {
			ticketList.getTicketsByType(null);
		} catch (IllegalArgumentException e) {
			assertEquals("Ticket type cannot be null.", e.getMessage());
		}
	}

	/**
	 * Test for method getTicketById()
	 */
	@Test
	public void testGetTicketById() {
		tickets.add(ticket1);
		tickets.add(ticket2);
		TicketList ticketList = new TicketList();
		ticketList.addTickets(tickets);
		assertEquals("Bender", ticketList.getTicketById(2).getCaller());
	}
	
	/**
	 * Test for method executeCommand()
	 */
	@Test
	public void testExecuteCommand() {
		tickets = TicketReader.readTicketFile("test-files/ticket1.txt");
		TicketList ticketList = new TicketList();
		ticketList.addTickets(tickets);
		Command c = new Command(Command.CommandValue.PROCESS, "Benson", null, null, null, "note");
		ticketList.executeCommand(1, c);
		assertEquals("Working", ticketList.getTicketById(1).getState());
	}

	/**
	 * Test for method deleteTicketById()
	 */
	@Test
	public void testDeleteTicketById() {
		tickets = TicketReader.readTicketFile("test-files/ticket1.txt");
		TicketList ticketList = new TicketList();
		ticketList.addTickets(tickets);
		ticketList.deleteTicketById(3);
		assertEquals(null, ticketList.getTicketById(3));
		assertEquals(6, ticketList.getTickets().size());

	}
}

package edu.ncsu.csc216.ticket_manager.model.manager;

import java.util.ArrayList;

import edu.ncsu.csc216.ticket_manager.model.command.Command;
import edu.ncsu.csc216.ticket_manager.model.ticket.Ticket;
import edu.ncsu.csc216.ticket_manager.model.ticket.Ticket.Category;
import edu.ncsu.csc216.ticket_manager.model.ticket.Ticket.Priority;
import edu.ncsu.csc216.ticket_manager.model.ticket.Ticket.TicketType;

/**
 * Maintains a group of Tickets in a List. Tickets can be added individually or
 * in a group. Tickets can deleted or returned by ID. Tickets can be returned by
 * type.
 * 
 * @author Benson Liu
 *
 */
public class TicketList {

	ArrayList<Ticket> tickets = new ArrayList<Ticket>();

	/**
	 * Constructor for TicketList. Sets counter of Ticket to 0 when creating a new
	 * TicketList
	 */
	public TicketList() {
		Ticket.setCounter(1);
	}

	/**
	 * Adds a Ticket to an ArrayList of Tickets
	 * 
	 * @param ticketType Type of ticket
	 * @param subject    Subject of ticket
	 * @param caller     Caller of ticket
	 * @param category   Category of ticket
	 * @param priority   Priority of ticket
	 * @param note       Notes for ticket
	 * @return ID of ticket being added
	 */
	public int addTicket(TicketType ticketType, String subject, String caller, Category category, Priority priority,
			String note) {
		Ticket ticket = new Ticket(ticketType, subject, caller, category, priority, note);
		tickets.add(ticket);
		return ticket.getTicketId();
	}

	/**
	 * Adds a list of Tickets to the ArrayList
	 * 
	 * @param ticks List of Tickets
	 */
	public void addTickets(ArrayList<Ticket> ticks) {
		for (int i = 0; i < ticks.size(); i++) {
			tickets.add(ticks.get(i));
		}
	}

	/**
	 * Returns an ArrayList of Tickets
	 * 
	 * @return List of Tickets
	 */
	public ArrayList<Ticket> getTickets() {
		return tickets;
	}

	/**
	 * Returns list of Tickets by type of ticket. Throws an IllegalArgumentException
	 * if Ticket type is null
	 * 
	 * @param type Type of ticket
	 * @return List of Tickets by type
	 */
	public ArrayList<Ticket> getTicketsByType(TicketType type) {
		if (type == null) {
			throw new IllegalArgumentException("Ticket type cannot be null.");
		}
		ArrayList<Ticket> ticks = new ArrayList<Ticket>();
		for (int i = 0; i < tickets.size(); i++) {
			if (tickets.get(i).getTicketType() == type) {
				ticks.add(tickets.get(i));
			}
		}
		return ticks;
	}

	/**
	 * Returns Ticket based on ID
	 * 
	 * @param id ID of ticket
	 * @return Ticket with ID id
	 */
	public Ticket getTicketById(int id) {
		Ticket tick = tickets.get(id - 1);
		return tick;
	}

	/**
	 * Updates ticket with a command
	 * 
	 * @param id      ID of ticket to update
	 * @param command Type of command to update ticket
	 */
	public void executeCommand(int id, Command command) {
		for (int i = 0; i < tickets.size(); i++) {
			if (tickets.get(i).getTicketId() == id) {
				tickets.get(i).update(command);
			}
		}
	}

	/**
	 * Deletes ticket of ID id
	 * 
	 * @param id ID of ticket to delete
	 */
	public void deleteTicketById(int id) {
		for (int i = 0; i < tickets.size(); i++) {
			if (tickets.get(i).getTicketId() == id) {
				tickets.set(i, null);
			}
		}
	}

}

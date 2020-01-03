package edu.ncsu.csc216.ticket_manager.model.manager;

import java.util.ArrayList;

import edu.ncsu.csc216.ticket_manager.model.command.Command;
import edu.ncsu.csc216.ticket_manager.model.io.TicketReader;
import edu.ncsu.csc216.ticket_manager.model.io.TicketWriter;
import edu.ncsu.csc216.ticket_manager.model.ticket.Ticket;
import edu.ncsu.csc216.ticket_manager.model.ticket.Ticket.Category;
import edu.ncsu.csc216.ticket_manager.model.ticket.Ticket.Priority;
import edu.ncsu.csc216.ticket_manager.model.ticket.Ticket.TicketType;

/**
 * Class for adding and removing tickets from TicketManager. Allows users to
 * save and load Ticket lists, as well as execute commands on them to change
 * states.
 * 
 * @author Benson Liu
 *
 */
public class TicketManager {

	/** An ArrayList of Tickets */
	ArrayList<Ticket> tickets = new ArrayList<Ticket>();
	/** Instance of Ticket Manager */
	public static TicketManager ticketList = new TicketManager();

	/**
	 * Constructor for TicketManager
	 */
	private TicketManager() {

	}

	/**
	 * Gets an instance of TicketManager
	 * 
	 * @return An instance of TicketManager
	 */
	public static TicketManager getInstance() {
		return ticketList;
	}

	/**
	 * Singleton design pattern for TicketManager
	 * 
	 * @author Benson Liu
	 *
	 */
	public class Singleton {

		TicketManager obj1 = TicketManager.getInstance();
	}

	/**
	 * Saves the current ticket list to a file
	 * 
	 * @param fileName Name of saved file
	 */
	public void saveTicketsToFile(String fileName) {
		TicketWriter.writeTicketFile(fileName, tickets);
	}

	/**
	 * Loads ticket list from a file
	 * 
	 * @param fileName Name of file to load from
	 */
	public void loadTicketsFromFile(String fileName) {
		tickets = TicketReader.readTicketFile(fileName);

	}

	/**
	 * Creates a new blank ticket list
	 */
	public void createNewTicketList() {
		tickets = new ArrayList<Ticket>();
	}

	/**
	 * Returns a 2D array of ticket list with ID, type, state, subject, category,
	 * and priority.
	 * 
	 * @return 2D array of ticket list
	 */
	public String[][] getTicketsForDisplay() {
		String[][] ticks = new String[tickets.size()][6];
		for (int i = 0; i < tickets.size(); i++) {
			ticks[i][0] = Integer.toString(tickets.get(i).getTicketId());
			ticks[i][1] = tickets.get(i).getTicketTypeString();
			ticks[i][2] = tickets.get(i).getState();
			ticks[i][3] = tickets.get(i).getSubject();
			ticks[i][4] = tickets.get(i).getCategory();
			ticks[i][5] = tickets.get(i).getPriority();
		}
		return ticks;
	}

	/**
	 * Returns a 2D array of ticket list by type. Throws an IllegalArgumentException
	 * if the Ticket type is null.
	 * 
	 * @param type Type of ticket
	 * @return 2D array of ticket list by type
	 */
	public String[][] getTicketsForDisplayByType(TicketType type) {
		if (type == null) {
			throw new IllegalArgumentException("Ticket type cannot be null.");
		}
		ArrayList<Ticket> ticketsByType = new ArrayList<Ticket>();
		for (int i = 0; i < tickets.size(); i++) {
			if (tickets.get(i).getTicketType() == type) {
				ticketsByType.add(tickets.get(i));
			}
		}
		String[][] ticks = new String[tickets.size()][6];
		for (int i = 0; i < ticketsByType.size(); i++) {
			ticks[i][0] = Integer.toString(ticketsByType.get(i).getTicketId());
			ticks[i][1] = ticketsByType.get(i).getTicketTypeString();
			ticks[i][2] = ticketsByType.get(i).getState();
			ticks[i][3] = ticketsByType.get(i).getSubject();
			ticks[i][4] = ticketsByType.get(i).getCategory();
			ticks[i][5] = ticketsByType.get(i).getPriority();
		}
		return ticks;
	}

	/**
	 * Returns Ticket object by ID in a ticket list
	 * 
	 * @param id ID of Ticket
	 * @return Ticket object with specific ID
	 */
	public Ticket getTicketById(int id) {
		for (int i = 0; i < tickets.size(); i++) {
			if (tickets.get(i).getTicketId() == id) {
				return tickets.get(i);
			}
		}
		return null;
	}

	/**
	 * Executes command on a ticket with specific ID
	 * 
	 * @param id      ID of ticket command is used on
	 * @param command Command to execute
	 */
	public void executeCommand(int id, Command command) {
		for (int i = 0; i < tickets.size(); i++) {
			if (tickets.get(i).getTicketId() == id) {
				tickets.get(i).update(command);
			}
		}
	}

	/**
	 * Deletes ticket with specific ID
	 * 
	 * @param id ID of ticket
	 */
	public void deleteTicketById(int id) {
		for (int i = 0; i < tickets.size(); i++) {
			if (tickets.get(i).getTicketId() == id) {
				tickets.remove(i);
			}
		}
	}

	/**
	 * Adds a Ticket to the list of Tickets
	 * 
	 * @param ticketType Type of ticket
	 * @param subject    Subject of ticket
	 * @param caller     Caller of ticket
	 * @param category   Category of ticket
	 * @param priority   Priority of ticket
	 * @param notes      Notes for ticket
	 */
	public void addTicketToList(TicketType ticketType, String subject, String caller, Category category,
			Priority priority, String notes) {
		Ticket ticket = new Ticket(ticketType, subject, caller, category, priority, notes);
		tickets.add(ticket);
	}

	/**
	 * Returns tickets in the TicketManager array
	 * 
	 * @return array of Tickets
	 */
	public ArrayList<Ticket> getTickets() {
		return tickets;
	}

}

/**
 * 
 */
package edu.ncsu.csc216.ticket_manager.model.ticket;

import java.util.ArrayList;

import edu.ncsu.csc216.ticket_manager.model.command.Command;
import edu.ncsu.csc216.ticket_manager.model.command.Command.CancellationCode;
import edu.ncsu.csc216.ticket_manager.model.command.Command.CommandValue;
import edu.ncsu.csc216.ticket_manager.model.command.Command.FeedbackCode;
import edu.ncsu.csc216.ticket_manager.model.command.Command.ResolutionCode;

/**
 * Class that creates the Ticket object, which contains all the info for a
 * ticket. Tickets are constructed assuming valid parameters. Tickets can also
 * be updated or printed as a String for export.
 * 
 * @author Benson Liu
 *
 */
public class Ticket {
	/** Ticket type of Request */
	public static final String TT_REQUEST = "Request";
	/** Ticket type of Incident */
	public static final String TT_INCIDENT = "Incident";
	/** Ticket category of Inquiry */
	public static final String C_INQUIRY = "Inquiry";
	/** Ticket category of Software */
	public static final String C_SOFTWARE = "Software";
	/** Ticket category of Hardware */
	public static final String C_HARDWARE = "Hardware";
	/** Ticket category of Network */
	public static final String C_NETWORK = "Network";
	/** Ticket category of Database */
	public static final String C_DATABASE = "Database";
	/** Ticket priority of Urgent */
	public static final String P_URGENT = "Urgent";
	/** Ticket priority of High */
	public static final String P_HIGH = "High";
	/** Ticket priority of Medium */
	public static final String P_MEDIUM = "Medium";
	/** Ticket priority of Low */
	public static final String P_LOW = "Low";
	/** Ticket state of New */
	public static final String NEW_NAME = "New";
	/** Ticket state of Working */
	public static final String WORKING_NAME = "Working";
	/** Ticket state of Feedback */
	public static final String FEEDBACK_NAME = "Feedback";
	/** Ticket state of Resolved */
	public static final String RESOLVED_NAME = "Resolved";
	/** Ticket state of Closed */
	public static final String CLOSED_NAME = "Closed";
	/** Ticket state of Canceled */
	public static final String CANCELED_NAME = "Canceled";
	/** Keeps track of id value for next Ticket created */
	private static int counter = 1;
	/** ID for a ticket */
	private int ticketId;
	/** Subject for a ticket */
	private String subject;
	/** Caller for a ticket */
	private String caller;
	/** owner assigned to investigate ticket */
	private String owner;
	/** Notes left for updating tickets */
	private ArrayList<String> notes;
	/** One of five categories for a ticket */
	private Category category;
	/** One of four priorities for a ticket */
	private Priority priority;
	/** One of two ticket types for a ticket */
	private TicketType ticketType;
	/** The feedback code for a ticket */
	private FeedbackCode feedbackCode;
	/** The resolution code for a ticket */
	private ResolutionCode resolutionCode;
	/** The cancellation code for a ticket */
	private CancellationCode cancellationCode;
	/** State of a ticket */
	private TicketState state;
	/** Ticket in the new state */
	private NewState newState = new NewState();
	/** Ticket in the working state */
	private WorkingState workingState = new WorkingState();
	/** Ticket in the feedback state */
	private FeedbackState feedbackState = new FeedbackState();
	/** Ticket in the resolved state */
	private ResolvedState resolvedState = new ResolvedState();
	/** Ticket in the closed state */
	private ClosedState closedState = new ClosedState();
	/** Ticket in the canceled state */
	private CanceledState canceledState = new CanceledState();

	/**
	 * Increments counter for ticket IDs so each ticket has a unique ID
	 */
	public static void incrementCounter() {
		counter++;
	}

	/**
	 * Sets counter for ticket IDs, where each ticket has a unique ID
	 * 
	 * @param i Gives tickets unique IDs
	 */
	public static void setCounter(int i) {
		if (i < 1) {
			throw new IllegalArgumentException("Ticket id must be a value greater than 0.");
		}
		counter = i;

	}

	/**
	 * One of two constructors for Ticket. Creates a Ticket when reading from a
	 * ticket file. Creates a Ticket object with ticket ID, state, type, subject,
	 * caller, category, priority, owner, code, and notes. An
	 * IllegalArgumentException is thrown in the setter methods if the parameters
	 * are not valid. If the incoming ID is greater than the current value in
	 * Ticket.counter, the Ticket.counter is updated with Ticket.setCounter(id + 1)
	 * 
	 * @param id         ID of ticket
	 * @param state      State of ticket
	 * @param ticketType Type of ticket
	 * @param subject    Subject of ticket
	 * @param caller     Caller of ticket
	 * @param category   Category of ticket
	 * @param priority   Priority of ticket
	 * @param owner      Owner of ticket
	 * @param code       Code  ticket
	 * @param notes      Notes for ticket
	 */
	public Ticket(int id, String state, String ticketType, String subject, String caller, String category,
			String priority, String owner, String code, ArrayList<String> notes) {
		setTicketId(id);
		setState(state);
		setTicketType(ticketType);
		setSubject(subject);
		setCaller(caller);
		setCategory(category);
		setPriority(priority);
		setOwner(owner);
		if (id > counter) {
			setCounter(id + 1);
		}
		if (code == null) {
			this.feedbackCode = null;
			this.resolutionCode = null;
			this.cancellationCode = null;
		} else if (code.equals(Command.F_CALLER) || code.equals(Command.F_CHANGE) || code.equals(Command.F_PROVIDER)) {
			setFeedbackCode(code);
		} else if (code.equals(Command.RC_CALLER_CLOSED) || code.equals(Command.RC_COMPLETED)
				|| code.equals(Command.RC_NOT_COMPLETED) || code.equals(Command.RC_NOT_SOLVED)
				|| code.equals(Command.RC_SOLVED) || code.equals(Command.RC_WORKAROUND)) {
			setResolutionCode(code);
		} else if (code.equals(Command.CC_DUPLICATE) || code.equals(Command.CC_INAPPROPRIATE)) {
			setCancellationCode(code);
		}

		this.notes = notes;

	}

	/**
	 * One of two constructors for Ticket. Constructs a Ticket object with ticket
	 * type, subject, caller, category, priority, and notes. If any of the
	 * parameters are null or the strings are empty, an IllegalArgumentException is
	 * thrown. The ticketId is set to the value stored in the Ticket.counter field,
	 * then counter is incremented in the Ticket.incrementCounter() method. The
	 * owner field is initialized to an empty string, the other fields are
	 * initialized to their parameter values.
	 * 
	 * @param ticketType Type of ticket
	 * @param subject    Subject of ticket
	 * @param caller     Caller of ticket
	 * @param category   Category of ticket
	 * @param priority   Priority of ticket
	 * @param note       Notes on ticket
	 */
	public Ticket(TicketType ticketType, String subject, String caller, Category category, Priority priority,
			String note) {
		if (ticketType == null || subject == null || caller == null || category == null || priority == null
				|| note == null) {
			throw new IllegalArgumentException("Cannot have null parameters");
		}
		if ("".equals(subject) || "".equals(caller) || "".equals(note)) {
			throw new IllegalArgumentException("Invalid empty parameter");
		}
		ticketId = counter;
		setCounter(ticketId);
		this.ticketType = ticketType;
		this.subject = subject;
		this.caller = caller;
		this.category = category;
		this.priority = priority;
		owner = "";
		state = newState;
		ArrayList<String> n = new ArrayList<String>();
		n.add(note);
		this.notes = n;
		incrementCounter();

	}

	/**
	 * Returns unique ID for ticket
	 * 
	 * @return ID for ticket
	 */
	public int getTicketId() {
		return ticketId;
	}

	/**
	 * Returns subject for ticket
	 * 
	 * @return subject for ticket
	 */
	public String getSubject() {
		return subject;
	}

	/**
	 * Returns caller for ticket
	 * 
	 * @return caller for ticket
	 */
	public String getCaller() {
		return caller;
	}

	/**
	 * Returns owner for ticket
	 * 
	 * @return owner for ticket
	 */
	public String getOwner() {
		return owner;
	}

	/**
	 * Returns category for ticket
	 * 
	 * @return category for ticket
	 */
	public String getCategory() {
		if (category == Category.DATABASE) {
			return C_DATABASE;
		}
		if (category == Category.HARDWARE) {
			return C_HARDWARE;
		}
		if (category == Category.INQUIRY) {
			return C_INQUIRY;
		}
		if (category == Category.NETWORK) {
			return C_NETWORK;
		}
		if (category == Category.SOFTWARE) {
			return C_SOFTWARE;
		}
		return null;

	}

	/**
	 * Returns priority for ticket
	 * 
	 * @return priority for ticket
	 */
	public String getPriority() {
		if (priority == Priority.HIGH) {
			return P_HIGH;
		}
		if (priority == Priority.LOW) {
			return P_LOW;
		}
		if (priority == Priority.MEDIUM) {
			return P_MEDIUM;
		}
		if (priority == Priority.URGENT) {
			return P_URGENT;
		}
		return null;
	}

	/**
	 * Returns feedback code for ticket
	 * 
	 * @return feedback code for ticket
	 */
	public String getFeedbackCode() {
		if (feedbackCode == Command.FeedbackCode.AWAITING_CALLER) {
			return Command.F_CALLER;
		}
		if (feedbackCode == Command.FeedbackCode.AWAITING_CHANGE) {
			return Command.F_CHANGE;
		}
		if (feedbackCode == Command.FeedbackCode.AWAITING_PROVIDER) {
			return Command.F_PROVIDER;
		}
		return null;
	}

	/**
	 * Returns resolution code for ticket
	 * 
	 * @return resolution code for ticket
	 */
	public String getResolutionCode() {
		if (resolutionCode == Command.ResolutionCode.CALLER_CLOSED) {
			return Command.RC_CALLER_CLOSED;
		}
		if (resolutionCode == Command.ResolutionCode.COMPLETED) {
			return Command.RC_COMPLETED;
		}
		if (resolutionCode == Command.ResolutionCode.NOT_COMPLETED) {
			return Command.RC_NOT_COMPLETED;
		}
		if (resolutionCode == Command.ResolutionCode.NOT_SOLVED) {
			return Command.RC_NOT_SOLVED;
		}
		if (resolutionCode == Command.ResolutionCode.SOLVED) {
			return Command.RC_SOLVED;
		}
		if (resolutionCode == Command.ResolutionCode.WORKAROUND) {
			return Command.RC_WORKAROUND;
		}
		return null;
	}

	/**
	 * Returns cancellation code for ticket
	 * 
	 * @return cancellation code for ticket
	 */
	public String getCancellationCode() {
		if (cancellationCode == Command.CancellationCode.DUPLICATE) {
			return Command.CC_DUPLICATE;
		}
		if (cancellationCode == Command.CancellationCode.INAPPROPRIATE) {
			return Command.CC_INAPPROPRIATE;
		}
		return null;
	}

	/**
	 * Returns state for ticket
	 * 
	 * @return state for ticket
	 */
	public String getState() {
		if (state == newState) {
			return NEW_NAME;
		}
		if (state == workingState) {
			return WORKING_NAME;
		}
		if (state == feedbackState) {
			return FEEDBACK_NAME;
		}
		if (state == resolvedState) {
			return RESOLVED_NAME;
		}
		if (state == closedState) {
			return CLOSED_NAME;
		}
		if (state == canceledState) {
			return CANCELED_NAME;
		}
		return "";
	}

	/**
	 * Returns ticket type for ticket
	 * 
	 * @return ticket type for ticket
	 */
	public TicketType getTicketType() {
		return ticketType;
	}

	/**
	 * Returns ticket type for ticket in form of a String
	 * 
	 * @return ticket type in String
	 */
	public String getTicketTypeString() {
		if (ticketType == TicketType.INCIDENT) {
			return "Incident";
		}
		if (ticketType == TicketType.REQUEST) {
			return "Request";
		}
		return null;
	}

	/**
	 * Returns notes in a way suitable for printing to a .txt file or the GUI
	 * 
	 * @return Correct String format for notes
	 */
	public String getNotes() {
		String n = "";
		for (int i = 0; i < notes.size(); i++) {
			n = n + "-" + notes.get(i) + "\n";
		}
		return n;
	}

	/**
	 * Sets the type for a ticket. An IllegalArgumentException is thrown if the
	 * ticket does not have a type of Request or Incident.
	 * 
	 * @param type Type for ticket
	 */
	private void setTicketType(String type) {
		if (type.equals(TT_REQUEST)) {
			this.ticketType = TicketType.REQUEST;
		} else if (type.equals(TT_INCIDENT)) {
			this.ticketType = TicketType.INCIDENT;
		} else {
			throw new IllegalArgumentException("Invalid ticket type");
		}
	}

	/**
	 * Sets unique ID for a ticket. If ID value is less than 0 an
	 * IllegalArgumentException is thrown.
	 * 
	 * @param ticketId ID of ticket
	 */
	private void setTicketId(int ticketId) {
		if (ticketId < 0) {
			throw new IllegalArgumentException("Ticket ID must be greater than or equal to 0");
		}
		this.ticketId = ticketId;
	}

	/**
	 * Sets subject for a ticket. An IllegalArgumentException is thrown if the
	 * subject String has a length less than 1.
	 * 
	 * @param subject Subject for a ticket
	 */
	private void setSubject(String subject) {
		if (subject.length() < 1) {
			throw new IllegalArgumentException("Invalid subject");
		}
		this.subject = subject;
	}

	/**
	 * Sets caller for a ticket. An IllegalArgumentException is thrown if the caller
	 * String has a length less than 1.
	 * 
	 * @param caller Caller for a ticket
	 */
	private void setCaller(String caller) {
		if (caller.length() < 1) {
			throw new IllegalArgumentException("Invalid caller");
		}
		this.caller = caller;
	}

	/**
	 * Sets owner for a ticket. The ticket must have an owner if in the Working,
	 * Feedback, Resolved or Closed State. The ticket may have an owner in the
	 * Canceled State. If these conditions are not met, an IllegalArgumentException
	 * is thrown.
	 * 
	 * @param owner Owner for a ticket
	 */
	private void setOwner(String owner) {
		this.owner = owner;
	}

	/**
	 * Sets notes for a ticket. There must be at least one note line for a ticket,
	 * or an IllegalArgumentException is thrown. The '-' or '*' characters start a
	 * new line.
	 * 
	 * @param notes Notes for a ticket
	 * 
	 *              private void setNotes(ArrayList<String> notes) { this.notes =
	 *              notes; }
	 * 
	 */
	/**
	 * Sets category for a ticket. The category must be Inquiry, Software, Hardware,
	 * or Database, or an IllegalArgumentException is thrown
	 * 
	 * @param cat Category for a ticket
	 */
	private void setCategory(String cat) {
		if (cat.equals(C_INQUIRY)) {
			this.category = Category.INQUIRY;
		} else if (cat.equals(C_SOFTWARE)) {
			this.category = Category.SOFTWARE;
		} else if (cat.equals(C_HARDWARE)) {
			this.category = Category.HARDWARE;
		} else if (cat.equals(C_DATABASE)) {
			this.category = Category.DATABASE;
		} else if (cat.equals(C_NETWORK)) {
			this.category = Category.NETWORK;
		} else {
			throw new IllegalArgumentException("Invalid category");
		}
	}

	/**
	 * Sets priority for a ticket. An IllegalArgumentException is thrown if priority
	 * is not Urgent, High, Medium, or Low
	 * 
	 * @param priority Priority for ticket
	 */
	private void setPriority(String pr) {
		if (pr.equals(P_HIGH)) {
			this.priority = Priority.HIGH;
		} else if (pr.equals(P_LOW)) {
			this.priority = Priority.LOW;
		} else if (pr.equals(P_MEDIUM)) {
			this.priority = Priority.MEDIUM;
		} else if (pr.equals(P_URGENT)) {
			this.priority = Priority.URGENT;
		} else {
			throw new IllegalArgumentException("Invalid priority");
		}
	}

	/**
	 * Sets feedback code for a ticket. In the Feedback state, the ticket must have
	 * a code of Awaiting Caller, Awaiting Change, or Awaiting Provider, or else an
	 * IllegalArgumentException is thrown.
	 * 
	 * @param code Feedback code for a ticket
	 */
	private void setFeedbackCode(String code) {
		if (code.equals(Command.F_CALLER)) {
			this.feedbackCode = Command.FeedbackCode.AWAITING_CALLER;
		} else if (code.equals(Command.F_CHANGE)) {
			this.feedbackCode = Command.FeedbackCode.AWAITING_CHANGE;
		} else if (code.equals(Command.F_PROVIDER)) {
			this.feedbackCode = Command.FeedbackCode.AWAITING_PROVIDER;
		} else {
			throw new IllegalArgumentException("Invalid feedback code");
		}
	}

	/**
	 * Sets resolution code for a ticket. In the Resolved or Closed states, the
	 * ticket must have a code of Completed, Not Completed, Solved, Workaround, Not
	 * Solved, or Caller Closed. If the Ticket is a Request type, the code can only
	 * be Completed, Not Completed, or Caller Closed. If the ticket is an Incident
	 * type, the code can only be Solved, Workaround, Not Solved, or Caller Closed.
	 * 
	 * @param code Resolution code for ticket
	 */
	private void setResolutionCode(String code) {
		if (code.equals(Command.RC_CALLER_CLOSED)) {
			this.resolutionCode = Command.ResolutionCode.CALLER_CLOSED;
		}
		if (code.equals(Command.RC_COMPLETED)) {
			this.resolutionCode = Command.ResolutionCode.COMPLETED;
		}
		if (code.equals(Command.RC_NOT_COMPLETED)) {
			this.resolutionCode = Command.ResolutionCode.NOT_COMPLETED;
		}
		if (code.equals(Command.RC_NOT_SOLVED)) {
			this.resolutionCode = Command.ResolutionCode.NOT_SOLVED;
		}
		if (code.equals(Command.RC_SOLVED)) {
			this.resolutionCode = Command.ResolutionCode.SOLVED;
		}
		if (code.equals(Command.RC_WORKAROUND)) {
			this.resolutionCode = Command.ResolutionCode.WORKAROUND;
		} 
	}

	/**
	 * Sets cancellation code for a ticket. The ticket must have a code of Duplicate
	 * or Inappropriate if in the Canceled state, or an IllegalArgumentException is
	 * thrown.
	 * 
	 * @param code Cancellation code for ticket
	 */
	private void setCancellationCode(String code) {
		if (code.equals(Command.CC_DUPLICATE)) {
			this.cancellationCode = Command.CancellationCode.DUPLICATE;
		}
		else if (code.equals(Command.CC_INAPPROPRIATE)) {
			this.cancellationCode = Command.CancellationCode.INAPPROPRIATE;
		}
	}

	/**
	 * Sets the state of a code. An IllegalArgumentException is thrown if the ticket
	 * does not have a state of New, Working, Feedback, Resolved, Closed, or
	 * Canceled.
	 * 
	 * @param state State for ticket
	 */
	private void setState(String state) {
		if (state.equals(NEW_NAME) || "".equals(state)) {
			this.state = newState;
		} else if (state.equals(WORKING_NAME)) {
			this.state = workingState;
		} else if (state.equals(FEEDBACK_NAME)) {
			this.state = feedbackState;
		} else if (state.equals(RESOLVED_NAME)) {
			this.state = resolvedState;
		} else if (state.equals(CLOSED_NAME)) {
			this.state = closedState;
		} else if (state.equals(CANCELED_NAME)) {
			this.state = canceledState;
		} else {
			throw new IllegalArgumentException("Invalid state");
		}
	}

	/**
	 * Converts information of a ticket as a String suitable for printing to a .txt
	 * file
	 */
	@Override
	public String toString() {
		return "*" + getTicketId() + "#" + getState() + "#" + getTicketTypeString() + "#" + getSubject() + "#"
				+ getCaller() + "#" + getCategory() + "#" + getPriority() + "#" + getOwner() + "#" + getCode() + "\n"
				+ getNotes();
	}

	/**
	 * Returns the code in a String format
	 * 
	 * @return code in String format
	 */
	public String getCode() {
		if (this.getCancellationCode() != null) {
			return getCancellationCode();
		} else if (this.getFeedbackCode() != null) {
			return getFeedbackCode();
		} else if (this.getResolutionCode() != null) {
			return getResolutionCode();
		} else {
			return "";
		}
	}

	/**
	 * Applies command to ticket to update its state. If successful, adds new notes
	 * to the list. Throws an UnsupportedOperationException if the state transition
	 * is not valid.
	 * 
	 * @param command Command to update state.
	 */
	public void update(Command command) {
		String s = state.getStateName();
		switch (s) {
		case NEW_NAME:
			if (command.getCommand() == CommandValue.PROCESS) {
				state.updateState(command);
				setOwner(command.getOwnerId());
				notes.add(command.getNote());
			} else if (command.getCommand() == CommandValue.CANCEL) {
				state.updateState(command);
				setCancellationCode(convertCancelationCodeToString(command.getCancellationCode()));
				notes.add(command.getNote());
			} else {
				throw new UnsupportedOperationException();
			}

			break;

		case WORKING_NAME:
			if (command.getCommand() == CommandValue.CANCEL) {
				state.updateState(command);
				setCancellationCode(convertCancelationCodeToString(command.getCancellationCode()));
				notes.add(command.getNote());
			} else if (command.getCommand() == CommandValue.FEEDBACK) {
				state.updateState(command);
				String f = convertFeedbackCodeToString(command.getFeedbackCode());
				setFeedbackCode(f);
				notes.add(command.getNote());
			} else if (command.getCommand() == CommandValue.RESOLVE) {
				if (getTicketTypeString().equals(TT_INCIDENT)
						&& (command.getResolutionCode() == Command.ResolutionCode.COMPLETED
								|| command.getResolutionCode() == Command.ResolutionCode.NOT_COMPLETED)) {
					throw new UnsupportedOperationException();
				}
				if (getTicketTypeString().equals(TT_REQUEST)
						&& (command.getResolutionCode() == Command.ResolutionCode.NOT_SOLVED
								|| command.getResolutionCode() == Command.ResolutionCode.SOLVED
								|| command.getResolutionCode() == Command.ResolutionCode.WORKAROUND)) {
					throw new UnsupportedOperationException();
				}
				state.updateState(command);
				setResolutionCode(convertResolutionCodeToString(command.getResolutionCode()));
				notes.add(command.getNote());
			} else {
				throw new UnsupportedOperationException();
			}
			break;

		case FEEDBACK_NAME:
			if (command.getCommand() == CommandValue.REOPEN) {
				state.updateState(command);
				notes.add(command.getNote());
				this.resolutionCode = null;
				this.feedbackCode = null;
			} else if (command.getCommand() == CommandValue.CANCEL) {
				state.updateState(command);
				notes.add(command.getNote());
				setCancellationCode(convertCancelationCodeToString(command.getCancellationCode()));
				this.feedbackCode = null;
			} else if (command.getCommand() == CommandValue.RESOLVE) {
				if (getTicketTypeString().equals(TT_INCIDENT)
						&& (command.getResolutionCode() == Command.ResolutionCode.COMPLETED
								|| command.getResolutionCode() == Command.ResolutionCode.NOT_COMPLETED)) {
					throw new UnsupportedOperationException();
				}
				if (getTicketTypeString().equals(TT_REQUEST)
						&& (command.getResolutionCode() == Command.ResolutionCode.NOT_SOLVED
								|| command.getResolutionCode() == Command.ResolutionCode.SOLVED
								|| command.getResolutionCode() == Command.ResolutionCode.WORKAROUND)) {
					throw new UnsupportedOperationException();
				}
				state.updateState(command);
				notes.add(command.getNote());
				setResolutionCode(convertResolutionCodeToString(command.getResolutionCode()));
				this.feedbackCode = null;
			} else {
				throw new UnsupportedOperationException();
			}
			break;
		case RESOLVED_NAME:
			if (command.getCommand() == CommandValue.CONFIRM) {
				state.updateState(command);
				notes.add(command.getNote());
			} else if (command.getCommand() == CommandValue.FEEDBACK) {
				state.updateState(command);
				notes.add(command.getNote());
				this.resolutionCode = null;
				this.cancellationCode = null;
				setFeedbackCode(convertFeedbackCodeToString(command.getFeedbackCode()));
			} else if (command.getCommand() == CommandValue.REOPEN) {
				state.updateState(command);
				notes.add(command.getNote());
				this.resolutionCode = null;
				this.feedbackCode = null;
				this.cancellationCode = null;
			} else {
				throw new UnsupportedOperationException();
			}
			break;
		case CLOSED_NAME:
			if (command.getCommand() == CommandValue.REOPEN) {
				state.updateState(command);
				notes.add(command.getNote());
				this.resolutionCode = null;
				this.feedbackCode = null;
				this.cancellationCode = null;
			} else {
				throw new UnsupportedOperationException();
			}
			break;
		case CANCELED_NAME:
			if (command.getCommand() != null) {
				throw new UnsupportedOperationException();
			}
			break;

		default:
			break;
		}
	}

	/**
	 * Converts feedback code of type FeedbackCode to String
	 * 
	 * @param code code being converted
	 * @return code in form of String
	 */
	public String convertFeedbackCodeToString(FeedbackCode code) {
		if (code == Command.FeedbackCode.AWAITING_CALLER) {
			return Command.F_CALLER;
		}
		if (code == Command.FeedbackCode.AWAITING_CHANGE) {
			return Command.F_CHANGE;
		}
		if (code == Command.FeedbackCode.AWAITING_PROVIDER) {
			return Command.F_PROVIDER;
		}
		return "";
	}

	/**
	 * converts cancellation code of type CancellationCode to String
	 * 
	 * @param code code being converted
	 * @return code in form of String
	 */
	public String convertCancelationCodeToString(CancellationCode code) {
		if (code == Command.CancellationCode.DUPLICATE) {
			return Command.CC_DUPLICATE;
		}
		if (code == Command.CancellationCode.INAPPROPRIATE) {
			return Command.CC_INAPPROPRIATE;
		}
		return "";
	}

	/**
	 * converts resolution code of type ResolutionCode to String
	 * 
	 * @param code code being converted
	 * @return code in form of String
	 */
	public String convertResolutionCodeToString(ResolutionCode code) {
		if (code == Command.ResolutionCode.CALLER_CLOSED) {
			return Command.RC_CALLER_CLOSED;
		}
		if (code == Command.ResolutionCode.COMPLETED) {
			return Command.RC_COMPLETED;
		}
		if (code == Command.ResolutionCode.NOT_COMPLETED) {
			return Command.RC_NOT_COMPLETED;
		}
		if (code == Command.ResolutionCode.NOT_SOLVED) {
			return Command.RC_NOT_SOLVED;
		}
		if (code == Command.ResolutionCode.SOLVED) {
			return Command.RC_SOLVED;
		}
		if (code == Command.ResolutionCode.WORKAROUND) {
			return Command.RC_WORKAROUND;
		}
		return "";
	}

	/**
	 * Actions for ticket in New state
	 */
	private class NewState implements TicketState {

		@Override
		public void updateState(Command command) {
			if (command.getCommand() == CommandValue.PROCESS && state == newState) {
				state = workingState;
			}
			if (command.getCommand() == CommandValue.CANCEL && state == newState) {
				state = canceledState;
			}

		}

		@Override
		public String getStateName() {
			return NEW_NAME;
		}

	}

	/**
	 * Actions for ticket in Working state
	 */
	private class WorkingState implements TicketState {

		@Override
		public void updateState(Command command) {
			if (state.equals(workingState) && command.getCommand() == CommandValue.CANCEL) {
				state = canceledState;
			} else if (state.equals(workingState) && command.getCommand() == CommandValue.FEEDBACK) {
				state = feedbackState;
			} else if (state.equals(workingState) && command.getCommand() == CommandValue.RESOLVE) {
				state = resolvedState;
			}

		}

		@Override
		public String getStateName() {
			return WORKING_NAME;
		}
	}

	/**
	 * Actions for ticket in Feedback state
	 */
	private class FeedbackState implements TicketState {

		@Override
		public void updateState(Command command) {
			if (state.equals(feedbackState) && command.getCommand() == CommandValue.REOPEN) {
				state = workingState;
			} else if (state.equals(feedbackState) && command.getCommand() == CommandValue.RESOLVE) {
				state = resolvedState;
			} else if (state.equals(feedbackState) && command.getCommand() == CommandValue.CANCEL) {
				state = canceledState;
			}
		}

		@Override
		public String getStateName() {
			return FEEDBACK_NAME;
		}

	}

	/**
	 * Actions for ticket in Resolved state
	 */
	private class ResolvedState implements TicketState {

		@Override
		public void updateState(Command command) {
			if (command.getCommand() == CommandValue.CONFIRM) {
				state = closedState;
			} else if (command.getCommand() == CommandValue.FEEDBACK) {
				state = feedbackState;
			} else if (command.getCommand() == CommandValue.REOPEN) {
				state = workingState;
			}

		}

		@Override
		public String getStateName() {
			return RESOLVED_NAME;
		}

	}

	/**
	 * Actions for ticket in Closed state
	 */
	private class ClosedState implements TicketState {

		@Override
		public void updateState(Command command) {
			if (command.getCommand() == CommandValue.REOPEN) {
				state = workingState;
			}
		}

		@Override
		public String getStateName() {
			return CLOSED_NAME;
		}

	}

	/**
	 * Actions for ticket in Canceled state
	 */
	private class CanceledState implements TicketState {

		@Override
		public void updateState(Command command) {
			//Not possible
		}

		@Override
		public String getStateName() {
			return CANCELED_NAME;
		}

	}

	/** List of five categories for a ticket */
	public enum Category {
		/** Ticket category of Inquiry */
		INQUIRY,
		/** Ticket category of Software */
		SOFTWARE,
		/** Ticket category of Hardware */
		HARDWARE,
		/** Ticket category of Network */
		NETWORK,
		/** Ticket category of Database */
		DATABASE
	}

	/** List of four priorities for a ticket */
	public enum Priority {
		/** Ticket priority of Urgent */
		URGENT,
		/** Ticket priority of High */
		HIGH,
		/** Ticket priority of Medium */
		MEDIUM,
		/** Ticket priority of Low */
		LOW
	}

	/** List of two ticket types for a ticket */
	public enum TicketType {
		/** Ticket type of Request */
		REQUEST,
		/** Ticket type of Incident */
		INCIDENT
	}
}

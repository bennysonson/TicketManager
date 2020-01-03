package edu.ncsu.csc216.ticket_manager.model.command;

/**
 * Class for assigning commands to Tickets. Commands done by users transition
 * the state of a Ticket. Command contains four enumerations, which contain the
 * possible codes for a ticket based on their state.
 * 
 * @author Benson Liu
 *
 */
public class Command {
	/** Feedback code, waiting for more information from caller */
	public static final String F_CALLER = "Awaiting Caller";
	/** Feedback code, waiting for change in some system */
	public static final String F_CHANGE = "Awaiting Change";
	/** Feedback code, awaiting response from external source */
	public static final String F_PROVIDER = "Awaiting Provider";
	/** Resolution code, request ticket was completed */
	public static final String RC_COMPLETED = "Completed";
	/** Resolution code, request ticket was not completed */
	public static final String RC_NOT_COMPLETED = "Not Completed";
	/** Resolution code, incident ticket was solved */
	public static final String RC_SOLVED = "Solved";
	/** Resolution code, incident ticket was solved with a workaround */
	public static final String RC_WORKAROUND = "Workaround";
	/** Resolution code, incident ticket was not solved */
	public static final String RC_NOT_SOLVED = "Not Solved";
	/** Resolution code, ticket was closed by caller */
	public static final String RC_CALLER_CLOSED = "Caller Closed";
	/** Cancellation code, ticket is a duplicate of another in the system */
	public static final String CC_DUPLICATE = "Duplicate";
	/** Cancellation code, ticket should not be handled by IT */
	public static final String CC_INAPPROPRIATE = "Inappropriate";
	/** ID for owner assigned to investigate the ticket */
	private String ownerId;
	/** Note for ticket, describing updates in the investigation process */
	private String note;
	/** The command code for a ticket */
	private CommandValue c;
	/** The feedback code for a ticket */
	private FeedbackCode feedbackCode;
	/** The resolution code for a ticket */
	private ResolutionCode resolutionCode;
	/** The cancellation code for a ticket */
	private CancellationCode cancellationCode;

	/**
	 * Constructor for Command class. Creates Command object with command value,
	 * owner ID, feedback code, resolution code, cancellation code, and note info.
	 * Throws an IllegalArgumentException if the codes are null, the note is null or
	 * empty, or if the command value is Process and the owner ID is null or empty.
	 * 
	 * @param c                One of six possible command codes for ticket
	 * @param ownerId          ID of owner assigned to investigate ticket
	 * @param feedbackCode     One of three feedback codes for ticket
	 * @param resolutionCode   One of six resolution codes for ticket
	 * @param cancellationCode One of two cancellation codes for ticket
	 * @param note             Note for ticket, describes updates in investigation
	 *                         process
	 */
	public Command(CommandValue c, String ownerId, FeedbackCode feedbackCode, ResolutionCode resolutionCode,
			CancellationCode cancellationCode, String note) {
		if (c == null) {
			throw new IllegalArgumentException("Command Value cannot be null.");
		}
		if (c == CommandValue.PROCESS && (ownerId == null || "".equals(ownerId))) {
			throw new IllegalArgumentException("The Process command requires an owner ID.");
		}
		if (c == CommandValue.FEEDBACK && feedbackCode == null) {
			throw new IllegalArgumentException("The Feedback command requires a feedback code");
		}
		if (c == CommandValue.RESOLVE && resolutionCode == null) {
			throw new IllegalArgumentException("The Resolve command requires a resolution code");
		}
		if (c == CommandValue.CANCEL && cancellationCode == null) {
			throw new IllegalArgumentException("The Cancel command requires a cancellation code");
		}
		if (note == null || "".equals(note)) {
			throw new IllegalArgumentException("A Command requires a note");
		}
		this.ownerId = ownerId;
		this.note = note;
		this.c = c;
		this.feedbackCode = feedbackCode;
		this.resolutionCode = resolutionCode;
		this.cancellationCode = cancellationCode;

	}

	/**
	 * Returns ID for owner assigned to investigate ticket
	 * 
	 * @return owners ID
	 */
	public String getOwnerId() {
		return ownerId;
	}

	/**
	 * Returns note for ticket that describes updates in investigation process
	 * 
	 * @return note for ticket
	 */
	public String getNote() {
		return note;
	}

	/**
	 * Returns command value, one of six commands a user can make on a ticket
	 * 
	 * @return command value for ticket
	 */
	public CommandValue getCommand() {
		return c;
	}

	/**
	 * Returns resolution code, one of six resolution codes for tickets
	 * 
	 * @return resolution code for ticket
	 */
	public ResolutionCode getResolutionCode() {
		return resolutionCode;
	}

	/**
	 * Returns feedback code, one of three resolution codes for tickets
	 * 
	 * @return feedback code for ticket
	 */
	public FeedbackCode getFeedbackCode() {
		return feedbackCode;
	}

	/**
	 * Returns cancellation code, one of two cancellation codes for tickets
	 * 
	 * @return cancellation code for ticket
	 */
	public CancellationCode getCancellationCode() {
		return cancellationCode;
	}

	/**
	 * list of six command values possible for ticket
	 */
	public enum CommandValue {
		/** User command to process ticket */
		PROCESS,
		/** User command to place ticket in feedback state */
		FEEDBACK,
		/** User command to resolve ticket */
		RESOLVE,
		/** User command to confirm ticket */
		CONFIRM,
		/** User command to reopen ticket */
		REOPEN,
		/** User command to cancel ticket */
		CANCEL,

	}

	/** list of three feedback codes possible for ticket */
	public enum FeedbackCode {
		/** The ticket requires more info from the caller */
		AWAITING_CALLER,
		/** The ticket requires a change in the system */
		AWAITING_CHANGE,
		/** The ticket requires response from an external provider */
		AWAITING_PROVIDER
	}

	/** list of six resolution codes possible for ticket */
	public enum ResolutionCode {
		/** The request ticket was completed */
		COMPLETED,
		/** The request ticket was not completed */
		NOT_COMPLETED,
		/** The incident ticket was solved */
		SOLVED,
		/** The incident ticket was resolved with a workaround */
		WORKAROUND,
		/** The incident ticket was not solved */
		NOT_SOLVED,
		/** The ticket was closed by the caller */
		CALLER_CLOSED
	}

	/** list of two cancellation codes for ticket */
	public enum CancellationCode {
		/** The ticket was cancelled because it was a duplicate in the system */
		DUPLICATE,
		/** The ticket was cancelled because it should not be handled by IT */
		INAPPROPRIATE
	}

}

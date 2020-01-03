package edu.ncsu.csc216.ticket_manager.model.ticket;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import edu.ncsu.csc216.ticket_manager.model.command.Command;
import edu.ncsu.csc216.ticket_manager.model.command.Command.CancellationCode;
import edu.ncsu.csc216.ticket_manager.model.command.Command.CommandValue;
import edu.ncsu.csc216.ticket_manager.model.command.Command.FeedbackCode;
import edu.ncsu.csc216.ticket_manager.model.command.Command.ResolutionCode;

/**
 * Class for testing TicketState
 * 
 * @author Benson Liu
 *
 */
public class TicketStateTest {

	private ArrayList<String> notes = new ArrayList<String>();

	/**
	 * Test for New state
	 */
	@Test
	public void testNew() {
		notes.add("a note");
		Ticket a = new Ticket(3, "New", "Request", "Oh yea", "Benson", Ticket.C_SOFTWARE, "Urgent", "Ben",
				Command.RC_COMPLETED, notes);
		assertEquals("-a note\n", a.getNotes());
		Command c = new Command(CommandValue.PROCESS, "Benson", null, null, null, "note");
		assertEquals(Ticket.NEW_NAME, a.getState());
		a.update(c);
		assertEquals("-a note\n-note\n", a.getNotes());
		assertEquals(Ticket.WORKING_NAME, a.getState());
		Command c1 = new Command(CommandValue.CANCEL, null, null, null, CancellationCode.DUPLICATE, "note");
		Command c2 = new Command(CommandValue.REOPEN, null, null, null, null, "note");
		assertEquals(c1.getCommand(), CommandValue.CANCEL);
		Ticket b = new Ticket(3, "New", "Request", "Oh no", "Bender", Ticket.C_SOFTWARE, "Urgent", null, null, notes);
		try {
			b.update(c2);
		} catch (UnsupportedOperationException e) {
			assertEquals(null, e.getMessage());
		}

		b.update(c1);
		assertEquals(Ticket.CANCELED_NAME, b.getState());

	}

	/**
	 * Test for WorkingState state
	 */
	@Test
	public void testWorking() {
		Ticket a = new Ticket(2, "Working", "Request", "pizza", "Benson", Ticket.C_DATABASE, "High", "Annie", null, notes);
		Command c = new Command(CommandValue.CANCEL, "Dan", null, null, CancellationCode.INAPPROPRIATE, "note");
		a.update(c);
		assertEquals(a.getCancellationCode(), "Inappropriate");
		Ticket b = new Ticket(2, "Working", "Request", "pizza", "Benson", Ticket.C_DATABASE, "High", "Annie", null, notes);
		Command co = new Command(CommandValue.FEEDBACK, "Dan", FeedbackCode.AWAITING_PROVIDER, null, null, "note");
		b.update(co);
		Ticket be = new Ticket(2, "Working", "Incident", "pizza", "Benson", Ticket.C_DATABASE, "High", "Annie", null, notes);
		try {
			Command com = new Command(CommandValue.RESOLVE, "Daniel", null, ResolutionCode.SOLVED, null, "note!");
			be.update(com);
		} catch (UnsupportedOperationException e) {
			assertEquals(null, e.getMessage());
		}

	}
	
	/**
	 * Test for Feedback state
	 */
	@Test
	public void testFeedback() {
		Ticket a = new Ticket(2, "Feedback", "Request", "pizza", "Benson", Ticket.C_DATABASE, "High", "Annie", null, notes);
		Command c = new Command(CommandValue.REOPEN, "Dan", null, null, null, "note");
		a.update(c);
		assertEquals(a.getState(), Ticket.WORKING_NAME);
		Ticket b = new Ticket(2, "Feedback", "Request", "pizza", "Benson", Ticket.C_DATABASE, "High", "Annie", null, notes);
		Command co = new Command(CommandValue.CANCEL, "Dan", null, null, CancellationCode.DUPLICATE, "note");
		b.update(co);
		assertEquals(b.getState(), Ticket.CANCELED_NAME);
		Ticket be = new Ticket(2, "Feedback", "Incident", "pizza", "Benson", Ticket.C_DATABASE, "High", "Annie", null, notes);
		Command com = new Command(CommandValue.RESOLVE, "Dan", null, ResolutionCode.SOLVED, null, "note");
		be.update(com);
		assertEquals(be.getState(), Ticket.RESOLVED_NAME);
	}
	
	/**
	 * Test for Resolved State
	 */
	@Test
	public void testResolved() {
		Ticket a = new Ticket(2, "Resolved", "Request", "pizza", "Benson", Ticket.C_DATABASE, "High", "Annie", null, notes);
		Ticket b = new Ticket(2, "Resolved", "Request", "pizza", "Benson", Ticket.C_DATABASE, "High", "Annie", null, notes);
		Ticket be = new Ticket(2, "Resolved", "Request", "pizza", "Benson", Ticket.C_DATABASE, "High", "Annie", null, notes);
		Command c = new Command(CommandValue.CONFIRM, "Dan", null, null, null, "note");
		Command ce = new Command(CommandValue.FEEDBACK, "Dan", FeedbackCode.AWAITING_CALLER, null, null, "note");
		Command cem = new Command(CommandValue.REOPEN, "Dan", null, null, null, "note");
		a.update(c);
		assertEquals(a.getState(), Ticket.CLOSED_NAME);
		b.update(ce);
		assertEquals(b.getState(), Ticket.FEEDBACK_NAME);
		be.update(cem);
		assertEquals(be.getState(), Ticket.WORKING_NAME);

	}
	/**
	 * Test for Closed State
	 */
	@Test
	public void testClosed() {
		Ticket a = new Ticket(2, "Closed", "Request", "pizza", "Benson", Ticket.C_DATABASE, "High", "Annie", null, notes);
		Command c = new Command(CommandValue.REOPEN, "Dan", null, null, null, "note");
		Command ce = new Command(CommandValue.CONFIRM, "Dan", null, null, null, "note");
		Ticket b = new Ticket(2, "New", "Incident", "pizza", "Benson", Ticket.C_DATABASE, "High", "Annie", null, notes);
		Command cem = new Command(CommandValue.PROCESS, "owner", null, null, null, "note");
		Command com = new Command(CommandValue.RESOLVE, "owner", null, ResolutionCode.NOT_SOLVED, null, "note");
		Command ceme = new Command(CommandValue.CONFIRM, "owner", null, null, null, "note");

		try {
			a.update(ce);
		} catch (UnsupportedOperationException e) {
			assertEquals(null, e.getMessage());
		}
		
		a.update(c);
		assertEquals(a.getState(), Ticket.WORKING_NAME);
		b.update(cem);
		b.update(com);
		b.update(ceme);
		assertEquals("Not Solved", b.getResolutionCode());
		b.update(c);
		assertEquals(b.getState(), "Working");
		assertEquals(null, b.getResolutionCode());
	}
	
	/**
	 * Test for Canceled State
	 */
	@Test
	public void testCanceled() {
		Ticket a = new Ticket(2, "Canceled", "Request", "pizza", "Benson", Ticket.C_DATABASE, "High", "Annie", null, notes);
		assertEquals(a.getState(), "Canceled");
		Command c = new Command(CommandValue.REOPEN, "Dan", null, null, null, "note");
		try {
			a.update(c);
		} catch (UnsupportedOperationException e) {
			assertEquals(e.getMessage(), null);
		}
			}
}

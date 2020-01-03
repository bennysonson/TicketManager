package edu.ncsu.csc216.ticket_manager.model.ticket;


import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.Test;

import edu.ncsu.csc216.ticket_manager.model.command.Command;
/**
 * Class for testing Ticket
 * 
 * @author Benson Liu
 *
 */
public class TicketTest {

	private ArrayList<String> notes = new ArrayList<String>();
	
	/**
	 * Test for method incrementCounter()
	 */
	@Test
	public void testIncrementCounter() {
		Ticket.setCounter(1);
		Ticket a = new Ticket(Ticket.TicketType.INCIDENT, "subject", "caller", Ticket.Category.DATABASE, Ticket.Priority.HIGH, "note");
		Ticket b = new Ticket(Ticket.TicketType.REQUEST, "subject", "caller", Ticket.Category.INQUIRY, Ticket.Priority.HIGH, "note");
		assertEquals(2, b.getTicketId());
		assertEquals(a.getTicketId(), 1);
	}

	/**
	 * Test for method setCounter()
	 */
	@Test
	public void testSetCounter() {
		try {
			Ticket.setCounter(0);
		}
		catch (IllegalArgumentException e) {
			assertEquals("Ticket id must be a value greater than 0.", e.getMessage());
		}
	}

	/**
	 * Test for Ticket constructor
	 */
	@Test
	public void testTicket() {
		notes.add("note");
		Ticket a = new Ticket(3, "New", "Request", "Oh yea", "Benson", "Inquiry", "Urgent", "Ben", "Awaiting Caller", notes);
		Ticket b = new Ticket(2, "New", "Request", "Oh yea", "Benson", "Inquiry", "Urgent", "Ben", "Caller Closed", notes);
		Ticket c = new Ticket(2, "New", "Incident", "Oh yea", "Benson", "Inquiry", "Urgent", "Ben", "Duplicate", notes);
		assertEquals(a.getSubject(), "Oh yea");
		assertEquals(b.getCaller(), "Benson");
		assertEquals(c.getOwner(), "Ben");
		assertEquals(a.getTicketType(), Ticket.TicketType.REQUEST);
		assertEquals(c.getTicketTypeString(), "Incident");
		assertEquals(a.getTicketTypeString(), "Request");
	}

	/**
	 * Test for second Ticket constructor
	 */
	@Test
	public void testTicket2() {
		try {
			Ticket a = new Ticket(null, "subject", "caller", Ticket.Category.DATABASE, Ticket.Priority.HIGH, "note");
			assertEquals(a.getTicketId(), null);
		} catch (IllegalArgumentException e) {
			assertEquals("Cannot have null parameters", e.getMessage());
		}
		try {
			Ticket a = new Ticket(Ticket.TicketType.INCIDENT, "", "caller", Ticket.Category.DATABASE, Ticket.Priority.HIGH, "note");
			assertEquals(a.getSubject(), "");
		} catch (IllegalArgumentException e) {
			assertEquals("Invalid empty parameter", e.getMessage());
		}
	}

	/**
	 * Test for method setTicketId()
	 */
	@Test
	public void testSetTicketId() {
		try {
			Ticket a = new Ticket(-1, "New", "Request", "Oh yea", "Benson", "Inquiry", "Urgent", "Ben", "Caller Closed", notes);
			assertEquals(a.getTicketId(), -1);
		} catch (IllegalArgumentException e) {
			assertEquals("Ticket ID must be greater than or equal to 0", e.getMessage());
		}
	}

	/**
	 * Test for method setSubject()
	 */
	@Test
	public void testSetSubject() {
		try {
			Ticket a = new Ticket(1, "New", "Request", "", "Benson", "Inquiry", "Urgent", "Ben", "Caller Closed", notes);
			assertEquals(a.getSubject(), "");
		} catch (IllegalArgumentException e) {
			assertEquals("Invalid subject", e.getMessage());
		}
	}

	/**
	 * Test for method setCaller()
	 */
	@Test
	public void testSetCaller() {
		try {
			Ticket a = new Ticket(1, "New", "Request", "subject", "", "Inquiry", "Urgent", "Ben", "Caller Closed", notes);
			assertEquals(a.getCaller(), "");
		} catch (IllegalArgumentException e) {
			assertEquals("Invalid caller", e.getMessage());
		}	}

	/**
	 * Test for method setCancellationCode()
	 */
	@Test
	public void testSetCancellationCode() {
		Ticket a = new Ticket(3, "New", "Request", "Oh yea", "Benson", "Inquiry", "Urgent", "Ben", Command.CC_INAPPROPRIATE, notes);
		assertEquals(a.getCancellationCode(), Command.CC_INAPPROPRIATE);
	}

	/**
	 * Test for method setCategory()
	 */
	@Test
	public void testSetCategory() {
		Ticket a = new Ticket(3, "New", "Request", "Oh yea", "Benson", Ticket.C_DATABASE, "Urgent", "Ben", Command.CC_INAPPROPRIATE, notes);
		assertEquals(a.getCategory(), Ticket.C_DATABASE);
		Ticket b = new Ticket(3, "New", "Request", "Oh yea", "Benson", Ticket.C_HARDWARE, "Urgent", "Ben", Command.CC_INAPPROPRIATE, notes);
		assertEquals(b.getCategory(), Ticket.C_HARDWARE);
		Ticket c = new Ticket(3, "New", "Request", "Oh yea", "Benson", Ticket.C_SOFTWARE, "Urgent", "Ben", Command.CC_INAPPROPRIATE, notes);
		assertEquals(c.getCategory(), Ticket.C_SOFTWARE);
		Ticket g = new Ticket(3, "New", "Request", "Oh yea", "Benson", Ticket.C_INQUIRY, "Urgent", "Ben", Command.CC_INAPPROPRIATE, notes);
		assertEquals(g.getCategory(), Ticket.C_INQUIRY);
		Ticket f = new Ticket(3, "New", "Request", "Oh yea", "Benson", Ticket.C_NETWORK, "Urgent", "Ben", Command.CC_INAPPROPRIATE, notes);
		assertEquals(f.getCategory(), Ticket.C_NETWORK);
		try {
			Ticket d = new Ticket(3, "New", "Request", "Oh yea", "Benson", "", "Urgent", "Ben", Command.CC_INAPPROPRIATE, notes);
			assertEquals(d.getCategory(), "");
		} catch (IllegalArgumentException e) {
			assertEquals("Invalid category", e.getMessage());
		}
		
	}

	/**
	 * Test for method setFeedbackCode()
	 */
	@Test
	public void testSetFeedBackCode() {
		Ticket a = new Ticket(3, "New", "Request", "Oh yea", "Benson", Ticket.C_SOFTWARE, "Urgent", "Ben", Command.F_CHANGE, notes);
		assertEquals(a.getFeedbackCode(), Command.F_CHANGE);
		Ticket b = new Ticket(3, "New", "Request", "Oh yea", "Benson", Ticket.C_SOFTWARE, "Urgent", "Ben", Command.F_PROVIDER, notes);
		assertEquals(b.getFeedbackCode(), Command.F_PROVIDER);
		try {
			Ticket c = new Ticket(3, "New", "Request", "Oh yea", "Benson", Ticket.C_SOFTWARE, "Urgent", "Ben", "LOL", notes);
			assertEquals(c.getFeedbackCode(), null);
		} catch (IllegalArgumentException e) {
			assertEquals("Invalid feedback code", e.getMessage());
		}

	}

	/**
	 * Test for method setPriority()
	 */
	@Test
	public void testSetPriority() {
		Ticket a = new Ticket(3, "New", "Request", "Oh yea", "Benson", Ticket.C_SOFTWARE, "Urgent", "Ben", Command.F_CHANGE, notes);
		assertEquals(a.getPriority(), "Urgent");
		Ticket b = new Ticket(3, "New", "Request", "Oh yea", "Benson", Ticket.C_SOFTWARE, "High", "Ben", Command.F_CHANGE, notes);
		assertEquals(b.getPriority(), "High");
		Ticket c = new Ticket(3, "New", "Request", "Oh yea", "Benson", Ticket.C_SOFTWARE, "Low", "Ben", Command.F_CHANGE, notes);
		assertEquals(c.getPriority(), "Low");
		Ticket d = new Ticket(3, "New", "Request", "Oh yea", "Benson", Ticket.C_SOFTWARE, "Medium", "Ben", Command.F_CHANGE, notes);
		assertEquals(d.getPriority(), "Medium");
		try {
			Ticket e = new Ticket(3, "New", "Request", "Oh yea", "Benson", Ticket.C_SOFTWARE, "okay", "Ben", Command.F_CHANGE, notes);
			assertEquals(e.getPriority(), "Oh yea");
		} catch (IllegalArgumentException e) {
			assertEquals("Invalid priority", e.getMessage());
		}
	}

	/**
	 * Test for method setResolutionCode()
	 */
	@Test
	public void testSetResolutionCode() {
		Ticket a = new Ticket(3, "New", "Request", "Oh yea", "Benson", Ticket.C_SOFTWARE, "Urgent", "Ben", Command.RC_COMPLETED, notes);
		assertEquals(a.getResolutionCode(), "Completed");
		Ticket b = new Ticket(3, "New", "Request", "Oh yea", "Benson", Ticket.C_SOFTWARE, "Urgent", "Ben", Command.RC_NOT_COMPLETED, notes);
		assertEquals(b.getResolutionCode(), "Not Completed");
		Ticket c = new Ticket(3, "New", "Request", "Oh yea", "Benson", Ticket.C_SOFTWARE, "Urgent", "Ben", Command.RC_NOT_SOLVED, notes);
		assertEquals(c.getResolutionCode(), "Not Solved");
		Ticket d = new Ticket(3, "New", "Request", "Oh yea", "Benson", Ticket.C_SOFTWARE, "Urgent", "Ben", Command.RC_SOLVED, notes);
		assertEquals(d.getResolutionCode(), "Solved");
		Ticket e = new Ticket(3, "New", "Request", "Oh yea", "Benson", Ticket.C_SOFTWARE, "Urgent", "Ben", Command.RC_WORKAROUND, notes);
		assertEquals(e.getResolutionCode(), "Workaround");
	}

	/**
	 * Test for method setState()
	 */
	@Test
	public void testSetState() {
		Ticket a = new Ticket(3, "New", "Request", "Oh yea", "Benson", Ticket.C_SOFTWARE, "Urgent", "Ben", Command.RC_COMPLETED, notes);
		assertEquals(a.getState(), "New");
		Ticket b = new Ticket(3, "Working", "Request", "Oh yea", "Benson", Ticket.C_SOFTWARE, "Urgent", "Ben", Command.RC_COMPLETED, notes);
		assertEquals(b.getState(), "Working");
		Ticket c = new Ticket(3, "Feedback", "Request", "Oh yea", "Benson", Ticket.C_SOFTWARE, "Urgent", "Ben", Command.RC_COMPLETED, notes);
		assertEquals(c.getState(), "Feedback");
		Ticket d = new Ticket(3, "Resolved", "Request", "Oh yea", "Benson", Ticket.C_SOFTWARE, "Urgent", "Ben", Command.RC_COMPLETED, notes);
		assertEquals(d.getState(), "Resolved");
		Ticket e = new Ticket(3, "Closed", "Request", "Oh yea", "Benson", Ticket.C_SOFTWARE, "Urgent", "Ben", Command.RC_COMPLETED, notes);
		assertEquals(e.getState(), "Closed");
		Ticket f = new Ticket(3, "Canceled", "Request", "Oh yea", "Benson", Ticket.C_SOFTWARE, "Urgent", "Ben", Command.RC_COMPLETED, notes);
		assertEquals(f.getState(), "Canceled");
	}	

}

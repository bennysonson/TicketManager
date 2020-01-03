package edu.ncsu.csc216.ticket_manager.model.command;

import static org.junit.Assert.*;

import org.junit.Test;

import edu.ncsu.csc216.ticket_manager.model.command.Command.CancellationCode;
import edu.ncsu.csc216.ticket_manager.model.command.Command.CommandValue;
import edu.ncsu.csc216.ticket_manager.model.command.Command.FeedbackCode;
import edu.ncsu.csc216.ticket_manager.model.command.Command.ResolutionCode;

/**
 * Note class for Command.java
 * 
 * @author Benson Liu
 *
 */
public class CommandTest {

	/**
	 * Notes Command constructor
	 */
	@Test
	public void testCommand() {
		// Valid Command
		Command command = new Command(CommandValue.PROCESS, "bliu22", null, null, null, "Note");
		assertEquals(CommandValue.PROCESS, command.getCommand());
		assertEquals("bliu22", command.getOwnerId());
		assertEquals("Note", command.getNote());
		
		Command command2 = new Command(CommandValue.RESOLVE, "bliu22", null, ResolutionCode.CALLER_CLOSED, null, "Note");
		assertEquals(ResolutionCode.CALLER_CLOSED, command2.getResolutionCode());
		assertEquals(CommandValue.RESOLVE, command2.getCommand());

		Command command3 = new Command(CommandValue.CANCEL, "bliu22", null, null, CancellationCode.DUPLICATE, "Note");
		assertEquals(CancellationCode.DUPLICATE, command3.getCancellationCode());
		
		// Invalid Command
		try {
			command = new Command(CommandValue.PROCESS, "", null, null, null, "Note");
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals(CommandValue.PROCESS, command.getCommand());
		}

		try {
			command = new Command(CommandValue.PROCESS, null, null, null, null, "Note");
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals(CommandValue.PROCESS, command.getCommand());
		}

		try {
			command = new Command(null, "bliu22", null, null, null, "Note");
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals("Command Value cannot be null.", e.getMessage());
		}

		try {
			command = new Command(CommandValue.FEEDBACK, "bliu22", null, null, null, "Note");
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals("The Feedback command requires a feedback code", e.getMessage());
		}

		try {
			command = new Command(CommandValue.RESOLVE, "bliu22", null, null, null, "Note");
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals("The Resolve command requires a resolution code", e.getMessage());
		}

		try {
			command = new Command(CommandValue.CANCEL, "bliu22", null, null, null, "Note");
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals("The Cancel command requires a cancellation code", e.getMessage());
		}

		try {
			command = new Command(CommandValue.FEEDBACK, "bliu22", FeedbackCode.AWAITING_CALLER, null, null, "");
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals("A Command requires a note", e.getMessage());
		}

		try {
			command = new Command(CommandValue.FEEDBACK, "bliu22", FeedbackCode.AWAITING_CALLER, null, null, null);
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals("A Command requires a note", e.getMessage());
		}

	}

}

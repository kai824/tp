package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;

import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;

public class CommandResultTest {
    @Test
    public void equals() {
        CommandResult commandResult = new CommandResult("feedback");
        Person personAlice = new PersonBuilder().withName("Alice").build();
        Person personAliceCopy = new PersonBuilder().withName("Alice").build();
        Person personBob = new PersonBuilder().withName("Bob").build();

        // same values -> returns true
        assertEquals(new CommandResult("feedback"), commandResult);

        // same object -> returns true
        assertEquals(commandResult, commandResult);

        // same Person fields -> returns true
        assertEquals(CommandResult.createShowResult("feedback", personAlice),
                CommandResult.createShowResult("feedback", personAliceCopy));

        // null -> returns false
        assertNotEquals(null, commandResult);

        // different types -> returns false
        assertNotEquals(0.5f, commandResult);

        // different feedbackToUser value -> returns false
        assertNotEquals(new CommandResult("different"), commandResult);

        // different showHelp value -> returns false
        assertNotEquals(commandResult, CommandResult.createHelpResult("feedback"));

        // different exit value -> returns false
        assertNotEquals(commandResult, CommandResult.createExitResult("feedback"));

        // different Person presence -> returns false
        assertNotEquals(commandResult, CommandResult.createShowResult("feedback", personAlice));

        // different Person fields -> returns false
        assertNotEquals(CommandResult.createShowResult("feedback", personAlice),
                CommandResult.createShowResult("feedback", personBob));
    }

    @Test
    public void hashcode() {
        CommandResult commandResult = new CommandResult("feedback");
        Person personAlice = new PersonBuilder().withName("Alice").build();
        Person personAliceCopy = new PersonBuilder().withName("Alice").build();
        Person personBob = new PersonBuilder().withName("Bob").build();

        // same values -> returns same hashcode
        assertEquals(commandResult.hashCode(), new CommandResult("feedback").hashCode());

        // same Person fields -> returns same hashcode
        assertEquals(CommandResult.createShowResult("feedback", personAlice).hashCode(),
                CommandResult.createShowResult("feedback", personAliceCopy).hashCode());

        // different feedbackToUser value -> returns different hashcode
        assertNotEquals(commandResult.hashCode(), new CommandResult("different").hashCode());

        // different showHelp value -> returns different hashcode
        assertNotEquals(commandResult.hashCode(), CommandResult.createHelpResult("feedback").hashCode());

        // different exit value -> returns different hashcode
        assertNotEquals(commandResult.hashCode(), CommandResult.createExitResult("feedback").hashCode());

        // different Person presence -> returns different hashcode
        assertNotEquals(commandResult.hashCode(), CommandResult.createShowResult("feedback",
                personAlice).hashCode());

        // different Person fields -> returns different hashcode
        assertNotEquals(CommandResult.createShowResult("feedback", personAlice).hashCode(),
                CommandResult.createShowResult("feedback", personBob).hashCode());
    }

    @Test
    public void toStringMethod() {
        CommandResult commandResult = new CommandResult("feedback");
        String expected = CommandResult.class.getCanonicalName() + "{feedbackToUser="
                + commandResult.getFeedbackToUser() + ", showHelp=" + commandResult.isShowHelp()
                + ", exit=" + commandResult.isExit() + ", personToShow=" + commandResult.getPersonToShow() + "}";
        assertEquals(expected, commandResult.toString());
    }
}

package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ATTRIBUTE_NAME_GRAD_YEAR;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ATTRIBUTE_NAME_MAJOR;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ATTRIBUTE_VALUE_GRAD_YEAR;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ATTRIBUTE_VALUE_MAJOR;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.attribute.Attribute;
import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;

public class FilterCommandTest {
    private Attribute major1 = new Attribute(VALID_ATTRIBUTE_NAME_MAJOR, VALID_ATTRIBUTE_VALUE_MAJOR);
    private Attribute year1 = new Attribute(VALID_ATTRIBUTE_NAME_GRAD_YEAR, VALID_ATTRIBUTE_VALUE_GRAD_YEAR);

    // Basic idea comes from AddCommandTest.java.
    @Test
    public void constructor_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new FilterCommand(null, false));
    }

    @Test
    public void execute() throws Exception {
        Model zeroPerson = new ModelManager();
        Model onePerson = new ModelManager();
        Model twoPersons = new ModelManager();
        Person personWithMajor =
            new PersonBuilder().withName("1")
                .withAttributes(VALID_ATTRIBUTE_NAME_MAJOR, VALID_ATTRIBUTE_VALUE_MAJOR).build();
        Person personWithYear =
            new PersonBuilder().withName("2")
                .withAttributes(VALID_ATTRIBUTE_NAME_GRAD_YEAR, VALID_ATTRIBUTE_VALUE_GRAD_YEAR).build();
        onePerson.addPerson(personWithMajor);
        twoPersons.addPerson(personWithYear);
        twoPersons.addPerson(personWithMajor);

        FilterCommand year = new FilterCommand(Set.of(year1), false);
        FilterCommand yearMajor = new FilterCommand(Set.of(year1, major1), false);

        assertEquals(year.execute(zeroPerson), year.execute(zeroPerson));
        assertEquals(year.execute(zeroPerson), yearMajor.execute(zeroPerson));
        assertNotEquals(year.execute(onePerson), year.execute(twoPersons));
        CommandResult expectedResult = new CommandResult(String.format(Messages.MESSAGE_PERSONS_FILTERED_OVERVIEW, 1));
        assertEquals(year.execute(twoPersons), expectedResult);

        // With duplicates
        year = new FilterCommand(Set.of(year1), true);
        yearMajor = new FilterCommand(Set.of(year1, major1), true);

        assertEquals(year.execute(zeroPerson), year.execute(zeroPerson));
        assertEquals(year.execute(zeroPerson), yearMajor.execute(zeroPerson));
        assertNotEquals(year.execute(onePerson), year.execute(twoPersons));
        expectedResult = new CommandResult(String.format(
            FilterCommand.MESSAGE_WARNING_DUPLICATE + "\n" + Messages.MESSAGE_PERSONS_FILTERED_OVERVIEW, 1));
        assertEquals(year.execute(twoPersons), expectedResult);
    }

    @Test
    public void equals() {
        FilterCommand yearMajor = new FilterCommand(Set.of(year1, major1), false);
        FilterCommand year = new FilterCommand(Set.of(year1), false);

        // same object -> returns true
        assertTrue(yearMajor.equals(yearMajor));
        // same values -> returns true
        assertTrue(yearMajor.equals(new FilterCommand(Set.of(year1, major1), false)));
        // different types -> returns false
        assertFalse(year.equals(""));
        // different values -> returns false
        assertFalse(year.equals(yearMajor));
        // null -> returns false
        assertFalse(yearMajor.equals(null));

        FilterCommand yearDuplicate = new FilterCommand(Set.of(year1), true);
        // different duplicate state -> returns false
        assertFalse(year.equals(yearDuplicate));
    }
}

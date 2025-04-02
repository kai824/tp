package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.UndoCommand.MESSAGE_NO_LAST_CHANGE;
import static seedu.address.logic.commands.UndoCommand.MESSAGE_UNDO_SUCCESS;
import static seedu.address.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.util.Comparator;
import java.util.Optional;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.person.Person;

public class UndoCommandTest {

    private static final String LAST_COMMAND = "SAMPLE LAST COMMAND";

    private class ModelStub implements Model {
        @Override
        public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyUserPrefs getUserPrefs() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public GuiSettings getGuiSettings() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setGuiSettings(GuiSettings guiSettings) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Path getAddressBookFilePath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setAddressBookFilePath(Path addressBookFilePath) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addPerson(Person person) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setAddressBook(ReadOnlyAddressBook newData) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasPerson(Person person) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deletePerson(Person target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setPerson(Person target, Person editedPerson) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Person> getFilteredPersonList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredPersonList(Predicate<Person> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void sortFilteredPersonList(Comparator<Person> comparator) {
            throw new AssertionError("This method should not be called.");
        }
        @Override
        public void updateAlias(String attributeName, Optional<String> siteLink) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Optional<String> findMostCloseEnoughAttributeName(String target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Optional<String> findMostCloseEnoughAttributeValue(String target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Optional<Long> numOfPersonsWithNumericalValue(String attributeName) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void saveState(String commandText) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public String revertLastState() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Optional<String> getAlias(String attributeName) {
            throw new AssertionError("This method should not be called.");
        }
    }

    private class ModelStubCanRevert extends ModelStub {
        @Override
        public String revertLastState() {
            return LAST_COMMAND;
        }
    }

    private class ModelStubCannotRevert extends ModelStub {
        @Override
        public String revertLastState() {
            throw new IllegalStateException("Illegal state.");
        }
    }

    @Test
    public void execute_undo_test() {
        Command command = new UndoCommand();
        Model modelCannotRevert = new ModelStubCannotRevert();

        assertThrows(CommandException.class, MESSAGE_NO_LAST_CHANGE, () -> command.execute(modelCannotRevert));

        Model modelCanRevert = new ModelStubCanRevert();
        CommandResult expectedCommandResult = new CommandResult(String.format(MESSAGE_UNDO_SUCCESS, LAST_COMMAND));
        assertCommandSuccess(new UndoCommand(), modelCanRevert, expectedCommandResult, modelCanRevert);
    }
}

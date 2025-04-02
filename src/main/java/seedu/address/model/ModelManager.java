package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.Comparator;
import java.util.Optional;
import java.util.Stack;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.util.Pair;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.person.Person;

/**
 * Represents the in-memory model of the address book data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final AddressBook addressBook;
    private final UserPrefs userPrefs;
    private final FilteredList<Person> filteredPersons;

    private final Stack<Pair<AddressBook, String> > previousStates;

    /**
     * Initializes a ModelManager with the given addressBook and userPrefs.
     */
    public ModelManager(ReadOnlyAddressBook addressBook, ReadOnlyUserPrefs userPrefs) {
        requireAllNonNull(addressBook, userPrefs);

        logger.fine("Initializing with address book: " + addressBook + " and user prefs " + userPrefs);

        this.addressBook = new AddressBook(addressBook);
        this.userPrefs = new UserPrefs(userPrefs);
        this.filteredPersons = new FilteredList<>(this.addressBook.getPersonList());

        this.previousStates = new Stack<>();
    }

    public ModelManager() {
        this(new AddressBook(), new UserPrefs());
    }

    //=========== UserPrefs ==================================================================================

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        return userPrefs;
    }

    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        requireNonNull(userPrefs);
        this.userPrefs.resetData(userPrefs);
    }

    @Override
    public GuiSettings getGuiSettings() {
        return userPrefs.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        userPrefs.setGuiSettings(guiSettings);
    }

    @Override
    public Path getAddressBookFilePath() {
        return userPrefs.getAddressBookFilePath();
    }

    @Override
    public void setAddressBookFilePath(Path addressBookFilePath) {
        requireNonNull(addressBookFilePath);
        userPrefs.setAddressBookFilePath(addressBookFilePath);
    }

    //=========== AddressBook ================================================================================

    @Override
    public ReadOnlyAddressBook getAddressBook() {
        return addressBook;
    }

    @Override
    public void setAddressBook(ReadOnlyAddressBook addressBook) {
        this.addressBook.resetData(addressBook);
    }

    @Override
    public boolean hasPerson(Person person) {
        requireNonNull(person);
        return addressBook.hasPerson(person);
    }

    @Override
    public void deletePerson(Person target) {
        addressBook.removePerson(target);
    }

    @Override
    public void addPerson(Person person) {
        addressBook.addPerson(person);
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
    }

    @Override
    public void setPerson(Person target, Person editedPerson) {
        requireAllNonNull(target, editedPerson);

        addressBook.setPerson(target, editedPerson);
    }

    @Override
    public void updateAlias(String attributeName, Optional<String> siteLink) {
        addressBook.updateAlias(attributeName, siteLink);
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
    }

    public Optional<String> getAlias(String attributeName) {
        return addressBook.getAlias(attributeName);
    }

    //=========== Filtered Person List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Person} backed by the internal list of
     * {@code versionedAddressBook}
     */
    @Override
    public ObservableList<Person> getFilteredPersonList() {
        return filteredPersons;
    }

    @Override
    public void updateFilteredPersonList(Predicate<Person> predicate) {
        requireNonNull(predicate);

        // Supplies a different predicate object so that the GUI updates
        filteredPersons.setPredicate(x -> false);
        filteredPersons.setPredicate(predicate);
    }

    /**
     * Sorts filteredPerson with a given comparator.
     */
    @Override
    public void sortFilteredPersonList(Comparator<Person> comparator) {
        addressBook.sortPersons(comparator);
    }

    @Override
    public Optional<String> findMostCloseEnoughAttributeName(String target) {
        return addressBook.findMostCloseEnoughAttributeName(target);
    }

    @Override
    public Optional<String> findMostCloseEnoughAttributeValue(String target) {
        return addressBook.findMostCloseEnoughAttributeValue(target);
    }

    @Override
    public Optional<Long> numOfPersonsWithNumericalValue(String attributeName) {

        long countNumerical = filteredPersons.stream().filter(person ->
                person.getAttribute(attributeName).isPresent()
                        && person.getAttribute(attributeName).get().hasNumericalValue()).count();

        long countHaAttribute = filteredPersons.stream()
                .filter(person -> person.getAttribute(attributeName).isPresent()).count();
        if (countNumerical == countHaAttribute) {
            return Optional.empty();
        } else {
            return Optional.of(countNumerical);
        }
    }

    @Override
    public String revertLastState() {
        // Ignore previous state if no change happened since. Happens if last command doesn't change any data
        while (!previousStates.isEmpty() && previousStates.peek().getKey().equals(addressBook)) {
            previousStates.pop();
        }
        if (previousStates.isEmpty()) {
            throw new IllegalStateException("No previous state found");
        }
        Pair<AddressBook, String> currentStateWithCommand = previousStates.pop();

        assert currentStateWithCommand != null;
        assert currentStateWithCommand.getKey() != null;
        assert currentStateWithCommand.getValue() != null;

        addressBook.resetData(currentStateWithCommand.getKey());
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return currentStateWithCommand.getValue();
    }

    @Override
    public void saveState(String commandText) {
        if (!previousStates.isEmpty()) {
            if (previousStates.peek().getKey().equals(addressBook)) {
                // Avoid saving duplicate states. If it happens, only store it with latest commandText
                previousStates.pop();
            }
        }
        previousStates.push(new Pair<>(new AddressBook(addressBook), commandText));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ModelManager otherModelManager)) {
            return false;
        }

        return addressBook.equals(otherModelManager.addressBook)
                && userPrefs.equals(otherModelManager.userPrefs)
                && filteredPersons.equals(otherModelManager.filteredPersons);
    }
}

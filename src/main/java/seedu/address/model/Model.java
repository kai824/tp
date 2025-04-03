package seedu.address.model;

import java.nio.file.Path;
import java.util.Comparator;
import java.util.Optional;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.model.person.Person;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<Person> PREDICATE_SHOW_ALL_PERSONS = unused -> true;

    /**
     * Replaces user prefs data with the data in {@code userPrefs}.
     */
    void setUserPrefs(ReadOnlyUserPrefs userPrefs);

    /**
     * Returns the user prefs.
     */
    ReadOnlyUserPrefs getUserPrefs();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Sets the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);

    /**
     * Returns the user prefs' address book file path.
     */
    Path getAddressBookFilePath();

    /**
     * Sets the user prefs' address book file path.
     */
    void setAddressBookFilePath(Path addressBookFilePath);

    /**
     * Replaces address book data with the data in {@code addressBook}.
     */
    void setAddressBook(ReadOnlyAddressBook addressBook);

    /** Returns the AddressBook */
    ReadOnlyAddressBook getAddressBook();

    /**
     * Returns true if a person with the same identity as {@code person} exists in the address book.
     */
    boolean hasPerson(Person person);

    /**
     * Deletes the given person.
     * The person must exist in the address book.
     */
    void deletePerson(Person target);

    /**
     * Adds the given person.
     * {@code person} must not already exist in the address book.
     */
    void addPerson(Person person);

    /**
     * Replaces the given person {@code target} with {@code editedPerson}.
     * {@code target} must exist in the address book.
     * The person identity of {@code editedPerson} must not be the same as another existing person in the address book.
     */
    void setPerson(Person target, Person editedPerson);

    /**
     * Updates an alias mapping for {@code attributeName} with {@code siteLink}.
     * To remove the currently existing site link, set {@code siteLink} to empty.
     */
    void updateAlias(String attributeName, Optional<String> siteLink);

    /**
     * Returns a site link mapped to the given {@code attributeName}.
     * If there is no mapping currently, an empty Optional will be returned.
     */
    Optional<String> getAlias(String attributeName);

    /** Returns an unmodifiable view of the filtered person list */
    ObservableList<Person> getFilteredPersonList();

    /**
     * Updates the filter of the filtered person list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredPersonList(Predicate<Person> predicate);

    /**
     * Sorts the filtered person list by the given {@code comparator}.
     * @throws NullPointerException if {@code comparator} is null.
     */
    void sortFilteredPersonList(Comparator<Person> comparator);

    /**
     * Returns the closest matching existing attribute name, given a {@code target} string.
     * An empty Optional will be returned if there is no name close enough.
     */
    Optional<String> autocorrectAttributeName(String target);

    /**
     * Returns the closest matching existing attribute value, given a {@code target} string.
     * An empty Optional will be returned if there is no value close enough.
     */
    Optional<String> autocorrectAttributeValue(String target);

    /**
     * Returns the number of persons in the address book
     * with a numerical value for the given {@code attributeName}.
     * This method should only be called after a sort-num is applied to the saved list.
     * An empty Optional will be returned if all persons have a numerical value.
     */
    Optional<Long> numOfPersonsWithNumericalValue(String attributeName);

    /**
     * Returns the number of persons in the address book with the attribute {@code attributeName}.
     * This method should only be called after a sort or sort-num operation is applied to the saved list.
     * An empty Optional will be returned if all persons have the specified attribute.
     */
    Optional<Long> numOfPersonsWithAttribute(String attributeName);

    /**
     * Saves the current AddressBook into the history
     */
    void saveState(String commandText);

    /**
     * Reverts the AddressBook to the last saved state. Throws IllegalStateException if no previous state.
     *
     * @return String associated to data change that was successfully reverted.
     */
    String revertLastState();
}

package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.attribute.AliasMappingList;
import seedu.address.model.attribute.Attribute;
import seedu.address.model.attribute.AutoCorrectionUtil;
import seedu.address.model.person.Person;
import seedu.address.model.person.UniquePersonList;

/**
 * Wraps all data at the address-book level
 * Duplicates are not allowed (by .isSamePerson comparison)
 */
public class AddressBook implements ReadOnlyAddressBook {

    private final UniquePersonList persons;
    private final AliasMappingList aliasMappings;

    /*
     * The 'unusual' code block below is a non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        persons = new UniquePersonList();
        aliasMappings = new AliasMappingList();
    }

    public AddressBook() {}

    /**
     * Creates an AddressBook using the Persons in the {@code toBeCopied}
     */
    public AddressBook(ReadOnlyAddressBook toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// update aliasing

    /**
     * Updates site links in the {@code person}'s attributes, based on the current aliasMappings.
     */
    private void updateAliasingsForPerson(Person person) {
        Set<Attribute> updatedAttributes =
            person.getAttributes().stream()
                .map(attribute -> attribute.updateSiteLink(aliasMappings.getAlias(attribute.getAttributeName())))
                .collect(Collectors.toSet());

        updatedAttributes.forEach(person::updateAttribute);
    }

    /**
     * Updates site links for each Person in this address book.
     */
    private void updateAliasingsForAllPersons() {
        persons.asUnmodifiableObservableList().forEach(this::updateAliasingsForPerson);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the person list with {@code persons}.
     * {@code persons} must not contain duplicate persons.
     */
    public void setPersons(List<Person> persons) {
        this.persons.setPersons(persons);
        updateAliasingsForAllPersons();
    }

    /**
     * Replaces the alias mappings with the given {@code mappings};
     */
    public void setAliasMappings(ObservableMap<String, String> mappings) {
        this.aliasMappings.setAliases(mappings);
        updateAliasingsForAllPersons();
    }

    /**
     * Resets the existing data of this {@code AddressBook} with {@code newData}.
     */
    public void resetData(ReadOnlyAddressBook newData) {
        requireNonNull(newData);

        setPersons(newData.getPersonList());
        setAliasMappings(newData.getAliases());
        updateAliasingsForAllPersons();
    }

    //// person-level operations

    /**
     * Returns true if a person with the same identity as {@code person} exists in the address book.
     */
    public boolean hasPerson(Person person) {
        requireNonNull(person);
        return persons.contains(person);
    }

    /**
     * Adds a person to the address book.
     * The person must not already exist in the address book.
     */
    public void addPerson(Person p) {
        persons.add(p);
        updateAliasingsForPerson(p);
    }

    /**
     * Replaces the given person {@code target} in the list with {@code editedPerson}.
     * {@code target} must exist in the address book.
     * The person identity of {@code editedPerson} must not be the same as another existing person in the address book.
     */
    public void setPerson(Person target, Person editedPerson) {
        requireNonNull(editedPerson);

        persons.setPerson(target, editedPerson);
        updateAliasingsForPerson(editedPerson);
    }

    /**
     * Sorts persons in the AddressBook by the given {@code comparator}.
     */

    public void sortPersons(Comparator<Person> comparator) {
        requireNonNull(comparator);

        persons.sortPersons(comparator);
    }

    /**
     * Removes {@code key} from this {@code AddressBook}.
     * {@code key} must exist in the address book.
     */
    public void removePerson(Person key) {
        persons.remove(key);
    }

    //// alias-level operations

    /**
     * Updates an alias mapping for {@code attributeName} with {@code siteLink}.
     * To remove the currently existing site link, set {@code siteLink} to empty.
     */
    public void updateAlias(String attributeName, Optional<String> siteLink) {
        aliasMappings.updateAlias(attributeName, siteLink);
        updateAliasingsForAllPersons();
    }

    /**
     * Returns a site link mapped to the given {@code attributeName}.
     * If there is no mapping currently, an empty Optional will be returned.
     */
    public Optional<String> getAlias(String attributeName) {
        return aliasMappings.getAlias(attributeName);
    }

    //// attribute level operations

    private ObservableList<Attribute> getAttributes() {
        return persons.asUnmodifiableObservableList().stream()
            .flatMap(person -> person.getAttributes().stream())
            .collect(Collectors.collectingAndThen(Collectors.toList(), FXCollections::observableArrayList));
    }

    /**
     * Returns the closest matching existing attribute name, given a {@code target} string.
     * An empty Optional will be returned if there is no name close enough.
     */
    public Optional<String> findMostCloseEnoughAttributeName(String target) {
        return AutoCorrectionUtil.findMostCloseEnoughAttributeName(getAttributes(), target);
    }

    /**
     * Returns the closest matching existing attribute value, given a {@code target} string.
     * An empty Optional will be returned if there is no value close enough.
     */
    public Optional<String> findMostCloseEnoughAttributeValue(String target) {
        return AutoCorrectionUtil.findMostCloseEnoughAttributeValue(getAttributes(), target);
    }

    //// util methods

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("persons", persons)
                .toString();
    }

    @Override
    public ObservableList<Person> getPersonList() {
        return persons.asUnmodifiableObservableList();
    }

    @Override
    public ObservableMap<String, String> getAliases() {
        return this.aliasMappings.getUnmodifiableAliases();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AddressBook)) {
            return false;
        }

        AddressBook otherAddressBook = (AddressBook) other;
        return persons.equals(otherAddressBook.persons) && aliasMappings.equals(otherAddressBook.aliasMappings);
    }

    @Override
    public int hashCode() {
        return persons.hashCode();
    }
}

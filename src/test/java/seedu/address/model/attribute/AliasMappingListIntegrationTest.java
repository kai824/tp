package seedu.address.model.attribute;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.Test;

import seedu.address.model.AddressBook;
import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;

/**
 * Contains integration tests (interaction with the AddressBook) for {@code AliasMappingList}.
 */
public class AliasMappingListIntegrationTest {
    @Test
    void addPerson_success() {
        AddressBook book = new AddressBook();
        book.addPerson(new PersonBuilder().withAttributes("gitHUB", "hoge").build());
        Person added = book.getPersonList().get(0);
        assertTrue(added.getAttribute("github").isPresent());
        assertTrue(added.getAttribute("github").get().hasSiteLink());
    }
    @Test
    void setPerson_success() {
        AddressBook book = new AddressBook();
        book.addPerson(new PersonBuilder().build());
        book.setPerson(new PersonBuilder().build(), new PersonBuilder().withAttributes("gitHUB", "hoge").build());
        Person added = book.getPersonList().get(0);
        assertTrue(added.getAttribute("github").isPresent());
        assertTrue(added.getAttribute("github").get().hasSiteLink());
    }
    @Test
    void updateAlias_sucess() {
        AddressBook book = new AddressBook();
        book.addPerson(new PersonBuilder().withAttributes("gitHUB", "hoge").build());
        book.updateAlias("github", Optional.empty());
        Person added = book.getPersonList().get(0);
        assertTrue(added.getAttribute("github").isPresent());
        assertFalse(added.getAttribute("github").get().hasSiteLink());
    }
}

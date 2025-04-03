package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import javafx.collections.ObservableMap;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.ReadOnlyAddressBook;

public class AliasCommandTest {
    @Test
    void execute_removeAlias_success() throws Exception {
        AliasCommand command = AliasCommand.removeAliasCommand("github");
        Model model = new ModelManager();
        command.execute(model);
        ReadOnlyAddressBook book = model.getAddressBook();
        ObservableMap<String, String> mappings = book.getAliases();
        assertFalse(mappings.containsKey("github"));
        assertTrue(mappings.containsKey("linkedin"));
    }

    @Test
    void execute_addAlias_success() throws Exception {
        String name1 = "k3209g09krr";
        String link1 = "https://404-not-found";
        AliasCommand command = AliasCommand.addAliasCommand(name1, link1);
        Model model = new ModelManager();
        command.execute(model);
        ReadOnlyAddressBook book = model.getAddressBook();
        ObservableMap<String, String> mappings = book.getAliases();
        assertTrue(mappings.containsKey(name1));
        assertTrue(mappings.get(name1).equals(link1));
    }
}

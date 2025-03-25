package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import seedu.address.model.person.Person;

/**
 * Panel containing the details of a Person.
 */
public class PersonDetailsPanel extends UiPart<Region> {

    private static final String FXML = "PersonDetailsPanel.fxml";

    @FXML
    private StackPane personDetailsPlaceholder;

    @FXML
    private Label placeholderLabel;

    private PersonDetailsCard personDetailsCard;

    public PersonDetailsPanel() {
        super(FXML);
    }

    /**
     * Updates the person details panel with the given person.
     * If person is {@code null}, shows the placeholder text instead.
     */
    public void showPerson(Person person) {
        personDetailsPlaceholder.getChildren().clear();
        personDetailsPlaceholder.getChildren()
                .add(person != null ? new PersonDetailsCard(person).getRoot() : placeholderLabel);
    }
}

package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.Region;

/**
 * Panel containing the details of a Person.
 */
public class PersonDetailsPanel extends UiPart<Region> {

    private static final String FXML = "PersonDetailsPanel.fxml";

    @FXML
    private StackPane personDetailsPlaceholder;

    public PersonDetailsPanel() {
        super(FXML);
    }
}
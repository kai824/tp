package seedu.address.ui;

import java.util.Comparator;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import seedu.address.model.attribute.Attribute;
import seedu.address.model.person.Person;

/**
 * An UI component that displays detailed information of a {@code Person}.
 */
public class PersonDetailsCard extends UiPart<Region> {

    private static final String FXML = "PersonDetailsCard.fxml";

    public final Person person;

    @FXML
    private VBox detailsPane;
    @FXML
    private Label name;
    @FXML
    private Label phone;
    @FXML
    private Label email;
    @FXML
    private FlowPane tags;
    @FXML
    private FlowPane attributes;

    /**
     * Creates a {@code PersonDetailsCard} with the given {@code Person}.
     */
    public PersonDetailsCard(Person person) {
        super(FXML);
        this.person = person;
        name.setText(person.getName().fullName);
        phone.setText(person.getPhone().value);
        email.setText(person.getEmail().value);
        person.getTags().stream()
                .sorted(Comparator.comparing(tag -> tag.tagName))
                .forEach(tag -> {
                    Label tagLabel = new Label(tag.tagName);
                    tagLabel.getStyleClass().add("tag-details");
                    tags.getChildren().add(tagLabel);
                });
        person.getAttributes().stream()
                .sorted(Comparator.comparing(Attribute::getAttributeName))
                .forEach(attribute -> {
                    Label attributeLabel = new Label(attribute.getDisplayText());
                    attributeLabel.getStyleClass().add("attribute-details");
                    attributes.getChildren().add(attributeLabel);
                });
    }
}

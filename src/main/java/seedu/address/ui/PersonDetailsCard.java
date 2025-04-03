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
        makeLabelCopyable(phone, "Phone number");
        email.setText(person.getEmail().value);
        makeLabelCopyable(email, "Email");
        person.getTags().stream()
                .sorted(Comparator.comparing(tag -> tag.tagName))
                .forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
        person.getAttributes().stream()
                .sorted()
                .forEach(attribute -> attributes.getChildren().add(createAttributeLabel(attribute)));
    }

    private static Label createAttributeLabel(Attribute attribute) {
        Label label = new Label(attribute.getDisplayText());
        if (attribute.hasSiteLink()) {
            label.setId("site-attribute-details");
            label.setOnMouseClicked(event -> {
                String link = attribute.getSiteLink().orElseThrow(() ->
                        new AssertionError("Unreachable, Optional<String> siteLink should not be empty"))
                        + attribute.getAttributeValue();
                assert !link.isBlank() : "Link should not be blank";
                UiUtils.browse(link);
            });
        }
        return label;
    }

    /**
     * Makes the {@code Label} clickable. Clicking on it copies its text.
     *
     * @param type The type of text that is copied. This affects the message in the alert box.
     */
    private void makeLabelCopyable(Label label, String type) {
        label.setOnMouseClicked(event -> {
            UiUtils.copyText(label.getText());
            UiUtils.showInformationDialog(type + " copied", type + " has been copied to your clipboard.");
        });
        label.setId("copyable-label");
    }
}

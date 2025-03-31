package seedu.address.ui;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.person.Person;

/**
 * Panel containing the list of persons.
 */
public class PersonListPanel extends UiPart<Region> {
    private static final String FXML = "PersonListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(PersonListPanel.class);

    @FXML
    private ListView<Person> personListView;

    private Person lastShownPerson;
    private int lastShownIndex;

    /**
     * Creates a {@code PersonListPanel} with the given {@code ObservableList}.
     */
    public PersonListPanel(ObservableList<Person> personList) {
        super(FXML);
        personListView.setItems(personList);
        personListView.setCellFactory(listView -> new PersonListViewCell());

        lastShownPerson = null;
        lastShownIndex = -1;
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Person} using a {@code PersonCard}.
     */
    class PersonListViewCell extends ListCell<Person> {
        @Override
        protected void updateItem(Person person, boolean empty) {
            super.updateItem(person, empty);

            if (empty || person == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new PersonCard(person, getIndex() + 1).getRoot());
            }
        }
    }

    public ListView<Person> getListView() {
        return personListView;
    }

    /**
     * Shows the person in the details panel.
     */
    public void showPerson(Person person) {
        personListView.scrollTo(person);
        personListView.getSelectionModel().select(person);

        lastShownPerson = person;
        lastShownIndex = personListView.getSelectionModel().getSelectedIndex();
    }

    /**
     * Show last shown person.
     */
    public void showLastPerson() {
        ObservableList<Person> list = personListView.getItems();
        assert list != null;

        if (lastShownPerson != null) {
            if (list.contains(lastShownPerson)) {
                showPerson(lastShownPerson);
            } else if (lastShownIndex < list.size() && lastShownIndex >= 0) {
                showPerson(list.get(lastShownIndex));
            } else {
                resetLastShown();
            }
        } else {
            resetLastShown();
        }
    }

    /**
     * Updates data about last shown person.
     */
    public void updateLastShown() {
        lastShownPerson = personListView.getSelectionModel().getSelectedItem();
        lastShownIndex = personListView.getSelectionModel().getSelectedIndex();
    }

    /**
     * Reset data about last shown person, and deselects any currently selected Person.
     */
    private void resetLastShown() {
        lastShownPerson = null;
        lastShownIndex = -1;
        personListView.getSelectionModel().clearSelection();
    }

}

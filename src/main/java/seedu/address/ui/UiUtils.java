package seedu.address.ui;

import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;

/**
 * A utility class for Ui-related methods.
 */
public class UiUtils {
    /**
     * Displays an alert dialog indicating an error occurred while attempting to open the specified link.
     *
     * @param link The link that could not be opened.
     */
    public static void showErrorOpeningLink(String link) {
        // Solution below adapted from
        // https://stackoverflow.com/questions/45620901/javafx-copy-text-from-alert
        TextArea textArea = new TextArea("There was an error in opening the link: " + link);
        textArea.setEditable(false);
        textArea.setWrapText(true);
        GridPane gridPane = new GridPane();
        gridPane.setMaxWidth(Double.MAX_VALUE);
        gridPane.add(textArea, 0, 0);

        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.getDialogPane().setContent(gridPane);
        alert.showAndWait();
    }
}

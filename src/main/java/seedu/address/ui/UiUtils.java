package seedu.address.ui;

import java.awt.Desktop;
import java.net.URI;
import java.util.logging.Logger;

import com.sun.javafx.PlatformUtil;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.stage.Stage;
import seedu.address.commons.core.LogsCenter;

/**
 * A utility class for Ui-related methods.
 */
public class UiUtils {
    private static final Logger logger = LogsCenter.getLogger(UiUtils.class);

    /**
     * Opens the link in the user's default browser.
     * If the link could not be opened (most likely because the user is on Linux/Unix), it is copied instead
     * and a dialog opens to notify the user that the link has been copied.
     *
     * @param link The link to open.
     */
    public static void browse(String link) {
        logger.info(String.format("Attempting to browse (%s).", link));

        // Certain Linux/Unix distributions freeze when Desktop::browse is called, even though
        // they return true for Desktop::isSupported(Desktop.Action.BROWSE)
        if (Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)
                && !PlatformUtil.isLinux() && !PlatformUtil.isUnix()) {
            logger.info("Browsing is supported.");

            try {
                Desktop.getDesktop().browse(new URI(link));
                logger.info(String.format("Link (%s) has successfully opened.", link));
            } catch (Exception e) { // many possible exceptions here, so catch-all is used
                logger.info(String.format("Copying the link (%s) as it failed to open.", link));
                copyLinkAndShowDialog(link);
            }
        } else {
            logger.info(String.format("Copying the link (%s) as browsing is not supported.", link));
            copyLinkAndShowDialog(link);
        }
    }

    /**
     * Copies the link and displays a dialog indicating that the link has been copied to the system clipboard.
     *
     * @param link The link to copy.
     */
    private static void copyLinkAndShowDialog(String link) {
        final Clipboard clipboard = Clipboard.getSystemClipboard();
        final ClipboardContent url = new ClipboardContent();
        url.putString(link);
        clipboard.setContent(url);

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText("Link copied");
        alert.setContentText("The link has been copied to your clipboard.");

        // Solution below adapted from
        // https://stackoverflow.com/questions/27976345/how-do-you-set-the-icon-of-a-dialog-control-java-fx-java-8
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image("/images/address_book_32.png"));

        alert.showAndWait();
    }
}

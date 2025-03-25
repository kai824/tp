package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.Objects;
import java.util.Optional;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.person.Person;

/**
 * Represents the result of a command execution.
 */
public class CommandResult {

    private final String feedbackToUser;

    /** Help information should be shown to the user. */
    private final boolean showHelp;

    /** The application should exit. */
    private final boolean exit;

    /** The person to show. */
    private final Optional<Person> personToShow;

    /**
     * Constructs a {@code CommandResult} with the specified fields.
     */
    private CommandResult(String feedbackToUser, boolean showHelp, boolean exit, Optional<Person> personToShow) {
        this.feedbackToUser = requireNonNull(feedbackToUser);
        this.showHelp = showHelp;
        this.exit = exit;
        this.personToShow = personToShow;
    }

    /**
     * Constructs a {@code CommandResult} with the specified {@code feedbackToUser},
     * and other fields set to their default value.
     */
    public CommandResult(String feedbackToUser) {
        this(feedbackToUser, false, false, Optional.empty());
    }

    /**
     * Returns a {@code CommandResult} with the specified {@code feedbackToUser} and {@code personToShow}.
     */
    public static CommandResult createShowResult(String feedbackToUser, Person personToShow) {
        return new CommandResult(feedbackToUser, false, false, Optional.of(personToShow));
    }

    /**
     * Returns a {@code CommandResult} with the specified {@code feedbackToUser}
     * and with {@code showHelp} set to {@code true}.
     */
    public static CommandResult createHelpResult(String feedbackToUser) {
        return new CommandResult(feedbackToUser, true, false, Optional.empty());
    }

    /**
     * Returns a {@code CommandResult} with the specified {@code feedbackToUser}
     * and with {@code exit} set to {@code true}.
     */
    public static CommandResult createExitResult(String feedbackToUser) {
        return new CommandResult(feedbackToUser, false, true, Optional.empty());
    }

    public String getFeedbackToUser() {
        return feedbackToUser;
    }

    public boolean isShowHelp() {
        return showHelp;
    }

    public boolean isExit() {
        return exit;
    }

    public Optional<Person> getPersonToShow() {
        return personToShow;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (other instanceof CommandResult otherCommandResult) {
            return feedbackToUser.equals(otherCommandResult.feedbackToUser)
                    && showHelp == otherCommandResult.showHelp
                    && exit == otherCommandResult.exit
                    && Objects.equals(personToShow, otherCommandResult.personToShow);
        }

        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(feedbackToUser, showHelp, exit, personToShow);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("feedbackToUser", feedbackToUser)
                .add("showHelp", showHelp)
                .add("exit", exit)
                .toString();
    }

}

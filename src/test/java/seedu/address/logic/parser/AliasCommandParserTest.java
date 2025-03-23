package seedu.address.logic.parser;

import static seedu.address.logic.commands.CommandTestUtil.ATTRIBUTE_MAJOR;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ATTRIBUTE_NAME_MAJOR;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ATTRIBUTE_VALUE_MAJOR;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ATTRIBUTE;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.AliasCommand;

public class AliasCommandParserTest {
    private final AliasCommandParser parser = new AliasCommandParser();
    @Test
    void parse_removeAlias_success() throws Exception {
        assertParseSuccess(parser, ATTRIBUTE_MAJOR,
            new AliasCommand(VALID_ATTRIBUTE_NAME_MAJOR, VALID_ATTRIBUTE_VALUE_MAJOR));
        assertParseSuccess(parser, " " + PREFIX_ATTRIBUTE.toString() + "github=",
            new AliasCommand("github", ""));
    }

    @Test
    void parse_addAlias_success() throws Exception {
        String name1 = "k3209g09krr";
        String link1 = "https://404-not-found";
        assertParseSuccess(parser, " " + PREFIX_ATTRIBUTE.toString() + name1 + "=" + link1,
            new AliasCommand(name1, link1));
    }
}

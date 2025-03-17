package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.parser.ParserUtil.MESSAGE_INVALID_INDEX;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.attribute.Attribute;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Tag;

public class ParserUtilTest {
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_PHONE = "+651234";
    private static final String INVALID_EMAIL = "example.com";
    private static final String INVALID_TAG = "/friend";

    private static final String VALID_NAME = "Rachel Walker";
    private static final String VALID_PHONE = "123456";
    private static final String VALID_EMAIL = "rachel@example.com";
    private static final String VALID_TAG_1 = "C++";
    private static final String VALID_TAG_2 = "Supply chain";

    private static final String VALID_ATTRIBUTE_NAME = "Graduation Year";
    private static final String VALID_ATTRIBUTE_VALUE = "2027";
    private static final String VALID_ATTRIBUTE = "Graduation Year=2027";
    private static final String VALID_ATTRIBUTE_ALT = "Graduation Year=2028";
    private static final String INVALID_ATTRIBUTE_WITH_INVALID_NAME = "Graduation/Year=2027";
    private static final String INVALID_ATTRIBUTE_WITH_INVALID_VALUE = "Graduation Year=2027\\";
    private static final String INVALID_ATTRIBUTE_NO_EQUALS = "Graduation Year: 2027";
    private static final String INVALID_ATTRIBUTE_MULTIPLE_EQUALS = "Graduation=Year=2027";

    private static final String WHITESPACE = " \t\r\n";

    @Test
    public void parseIndex_invalidInput_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseIndex("10 a"));
    }

    @Test
    public void parseIndex_outOfRangeInput_throwsParseException() {
        assertThrows(ParseException.class, MESSAGE_INVALID_INDEX, ()
            -> ParserUtil.parseIndex(Long.toString(Integer.MAX_VALUE + 1)));
    }

    @Test
    public void parseIndex_validInput_success() throws Exception {
        // No whitespaces
        assertEquals(INDEX_FIRST_PERSON, ParserUtil.parseIndex("1"));

        // Leading and trailing whitespaces
        assertEquals(INDEX_FIRST_PERSON, ParserUtil.parseIndex("  1  "));
    }

    @Test
    public void parseName_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseName(null));
    }

    @Test
    public void parseName_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseName(INVALID_NAME));
    }

    @Test
    public void parseName_validValueWithoutWhitespace_returnsName() throws Exception {
        Name expectedName = new Name(VALID_NAME);
        assertEquals(expectedName, ParserUtil.parseName(VALID_NAME));
    }

    @Test
    public void parseName_validValueWithWhitespace_returnsTrimmedName() throws Exception {
        String nameWithWhitespace = WHITESPACE + VALID_NAME + WHITESPACE;
        Name expectedName = new Name(VALID_NAME);
        assertEquals(expectedName, ParserUtil.parseName(nameWithWhitespace));
    }

    @Test
    public void parsePhone_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parsePhone(null));
    }

    @Test
    public void parsePhone_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parsePhone(INVALID_PHONE));
    }

    @Test
    public void parsePhone_validValueWithoutWhitespace_returnsPhone() throws Exception {
        Phone expectedPhone = new Phone(VALID_PHONE);
        assertEquals(expectedPhone, ParserUtil.parsePhone(VALID_PHONE));
    }

    @Test
    public void parsePhone_validValueWithWhitespace_returnsTrimmedPhone() throws Exception {
        String phoneWithWhitespace = WHITESPACE + VALID_PHONE + WHITESPACE;
        Phone expectedPhone = new Phone(VALID_PHONE);
        assertEquals(expectedPhone, ParserUtil.parsePhone(phoneWithWhitespace));
    }

    @Test
    public void parseEmail_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseEmail(null));
    }

    @Test
    public void parseEmail_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseEmail(INVALID_EMAIL));
    }

    @Test
    public void parseEmail_validValueWithoutWhitespace_returnsEmail() throws Exception {
        Email expectedEmail = new Email(VALID_EMAIL);
        assertEquals(expectedEmail, ParserUtil.parseEmail(VALID_EMAIL));
    }

    @Test
    public void parseEmail_validValueWithWhitespace_returnsTrimmedEmail() throws Exception {
        String emailWithWhitespace = WHITESPACE + VALID_EMAIL + WHITESPACE;
        Email expectedEmail = new Email(VALID_EMAIL);
        assertEquals(expectedEmail, ParserUtil.parseEmail(emailWithWhitespace));
    }

    @Test
    public void parseTag_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseTag(null));
    }

    @Test
    public void parseTag_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseTag(INVALID_TAG));
    }

    @Test
    public void parseTag_validValueWithoutWhitespace_returnsTag() throws Exception {
        Tag expectedTag = new Tag(VALID_TAG_1);
        assertEquals(expectedTag, ParserUtil.parseTag(VALID_TAG_1));
    }

    @Test
    public void parseTag_validValueWithWhitespace_returnsTrimmedTag() throws Exception {
        String tagWithWhitespace = WHITESPACE + VALID_TAG_1 + WHITESPACE;
        Tag expectedTag = new Tag(VALID_TAG_1);
        assertEquals(expectedTag, ParserUtil.parseTag(tagWithWhitespace));
    }

    @Test
    public void parseTags_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseTags(null));
    }

    @Test
    public void parseTags_collectionWithInvalidTags_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseTags(Arrays.asList(VALID_TAG_1, INVALID_TAG)));
    }

    @Test
    public void parseTags_emptyCollection_returnsEmptySet() throws Exception {
        assertTrue(ParserUtil.parseTags(Collections.emptyList()).isEmpty());
    }

    @Test
    public void parseTags_collectionWithValidTags_returnsTagSet() throws Exception {
        Set<Tag> actualTagSet = ParserUtil.parseTags(Arrays.asList(VALID_TAG_1, VALID_TAG_2));
        Set<Tag> expectedTagSet = new HashSet<>(Arrays.asList(new Tag(VALID_TAG_1), new Tag(VALID_TAG_2)));

        assertEquals(expectedTagSet, actualTagSet);
    }

    // Claude 3.7 Sonnet was used to generate the following tests for parsing attributes

    @Test
    public void parseAttribute_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseAttribute(null));
    }

    @Test
    public void parseAttribute_invalidFormatNoEquals_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseAttribute(INVALID_ATTRIBUTE_NO_EQUALS));
    }

    @Test
    public void parseAttribute_invalidFormatMultipleEquals_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseAttribute(INVALID_ATTRIBUTE_MULTIPLE_EQUALS));
    }

    @Test
    public void parseAttribute_invalidAttributeName_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseAttribute(INVALID_ATTRIBUTE_WITH_INVALID_NAME));
    }

    @Test
    public void parseAttribute_invalidAttributeValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseAttribute(INVALID_ATTRIBUTE_WITH_INVALID_VALUE));
    }

    @Test
    public void parseAttribute_validAttributeWithoutWhitespace_returnsAttribute() throws Exception {
        Attribute expectedAttribute = new Attribute(VALID_ATTRIBUTE_NAME, VALID_ATTRIBUTE_VALUE);
        assertEquals(expectedAttribute, ParserUtil.parseAttribute(VALID_ATTRIBUTE));
    }

    @Test
    public void parseAttribute_validAttributeWithWhitespace_returnsTrimmedAttribute() throws Exception {
        String attributeWithWhitespace = WHITESPACE + VALID_ATTRIBUTE + WHITESPACE;
        Attribute expectedAttribute = new Attribute(VALID_ATTRIBUTE_NAME, VALID_ATTRIBUTE_VALUE);
        assertEquals(expectedAttribute, ParserUtil.parseAttribute(attributeWithWhitespace));
    }

    @Test
    public void parseAttribute_emptyAttributeName_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseAttribute("=value"));
    }

    @Test
    public void parseAttribute_emptyAttributeValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseAttribute("name="));
    }

    @Test
    public void parseAttribute_specialCharactersInValidAttribute_returnsAttribute() throws Exception {
        String validNameWithSpecialChars = "my-attr_123!";
        String validValueWithSpecialChars = "value.with@special#chars$";
        String validAttributeWithSpecialChars = validNameWithSpecialChars + "=" + validValueWithSpecialChars;

        Attribute expectedAttribute = new Attribute(validNameWithSpecialChars, validValueWithSpecialChars);
        assertEquals(expectedAttribute, ParserUtil.parseAttribute(validAttributeWithSpecialChars));
    }

    @Test
    public void parseAttributes_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseAttributes(null));
    }

    @Test
    public void parseAttributes_emptyCollection_returnsEmptySet() throws Exception {
        assertTrue(ParserUtil.parseAttributes(Collections.emptyList()).isEmpty());
    }

    @Test
    public void parseAttributes_collectionWithInvalidAttribute_throwsParseException() {
        assertThrows(ParseException.class, () ->
                ParserUtil.parseAttributes(Arrays.asList(VALID_ATTRIBUTE, INVALID_ATTRIBUTE_NO_EQUALS)));
    }

    @Test
    public void parseAttributes_collectionWithValidAttributes_returnsAttributeSet() throws Exception {
        String validAttribute2 = "size=large";

        Set<Attribute> actualAttributeSet = ParserUtil.parseAttributes(
                Arrays.asList(VALID_ATTRIBUTE, validAttribute2));

        Set<Attribute> expectedAttributeSet = new HashSet<>(Arrays.asList(
                new Attribute(VALID_ATTRIBUTE_NAME, VALID_ATTRIBUTE_VALUE),
                new Attribute("size", "large")));

        assertEquals(expectedAttributeSet, actualAttributeSet);
    }

    @Test
    public void parseAttributes_duplicateAttributes_returnsUniqueAttributeSet() throws Exception {
        Set<Attribute> actualAttributeSet = ParserUtil.parseAttributes(
                Arrays.asList(VALID_ATTRIBUTE, VALID_ATTRIBUTE));

        Set<Attribute> expectedAttributeSet = new HashSet<>(List.of(
                new Attribute(VALID_ATTRIBUTE_NAME, VALID_ATTRIBUTE_VALUE)));

        assertEquals(expectedAttributeSet, actualAttributeSet);
        assertEquals(1, actualAttributeSet.size());
    }

    @Test
    public void parseAttributes_duplicateAttributeNames_throwsParseException() {
        assertThrows(ParseException.class, () ->
                ParserUtil.parseAttributes(Arrays.asList(VALID_ATTRIBUTE, VALID_ATTRIBUTE_ALT)));
    }
}

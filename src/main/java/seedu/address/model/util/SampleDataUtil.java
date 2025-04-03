package seedu.address.model.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.attribute.Attribute;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {
    public static Person[] getSamplePersons() {
        return new Person[] {
            new Person(new Name("Alex Yeoh"), new Phone("87438807"), new Email("alexyeoh@example.com"),
                getTagSet("R", "Python", "Tableau"),
                getAttributeSet("Graduation Year", "2028", "Major", "Data Science and Analytics",
                        "GitHub", "example-alexyeoh", "LinkedIn", "example-alexyeoh")),
            new Person(new Name("Bernice Yu"), new Phone("99272758"), new Email("berniceyu@example.com"),
                getTagSet("Java", "C++"),
                getAttributeSet("Major", "Computer Science", "GitHub", "example-berniceyu")),
            new Person(new Name("Charlotte Oliveiro"), new Phone("93210283"), new Email("charlotte@example.com"),
                getTagSet("JavaScript", "React", "AngularJS"),
                getAttributeSet("Graduation Year", "2027", "Major", "Computer Science",
                        "GitHub", "example-charlotteoliveiro", "LinkedIn", "example-charlotteoliveiro")),
            new Person(new Name("David Li"), new Phone("91031282"), new Email("lidavid@example.com"),
                getTagSet("Excel"),
                getAttributeSet("Graduation Year", "2027", "Major", "Business Administration",
                        "LinkedIn", "example-davidli")),
            new Person(new Name("Irfan Ibrahim"), new Phone("92492021"), new Email("irfan@example.com"),
                getTagSet("Supply chain"),
                getAttributeSet("Graduation Year", "2026", "Major", "Business Administration",
                        "LinkedIn", "example-irfanibrahim")),
            new Person(new Name("Roy Balakrishnan"), new Phone("92624417"), new Email("royb@example.com"),
                getTagSet("BSL2", "Cell culture"),
                getAttributeSet("Major", "Life Sciences", "LinkedIn", "example-roybalakrishnan")),
            new Person(new Name("Gavin Lee"), new Phone("89793238"), new Email("gavinl@example.com"),
                    getTagSet("MATLAB", "LaTeX", "Python"),
                    getAttributeSet("Major", "Mathematics", "Graduation Year", "2027",
                            "GitHub", "example-gavinlee", "LinkedIn", "example-gavinlee"))
        };
    }

    public static ReadOnlyAddressBook getSampleAddressBook() {
        AddressBook sampleAb = new AddressBook();
        for (Person samplePerson : getSamplePersons()) {
            sampleAb.addPerson(samplePerson);
        }
        return sampleAb;
    }

    /**
     * Returns a tag set containing the list of strings given.
     */
    public static Set<Tag> getTagSet(String... strings) {
        return Arrays.stream(strings)
                .map(Tag::new)
                .collect(Collectors.toSet());
    }

    /**
     * Returns an attribute set containing the list of strings given.
     */
    public static Set<Attribute> getAttributeSet(String... strings) {
        assert strings.length % 2 == 0 : "The number of Strings provided should be an even number";

        return IntStream.range(0, strings.length / 2)
                .mapToObj(i -> new Attribute(strings[i * 2], strings[i * 2 + 1]))
                .collect(Collectors.toSet());
    }

}

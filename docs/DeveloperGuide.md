---
layout: default.md
title: "Developer Guide"
pageNav: 3
---

# TalentFolio Developer Guide

<!-- * Table of Contents -->
<page-nav-print />

--------------------------------------------------------------------------------------------------------------------

## **Acknowledgements**

_{ list here sources of all reused/adapted ideas, code, documentation, and third-party libraries -- include links to the original source as well }_

--------------------------------------------------------------------------------------------------------------------

## **Setting up, getting started**

Refer to the guide [_Setting up and getting started_](SettingUp.md).

--------------------------------------------------------------------------------------------------------------------

## **Design**

### Architecture

<puml src="diagrams/ArchitectureDiagram.puml" width="280" />

The ***Architecture Diagram*** given above explains the high-level design of the App.

Given below is a quick overview of main components and how they interact with each other.

**Main components of the architecture**

**`Main`** (consisting of classes [`Main`](https://github.com/AY2425S2-CS2103T-T10-1/tp/tree/master/src/main/java/seedu/address/Main.java) and [`MainApp`](https://github.com/AY2425S2-CS2103T-T10-1/tp/tree/master/src/main/java/seedu/address/MainApp.java)) is in charge of the app launch and shut down.

* At app launch, it initializes the other components in the correct sequence, and connects them up with each other.
* At shut down, it shuts down the other components and invokes cleanup methods where necessary.

The bulk of the app's work is done by the following four components:

* [**`UI`**](#ui-component): The UI of the App.
* [**`Logic`**](#logic-component): The command executor.
* [**`Model`**](#model-component): Holds the data of the App in memory.
* [**`Storage`**](#storage-component): Reads data from, and writes data to, the hard disk.

[**`Commons`**](#common-classes) represents a collection of classes used by multiple other components.

**How the architecture components interact with each other**

The *Sequence Diagram* below shows how the components interact with each other for the scenario where the user issues the command `delete 1`.

<puml src="diagrams/ArchitectureSequenceDiagram.puml" width="574" />

Each of the four main components (also shown in the diagram above),

* defines its *API* in an `interface` with the same name as the Component.
* implements its functionality using a concrete `{Component Name}Manager` class (which follows the corresponding API `interface` mentioned in the previous point.

For example, the `Logic` component defines its API in the `Logic.java` interface and implements its functionality using the `LogicManager.java` class which follows the `Logic` interface. Other components interact with a given component through its interface rather than the concrete class (reason: to prevent outside component's being coupled to the implementation of a component), as illustrated in the (partial) class diagram below.

<puml src="diagrams/ComponentManagers.puml" width="300" />

The sections below give more details of each component.

### UI component

The **API** of this component is specified in [`Ui.java`](https://github.com/AY2425S2-CS2103T-T10-1/tp/tree/master/src/main/java/seedu/address/ui/Ui.java)

<puml src="diagrams/UiClassDiagram.puml" alt="Structure of the UI Component"/>

The UI consists of a `MainWindow` that is made up of parts e.g.`CommandBox`, `ResultDisplay`, `PersonListPanel`, `StatusBarFooter` etc. All these, including the `MainWindow`, inherit from the abstract `UiPart` class which captures the commonalities between classes that represent parts of the visible GUI.

The `UI` component uses the JavaFx UI framework. The layout of these UI parts are defined in matching `.fxml` files that are in the `src/main/resources/view` folder. For example, the layout of the [`MainWindow`](https://github.com/AY2425S2-CS2103T-T10-1/tp/tree/master/src/main/java/seedu/address/ui/MainWindow.java) is specified in [`MainWindow.fxml`](https://github.com/AY2425S2-CS2103T-T10-1/tp/tree/master/src/main/resources/view/MainWindow.fxml)

The `UI` component,

* executes user commands using the `Logic` component.
* listens for changes to `Model` data so that the UI can be updated with the modified data.
* keeps a reference to the `Logic` component, because the `UI` relies on the `Logic` to execute commands.
* depends on some classes in the `Model` component, as it displays `Person` object residing in the `Model`.

### Logic component

**API** : [`Logic.java`](https://github.com/AY2425S2-CS2103T-T10-1/tp/tree/master/src/main/java/seedu/address/logic/Logic.java)

Here's a (partial) class diagram of the `Logic` component:

<puml src="diagrams/LogicClassDiagram.puml" width="550"/>

The sequence diagram below illustrates the interactions within the `Logic` component, taking `execute("delete 1")` API call as an example.

<puml src="diagrams/DeleteSequenceDiagram.puml" alt="Interactions Inside the Logic Component for the `delete 1` Command" />

**Note:** The lifeline for `DeleteCommandParser` should end at the destroy marker (X) but due to a limitation of PlantUML, the lifeline continues till the end of diagram.
</box>

How the `Logic` component works:

1. When `Logic` is called upon to execute a command, it is passed to an `AddressBookParser` object which in turn creates a parser that matches the command (e.g., `DeleteCommandParser`) and uses it to parse the command.
1. This results in a `Command` object (more precisely, an object of one of its subclasses e.g., `DeleteCommand`) which is executed by the `LogicManager`.
1. The command can communicate with the `Model` when it is executed (e.g. to delete a person).<br>
   Note that although this is shown as a single step in the diagram above (for simplicity), in the code it can take several interactions (between the command object and the `Model`) to achieve.
1. The result of the command execution is encapsulated as a `CommandResult` object which is returned back from `Logic`.

Here are the other classes in `Logic` (omitted from the class diagram above) that are used for parsing a user command:

<puml src="diagrams/ParserClasses.puml" width="600"/>

How the parsing works:

* When called upon to parse a user command, the `AddressBookParser` class creates an `XYZCommandParser` (`XYZ` is a placeholder for the specific command name e.g., `AddCommandParser`) which uses the other classes shown above to parse the user command and create a `XYZCommand` object (e.g., `AddCommand`) which the `AddressBookParser` returns back as a `Command` object.
* All `XYZCommandParser` classes (e.g., `AddCommandParser`, `DeleteCommandParser`, ...) inherit from the `Parser` interface so that they can be treated similarly where possible e.g, during testing.

### Model component

**API** : [`Model.java`](https://github.com/AY2425S2-CS2103T-T10-1/tp/tree/master/src/main/java/seedu/address/model/Model.java)

<puml src="diagrams/ModelClassDiagram.puml" width="450" />


The `Model` component,

* stores the address book data i.e., all `Person` objects (which are contained in a `UniquePersonList` object).
* stores the currently 'selected' `Person` objects (e.g., results of a search query) as a separate _filtered_ list which is exposed to outsiders as an unmodifiable `ObservableList<Person>` that can be 'observed' e.g. the UI can be bound to this list so that the UI automatically updates when the data in the list change.
* stores a `UserPref` object that represents the user’s preferences. This is exposed to the outside as a `ReadOnlyUserPref` objects.
* does not depend on any of the other three components (as the `Model` represents data entities of the domain, they should make sense on their own without depending on other components)

<box type="info" seamless>

**Note:** An alternative (arguably, a more OOP) model is given below. It has a `Tag` list in the `AddressBook`, which `Person` references. This allows `AddressBook` to only require one `Tag` object per unique tag, instead of each `Person` needing their own `Tag` objects.<br>

<puml src="diagrams/BetterModelClassDiagram.puml" width="450" />

</box>


### Storage component

**API** : [`Storage.java`](https://github.com/AY2425S2-CS2103T-T10-1/tp/tree/master/src/main/java/seedu/address/storage/Storage.java)

<puml src="diagrams/StorageClassDiagram.puml" width="550" />

The `Storage` component,
* can save both address book data and user preference data in JSON format, and read them back into corresponding objects.
* inherits from both `AddressBookStorage` and `UserPrefStorage`, which means it can be treated as either one (if only the functionality of only one is needed).
* depends on some classes in the `Model` component (because the `Storage` component's job is to save/retrieve objects that belong to the `Model`)

### Common classes

Classes used by multiple components are in the `seedu.address.commons` package.

--------------------------------------------------------------------------------------------------------------------

## **Implementation**

This section describes some noteworthy details on how certain features are implemented.

### Filtering

The following describes how the filter command `filter a/Mayor=CS` is executed.

First, `FilterCommandParser` parses a set of attributes (i.e., the pair of `Mayor` and `CS`), creating a new instance of `FilterCommand`. The procedure is almost the same as shown in the [logic component section](#Logic-omponent). `FilterCommand` is initialized with the attribute(s) and `wasDuplicate`, set `true` when there is a duplicate in input attributes. This boolean parameter is later used to generate a warning message about the duplicate. In this case, this parameter is set `false`.

Next, `FilterCommand::execute(m)` is called. The method consists of three steps:

1. autocorrection of `Mayor` and `CS`,
1. creation and application of filtering predicate, and
1. generation of warning messages.

The following sequence diagram illustrates how the method works at the low-level:

<puml src="diagrams/FilterSequenceDiagram.puml" alt="filter a/Mayor=CS" />

To autocorrect the attribute name `Mayor`, the Model goes through the following process:

<puml src="diagrams/AutoCorrection.puml" alt="Autocorrection of 'Mayor'" />

The attribute value `CS` will be autocorrected in a similar way.

Alternatively, if there is no attribute name/value close enough to `Mayor`/`CS`, nothing will be returned from `autocorrectAttributeName/Value`; this is why their return value is `Optional`.

If there are multiple attributes given to the filter command, each attribute will go through the same process as above. Specifically, the command

1. first autocorrects all the attributes,
1. followed by the creation of predicate with the corrected attributes, and
1. obtains warning messages for the entire attribute name/values.

Please also note that, in the actual implementation, the command repeats the autocorrection process during the acquisition of warning messages. This is for the sake of simplicity of code.

### Undo feature

The undo mechanism is implemented inside `Model`. `Model` now implements the following operations:
* `Model#saveState()` - Saves the current address book state in its history. This is executed before every command in `LogicManager`.
* `Model#revertLastState()` - Reverts the address book to the last state in its history.

`Model` stores a `Stack` of `AddressBook`-s. To save the current address book, it creates a copy of the current address book, but copies the `Person` object by reference. This feature therefore requires that `Person` is not modified in-place when editing, but instead re-constructed.

<box type="info" seamless>

To prevent the case where an `UndoCommand` does not change the address book, `Model#saveState()` enforces that duplicate address books will not be saved. `Model#revertLastState()` enforces that it will not revert to an `AddressBook` which is equal to the current `AddressBook`, using the Java `Object#equals()` method.

As `AddressBook#equals()` only checks for `UniquePersonList` and `aliasMappings`, only changes to these will be undone.

</box>

The following sequence diagram illustrates how `Model#saveState()` is implemented.

<puml src="diagrams/ModelSaveState.puml" alt="ModelSaveState" />

The following sequence diagram illustrates how `UndoCommand#execute()` is implemented.

<puml src="diagrams/UndoSequenceDiagram-Logic.puml" alt="UndoSequenceDiagram-Logic" />

It is noteworthy that although `saveState()` is called before `UndoCommand` gets executed, the undoing still works as `revertLastState()` ensures it will revert to a different `AddressBook`.

### Sort/numerical sort feature

Sorting is currently done via custom comparators.

The following sequence diagrams show how a default sort operation "sort a/Location" goes through the `Logic` component:

<puml src="diagrams/LexSortSequenceDiagram1.puml"></puml>
<puml src="diagrams/LexSortSequenceDiagram2.puml"></puml>

The process of a numerical sort operation "sort-num a/Graduation Year" is similar to the above, except for the actual sorting process:

<puml src="diagrams/NumSortSequenceDiagram.puml"></puml>

--------------------------------------------------------------------------------------------------------------------

## **Documentation, logging, testing, configuration, dev-ops**

* [Documentation guide](Documentation.md)
* [Testing guide](Testing.md)
* [Logging guide](Logging.md)
* [Configuration guide](Configuration.md)
* [DevOps guide](DevOps.md)

--------------------------------------------------------------------------------------------------------------------

## **Appendix: Requirements**

### Product scope

**Target user profile**:

* is a hiring manager
* has a need to manage a significant number of applicants
* prefer desktop apps over other types
* can type fast
* prefers typing to mouse interactions
* is reasonably comfortable using CLI apps

**Value proposition**: provides fast access to applicants and their socials, while allowing them to quickly filter applicants

### User stories

Priorities: High (must have) - `* * *`, Medium (nice to have) - `* *`, Low (unlikely to have) - `*`

| Priority | As a …​                                    | I want to …​                 | So that I can…​                                                        |
|----------|--------------------------------------------|------------------------------|------------------------------------------------------------------------|
| `* * *`  | new user                                   | see a user guide             | I can learn how to use the program                                     |
| `* * *`  | user managing a number of candidates       | sort candidates by some value (graduation years, discipline, etc.)| I can access to the relevant candidates easily                         |
| `* * *`  | user managing a number of candidates       | also filter the candidates   | I can access to the relevant candidates easily                         |
| `* *`    | careless user                              | undo my most recent command  | accidental changes I make can be reverted easily                       |
| `* *`    | careless user                              | get back my most recent command| I don't have to retype my previous command should there be typoes      |
| `* *`    | user who is managing information of high privacy concerns| ensure that the system is safe enough (i.e., no significant risk of data leakage)| I can protect candidates privacy well                                  |
| `* *`    | user who needs to frequently filter out appliactants in some domain-specific aspects| add tag feature and relevant filter| so that users can organise data in more customised way                 |
| `* *`    | existing user but I forgot about available commands| make list of all commands and their syntax easily assessible| I can check against the list                                           |
| `* *`    | meticulous user                            | edit a contact's information | I can keep my address book up to date                                  |
| `* *`    | user required to share the information on candidates with others| pick up candidates and print out their information, in an organized way| I can present the candidates to others                                 |
| `* *`    | user interviewing the candidates           | relate the date of interview to the candidates| I can organize my schedule easily                                      |
| `* *`    | user managing candidates across different positions| tag candidates               | I can easily categorize them                                           |
| `* *`    | forgetful user                             | see my most recent commands  | I can be reminded of what changes I just made                          |
| `* *`    | creative user                              | assign custom tags to contacts| categorize them in a way that is most sensible to me                   |
| `*`      | user changing their computer               | import my data               | I can transfer data over from my old computer                          |
| `*`      | user who is familiar with regular expression| use regular expression when search for certain keywords among applicants| more efficient search can be done                                      |
| `*`      | user changing their computer               | export my data               | I can transfer it over to the new computer                             |
| `*`      | use who needs to collaborate with others   | synchronise my database with others| we can stay on the same page                                           |
| `*`      | frequent user                              | create snippets for commands | I can enter frequently-used commands more quickly                      |
| `*`      | recruiter who needs to regularly remove some candidate from the address book at the end of each recruit season| have built-in script to automatically delete irrelevant candidates (i.e., those who meet certain conditions)| I don't have to manually do deletions routinely                        |
| `*`      | careless user                              | have my command autocorrected| I don't need to fix my typos                                           |
| `*`      | busy user                                  | have my command predicted and auto-filled| I can save time typing commands in full                                |
| `*`      | event organiser                            | quickly find people who live near to a certain location|                                                                        |
| `*`      | user with limited PC resource              | delete candidates easily     | I can free up the memory resource                                      |

*{More to be added}*

### Use cases

(For all use cases below, the **System** is the `TalentFolio` and the **Actor** is the `user`, unless specified otherwise)

**Use Case: Add a candidate**

**MSS**

1. User requests to add a candidate with name, phone number, email, address, and optional tags.
2. System verifies that the input values are valid.
3. System adds the candidate.
4. System displays confirmation message.
5. GUI updates to show the new candidate.

   Use case ends.

**Extensions**
* 2a. Name is invalid.

   * 2a1. System displays an error: “Error: {candidate name} is not a valid name.”

     Use case ends.

* 2b. Email is invalid.

   * 2b1. System displays an error: “Error: {candidate email} is not a valid email.”

     Use case ends.

* 2c. Required parameters are missing.

   * 2c1. System displays an error: “Error: The command format is add n/{candidate name} p/{phone number} e/{email} [t/{tag}].”

     Use case ends.

*{More to be added}*

### Non-Functional Requirements

1.  Should work on any _mainstream OS_ as long as it has Java `17` or above installed.
2.  Should be able to hold up to 1000 persons without a noticeable sluggishness in performance for typical usage.
3.  A user with above average typing speed for regular English text (i.e. not code, not system admin commands) should be able to accomplish most of the tasks faster using commands than using the mouse.
4.  Logs must be automatically generated.
5.  Any information related to candidates must remain confidential in the logs.
6.  Logs must be identical across different operating systems to enable sharing between the same app on different OS platforms.
7.  The UI has to be clear enough for the user to find the necessary information at a glance.
8.  The app design should offer flexibility in the tag feature to accommodate unforeseen criteria in the future.
9.  Copy and paste should be supported in text fields, allowing the user to enter information without typing manually.
10. The app has to respond within one second.


### Glossary

* **Attribute**: A key-value pair associated with a candidate.
  * For example, `Graduation Year: 2027` is an attribute, where the **attribute name** is `Graduation Year` and the **attribute value** is `2027`.
* **Candidate**: Any contact in the address book.
* **Command**: A string of text that the user enters via the command line.
* **Duplicate**: A string that matches another string exactly, ignoring case.
* **Mainstream OS**: Windows, Linux, Unix, macOS.
* **Valid email**: A string of characters of the form `{local-part}@{domain}` adhering to the following constraints:
  * `{local-part}` contains only alphanumeric characters and the special characters `+_.-`, and cannot start or end with a special character.
  * `{domain}` consists of _domain labels_ separated by periods (`.`), where the last domain label is at least 2 characters long.
  * Each _domain label_ contains only alphanumeric characters and hyphens (`-`), and cannot start or end with a hyphen.
* **Valid name**: Any sequence of whitespace and/or alphabetical characters.

--------------------------------------------------------------------------------------------------------------------

## **Appendix: Instructions for manual testing**

Given below are instructions to test the app manually.

<box type="info" seamless>

**Note:** These instructions only provide a starting point for testers to work on;
testers are expected to do more *exploratory* testing.

</box>

### Launch and shutdown

1. Initial launch

   1. Download the jar file and copy into an empty folder

   1. Double-click the jar file Expected: Shows the GUI with a set of sample contacts. The window size may not be optimum.

1. Saving window preferences

   1. Resize the window to an optimum size. Move the window to a different location. Close the window.

   1. Re-launch the app by double-clicking the jar file.<br>
       Expected: The most recent window size and location is retained.

1. _{ more test cases …​ }_

### Deleting a person

1. Deleting a person while all persons are being shown

   1. Prerequisites: List all persons using the `list` command. Multiple persons in the list.

   1. Test case: `delete 1`<br>
      Expected: First contact is deleted from the list. Details of the deleted contact shown in the status message. Timestamp in the status bar is updated.

   1. Test case: `delete 0`<br>
      Expected: No person is deleted. Error details shown in the status message. Status bar remains the same.

   1. Other incorrect delete commands to try: `delete`, `delete x`, `...` (where x is larger than the list size)<br>
      Expected: Similar to previous.

1. _{ more test cases …​ }_

### Saving data

1. Dealing with missing/corrupted data files

   1. _{explain how to simulate a missing/corrupted file, and the expected behavior}_

1. _{ more test cases …​ }_

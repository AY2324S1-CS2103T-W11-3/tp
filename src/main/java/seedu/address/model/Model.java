package seedu.address.model;

import java.nio.file.Path;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.model.band.Band;
import seedu.address.model.musician.Musician;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<Musician> PREDICATE_SHOW_ALL_MUSICIANS = unused -> true;

    /**
     * Replaces user prefs data with the data in {@code userPrefs}.
     */
    void setUserPrefs(ReadOnlyUserPrefs userPrefs);

    /**
     * Returns the user prefs.
     */
    ReadOnlyUserPrefs getUserPrefs();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Sets the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);

    /**
     * Returns the user prefs' address book file path.
     */
    Path getAddressBookFilePath();

    /**
     * Sets the user prefs' address book file path.
     */
    void setAddressBookFilePath(Path addressBookFilePath);

    /**
     * Replaces address book data with the data in {@code addressBook}.
     */
    void setAddressBook(ReadOnlyAddressBook addressBook);

    /** Returns the AddressBook */
    ReadOnlyAddressBook getAddressBook();

    /**
     * Returns true if a musician with the same identity as {@code musician} exists in the address book.
     */
    boolean hasMusician(Musician musician);

    /**
     * Deletes the given musician.
     * The musician must exist in the address book.
     */
    void deleteMusician(Musician target);

    /**
     * Adds the given musician.
     * {@code musician} must not already exist in the address book.
     */
    void addMusician(Musician musician);

    /**
     * Replaces the given musician {@code target} with {@code editedMusician}.
     * {@code target} must exist in the address book.
     * The musician identity of {@code editedMusician} must not be the same as another existing musician
     * in the address book.
     */
    void setMusician(Musician target, Musician editedMusician);

    /** Returns an unmodifiable view of the filtered musician list */
    ObservableList<Musician> getFilteredMusicianList();

    /**
     * Updates the filter of the filtered musician list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredMusicianList(Predicate<Musician> predicate);

    /**
     * Returns true if a band with the same identity as {@code band} exists in the address book.
     */
    boolean hasBand(Band band);

    /**
     * Adds the given band.
     * {@code band} must not already exist in the address book.
     */
    void addBand(Band band);
}

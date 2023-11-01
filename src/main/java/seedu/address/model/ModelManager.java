package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.band.Band;
import seedu.address.model.musician.Musician;
import seedu.address.model.musician.MusicianInBandPredicate;

/**
 * Represents the in-memory model of the address book data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final AddressBook addressBook;
    private final UserPrefs userPrefs;
    private final FilteredList<Musician> filteredMusicians;
    private final FilteredList<Band> filteredBands;

    /**
     * Initializes a ModelManager with the given addressBook and userPrefs.
     */
    public ModelManager(ReadOnlyAddressBook addressBook, ReadOnlyUserPrefs userPrefs) {
        requireAllNonNull(addressBook, userPrefs);

        logger.fine("Initializing with address book: " + addressBook + " and user prefs " + userPrefs);

        this.addressBook = new AddressBook(addressBook);
        this.userPrefs = new UserPrefs(userPrefs);
        filteredMusicians = new FilteredList<>(this.addressBook.getMusicianList());
        filteredBands = new FilteredList<>(this.addressBook.getBandList());
    }

    public ModelManager() {
        this(new AddressBook(), new UserPrefs());
    }

    //=========== UserPrefs ==================================================================================

    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        requireNonNull(userPrefs);
        this.userPrefs.resetData(userPrefs);
    }

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        return userPrefs;
    }

    @Override
    public GuiSettings getGuiSettings() {
        return userPrefs.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        userPrefs.setGuiSettings(guiSettings);
    }

    @Override
    public Path getAddressBookFilePath() {
        return userPrefs.getAddressBookFilePath();
    }

    @Override
    public void setAddressBookFilePath(Path addressBookFilePath) {
        requireNonNull(addressBookFilePath);
        userPrefs.setAddressBookFilePath(addressBookFilePath);
    }

    //=========== AddressBook ================================================================================

    @Override
    public void setAddressBook(ReadOnlyAddressBook addressBook) {
        this.addressBook.resetData(addressBook);
    }

    @Override
    public ReadOnlyAddressBook getAddressBook() {
        return addressBook;
    }

    @Override
    public boolean hasMusician(Musician musician) {
        requireNonNull(musician);
        return addressBook.hasMusician(musician);
    }

    @Override
    public void deleteMusician(Musician target) {
        addressBook.removeMusician(target);
        setToDefaultGui();
        updateMusicianInAllBands(target, null);
    }

    @Override
    public void addMusician(Musician musician) {
        addressBook.addMusician(musician);
        setToDefaultGui();
    }

    @Override
    public void setMusician(Musician target, Musician editedMusician) {
        requireAllNonNull(target, editedMusician);
        addressBook.setMusician(target, editedMusician);
        setToDefaultGui();
        updateMusicianInAllBands(target, editedMusician);
    }

    /**
     * Used by edit and delete musician commands to simultaneously update the
     * corresponding musician in band list.
     */
    private void updateMusicianInAllBands(Musician target, Musician editedMusician) {
        requireNonNull(target);
        addressBook.updateMusicianInAllBands(target, editedMusician);
    }

    @Override
    public boolean hasBand(Band band) {
        requireNonNull(band);
        return addressBook.hasBand(band);
    }

    @Override
    public void addBand(Band band) {
        requireAllNonNull(band);
        addressBook.addBand(band);
        setToDefaultGui();
    }

    @Override
    public boolean hasMusicianInBand(int bandIndex, int musicianIndex) {
        requireNonNull(bandIndex);
        requireNonNull(musicianIndex);
        return addressBook.hasMusicianInBand(bandIndex, filteredMusicians.get(musicianIndex));
    }

    @Override
    public void addMusicianToBand(int bandIndex, int musicianIndex) {
        requireNonNull(bandIndex);
        requireNonNull(musicianIndex);
        addressBook.addMusicianToBand(bandIndex, filteredMusicians.get(musicianIndex));
    }

    @Override
    public void deleteBand(Band target) {
        addressBook.removeBand(target);
        setToDefaultGui();
    }

    //=========== Filtered Musician List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Musician} backed by the internal list of
     * {@code versionedAddressBook}
     */
    @Override
    public ObservableList<Musician> getFilteredMusicianList() {
        return filteredMusicians;
    }

    @Override
    public void updateFilteredMusicianList(Predicate<Musician> predicate) {
        requireNonNull(predicate);
        filteredMusicians.setPredicate(predicate);
    }

    //=========== Filtered Band List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Band} backed by the internal list of
     * {@code versionedAddressBook}
     */
    @Override
    public ObservableList<Band> getFilteredBandList() {
        return filteredBands;
    }

    @Override
    public void updateFilteredBandList(Predicate<Band> predicate) {
        requireNonNull(predicate);
        filteredBands.setPredicate(predicate);
    }

    /**
     * Updates the filtered band list to show only the band with the corresponding predicate and
     * updates the filtered musician list to show only the members in the band.
     * If there is no band with the corresponding name, an error message will be displayed
     * And the panel will show initial state of listing all.
     */
    @Override
    public void updateFilteredBandMusicianList(Predicate<Band> bandPredicate) {
        filteredBands.setPredicate(bandPredicate);

        if (filteredBands.size() == 0) {
            updateFilteredMusicianList(PREDICATE_SHOW_ALL_MUSICIANS);
            updateFilteredBandList(PREDICATE_SHOW_ALL_BANDS);
            return;
        }

        Predicate<Musician> musicianPredicate = new MusicianInBandPredicate(filteredBands.get(0));
        filteredMusicians.setPredicate(musicianPredicate);
    }

    /**
     * Update the filtered band list to show all bands and the filtered musician list to show all musicians.
     * This is the default state of the GUI and the result of the list command.
     */
    private void setToDefaultGui() {
        updateFilteredMusicianList(PREDICATE_SHOW_ALL_MUSICIANS);
        updateFilteredBandList(PREDICATE_SHOW_ALL_BANDS);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ModelManager)) {
            return false;
        }

        ModelManager otherModelManager = (ModelManager) other;
        return addressBook.equals(otherModelManager.addressBook)
                && userPrefs.equals(otherModelManager.userPrefs)
                && filteredMusicians.equals(otherModelManager.filteredMusicians);
    }

}

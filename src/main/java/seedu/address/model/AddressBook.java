package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.List;

import javafx.collections.ObservableList;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.band.Band;
import seedu.address.model.band.UniqueBandList;
import seedu.address.model.musician.Musician;
import seedu.address.model.musician.UniqueMusicianList;

/**
 * Wraps all data at the address-book level
 * Duplicates are not allowed (by .isSameMusician comparison)
 */
public class AddressBook implements ReadOnlyAddressBook {

    private final UniqueMusicianList musicians;
    private final UniqueBandList bands;


    {
        musicians = new UniqueMusicianList();
        bands = new UniqueBandList();
    }

    public AddressBook() {
    }

    /**
     * Creates an AddressBook using the Musicians in the {@code toBeCopied}
     */
    public AddressBook(ReadOnlyAddressBook toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the musician list with {@code musicians}.
     * {@code musicians} must not contain duplicate musicians.
     */
    public void setMusicians(List<Musician> musicians) {
        this.musicians.setMusicians(musicians);
    }

    /**
     * Replaces the contents of the band list with {@code bands}.
     * {@code bands} must not contain duplicate bands.
     */
    public void setBands(List<Band> bands) {
        this.bands.setBand(bands);
    }

    /**
     * Resets the existing data of this {@code AddressBook} with {@code newData}.
     */
    public void resetData(ReadOnlyAddressBook newData) {
        requireNonNull(newData);

        setMusicians(newData.getMusicianList());
        setBands(newData.getBandList());
    }

    //// musician-level operations

    /**
     * Returns true if a musician with the same identity as {@code musician} exists in the address book.
     */
    public boolean hasMusician(Musician musician) {
        requireNonNull(musician);
        return musicians.contains(musician);
    }

    /**
     * Adds a musician to the address book.
     * The musician must not already exist in the address book.
     */
    public void addMusician(Musician p) {
        musicians.add(p);
    }

    /**
     * Replaces the given musician {@code target} in the list with {@code editedMusician}.
     * {@code target} must exist in the address book.
     * The musician identity of {@code editedMusician} must not be the same as another existing musician in the
     * address book.
     */
    public void setMusician(Musician target, Musician editedMusician) {
        requireNonNull(editedMusician);

        musicians.setMusician(target, editedMusician);
    }

    /**
     * Removes {@code key} from this {@code AddressBook}.
     * {@code key} must exist in the address book.
     */
    public void removeMusician(Musician key) {
        musicians.remove(key);
    }

    /**
     * Adds a band to the address book.
     * The band must not already exist in the address book.
     */
    public void addBand(Band band) {
        bands.add(band);
    }

    /**
     * Returns true if a band with the same identity as {@code musician} exists in the address book.
     */
    public boolean hasBand(Band band) {
        requireNonNull(band);
        return bands.contains(band);
    }

    /**
     * Replaces the given musician {@code target} in the list with {@code editedMusician}.
     * {@code target} must exist in the address book.
     * The musician identity of {@code editedMusician} must not be the same as another existing musician in the
     * address book.
     */
    public void setBand(Band target, Band editedBand) {
        requireNonNull(editedBand);

        bands.setBand(target, editedBand);
    }


    //// util methods

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("musicians", musicians)
                .toString();
    }

    @Override
    public ObservableList<Musician> getMusicianList() {
        return musicians.asUnmodifiableObservableList();
    }

    @Override
    public ObservableList<Band> getBandList() {
        return bands.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AddressBook)) {
            return false;
        }

        AddressBook otherAddressBook = (AddressBook) other;
        return musicians.equals(otherAddressBook.musicians);
    }

    @Override
    public int hashCode() {
        return musicians.hashCode();
    }
}

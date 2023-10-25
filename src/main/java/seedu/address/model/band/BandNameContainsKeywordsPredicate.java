package seedu.address.model.band;

import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;
import seedu.address.commons.util.ToStringBuilder;

/**
 * Tests that a {@code Band}'s {@code Name} matches any of the keywords given.
 */
public class BandNameContainsKeywordsPredicate implements Predicate<Band> {

    private final BandName bandName;

    public BandNameContainsKeywordsPredicate(BandName bandName) {
        this.bandName = bandName;
    }
    @Override
    public boolean test(Band band) {
        return StringUtil.containsWordIgnoreCase(band.getName().fullName, bandName.toString());
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof BandNameContainsKeywordsPredicate)) {
            return false;
        }

        BandNameContainsKeywordsPredicate otherBandNameContainsKeywordsPredicate =
            (BandNameContainsKeywordsPredicate) other;
        return bandName.equals(otherBandNameContainsKeywordsPredicate.bandName);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("bandName", bandName).toString();
    }
}

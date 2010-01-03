package jw.tl.service;

import com.google.common.collect.ImmutableList;
import org.junit.Test;
import static org.junit.Assert.*;

public class NamesTest {

    String norm = "normalized" ;
    String other = "other";

    @Test
    public void normalizeShouldTreatUnderscoreAsSpace() {
        String result = norm + " " + other;
        assertEquals(result, Names.norm(result.replaceAll(" ", "_")));
    }

    @Test
    public void normalizedNameShouldReturnSelf() {
        assertEquals(norm, Names.norm(norm));
    }

    @Test
    public void upperCaseShouldReturnLowercase() {
        assertEquals(norm, Names.norm(norm.toUpperCase()));
    }

    @Test
    public void doubleSpaceShouldBeSingleSpace() {
        assertEquals(norm + " " + other, Names.norm(norm + "  " + other));
    }

    @Test
    public void tabbedSpaceShouldBeSingleSpace() {
        assertEquals(norm + " " + other, Names.norm(norm + "\t" + other));
    }

    @Test
    public void trailingSpaceShouldBeRemoved() {
        assertEquals(norm, Names.norm(" " + norm + " "));
    }

    @Test
    public void splitShouldReturnSingleForOneWord() {
        assertEquals(ImmutableList.of(norm), Names.split(norm));
    }

    @Test
    public void splitShouldNormalize() {
        assertEquals(ImmutableList.of(norm, other), Names.split(" " + norm + "\t " + other + " "));
    }
}

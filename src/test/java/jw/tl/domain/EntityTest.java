/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jw.tl.domain;

import org.junit.Before;
import org.junit.Test;
import static junit.framework.Assert.*;

/**
 *
 * @author Jonas Wiklund
 */
public class EntityTest {

    Serie juuni;
    Serie naruto;

    @Before
    public void setup() {
        juuni = new Serie("Juuni Kokki");
        naruto = new Serie("Naruto");
    }

    @Test
    public void validateEquals() {
        assertFalse(juuni.equals(naruto));
        assertTrue(juuni.equals(juuni));
        Serie anotherJuuni = new Serie("Juuni Kokki");
        assertFalse(juuni.equals(anotherJuuni));
    }
}

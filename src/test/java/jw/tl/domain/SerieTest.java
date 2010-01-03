package jw.tl.domain;

import jw.tl.service.Names;
import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.Before;

public class SerieTest {

    Serie juuni = null ;

    @Before
    public void setup() {
        juuni = new Serie("Juuni Kokki");
    }

    @Test(expected=NullPointerException.class)
    public void serieNameShouldNotBeNull() {
        new Serie(null);
    }

    @Test(expected=NullPointerException.class)
    public void serieAliasShouldNotBeNull() {
        juuni.addAlias(null);
    }

    @Test
    public void settingNullNameShouldNotChangeName() {
        String name = juuni.getName();
        try {
            juuni.setName(null);
            fail("It should not be possible to set a null name");
        } catch( NullPointerException e ) {
            assertEquals(name, juuni.getName());
        }
    }

    @Test
    public void serieShouldHaveNormalizedNameAsAlias() {
        assertTrue(juuni.hasAlias(Names.norm(juuni.getName())));
    }

    @Test
    public void serieShouldNormalizeAliasQuery() {
        assertTrue(juuni.hasAlias(juuni.getName()));
    }

    @Test
    public void addedAliasShouldBeStored() {
        assertFalse(juuni.hasAlias("alias"));
        juuni.addAlias("alias");
        assertTrue(juuni.hasAlias("alias"));
    }

    @Test
    public void changingNameShouldChangeAlias() {
        juuni.setName("Juuni kokki2");
        assertTrue(juuni.hasAlias("juuni kokki2"));
        assertFalse(juuni.hasAlias("juuni kokki"));
    }

    @Test
    public void sequelDetectionShouldWork() {
        Serie sequel = new Serie(juuni.getName() + " 2");
        Serie other = new Serie("Other");
        assertFalse(juuni.isSequelTo(sequel));
        assertTrue(sequel.isSequelTo(juuni));
        assertFalse(juuni.isSequelTo(juuni));
        assertFalse(other.isSequelTo(juuni));
        assertFalse(juuni.isSequelTo(other));
    }
}

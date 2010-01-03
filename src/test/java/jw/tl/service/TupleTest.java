package jw.tl.service;

import jw.tl.util.Tuple;

import org.junit.Test;
import static org.junit.Assert.*;

public class TupleTest {

    @Test
    public void toStringShouldReturn() {
        assertEquals("(a, b)", Tuple.of("a", "b").toString());
        assertEquals("(a, b, 3)", Tuple.of("a", "b", 3).toString());
    }

    @Test
    public void equalsShouldReturn() {
        assertTrue(Tuple.of("a", "b").equals(Tuple.of("a", "b")));
        assertFalse(Tuple.of("a", "c").equals(Tuple.of("a", "b")));
        assertFalse(Tuple.of("c", "b").equals(Tuple.of("a", "b")));
        assertFalse(Tuple.of("a", "b").equals(Tuple.of("a", "b", 3)));
        assertTrue(Tuple.of("a", "b", 3).equals(Tuple.of("a", "b", 3)));
    }

    @Test
    public void hashShouldReturn() {
        assertEquals(Tuple.of("a", "b").hashCode(), Tuple.of("a", "b").hashCode());
        assertEquals(Tuple.of("a", "b", 3).hashCode(), Tuple.of("a", "b", 3).hashCode());
    }
}

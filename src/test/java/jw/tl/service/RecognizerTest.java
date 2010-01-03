package jw.tl.service;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.*;
import jw.tl.domain.Episode;
import jw.tl.domain.Serie;

import jw.tl.repo.MemorySerieRepo;
import jw.tl.util.Pair;
import jw.tl.util.Tuple;

import org.junit.Before;
import org.junit.Test;

public class RecognizerTest {

	Recognizer recognizer;
	Serie kokki = new Serie("Juuni Kokki");
	Serie sequel = new Serie("Juuni Kokki 2");
	
	@Before
	public void setup() {
        MemorySerieRepo repo = new MemorySerieRepo();
        repo.save(kokki);
        repo.save(sequel);
        recognizer = new Recognizer(repo);
	}

    @Test
    public void simpleMatching() {
        assertEquals(expect(new Episode(kokki, -1)),
                matching("juuni", "kokki"));
        assertEquals(expect(new Episode(kokki, -1)),
                matching("this", "juuni", "kokki"));
        assertEquals(expect(), matching("this", "juuni", "is", "kokki"));
        assertEquals(expect(new Episode(kokki, 2), new Episode(sequel, -1)),
                matching("juuni", "kokki", "2"));
        assertEquals(expect(new Episode(kokki, -1), new Episode(sequel, 1)),
                matching("juuni", "kokki", "2", "-", "1"));
        assertEquals(expect(new Episode(kokki, 2)),
                matching("juuni", "kokki", "-", "2", "1"));
    }

    public void episodeParsing() {
        assertEquals(expect(new Episode(kokki, 1)), matching("juuni", "kokki", "-1"));
        assertEquals(expect(new Episode(kokki, 1)), matching("juuni", "kokki", "1.avi"));
        assertEquals(expect(new Episode(kokki, 18)), matching("juuni", "kokki", "018"));
    }

    @Test
    public void batchParsing() {
        assertEquals(expect(new Episode(kokki, -1)), matching("juuni", "kokki", "1-2"));
        assertEquals(expect(new Episode(kokki, -1)), matching("juuni", "kokki", "1", "-2"));
        assertEquals(expect(new Episode(kokki, -1)), matching("juuni", "kokki", "1", "-", "2[group]"));
    }

    @Test
    public void simpleSelect() {
        assertEquals(new Episode(kokki, 1), select(new Episode(kokki, 1), new Episode(sequel, -1)));
    }

    @Test
    public void noEpisodeSelection() {
        assertNull(select(new Episode(kokki, -1), new Episode(new Serie("other"), -1)));
        assertEquals(new Episode(kokki, -1), select(new Episode(kokki, -1)));
    }

    @Test
    public void sequelSelect() {
        assertEquals(new Episode(sequel, 1), select(new Episode(kokki, 2), new Episode(sequel, 1)));
        assertNull(select(new Episode(kokki, 1), new Episode(sequel, 1)));
        assertNull(select(new Episode(new Serie("test"), 2), new Episode(sequel, 1)));
        assertEquals(new Episode(sequel, -1), select(new Episode(kokki, -1), new Episode(sequel, -1)));
    }

	@Test
	public void singleMatchShouldBeReturned() {
		assertEquals(new Episode(kokki, 1), recognizer.recognize("[group] juuni kokki 1.avi"));
	}

    private Episode select(Episode... episodes) {
        List<Pair<Episode, String>> check = new ArrayList<Pair<Episode, String>>();
        for( Episode ep : episodes ) {
            check.add(Tuple.of(ep, Names.norm(ep.getSerie().getName())));
        }
        return recognizer.select(ImmutableSet.copyOf(check));
    }

    private ImmutableSet<Pair<Episode, String>> matching(String... args) {
        return recognizer.matching(ImmutableList.of(args));
    }

    private ImmutableSet<Pair<Episode,String>> expect(Episode... episodes) {
        List<Pair<Episode, String>> check = new ArrayList<Pair<Episode, String>>();
        for( Episode ep : episodes ) {
            check.add(Tuple.of(ep, Names.norm(ep.getSerie().getName())));
        }
        return ImmutableSet.copyOf(check);
    }
}

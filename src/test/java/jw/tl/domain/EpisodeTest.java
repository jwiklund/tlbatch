package jw.tl.domain;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import org.junit.Test;

public class EpisodeTest {

	Serie first = new Serie("Juuni Kokki");
	Episode firstEpisode = new Episode(first, 1);
	
	@Test
	public void shouldEqualSelf() {
		assertEquals(firstEpisode, firstEpisode);
	}
	
	@Test
	public void shouldEqualSerieWithSameName() {
		assertEquals(firstEpisode, new Episode(new Serie("Juuni Kokki"), 1));
	}
	
	@Test
	public void shouldNotEqualNull() {
		assertThat(firstEpisode, not(equalTo(null)));
		assertThat(firstEpisode, not(equalTo(new Episode(null, 1))));
	}
	
	@Test
	public void shouldNotEqualOtherNumber() {
		assertThat(firstEpisode, not(equalTo(new Episode(first, 2))));
	}
	
	@Test
	public void shouldNotEqualOtherSerie() {
		assertThat(firstEpisode, not(equalTo(new Episode(new Serie("Juuni Kokki 2"), 1))));
	}
}

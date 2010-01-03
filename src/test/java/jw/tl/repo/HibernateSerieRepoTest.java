package jw.tl.repo;

import java.sql.SQLException;
import jw.tl.domain.Serie;
import org.hibernate.SessionFactory;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class HibernateSerieRepoTest {

    DatabaseTestUtil util = new DatabaseTestUtil();
    SessionFactory factory = util.getFactory();
    HibernateSerieRepo repo = new HibernateSerieRepo(factory);

    @Before
    public void setup() {
        try {
            util.clear();
        } catch( SQLException e ) {
            // Skipp since the tables may not exist
        }
        util.run(addSeedData());
        util.begin();
    }

    private Runnable addSeedData() {
        return new Runnable() {
            @Override
            public void run() {
                Serie example = new Serie("Juuni Kokki");
                example.addAlias("Twelve Kingdoms");
                util.getFactory().getCurrentSession().persist(example);
                example = new Serie("Juuni Kokki 2");
                example.addAlias("Twelve Kingdoms 2");
                util.getFactory().getCurrentSession().persist(example);
                util.commit();
            }
        };
    }

    @Test
    public void byNameShouldReturnExactMatch() {
        Serie serie = repo.find("Juuni Kokki");
        assertNotNull(serie);
        assertEquals("Juuni Kokki", serie.getName());
        assertTrue(serie.hasAlias("juuni kokki"));
        assertTrue(serie.hasAlias("twelve kingdoms"));
    }

    @Test
    public void byAliasShouldReturnExactMatch() {
        Serie serie = repo.findByAlias("juuni kokki");
        assertNotNull(serie);
        assertEquals("Juuni Kokki", serie.getName());
    }

    @Test
    public void countShouldCountLike() {
        assertEquals(2, repo.countAliasesStartingWith("juuni kokki"));
        assertEquals(1, repo.countAliasesStartingWith("juuni kokki 2"));
    }
}

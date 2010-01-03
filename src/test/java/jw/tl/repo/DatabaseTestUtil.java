package jw.tl.repo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import jw.tl.domain.Serie;
import jw.tl.domain.SerieAlias;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.cfg.ImprovedNamingStrategy;
import org.hibernate.context.ThreadLocalSessionContext;

public class DatabaseTestUtil {

    private String url = "jdbc:h2:mem:test";
    private SessionFactory factory = null;

    public SessionFactory getFactory() {
        if (factory == null) {
            factory = newFactory();
        }
        return factory;
    }

    public void clear() throws SQLException {
        delete("serie");
        delete("serie_alias");
    }
    private Connection connection = null;

    public Connection getConnection() throws SQLException {
        if (connection == null) {
            connection = DriverManager.getConnection(url);
        }
        return connection;
    }

    public void delete(String table) throws SQLException {
        Statement st = getConnection().createStatement();
        try {
            st.execute("delete from " + table);
        } finally {
            st.close();
        }
    }

    public void run(Runnable runnable) {
        Session session = getFactory().openSession();
        ThreadLocalSessionContext.bind(session);
        try {
            runnable.run();
        } finally {
            cleanup(session);
        }
    }

    private void cleanup(Session session) {
        try {
            ThreadLocalSessionContext.unbind(getFactory());
        } finally {
            try {
                if (session.getTransaction().isActive()) {
                    session.getTransaction().rollback();
                }
            } finally {
                session.close();
            }
        }
    }

    public void commit() {
        Session session = getFactory().getCurrentSession();
        session.beginTransaction();
        session.flush();
        session.getTransaction().commit();
    }
    private Session last = null;

    public void begin() {
        if (last != null) {
            cleanup(last);
        }
        last = factory.openSession();
        ThreadLocalSessionContext.bind(last);
    }

    private SessionFactory newFactory() {
        AnnotationConfiguration cfg = new AnnotationConfiguration();

        cfg.addAnnotatedClass(Serie.class);
        cfg.addAnnotatedClass(SerieAlias.class);

        cfg.setNamingStrategy(new ImprovedNamingStrategy());
        // use in memory h2 database
        cfg.setProperty("hibernate.connection.driver_class", "org.h2.Driver");
        cfg.setProperty("hibernate.connection.url", url);
        cfg.setProperty("hibernate.dialect", "org.hibernate.dialect.H2Dialect");
        // show sql
        cfg.setProperty("hibernate.show_sql", "true");
        cfg.setProperty("hibernate.format_sql", "true");
        // create tables
        cfg.setProperty("hibernate.hbm2ddl.auto", "create");
        // shortname for ThreadLocalSessionContext
        cfg.setProperty("hibernate.current_session_context_class", "thread");
        return cfg.buildSessionFactory();
    }
}

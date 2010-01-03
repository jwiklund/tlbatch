package jw.tl.repo ;

import java.util.List;

import jw.tl.domain.Serie;

import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;

import com.google.inject.Inject;

public class HibernateSerieRepo implements SerieRepo
{

    private SessionFactory factory ;

    @Inject
    public HibernateSerieRepo(SessionFactory factory)
    {
        this.factory = factory ;
    }

    @Override
    public Serie load(String uuid)
    {
        return (Serie) factory.getCurrentSession().load(Serie.class, uuid) ;
    }

    @Override
    public void save(Serie serie)
    {
        factory.getCurrentSession().save(serie) ;
    }

    @Override
    public Serie find(String name)
    {
        List<?> result = factory.getCurrentSession()
                .createCriteria(Serie.class).add(
                        Restrictions.eq("serieName", name)).list() ;
        if (result.size() == 0)
            return null ;
        return (Serie) result.get(0) ;
    }

    @Override
    public Serie findByAlias(String alias)
    {
        List<?> result = factory.getCurrentSession().createQuery(
                "select serie from SerieAlias where alias_name = ?").setString(
                0, alias).list() ;
        if (result.size() == 0)
            return null ;
        return (Serie) result.get(0) ;
    }

    @Override
    public int countAliasesStartingWith(String alias)
    {
        List<?> result = factory.getCurrentSession().createQuery(
                "select count(serie) from SerieAlias where alias_name like ?")
                .setString(0, alias + "%").list() ;
        return ((Long) result.get(0)).intValue() ;
    }

}

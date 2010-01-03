package jw.tl.domain ;

import java.io.Serializable;

import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import jw.tl.service.Names;

@javax.persistence.Entity
public class SerieAlias implements Serializable
{
    private static final long serialVersionUID = -7488931909261705730L ;

    @Id
    private String            aliasName ;
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Serie             serie ;

    protected SerieAlias()
    {
    }

    public SerieAlias(String alias, Serie serie)
    {
        this.aliasName = Names.norm(alias) ;
        this.serie = serie ;
    }

    public String getAliasName()
    {
        return aliasName ;
    }

    public Serie getSerie()
    {
        return serie ;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (obj instanceof SerieAlias)
        {
            SerieAlias that = (SerieAlias) obj ;
            if (!aliasName.equals(that.getAliasName()))
            {
                return false ;
            }
            if (!serie.equals(that.getSerie()))
            {
                return false ;
            }
            return true ;
        }
        return false ;
    }

    @Override
    public int hashCode()
    {
        int hashCode = getAliasName().hashCode() ;
        hashCode = hashCode * 13 + getSerie().hashCode() ;
        return hashCode ;
    }
}

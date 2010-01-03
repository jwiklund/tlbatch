package jw.tl.domain ;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@javax.persistence.Entity
public class Serie implements Serializable
{
    private static final long serialVersionUID = 3972195389434238522L ;

    @Id
    @Column(nullable = false, unique = true, length = 36)
    private String            uuid ;
    @Column(nullable = false, unique = true)
    private String            serieName ;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "serie")
    private Set<SerieAlias>   aliases          = new HashSet<SerieAlias>() ;

    public Serie()
    {
        this("") ;
    }

    public Serie(String name)
    {
        uuid = UUID.randomUUID().toString() ;
        serieName = name ;
        addAlias(name) ;
    }

    public String getName()
    {
        return serieName ;
    }

    public String getUUID()
    {
        return uuid ;
    }

    public void setName(String serieName)
    {
        SerieAlias alias = new SerieAlias(serieName, this) ;
        removeAlias(this.serieName) ;
        addAlias(alias.getAliasName()) ;
        this.serieName = serieName ;
    }

    public boolean hasAlias(String alias)
    {
        return aliases.contains(new SerieAlias(alias, this)) ;
    }

    public boolean hasAliasLike(String alias)
    {
        SerieAlias beginsWith = new SerieAlias(alias, this) ;
        for (SerieAlias actual : aliases)
        {
            if (actual.getAliasName().startsWith(beginsWith.getAliasName()))
                return true ;
        }
        return false ;
    }

    public void addAlias(String string)
    {
        aliases.add(new SerieAlias(string, this)) ;
    }

    public void removeAlias(String string)
    {
        aliases.remove(new SerieAlias(string, this)) ;
    }

    public boolean isSequelTo(Serie other)
    {
        if (other.getName().equals(getName()))
            return false ;
        if (getName().startsWith(other.getName()))
            return true ;
        return false ;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (obj instanceof Serie)
        {
            Serie that = (Serie) obj ;
            return uuid.equals(that.getUUID()) ;
        }
        return false ;
    }

    @Override
    public int hashCode()
    {
        return uuid.hashCode() ;
    }

    @Override
    public String toString()
    {
        return "[" + uuid + "] " + getClass().getSimpleName() + " " + serieName ;
    }
}

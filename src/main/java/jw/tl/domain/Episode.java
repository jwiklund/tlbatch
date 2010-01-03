package jw.tl.domain ;

public class Episode
{

    private final int   episode ;
    private final Serie serie ;

    public Episode(Serie serie, int episode)
    {
        this.episode = episode ;
        this.serie = serie ;
    }

    public int getEpisode()
    {
        return episode ;
    }

    public Serie getSerie()
    {
        return serie ;
    }

    @Override
    public int hashCode()
    {
        final int prime = 31 ;
        int result = 1 ;
        result = prime * result + episode ;
        result = prime * result
                + ((serie == null) ? 0 : serie.getName().hashCode()) ;
        return result ;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (this == obj)
            return true ;
        if (obj == null)
            return false ;
        if (getClass() != obj.getClass())
            return false ;
        Episode other = (Episode) obj ;
        if (episode != other.episode)
            return false ;
        if (serie == null)
        {
            if (other.serie != null)
                return false ;
        } else if (!serie.getName().equals(
                other.serie == null ? null : other.serie.getName()))
            return false ;
        return true ;
    }

    @Override
    public String toString()
    {
        return serie.getName()
                + (episode == -1 ? " Unknown" : " Episode " + episode) ;
    }
}

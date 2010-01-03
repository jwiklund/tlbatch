package jw.tl.util ;

public class Triple<A, B, C> extends Pair<A, B>
{

    public final C c ;

    public Triple(A a, B b, C c)
    {
        super(a, b) ;
        this.c = c ;
    }

    @Override
    public String toString()
    {
        return "(" + a + ", " + b + ", " + c + ")" ;
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean equals(Object obj)
    {
        if (obj == null)
        {
            return false ;
        }
        if (getClass() != obj.getClass())
        {
            return false ;
        }
        final Triple<A, B, C> other = (Triple<A, B, C>) obj ;
        if (this.a != other.a && (this.a == null || !this.a.equals(other.a)))
        {
            return false ;
        }
        if (this.b != other.b && (this.b == null || !this.b.equals(other.b)))
        {
            return false ;
        }
        if (this.c != other.c && (this.c == null || !this.c.equals(other.c)))
        {
            return false ;
        }
        return true ;
    }

    @Override
    public int hashCode()
    {
        int hash = 7 ;
        hash = 97 * hash + (this.a != null ? this.a.hashCode() : 0) ;
        hash = 97 * hash + (this.b != null ? this.b.hashCode() : 0) ;
        hash = 97 * hash + (this.c != null ? this.c.hashCode() : 0) ;
        return hash ;
    }
}

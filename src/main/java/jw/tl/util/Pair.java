package jw.tl.util ;

public class Pair<A, B>
{

    public final A a ;
    public final B b ;

    public Pair(A a, B b)
    {
        super() ;
        this.a = a ;
        this.b = b ;
    }

    @Override
    public String toString()
    {
        return "(" + a + ", " + b + ")" ;
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
        if (obj instanceof Pair<?, ?>)
        {

        }
        final Pair<A, B> other = (Pair<A, B>) obj ;
        if (this.a != other.a && (this.a == null || !this.a.equals(other.a)))
        {
            return false ;
        }
        if (this.b != other.b && (this.b == null || !this.b.equals(other.b)))
        {
            return false ;
        }
        return true ;
    }

    @Override
    public int hashCode()
    {
        int hash = 7 ;
        hash = 17 * hash + (this.a != null ? this.a.hashCode() : 0) ;
        hash = 17 * hash + (this.b != null ? this.b.hashCode() : 0) ;
        return hash ;
    }
}

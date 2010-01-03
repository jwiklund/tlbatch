package jw.tl.util ;

public class Tuple
{

    public static <A, B> Pair<A, B> of(A a, B b)
    {
        return new Pair<A, B>(a, b) ;
    }

    public static <A, B, C> Triple<A, B, C> of(A a, B b, C c)
    {
        return new Triple<A, B, C>(a, b, c) ;
    }

}

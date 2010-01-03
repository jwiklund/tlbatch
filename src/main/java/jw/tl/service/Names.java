/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package jw.tl.service ;

import java.util.Arrays;
import java.util.StringTokenizer;

import com.google.common.collect.ImmutableList;

/**
 * 
 * @author Jonas Wiklund
 */
public class Names
{

    public static String norm(String name)
    {
        StringTokenizer st = new StringTokenizer(name.replaceAll("_", " ")) ;
        StringBuilder sb = new StringBuilder() ;
        boolean first = true ;
        while (st.hasMoreTokens())
        {
            if (first)
                first = false ;
            else
                sb.append(" ") ;
            sb.append(st.nextToken().toLowerCase()) ;
        }
        return sb.toString() ;
    }

    public static ImmutableList<String> split(String name)
    {
        return ImmutableList.copyOf(Arrays.asList(norm(name).split(" "))) ;
    }
}

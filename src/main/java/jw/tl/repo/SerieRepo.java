/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package jw.tl.repo ;

import jw.tl.domain.Serie;

/**
 * 
 * @author Jonas Wiklund
 */
public interface SerieRepo
{

    Serie load(String uuid) ;

    void save(Serie serie) ;

    Serie find(String name) ;

    Serie findByAlias(String alias) ;

    int countAliasesStartingWith(String alias) ;
}

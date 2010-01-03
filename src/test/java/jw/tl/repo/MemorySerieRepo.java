package jw.tl.repo;

import java.util.ArrayList;
import java.util.List;
import jw.tl.domain.Serie;

public class MemorySerieRepo implements SerieRepo {

    private List<Serie> series = new ArrayList<Serie>();

    @Override
    public void save(Serie serie) {
        if (null != find(serie.getName())) {
            throw new RuntimeException("Duplicate name found");
        }
        if (null != load(serie.getUUID())) {
            throw new RuntimeException("Duplicate UUID found");
        }
        series.add(serie);
    }

    @Override
    public Serie find(String name) {
        for (Serie serie : series) {
            if (serie.getName().equals(name)) {
                return serie;
            }
        }
        return null;
    }

    @Override
    public Serie load(String uuid) {
        for (Serie serie : series) {
            if (serie.getUUID().equals(uuid)) {
                return serie;
            }
        }
        return null;
    }

    @Override
    public Serie findByAlias(String alias) {
        for (Serie serie : series) {
            if (serie.hasAlias(alias)) {
                return serie;
            }
        }
        return null;
    }

    @Override
    public int countAliasesStartingWith(String alias) {
        int result = 0 ;
        for( Serie serie : series ) {
            if( serie.hasAliasLike(alias) )
                result++;
        }
        return result;
    }
}

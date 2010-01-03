package jw.tl.service ;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import jw.tl.domain.Episode;
import jw.tl.domain.Serie;
import jw.tl.repo.SerieRepo;
import jw.tl.util.Pair;
import jw.tl.util.Tuple;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.inject.Inject;

public class Recognizer
{

    private SerieRepo serieRepo ;

    @Inject
    public Recognizer(SerieRepo serieRepo)
    {
        this.serieRepo = serieRepo ;
    }

    public Episode recognize(String fileName)
    {
        String normalized = Names.norm(fileName) ;
        ImmutableList<String> parts = Names.split(normalized) ;
        return select(matching(parts)) ;
    }

    public ImmutableSet<Pair<Episode, String>> matching(
            ImmutableList<String> parts)
    {
        List<Pair<Episode, String>> episodes = new ArrayList<Pair<Episode, String>>() ;
        List<String> matching = new ArrayList<String>() ;
        for (int count = 0; count < parts.size(); count++)
        {
            List<String> nextMatching = new ArrayList<String>() ;
            for (String prior : matching)
            {
                String current = prior + " " + parts.get(count) ;
                Pair<Boolean, Pair<Episode, String>> result = check(current,
                        parts, count) ;
                if (result.a)
                {
                    nextMatching.add(current) ;
                }
                if (result.b != null)
                {
                    episodes.add(result.b) ;
                }
            }
            Pair<Boolean, Pair<Episode, String>> result = check(parts
                    .get(count), parts, count) ;
            if (result.a)
            {
                nextMatching.add(parts.get(count)) ;
            }
            if (result.b != null)
            {
                episodes.add(result.b) ;
            }
            matching = nextMatching ;
        }
        for (String alias : matching)
        {
            Pair<Boolean, Pair<Episode, String>> result = check(alias, parts,
                    parts.size()) ;
            if (result.b != null)
            {
                episodes.add(result.b) ;
            }
        }
        return ImmutableSet.copyOf(episodes) ;
    }

    private Pair<Boolean, Pair<Episode, String>> check(String alias,
            ImmutableList<String> parts, int current)
    {
        boolean matching = false ;
        Episode episode = null ;
        if (serieRepo.countAliasesStartingWith(alias) > 0)
        {
            matching = true ;
            Serie exactMatch = serieRepo.findByAlias(alias) ;
            if (exactMatch != null)
            {
                episode = constructEpisode(exactMatch, parts, current + 1) ;
            }
        }
        return Tuple.of(matching, episode == null ? null : Tuple.of(episode,
                alias)) ;
    }

    private Episode constructEpisode(Serie serie, ImmutableList<String> parts,
            int current)
    {
        StringBuilder rest = new StringBuilder() ;
        for (int i = current; i < parts.size(); i++)
        {
            rest.append(parts.get(i)).append(" ") ;
        }
        String theRest = rest.toString() ;
        final String batchPattern = "^-?[0-9]+ ?- ?[0-9]+" ;
        if (Pattern.compile(batchPattern).matcher(theRest).find())
        {
            return new Episode(serie, -1) ;
        }
        Pattern episodePattern = Pattern.compile("^-? ?([0-9])+") ;
        Matcher matcher = episodePattern.matcher(theRest) ;
        if (matcher.find())
        {
            return new Episode(serie, Integer.parseInt(matcher.group(1))) ;
        }
        return new Episode(serie, -1) ;
    }

    /**
     * Return the best match if it is clear what the match should be, ie prefer
     * a match with episode to one without number. Prefer a sequel when the
     * prequel could be a mistake for the sequel numbering. Else if it is not
     * clear return nothing.
     * 
     * @param matches
     * @return The best match or null if none is found or they are ambiguus
     */
    public Episode select(ImmutableSet<Pair<Episode, String>> matches)
    {
        Pair<Episode, String> best = null ;
        for (Pair<Episode, String> match : matches)
        {
            Pair<Boolean, Boolean> result = compare(best, match) ;
            if (result.a)
            {
                return null ;
            }
            if (result.b)
            {
                best = match ;
            }
        }
        return best == null ? null : best.a ;
    }

    /**
     * @return (ambiguus, next is better than current)
     */
    private Pair<Boolean, Boolean> compare(Pair<Episode, String> current,
            Pair<Episode, String> next)
    {
        if (current == null)
        {
            return Tuple.of(false, true) ;
        }
        if (current.a.getEpisode() == -1 || next.a.getEpisode() == -1)
        {
            if (current.a.getEpisode() == -1 && next.a.getEpisode() == -1)
            {
                if (current.b.startsWith(next.b))
                    return Tuple.of(false, false) ;
                if (next.b.startsWith(current.b))
                    return Tuple.of(false, true) ;
                return Tuple.of(true, false) ;
            } else if (current.a.getEpisode() != -1)
                return Tuple.of(false, false) ;
            else
                return Tuple.of(false, true) ;
        } else
        {
            if (current.a.getSerie().isSequelTo(next.a.getSerie()))
            {
                if (current.b.equals(next.b + " " + next.a.getEpisode()))
                    return Tuple.of(false, false) ;
            }
            if (next.a.getSerie().isSequelTo(current.a.getSerie()))
            {
                if (next.b.equals(current.b + " " + current.a.getEpisode()))
                    return Tuple.of(false, true) ;
            }
            return Tuple.of(true, false) ;
        }
    }

    // if( match.a.getEpisode() != -1 ) {
    // if( best == null ) {
    // best = match;
    // } else {
    // // best is sequel to match
    // if( best.a.getSerie().getName().startsWith(match.a.getSerie().getName())
    // ) {
    // if( best.b.equals(match.b + " " + match.a.getEpisode()) )
    // ; // ok match on first serie
    // else
    // return null; // multiple matches
    // // match is sequel to
    // } else if(
    // match.a.getSerie().getName().startsWith(match.a.getSerie().getName()) ) {
    // if( match.b.equals(best.b + " " + best.a.getEpisode()) )
    // best = match ; // better to match sequel
    // else
    // return null; // multiple matches
    // } else {
    // return null; // multiple matches
    // }
    // }
}

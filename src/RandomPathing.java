import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class RandomPathing
   implements PathingStrategy
{
   public List<Point> computePath(Point start, Point end,
      Predicate<Point> canPassThrough,
      BiPredicate<Point, Point> withinReach,
      Function<Point, Stream<Point>> potentialNeighbors)
   {
      /* Does not check withinReach.  Since only a single step is taken
       * on each call, the caller will need to check if the destination
       * has been reached.
       */
      List<Point> l = potentialNeighbors.apply(start)
         .filter(canPassThrough)
         .collect(Collectors.toList());
      Random r = new Random();

      Point p =  l.get(r.nextInt(l.size()));
      List<Point> retList = new ArrayList<>();
      retList.add(p);
      return retList;


   }
}
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.TreeSet;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class AStarPathingStrategy implements PathingStrategy
{

    public List<Point> computePath(Point start, Point end,
                                   Predicate<Point> canPassThrough,
                                   BiPredicate<Point, Point> withinReach,
                                   Function<Point, Stream<Point>> potentialNeighbors)
    {
        LinkedHashSet<Point> closedList = new LinkedHashSet<>();
        TreeSet<Node> openList = new TreeSet<>();
        Node current = new Node(start,end);
        openList.add(current);

        while(!(openList.isEmpty())) {
            current = openList.pollFirst();
            closedList.add(current.getPoint());
            List<Point> tempList = potentialNeighbors.apply(current.getPoint()).
                    filter(canPassThrough).
                    filter(point -> !(closedList.contains(point))).
                    collect(Collectors.toList());
            for (Point neighbor: tempList) {
                Node n = new Node(neighbor,current,end);
                if (withinReach.test(neighbor,end)) {
                    List<Point> path = new ArrayList<>();
                    while (n.getParent() != null) {
                        path.add(0, n.getPoint());
                        n = n.getParent();
                    }
                    return path;
                }
                openList.add(n);
            }
        }
        return new ArrayList<>();
    }
}


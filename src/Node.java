import java.util.Objects;

import static java.lang.Math.*;

public class Node implements Comparable<Node> {
    private Point point;
    private Node parent;
    private int g,h,f;


    public Node(Point point, Point end)
    {
        this.point = point;
        this.g = 0;
        this.h = abs(end.x - point.x) + abs(end.y - point.y);
        this.f = this.g + this.h;
        this.parent = null;

    }
    public Node(Point point, Node parent, Point end)
    {
        this.point = point;
        this.g = parent.g+ (int)(10* sqrt(exp(abs(parent.getPoint().x - point.x)) + exp(abs(parent.getPoint().y - point.y))));
        this.h = abs(end.x - point.x) + abs(end.y - point.y);
        this.f = this.g + this.h;
        this.parent = parent;

    }

    public Point getPoint() {return point;}

    public Node getParent() {
        return parent;
    }

    public int compareTo(Node n)
    {
        if (this.f != n.f) {
            return this.f - n.f;
        }
        else if (this.g != n.g) {
            return this.g - n.g;
        }
        else if (this.point.x != n.point.x){
            return this.point.x - n.point.x;
        }
        else {
            return this.point.y - n.point.y;
        }
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Node node = (Node) o;
        return g == node.g && h == node.h && f == node.f && point.equals(node.point);
    }

    public int hashCode() {
        return Objects.hash(point, g, h, f);
    }

    public String toString() {
        return "Node{" +
                "point=" + point +
                '}';
    }
}

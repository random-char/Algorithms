import edu.princeton.cs.algs4.StdDraw;

import java.util.Comparator;

public class Point implements Comparable<Point> {

    private final int x;
    private final int y;

    private class PointComparator implements Comparator<Point> {
        public Point invoking;

        PointComparator(Point invoking) {
            this.invoking = invoking;
        }

        public int compare(Point o1, Point o2) {
            return Double.compare(invoking.slopeTo(o1), invoking.slopeTo(o2));
        }
    }

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void draw() {
        StdDraw.point(x, y);
    }

    public void drawTo(Point that) {
        StdDraw.line(this.x, this.y, that.x, that.y);
    }

    public String toString() {
        return "(" + x + ", " + y + ")";
    }

    public int compareTo(Point that) {
        if (this.y < that.y || (this.y == that.y && this.x < that.x)) {
            return -1;
        } else if (this.x == that.x && this.y == that.y) {
            return 0;
        }
        return +1;
    }

    public double slopeTo(Point that) {
        if (this.y == that.y && this.x == that.x) {
            return Double.NEGATIVE_INFINITY;
        } else if (this.y == that.y) {
            return +0.0;
        } else if (this.x == that.x) {
            return Double.POSITIVE_INFINITY;
        } else {
            return (double) (that.y - this.y) / (double) (that.x - this.x);
        }
    }

    public Comparator<Point> slopeOrder() {
        return new PointComparator(this);
    }

    public static void main(String[] args) {
        Point p1 = new Point(0, 0);
        Point p2 = new Point(1, 1);

        System.out.println(p1.compareTo(p2));
    }
}
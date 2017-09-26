import edu.princeton.cs.algs4.Insertion;

public class FastCollinearPoints {

    private Point[] points;

    public FastCollinearPoints(Point[] points) {
        if (points == null) {
            throw new IllegalArgumentException();
        }
        Insertion.sort(points);
        for (int i = 0; i < points.length - 1; i++) {
            if (points[i] == null || points[i].compareTo(points[i + 1]) == 0) {
                throw new IllegalArgumentException();
            }
        }
        this.points = points;
    }

    public int numberOfSegments() {
        return 0;
    }

    public LineSegment[] segments() {
        return new LineSegment[1];
    }
}


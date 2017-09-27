import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Insertion;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

public class BruteCollinearPoints {

    private final Point[] points;
    private LineSegment[] lineSegmentsArray;
    private int segmentIndex = 0;

    public BruteCollinearPoints(Point[] points) {
        if (points == null) {
            throw new IllegalArgumentException();
        }
        for (int i = 0; i < points.length; i++) {
            if (points[i] == null) {
                throw new IllegalArgumentException();
            }
        }
        Insertion.sort(points);
        for (int i = 0; i < points.length - 1; i++) {
            if (points[i].compareTo(points[i + 1]) == 0) {
                throw new IllegalArgumentException();
            }
        }
        this.points = points;
        this.lineSegmentsArray = new LineSegment[2];
        this.processPoints();
    }

    private void increaseLineSegmentsArray() {
        LineSegment[] a = new LineSegment[lineSegmentsArray.length * 2];
        System.arraycopy(lineSegmentsArray, 0, a, 0, lineSegmentsArray.length);
        lineSegmentsArray = a;
    }

    private boolean lineSegmentAlreadyInArray(LineSegment newLineSegment) {
        for (LineSegment lineSegment: lineSegmentsArray) {
            if (lineSegment == null) break;
            if (lineSegment.toString().equals(newLineSegment.toString())) {
                return true;
            }
        }
        return false;
    }

    private void processPoints() {
        for (int i = 0; i < points.length; i++) {
            for (int j = 0; j < points.length; j++) {
                if (j == i) continue;
                for (int k = 0; k < points.length; k++) {
                    if (k == i || k == j) continue;
                    if (Double.compare(points[k].slopeTo(points[j]), points[k].slopeTo(points[i])) != 0) continue;
                    for (int y = 0; y < points.length; y++) {
                        if (y == i || y == j || y == k) continue;
                        if (Double.compare(points[y].slopeTo(points[k]), points[k].slopeTo(points[j])) == 0) {
                            Point[] fourCollinearPoints = {
                                    points[i],
                                    points[j],
                                    points[k],
                                    points[y]
                            };
                            Insertion.sort(fourCollinearPoints);
                            LineSegment newLineSegment = new LineSegment(fourCollinearPoints[0], fourCollinearPoints[3]);
                            if (!lineSegmentAlreadyInArray(newLineSegment)) {
                                if (segmentIndex == lineSegmentsArray.length) increaseLineSegmentsArray();
                                lineSegmentsArray[segmentIndex++] = newLineSegment;
                            }
                        }
                    }
                }
            }
        }
    }

    public int numberOfSegments() {
        return segmentIndex;
    }

    public LineSegment[] segments() {
        LineSegment[] a = new LineSegment[segmentIndex];
        System.arraycopy(lineSegmentsArray, 0, a, 0, segmentIndex);
        return a;
    }

    public static void main(String[] args) {

        // read the n points from a file
        In in = new In("equidistant.txt");
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
        System.out.println(collinear.numberOfSegments());
    }
}
import java.util.Arrays;
import edu.princeton.cs.algs4.*;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdDraw;

public class BruteCollinearPoints {
    
    private LineSegment[] segments = new LineSegment[1];
    private int numberOfSegments = 0;
    
    private void resize(int length) {
        //LineSegment[] oldArr = segments;
        LineSegment[] newArr = new LineSegment[segments.length*2];
        
        for (int i = 0; i < segments.length; i++) {
            newArr[i] = segments[i];
        }
        
        segments = newArr;
        newArr = null;                                                                                    
    }
    
    public BruteCollinearPoints(Point[] points) {
        if (points == null) {
            throw new NullPointerException();                   
        }
        
        Arrays.sort(points);
        int N = points.length;
        
        for (int i = 0; i < N; i++) {
            if (points[i] == null) {
                throw new NullPointerException();
            }
            if (i > 0 && points[i-1].compareTo(points[i]) == 0) { // Checks for duplicate points
                throw new IllegalArgumentException();
            }
            
            Point firstPoint = points[i];
            
            for (int j = i + 1; j < N; j++) { //Change from j = 0 to  j = i + 1 and from j < N to j < N-2?
                Point secondPoint = points[j];
                double segmentSlope = firstPoint.slopeTo(secondPoint);
                
                //if (points[i].compareTo(points[j]) != 0 && (j + 1 < N)) { // Skip if current iteration for i is the same point
                    for (int k = j + 1; k < N; k++) {
                        Point thirdPoint = points[k];
                        
                        if (segmentSlope == secondPoint.slopeTo(thirdPoint) && (k + 1 < N) ) {
                            for (int l = k + 1; l < N; l++) {
                                Point fourthPoint = points[l];
                                if (segmentSlope == thirdPoint.slopeTo(fourthPoint)) {
                                    //add segment
                                    LineSegment maximal = new LineSegment(firstPoint, fourthPoint);
                                    if (segments.length == numberOfSegments) resize(segments.length*2);
                                    segments[numberOfSegments++] = maximal;
                                    //break;
                                }
                            }
                            //break;
                        }
                    }
                //}
            }
        }
    }
    
    public int numberOfSegments() {
        return numberOfSegments;
    }
    
    public LineSegment[] segments() {
        
        return segments;
    }
    
    public static void main(String[] args) {
        // read the N points from a file
        In in = new In(args[0]);
        int N = in.readInt();
        //System.out.println("after N is read: " + in.readLine());
        Point[] points = new Point[N];
        for (int i = 0; i < N; i++) {
            int x = in.readInt();
            int y = in.readInt();
            //in.readLine();
            points[i] = new Point(x, y);
        }
        
        // draw the points
        StdDraw.show(0);
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

    }
    
}
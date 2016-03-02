import java.util.Arrays;
import edu.princeton.cs.algs4.*;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdRandom;

public class BruteCollinearPoints {
    
    private Point[] copyOfPoints;
    private LineSegment[] segments = new LineSegment[1];
    private int numberOfSegments = 0;
    
    private void resize(int length) {
        LineSegment[] newArr = new LineSegment[length];
        
        for (int i = 0; i < numberOfSegments; i++) {
            newArr[i] = segments[i];
        }
        
        segments = newArr;
        newArr = null;                                                                                    
    }
    
    private void checkForDuplicates(Point[] points) {
        for (int i = 1; i < points.length; i++) {
            if (points[i].compareTo(points[i-1]) == 0) {
                throw new IllegalArgumentException();
            }
        }
    }
    
    public BruteCollinearPoints(Point[] points) {
        if (points == null) {
            throw new NullPointerException();                   
        }
        
        int N = points.length;
        copyOfPoints = new Point[N];
        
        for (int i = 0; i < points.length; i++) {
            copyOfPoints[i] = points[i];
        }
        Arrays.sort(copyOfPoints);
        checkForDuplicates(copyOfPoints);
        
        for (int i = 0; i < N-3; i++) {
            if (copyOfPoints[i] == null) {
                throw new NullPointerException();
            }
            
            Point firstPoint = copyOfPoints[i];
            
            for (int j = i + 1; j < N-2; j++) { 
                Point secondPoint = copyOfPoints[j];
                double segmentSlope = firstPoint.slopeTo(secondPoint);
                
                for (int k = j + 1; k < N-1; k++) {
                    Point thirdPoint = copyOfPoints[k];
                   
                    if (segmentSlope == secondPoint.slopeTo(thirdPoint) ) { 
                        
                        for (int l = k + 1; l < N; l++) {
                            Point fourthPoint = copyOfPoints[l];
                            
                            double thirdToFourthSlope = thirdPoint.slopeTo(fourthPoint);
                 
                            if (segmentSlope == thirdPoint.slopeTo(fourthPoint)) {
                                
                                LineSegment maximal = new LineSegment(firstPoint, fourthPoint);
                                if (segments.length == numberOfSegments) resize(segments.length*2);
                                segments[numberOfSegments++] = maximal;
                                
                            }
                        }
                    }
                }
            
            }
            
        }
    }
    
    public int numberOfSegments() {
        return numberOfSegments;
    }
    
    public LineSegment[] segments() {
        return Arrays.copyOf(segments, numberOfSegments);
    }
    
    public static void main(String[] args) {
        // read the N points from a file
        
        In in = new In(args[0]);
        int N = in.readInt();
        
        Point[] points = new Point[N];
        for (int i = 0; i < N; i++) {
            int x = in.readInt();
            int y = in.readInt();
            
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
            if(segment != null) {  
                StdOut.println(segment);
                segment.draw();
            }
        }

    }
    
}
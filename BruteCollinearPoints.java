import java.util.Arrays;
import edu.princeton.cs.algs4.*;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdRandom;

public class BruteCollinearPoints {
    
    private LineSegment[] segments = new LineSegment[1];
    private int numberOfSegments = 0;
    
    private void resize(int length) {
        LineSegment[] newArr = new LineSegment[segments.length*2];
        
        for (int i = 0; i < segments.length; i++) {
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
        
        Arrays.sort(points);
        checkForDuplicates(points);
         
        int N = points.length;
        
        for (int i = 0; i < N-3; i++) {
            if (points[i] == null) {
                throw new NullPointerException();
            }
            
            Point firstPoint = points[i];
            
            for (int j = i + 1; j < N-2; j++) { 
                Point secondPoint = points[j];
                double segmentSlope = firstPoint.slopeTo(secondPoint);
                
                for (int k = j + 1; k < N-1; k++) {
                    Point thirdPoint = points[k];
                   
                    if (segmentSlope == secondPoint.slopeTo(thirdPoint) ) { 
                        
                        for (int l = k + 1; l < N; l++) {
                            Point fourthPoint = points[l];
                            
                            double thirdToFourthSlope = thirdPoint.slopeTo(fourthPoint);
                 
                            if (segmentSlope == thirdPoint.slopeTo(fourthPoint)) {
                                
                                /* Print out points of segment for debugging purposes.
                                System.out.println("first point: " + firstPoint);
                                System.out.println("second point: " + secondPoint);
                                System.out.println("third point: " + thirdPoint);
                                System.out.println("fourth point: " + fourthPoint);
                                */ 
                                
                                LineSegment maximal = new LineSegment(firstPoint, fourthPoint);
                                //System.out.println("New max line segment: " + maximal);
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
        
        return segments;
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
            if(segment != null) {  //Might have to resize array to take out null entries 
                StdOut.println(segment);
                segment.draw();
            }
        }

    }
    
}
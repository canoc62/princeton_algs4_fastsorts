import java.util.Arrays;
import edu.princeton.cs.algs4.*;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdDraw;

public class FastCollinearPoints {
    
    private final Point[] copyOfPoints;
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
    
    public FastCollinearPoints(Point[] points) {
        if (points == null) {
            throw new NullPointerException();                   
        }
    
        copyOfPoints = new Point[points.length];
        for (int i = 0; i < points.length; i++) {
            copyOfPoints[i] = points[i];
        }
        Arrays.sort(copyOfPoints);
        checkForDuplicates(copyOfPoints);
        
        int N = points.length;
        boolean maxSegment = true;
        for (int i = 0; i < N; i++) {
            Point p = points[i];
            Arrays.sort(copyOfPoints);//Sort so that points are in natural order
            Arrays.sort(copyOfPoints, p.slopeOrder());//Then sort so that points are in order by slope
        
            int count = 1;
            maxSegment = true;
            
            for (int j = 1; j < N; j++) {
                if (p.slopeTo(copyOfPoints[j]) == p.slopeTo(copyOfPoints[j-1])) {
                    count++;
                    
                    if (p.compareTo(copyOfPoints[j]) > 0 || p.compareTo(copyOfPoints[j-1]) > 0) {
                        maxSegment = false;
                    }
                    
                    if (count == 3 && maxSegment){
                        LineSegment newMaxSegment = new LineSegment(p, copyOfPoints[j]);
                        if (segments.length == numberOfSegments) resize(segments.length*2);      
                        segments[numberOfSegments++] = newMaxSegment;      
                    }
                    else if (count > 3 && maxSegment) {
                        LineSegment newMaxSegment = new LineSegment(p, copyOfPoints[j]);
                        segments[numberOfSegments-1] = newMaxSegment;
                    }
                }
                else {
                    count = 1;
                    maxSegment = true;
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
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            if(segment != null) {  
                StdOut.println(segment);
                segment.draw();
            }
        }
    }
    
}
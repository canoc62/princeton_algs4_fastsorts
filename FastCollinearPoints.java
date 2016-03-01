import java.util.Arrays;
import edu.princeton.cs.algs4.*;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdRandom;
import java.util.ArrayList;

public class FastCollinearPoints {
    
    private Point[] copyOfPoints;
    private LineSegment[] segments = new LineSegment[1];
    private LineSegment[] copyOfSegments;
    private int numberOfSegments = 0;
    
    private void resize(int length) {
        LineSegment[] newArr = new LineSegment[length];
        
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
    
    public FastCollinearPoints(Point[] points) {
        if (points == null) {
            throw new NullPointerException();                   
        }
        
        checkForDuplicates(points);
        Arrays.sort(points);
        copyOfPoints = new Point[points.length];
        for (int i = 0; i < points.length; i++) {
            copyOfPoints[i] = points[i];
            System.out.println(copyOfPoints[i]);
        }
        
        int N = points.length;
        boolean maxSegment = true;
        for (int i = 0; i < N; i++) {
            Arrays.sort(copyOfPoints);
            Arrays.sort(copyOfPoints, points[i].slopeOrder());
        
            int count = 1;
           //System.out.println("Points[i]: " + points[i]);
           
           // System.out.println("\nPOINT i: " + points[i] + "\n");
            maxSegment = true;
            for (int j = 1; j < N; j++) {
          //System.out.println("copyOfPoints[j]: " + copyOfPoints[j]);
               // System.out.println("j - 1: " + copyOfPoints[j-1] + " slope: " + points[i].slopeTo(copyOfPoints[j-1]));
               //System.out.println("j: " + copyOfPoints[j] + " slope: " + points[i].slopeTo(copyOfPoints[j]));
                if (points[i].slopeTo(copyOfPoints[j]) == points[i].slopeTo(copyOfPoints[j-1])) {
                    
                    count++;
                    if (points[i].compareTo(copyOfPoints[j]) > 0 || points[i].compareTo(copyOfPoints[j-1]) > 0) {
                        maxSegment = false;
                        //count = 1;
                    }
                   
                   // if (points[i].compareTo(copyOfPoints[j-1])
                   // if (points[i].compareTo(copyOfPoints[j-1]) > 0) {  
                    //   System.out.println("greater.");
                    //   count = 1;
                    //   break;
                    //}
                     
                   // System.out.println("count: " + count);
                    
                    if (count == 3 && maxSegment == true){//&& points[i].compareTo(copy)) {
                        //if(points[i].compareTo(copyOfPoints[j]) , 0) 
                        
                        LineSegment newMaxSegment = new LineSegment(points[i], copyOfPoints[j]);
                        if (segments.length == numberOfSegments) resize(segments.length*2);      
                        segments[numberOfSegments++] = newMaxSegment;
                        
                    }
                    else if (count > 3 && maxSegment == true) {
                       // System.out.println("Over 3.");
                        //segments[numberOfSegments-1] = null;
                        LineSegment newMaxSegment = new LineSegment(points[i], copyOfPoints[j]);
                        segments[numberOfSegments-1] = newMaxSegment;
                    }
                }
                else {
                    count = 1;
                    maxSegment = true;
                }
                
            }
        }
        //LineSegment[] copyOfSegments = Arrays.copyOf(segments, numberOfSegments);
        //System.out.println(numberOfSegments());
       if (numberOfSegments() > 0) resize(numberOfSegments());
        //System.out.println(numberOfSegments);
        //if (numberOfSegments > 0) resize(1); //so that it avoids run time error
        
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
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            if(segment != null) {  //Might have to resize array to take out null entries 
                StdOut.println(segment);
                segment.draw();
            }
        }
        //for (int i = 0; i < collinear.segments().length; i++) {
         //   StdOut.println(collinear.segments()[i]);
        //}

    }
    
}
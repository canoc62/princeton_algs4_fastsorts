import java.util.Arrays;
import edu.princeton.cs.algs4.*;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdRandom;

public class FastCollinearPoints {
    
    private Point[] copyOfPoints;
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
    
    public FastCollinearPoints(Point[] points) {
        Arrays.sort(points);
        copyOfPoints = new Point[points.length];
        for (int i = 0; i < points.length; i++) {
            copyOfPoints[i] = points[i];
            System.out.println(copyOfPoints[i]);
        }
        
        int N = points.length;
        
        for (int i = 0; i < N; i++) {
            Arrays.sort(copyOfPoints);
            Arrays.sort(copyOfPoints, points[i].slopeOrder());
          // for (int x = 0; x < points.length; x++) {
            //copyOfPoints[x] = points[i];
           // System.out.println(copyOfPoints[x]);
      // }
            int count = 1;
           
            System.out.println("\nPOINT i: " + points[i] + "\n");
            
            for (int j = 1; j < N-1; j++) {
          
                System.out.println("j - 1: " + copyOfPoints[j-1] + " slope: " + points[i].slopeTo(copyOfPoints[j-1]));
                System.out.println("j: " + copyOfPoints[j] + " slope: " + points[i].slopeTo(copyOfPoints[j]));
                if (points[i].slopeTo(copyOfPoints[j]) == points[i].slopeTo(copyOfPoints[j-1])) {
                    //if(points[i].compareTo(copyOfPoints[j]) > 0) { 
                      //  count = 0;
                    //}
                    //else {
                        count++;
                    //}
                        if(points[i].compareTo(copyOfPoints[j-1]) > 0) { //|| points[i].compareTo(copyOfPoints[j]) > 0) { 
                        //count = 0;
                        //i++;
                        break;
                    }
                     
                    System.out.println("count: " + count);
                    
                    if (count == 3 ){//&& points[i].compareTo(copy)) {
                        //if(points[i].compareTo(copyOfPoints[j]) , 0) 
                        LineSegment newMaxSegment = new LineSegment(points[i], copyOfPoints[j]);
                        if (segments.length == numberOfSegments) resize(segments.length*2);      
                        segments[numberOfSegments++] = newMaxSegment;
                        
                    }
                    else if (count > 3) {
                        segments[numberOfSegments-1] = null;
                        LineSegment newMaxSegment = new LineSegment(points[i], copyOfPoints[j]);
                        segments[numberOfSegments-1] = newMaxSegment;
                    }
                }
                else {
                    count = 1;
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
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            if(segment != null) {  //Might have to resize array to take out null entries 
                StdOut.println(segment);
                segment.draw();
            }
        }

    }
    
}
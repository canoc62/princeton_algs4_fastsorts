import java.util.Arrays;
import edu.princeton.cs.algs4.*;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdRandom;

public class FastCollinearPoints {
    
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
        int N = points.length;
        
        for (int i = 0; i < N; i++) {
            Arrays.sort(points, points[i].slopeOrder());
            for (int j = 1; j < N; i++) {
                int count = 0;
                if (points[i].slopeTo(points[j]) == points[i].slopeTo(points[j-1])) {
                    count++;
                    if (count == 3) {
                        LineSegment newMaxSegment = new LineSegment(points[i], points[j]);
                        segments[numberOfSegments++] = newMaxSegment;
                    }
                    else if (count > 3) {
                        segments[numberOfSegments-1] = null;
                        LineSegment newMaxSegment = new LineSegment(points[i], points[j]);
                        segments[numberOfSegments-1] = newMaxSegment;
                    }
                }
                //Need to check for multiples of segment containing same points but different order
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
        
        //System.out.println("Rand: " + StdRandom.uniform(1,11));
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
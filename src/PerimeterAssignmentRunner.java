import edu.duke.*;
import java.io.File;

public class PerimeterAssignmentRunner {
    public double getPerimeter (Shape s) {
        // Start with totalPerim = 0
        double totalPerim = 0.0;
        // Start with prevPt = the last point 
        Point prevPt = s.getLastPoint();
        // For each point currPt in the shape,
        for (Point currPt : s.getPoints()) {
            // Find distance from prevPt point to currPt 
            double currDist = prevPt.distance(currPt);
            // Update totalPerim by currDist
            totalPerim = totalPerim + currDist;
            // Update prevPt to be currPt
            prevPt = currPt;
        }
        // totalPerim is the answer
        return totalPerim;
    }

    public int getNumPoints (Shape s) {
        /* Track the number of points */
    	int numberPoints = 0;
    	for (Point point : s.getPoints()) {
    		if (point != null) {
        		numberPoints += 1;
    		}
    	}
    	
        return numberPoints;
    }

    public double getAverageLength(Shape s) {
        
    	/* Track the total length and start with zero */
    	double totalLength = 0;
    	
    	/* Track the number of sides and start with zero */
    	int numberSides = 0;
    	
    	/* Track the previous point and start with the last point of the shape */
    	Point previousPoint = s.getLastPoint();
    	
    	/* 
    	 * For each current point of the shape
    	 * add the length to the previous point and count the number of sides of the shape
    	 */
    	for (Point currentPoint : s.getPoints()) {
    		
    		/*
    		 * Add to the total length 
    		 * the distance from the previous point to the current point 
    		 */
    		totalLength += previousPoint.distance(currentPoint);
    		
    		/* Increase by one the number of sides of the shape */
    		numberSides += 1;
    		
    		/* Update the previous point to the current point */
    		previousPoint = currentPoint;
    	}
    	
    	/* Calculate and return the average length */
        return totalLength / numberSides;
    }

    public double getLargestSide(Shape s) {
        // Put code here
        return 0.0;
    }

    public double getLargestX(Shape s) {
        // Put code here
        return 0.0;
    }

    public double getLargestPerimeterMultipleFiles() {
        // Put code here
        return 0.0;
    }

    public String getFileWithLargestPerimeter() {
        // Put code here
        File temp = null;    // replace this code
        return temp.getName();
    }

    public void testPerimeter () {
        FileResource fr = new FileResource();
        Shape s = new Shape(fr);
        double length = getPerimeter(s);
        System.out.println("perimeter = " + length);
        
        /* Test the getNumPoints() method */
        System.out.println("Number of Points = " + getNumPoints(s));
    }
    
    public void testPerimeterMultipleFiles() {
        // Put code here
    }

    public void testFileWithLargestPerimeter() {
        // Put code here
    }

    // This method creates a triangle that you can use to test your other methods
    public void triangle(){
        Shape triangle = new Shape();
        triangle.addPoint(new Point(0,0));
        triangle.addPoint(new Point(6,0));
        triangle.addPoint(new Point(3,6));
        for (Point p : triangle.getPoints()){
            System.out.println(p);
        }
        double peri = getPerimeter(triangle);
        System.out.println("perimeter = "+peri);
    }

    // This method prints names of all files in a chosen folder that you can use to test your other methods
    public void printFileNames() {
        DirectoryResource dr = new DirectoryResource();
        for (File f : dr.selectedFiles()) {
            System.out.println(f);
        }
    }

    public static void main (String[] args) {
        PerimeterAssignmentRunner pr = new PerimeterAssignmentRunner();
        pr.testPerimeter();
    }
}

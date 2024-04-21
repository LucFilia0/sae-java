package util;

/**
 * This class contains all the mathematics utilities that are made.
 * Because we don't need to import the Math package just for that...
 * 
 * @author Luc le Manifik
 */
public class Math {
    
    /**
     * Returns the gap between two numbers (doubles).
     * 
     * @param a (double) - The first number
     * @param b (double) - The second number
     * 
     * @return (double) - The gap between "a" and "b"
     * 
     * @author Luc le Manifik
     */
    public static double absoluteValue(double a, double b) {
        double sort;
        if(a < b)
            sort = b - a;
        else
            sort = a - b;
        return sort;
    }
}

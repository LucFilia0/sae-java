package util;

public class Math {
    
    public static double absoluteValue(double a, double b) {
        double sort;
        if(a < b)
            sort = b - a;
        else
            sort = a - b;
        return sort;
    }
}

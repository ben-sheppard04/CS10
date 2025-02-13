import java.awt.*;
import java.util.ArrayList;

public class TestQuadTreeMethods {
    public static void main(String[] args) {
        PointQuadtree <Point> paska = new PointQuadtree<Point>(new Point(5,5), 0, 0, 10, 10);
        paska.insert(new Point(6,6));
        paska.insert(new Point(7,7));
        paska.insert(new Point(1,1));
        paska.insert(new Point(3, 8));
        paska.insert(new Point(4, 9));
        ArrayList<Point> inCircle = (ArrayList<Point>)paska.findInCircle(6.5, 6.5, 7.0);
        System.out.println(paska.size());
        System.out.println(paska.allPoints());
        System.out.println(inCircle);

    }
}

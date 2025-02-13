import java.util.ArrayList;

public class TestPointInCircle {
    public static void main(String[] args) {
        PointQuadtree <MovingPoint> paska = new PointQuadtree<MovingPoint>(new MovingPoint(5,5, 800, 600), 0, 0, 800,600);
        paska.insert(new MovingPoint(5,6, 800, 600));
        paska.insert(new MovingPoint(5,4, 800, 600));
        paska.insert(new MovingPoint(5,3, 800, 600));
        paska.insert(new MovingPoint(5,2, 800, 600));
        ArrayList<MovingPoint> inCircle = (ArrayList<MovingPoint>)paska.findInCircle(5, 5, 7.0);
        System.out.println(paska.size());
        System.out.println(paska.allPoints());
        System.out.println(inCircle);
    }
}

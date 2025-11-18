package raytracer.math;

public class Point extends AbstractVec3 {

    public Point(double x, double y, double z) {
        super(x, y, z);
    }

    /**
     Soustraction de deux points. Renvoie un vecteur.
    **/
    public Vector subtract(Point p) {
        return new Vector(this.x - p.x, this.y - p.y, this.z - p.z);
    }

    /**
     Addition d'un point et d'un vecteur. Renvoie un point.
    **/
    public Point add(Vector v) {
        return new Point(this.x + v.x, this.y + v.y, this.z + v.z);
    }
}
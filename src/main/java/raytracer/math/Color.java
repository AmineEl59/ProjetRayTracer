package math;

public class Color extends AbstractVec3 {

    public Color() {
        super(0, 0, 0);
    }

    public Color(double r, double g, double b) {
        super(clamp(r), clamp(g), clamp(b));
    }

    public double getR() { return this.x; }
    public double getG() { return this.y; }
    public double getB() { return this.z; }

    public Color add(Color c) {
        return new Color(this.x + c.x, this.y + c.y, this.z + c.z);
    }

    public Color multiply(double scalar) {
        return new Color(this.x * scalar, this.y * scalar, this.z * scalar);
    }

    public Color schur(Color c) {
        return new Color(this.x * c.x, this.y * c.y, this.z * c.z);
    }

    private static double clamp(double val) {
        return Math.max(0.0, Math.min(1.0, val));
    }

    public int toRGB() {
        int red = (int) Math.round(this.x * 255);
        int green = (int) Math.round(this.y * 255);
        int blue = (int) Math.round(this.z * 255);

        return ((red & 0xff) << 16) +
               ((green & 0xff) << 8) +
               (blue & 0xff);
    }
}
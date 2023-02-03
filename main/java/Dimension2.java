public class Dimension2
{
    /**
     * {@code Dimension2} is a data type representing a two-dimensional value (x + xscale, y + yscale)
     * <p>
     *     Each dimension consists of an absolute offset and a relative scale.
     *     {@code Dimension2} is intended to be used alongside {@code AppPanel}s.
     */
    public int x;
    public int y;
    public double xScale;
    public double yScale;

    /** Constructors **/
    Dimension2()
    {
        x = 0;
        y = 0;
        xScale = 0;
        yScale = 0;
    }

    Dimension2(int _x, int _y)
    {
        x = _x;
        y = _y;
        xScale = 0;
        yScale = 0;
    }

    Dimension2(int _x, int _y, double _xScale, double _yScale)
    {
        x = _x;
        y = _y;
        xScale = _xScale;
        yScale = _yScale;
    }

    /** Getters **/
    public int getX() {return x;}
    public int getY() {return y;}
    public double getXScale() {return xScale;}
    public double getYScale() {return yScale;}

    /** Setters **/
    public void setX(int _x) {x = _x;}
    public void setY(int _y) {y = _y;}
    public void setXScale(double _xScale) {xScale = _xScale;}
    public void setYScale(double _yScale) {yScale = _yScale;}
    public void setOffset(int _x, int _y) {x = _x; y = _y;}
    public void setScale(double _xScale, double _yScale) {xScale = _xScale; yScale = _yScale;}
    public void setDimensions(int _x, int _y, double _xScale, double _yScale) {x = _x; y = _y; xScale = _xScale; yScale = _yScale;}
}

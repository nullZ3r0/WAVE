import java.util.ArrayList;

public class UITransform
{
    // Attributes
    UDim2 position = new UDim2();
    UDim2 size = new UDim2();
    private int cornerRadius = 0;
    Vector2 anchor = new Vector2();
    private ArrayList<UIConstraint> constraints = new ArrayList<UIConstraint>();

    // Setters
    public void setSize(int _x, int _y, double _xScale, double _yScale)
    {
        size.set(_x, _y, _xScale, _yScale);
    }
    public void setPosition(int _x, int _y, double _xScale, double _yScale)
    {
        position.set(_x, _y, _xScale, _yScale);
    }
    public void addConstraint(UIConstraint constraint) {constraints.add(constraint);}
    public void setCornerRadius(int _cornerRadius) {cornerRadius = _cornerRadius;}

    // Getters
    public ArrayList<UIConstraint> getConstraints() {return constraints;}
    public int getCornerRadius() {return cornerRadius;}

}

class UDim2
{
    int x = 0;
    int y = 0;
    double xScale = 0;
    double yScale = 0;

    public void set(int _x, int _y, double _xScale, double _yScale)
    {
        this.x = _x;
        this.y = _y;
        this.xScale = _xScale;
        this.yScale = _yScale;
    }
    public void setOffset(int _x, int _y)
    {
        this.x = _x;
        this.y = _y;
    }

    public int getX() {return x;}
    public int getY() {return y;}

    public double getXScale() {return xScale;}
    public double getYScale() {return yScale;}
}

class Vector2
{
    // Attributes
    public float x;
    public float y;

    // Constructors
    public Vector2()
    {
        this.x = 0.0f;
        this.y = 0.0f;
    }

    public Vector2(float x, float y)
    {
        this.x = x;
        this.y = y;
    }
}

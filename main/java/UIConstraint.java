public class UIConstraint { }

class UISizeConstraint extends UIConstraint
{
    int minWidth = 0;
    int maxWidth = 9999;

    int minHeight = 0;
    int maxHeight = 9999;

    UISizeConstraint()
    {

    }

    UISizeConstraint(int _minWidth, int _maxWidth, int _minHeight, int _maxHeight)
    {
        minWidth = Math.min(_minWidth, _maxWidth); // Prioritise maxWidth
        maxWidth = _maxWidth;
        minHeight = Math.min(_minHeight, _maxHeight); // Prioritise maxHeight
        maxHeight = _maxHeight;
    }

    public void set(int _minWidth, int _maxWidth, int _minHeight, int _maxHeight)
    {
        minWidth = Math.min(_minWidth, _maxWidth); // Prioritise maxWidth
        maxWidth = _maxWidth;
        minHeight = Math.min(_minHeight, _maxHeight); // Prioritise maxHeight
        maxHeight = _maxHeight;
    }

    public int maxWidth() {return maxWidth;}
    public int minWidth() {return minWidth;}
}

class UIPositionConstraint extends UIConstraint
{
    int minX = 0;
    int maxX = 9999;

    int minY = 0;
    int maxY = 9999;

    UIPositionConstraint()
    {

    }

    UIPositionConstraint(int _minX, int _maxX, int _minHeight, int _maxHeight)
    {
        minX = Math.min(_minX, _maxX); // Prioritise maxWidth
        maxX = _maxX;
        minY = Math.min(_minHeight, _maxHeight); // Prioritise maxHeight
        maxY = _maxHeight;
    }

    public void set(int _minX, int _maxX, int _minHeight, int _maxHeight)
    {
        minX = Math.min(_minX, _maxX); // Prioritise maxWidth
        maxX = _maxX;
        minY = Math.min(_minHeight, _maxHeight); // Prioritise maxHeight
        maxY = _maxHeight;
    }

    public int maxX() {return maxX;}
    public int minX() {return minX;}
    public int maxY() {return maxY;}
    public int minY() {return minY;}
}
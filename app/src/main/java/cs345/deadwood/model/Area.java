package cs345.deadwood.model;

public class Area implements IArea {
    private int x;
    private int y;
    private int h;
    private int w;

    public Area(int x, int y, int h, int w) {
        this.x = x;
        this.y = y;
        this.h = h;
        this.w = w;
    }

    @Override
    public int getX() {
        return x;
    }

    @Override
    public int getY() {
        return y;
    }

    @Override
    public int getH() {
        return h;
    }

    @Override
    public int getW() {
        return w;
    }

    @Override
    public void setX(int x) {
        this.x=x;
    }

    @Override
    public void setY(int y) {
        this.y=y;
    }
}

package social_network.domain;

// Source - https://stackoverflow.com/a/2671052
// Posted by maerics, modified by community. See post 'Timeline' for change history
// Retrieved 2026-11-15, License - CC BY-SA 3.0

public class Tuple<X, Y> {
    public final X x;
    public final Y y;
    public Tuple(X x, Y y) {
        this.x = x;
        this.y = y;
    }
    public X getX() {
        return x;
    }
    public Y getY() {
        return y;
    }
}

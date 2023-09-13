package Math;

public class Transform {
    public int winding=0;
    private Vec2 position;
    private Mat2 transform;
    private static Vec2 _forward = new Vec2(new int[]{1, 0});
    private static Vec2 _backward = new Vec2(new int[]{-1, 0});
    private static Mat2 _right = new Mat2(new int[][]{{0, -1}, {1, 0}});
    private static Mat2 _left = new Mat2(new int[][]{{0, 1}, {-1, 0}});

    public Transform(Vec2 position) {
        this.position = position;
        transform = Mat2.IDENTITY;
    }

    public Transform() {
        this.position = new Vec2();
        transform = Mat2.IDENTITY;
    }
    public Vec2 getForward(){return Vec2.transform(_forward,transform);}
    public Vec2 getBackward(){return Vec2.transform(_backward,transform);}
    public void moveForward() {
        position.add(getForward());
    }
    public void moveBackward() {
        position.add(getBackward());
    }

    public void rotateLeft() {
        transform.multiply(_left);
        winding--;
    }

    public void rotateRight() {
        transform.multiply(_right);
        winding++;
    }

    public Vec2 getPosition() {
        return position;
    }

    public void setPosition(Vec2 position) {
        this.position = position;
    }


}

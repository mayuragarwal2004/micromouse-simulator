package Math;

public class Vec2 {
    public int arr[];

    public Vec2() {
        arr = new int[2];
        arr[0] = 0;
        arr[1] = 0;
    }

    public Vec2(int[] arr) {
        this.arr = arr;
    }

    public Vec2(int a, int b) {
        this.arr = new int[]{a, b};
    }

    void transform(Mat2 a) {
        int res[] = new int[2];
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                res[i] += a.mat[i][j] * arr[i];
            }
        }
        arr = res;
    }

    static Vec2 transform(Vec2 a, Mat2 b) {
        int res[] = new int[2];
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                res[i] += b.mat[i][j] * a.arr[j];
            }
        }
        return new Vec2(res);
    }

    public void add(Vec2 a) {
        for (int i = 0; i < 2; i++) {
            arr[i] += a.arr[i];
        }
    }

    public void subtract(Vec2 a) {
        for (int i = 0; i < 2; i++) {
            arr[i] -= a.arr[i];
        }
    }

    public static Vec2 multiply(Vec2 a, int b) {
        return new Vec2(a.arr[0] * b, a.arr[1] * b);
    }

    public static Vec2 add(Vec2 a, int b) {
        return new Vec2(a.arr[0] + b, a.arr[1] + b);
    }

    public static Vec2 subtract(Vec2 a, int b) {
        return new Vec2(a.arr[0] - b, a.arr[1] - b);
    }

    public static Vec2 add(Vec2 a, Vec2 b) {
        return new Vec2(a.arr[0] + b.arr[0], a.arr[1] + b.arr[1]);
    }

    public static Vec2 subtract(Vec2 a, Vec2 b) {
        return new Vec2(a.arr[0] - b.arr[0], a.arr[1] - b.arr[1]);
    }

    public int getX() {
        return arr[0];
    }

    public int getY() {
        return arr[1];
    }

    @Override
    public String toString() {
        return "<" + arr[0] + "," + arr[1] + ">";
    }

    @Override
    public boolean equals(Object a) {
        return (((Vec2) a).arr[0] == arr[0] && ((Vec2) a).arr[1] == arr[1]);
    }
    public Vec2 getCopy(){
        return new Vec2(arr[0],arr[1]);
    }
}

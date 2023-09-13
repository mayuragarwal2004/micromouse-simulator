package Math;

public class Mat2 {
    int[][] mat;
    public static Mat2 IDENTITY = new Mat2(new int[][]{{1, 0}, {0, 1}});
    public static Mat2 NULL = new Mat2(new int[][]{{0, 0}, {0, 0}});

    public Mat2(int[][] mat) {
        this.mat = mat;
    }

    void add(Mat2 a) {
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                mat[i][j] += a.mat[i][j];
            }
        }
    }

    Vec2 multiply(Vec2 a) {
        int res[] = new int[2];
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                res[i] += mat[i][j] * a.arr[i];
            }
        }
        return new Vec2(res);
    }

    void multiply(Mat2 a) {
        int res[][] = new int[2][2];
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                for (int k = 0; k < 2; k++) {
                    res[i][j] += mat[i][k] * a.mat[k][j];
                }
            }
        }
        mat = res;
    }

    @Override
    public String toString() {
        String s = "";
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                s += " " + mat[i][j] + " ";
            }
            s += "\n";
        }
        return s;
    }
}

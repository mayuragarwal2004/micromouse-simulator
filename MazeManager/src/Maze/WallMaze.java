package Maze;

import Math.Vec2;

public class WallMaze implements OffsetMaze {
    int M, N;
    boolean[][] horizontal;
    boolean[][] vertical;
    private static Vec2 topOffset = new Vec2(new int[]{0, -1});
    private static Vec2 rightOffset = new Vec2(new int[]{1, 0});
    private static Vec2 leftOffset = new Vec2(new int[]{-1, 0});
    private static Vec2 bottomOffset = new Vec2(new int[]{0, 1});

    boolean getWallFromOffset(Vec2 pos, Vec2 o) {
        int i = pos.arr[0];
        int j = pos.arr[1];
        if (o.equals(topOffset)) {

            return horizontal[i][j];
        }
        if (o.equals(bottomOffset)) {
            return horizontal[i][j + 1];
        }
        if (o.equals(leftOffset)) {
            return vertical[i][j];
        }
        if (o.equals(rightOffset)) {
            return vertical[i + 1][j];
        }
        ////WHAT ON EARTH
        System.out.println("OFFSET NOT FOUND");
        return false;
    }

    void setWallFromOffset(Vec2 pos, Vec2 o, boolean value) {
        int i = pos.arr[0];
        int j = pos.arr[1];
        if (o.equals(topOffset)) {

            horizontal[i][j] = value;
            return;
        }
        if (o.equals(bottomOffset)) {
            horizontal[i][j + 1] = value;
            return;
        }
        if (o.equals(leftOffset)) {
            vertical[i][j] = value;
            return;
        }
        if (o.equals(rightOffset)) {
            vertical[i + 1][j] = value;
            return;
        }
        ////WHAT ON EARTH
        System.out.println("OFFSET NOT FOUND:" + o);
//        return false;
    }

    public WallMaze(int M, int N) {
        horizontal = new boolean[M][N + 1];
        vertical = new boolean[M + 1][N];
    }

    public WallMaze(int M, int N, String[] hor, String[] vert) {
        this.M = M;
        this.N = N;
        horizontal = new boolean[M][N + 1];
        vertical = new boolean[M + 1][N];
        for (int i = 0; i < M; i++) {
            for (int j = 0; j < N + 1; j++) {
                horizontal[i][j] = (hor[j].charAt(i) == '1');
            }
        }
        for (int i = 0; i < M + 1; i++) {
            for (int j = 0; j < N; j++) {
                vertical[i][j] = (vert[j].charAt(i) == '1');
            }
        }
    }

    public WallMaze(int M, int N, int[] hor[], int[] vert[]) {
        this.M = M;
        this.N = N;
        horizontal = new boolean[M][N + 1];
        vertical = new boolean[M + 1][N];
        for (int i = 0; i < M; i++) {
            for (int j = 0; j < N + 1; j++) {
                horizontal[i][j] = (hor[j][(i)] == 1);
            }
        }
        for (int i = 0; i < M + 1; i++) {
            for (int j = 0; j < N; j++) {
                vertical[i][j] = (vert[j][(i)] == 1);
            }
        }
    }

    public WallMaze(int M, int N, boolean[] hor[], boolean[] vert[]) {
        this.M = M;
        this.N = N;
        horizontal = new boolean[M][N + 1];
        vertical = new boolean[M + 1][N];
        for (int i = 0; i < M; i++) {
            for (int j = 0; j < N + 1; j++) {
                horizontal[i][j] = (hor[j][(i)]);
            }
        }
        for (int i = 0; i < M + 1; i++) {
            for (int j = 0; j < N; j++) {
                vertical[i][j] = (vert[j][(i)]);
            }
        }
    }

    @Override
    public boolean getWall(Vec2 pos, Vec2 offset) {
        return getWallFromOffset(pos, offset);
    }

    public void setWall(Vec2 pos, Vec2 offset, boolean value) {
        setWallFromOffset(pos, offset, value);
    }

    @Override
    public String showWall(Vec2 pos) {
        int i = pos.arr[0];
        int j = pos.arr[1];
        return String.format("%c%c%c%c",
                boolToChar(horizontal[i][j]),
                boolToChar(vertical[i + 1][j]),
                boolToChar(horizontal[i][j + 1]),
                boolToChar(vertical[i][j])
        );
    }

    @Override
    public int getM() {
        return M;
    }

    @Override
    public int getN() {
        return N;
    }

    char boolToChar(boolean b) {
        return (b) ? '1' : '0';
    }


}

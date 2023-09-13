package Maze;

import Math.Vec2;

public class CellMaze implements OffsetMaze {
    int M, N;
    private Cell[][] cells;

    public CellMaze(int M, int N) {
        this.M = M;
        this.N = N;
        cells = new Cell[M][N];
        for (int i = 0; i < M; i++) {
            for (int j = 0; j < N; j++) {
                cells[i][j] = new Cell();
            }
        }
    }

    public CellMaze(int M, int N, String str[][]) {
        this.M = M;
        this.N = N;
        cells = new Cell[M][N];
        for (int i = 0; i < M; i++) {
            for (int j = 0; j < N; j++) {
                cells[i][j] = new Cell(str[j][i]);
            }
        }
    }

    public Cell getCell(int a, int b) {
        return cells[a][b];
    }

    @Override
    public boolean getWall(Vec2 pos, Vec2 offset) {
        return cells[pos.arr[0]][pos.arr[1]].getWallFromOffset(offset);
    }

    @Override
    public String showWall(Vec2 pos) {
        return cells[pos.arr[0]][pos.arr[1]].toString();
    }

    @Override
    public int getM() {
        return M;
    }

    @Override
    public int getN() {
        return N;
    }


    public void setWalls(String str[][]) {
        for (int i = 0; i < M; i++) {
            for (int j = 0; j < N; j++) {
                cells[i][j].setWalls(str[i][j]);
            }
        }
    }
}

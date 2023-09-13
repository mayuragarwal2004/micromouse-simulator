package Gameplay;

import Graphics.FrameManager;
import Maze.OffsetMaze;
import Maze.WallMaze;
import Math.Transform;

public class GameManager {
    private boolean finalRun = false;
    public static boolean SHOW_MAZE = false;
    public Player p;

    public OffsetMaze maze;


    private MoveHistory history_test;
    private MoveHistory history_final;
    //FrameManager frame;
    int M, N;
    private boolean complete = false;

    public GameManager(int M, int N, String hor[], String ver[]) {
        history_test = new MoveHistory();
        history_final = new MoveHistory();
        this.M = M;
        this.N = N;
        p = new Player();
        maze = new WallMaze(M, N, hor, ver);
        if (SHOW_MAZE) {
            FrameManager manager = new FrameManager(this.maze);
        }

    }

    public GameMove playMove(Move m) {

        boolean success = true;

        switch (m) {
            case FORWARD:
                if (maze.getWall(p.transform.getPosition(), p.transform.getForward())) {
                    success = false;
                } else {
                    p.transform.moveForward();
                }
                break;
            case LEFT:
                p.transform.rotateLeft();
                break;
            case RIGHT:
                p.transform.rotateRight();
                break;
            case BACK:
                if (maze.getWall(p.transform.getPosition(), p.transform.getBackward())) {
                    success = false;
                } else {
                    p.transform.moveBackward();
                }
        }

        //display();
        GameMove move = new GameMove(p.transform.getPosition().getCopy(), m, success, maze.showWall(p.transform.getPosition()));
        if (finalRun) {
            history_final.addMove(move);
        } else {
            history_test.addMove(move);
        }


        return move;
    }

    public String GetWall() {
        return maze.showWall(p.transform.getPosition());
    }

    public void display() {

        int mat[][] = new int[M][N];
        mat[p.transform.getPosition().arr[1]][p.transform.getPosition().arr[0]] = 1;
        for (int i = 0; i < M; i++) {
            for (int j = 0; j < N; j++) {
                if (mat[i][j] == 1) {
                    System.out.printf(" %c ", getArrowFromInt(p.transform.winding % 4));
                } else {
                    System.out.printf(" - ");
                }
            }
            System.out.println();
        }
        System.out.println();

    }

    static char getArrowFromInt(int a) {
        switch ((a + 4) % 4) {
            case 0:
                return '→';
            case 1:
                return '↓';
            case 2:
                return '←';
            case 3:
                return '↑';
        }
        return '?';
    }

    public MoveHistory getHistory_test() {
        return history_test;
    }

    public MoveHistory getHistory_final() {
        return history_final;
    }

    public void reset() {
        p.transform = new Transform();
        finalRun = true;
    }

    public boolean isComplete() {
        return complete;
    }

    public void setComplete(boolean complete) {
        this.complete = complete;
    }
}

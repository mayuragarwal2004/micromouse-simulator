package Graph;

import Maze.WallMaze;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Stack;
import java.util.function.Consumer;

import Math.Vec2;


public class Graph<T> {

    public Node<Vec2> root;

    public Graph(Vec2 val) {
        this.root = new Node<Vec2>(val);
    }

    @Override
    public String toString() {
        String s = "";
        HashSet<Node<Vec2>> set = new HashSet<>();
        Stack<Node<Vec2>> stack = new Stack<>();

        stack.add(root);
        while (!stack.isEmpty()) {
            Node<Vec2> t = stack.pop();
            set.add(t);
            s += t + "\n";
            for (Node n : t.GetNodes()) {
                if (!set.contains(n)) {
                    stack.add(n);
                }
            }
        }
        return s;
    }

    public WallMaze GetWallMaze(int M,int N) {

        boolean[][] hor = new boolean[M+1][N];
        boolean[][] ver = new boolean[M][N+1];
        for (int i = 0; i < M+1; i++) {
            for (int j = 0; j < N; j++) {
                hor[i][j] = true;

            }
        }
        for (int i = 0; i < M; i++) {
            for (int j = 0; j < N+1; j++) {
                ver[i][j] = true;

            }
        }
        WallMaze maze = new WallMaze(M,N, hor, ver);
        //do stuff;

        HashSet<Node<Vec2>> set = new HashSet<>();
        Stack<Node<Vec2>> stack = new Stack<>();

        stack.add(root);
        while (!stack.isEmpty()) {
            Node<Vec2> t = stack.pop();
            set.add(t);

            for (Node<Vec2> n : t.GetNodes()) {
                if (!set.contains(n)) {
                    Vec2 offset = Vec2.subtract(t.getValue(), n.getValue());
                    maze.setWall(t.getValue(), Vec2.multiply(offset,-1), false);
                    System.out.println("x");
                    stack.add(n);
                }
            }
        }


        return maze;
    }
}

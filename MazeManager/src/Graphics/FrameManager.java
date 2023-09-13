package Graphics;

import Gameplay.GameManager;
import Maze.OffsetMaze;

import javax.swing.*;
import java.awt.*;

import Math.Vec2;

public class FrameManager extends JPanel {
    OffsetMaze maze;
    // GameManager manager;

    public FrameManager(OffsetMaze maze) {
        JFrame frame = new JFrame();
        frame.setSize(600, 600);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(this);

        this.maze = maze;
    }

    @Override
    public void paint(Graphics g) {

        int x = 100, y = 100;
        int WIDTH = 10;
        int M = maze.getM();
        int N = maze.getN();
        int LENGTH = 400/M;
        //System.out.println(M+"\t"+N);
        super.paint(g);
        g.setColor(Color.BLACK);
        for (int i = 0; i < M; i++) {
            for (int j = 0; j < N + 1; j++) {
                if (maze.getWall(new Vec2(i, j), new Vec2(0, -1)))
                    g.fillRect(x + i * LENGTH, y + j * LENGTH, LENGTH + WIDTH, WIDTH);
            }

        }
        for (int i = 0; i < M + 1; i++) {
            for (int j = 0; j < N; j++) {
                if (maze.getWall(new Vec2(i, j), new Vec2(-1, 0)))
                    g.fillRect(x + i * LENGTH, y + j * LENGTH, WIDTH, LENGTH + WIDTH);
            }

        }
        g.setColor(Color.red);
        //g.fillRect(x + player.getX()+WIDTH+5, y + (N-1-player.getY())*LENGTH+WIDTH+5, 80, 80);
        //top
        /**
         switch (manager.p.transform.winding) {
         case 0:
         g.fillPolygon(new int[]{
         x + player.getX() + WIDTH + 5,
         x + player.getX() + WIDTH + 5,
         x + player.getX() + WIDTH + 5 + 80
         }, new int[]{
         y + (player.getY()) * LENGTH + WIDTH + 5,
         y + (player.getY()) * LENGTH + WIDTH + 5 + 80,
         y + (player.getY()) * LENGTH + WIDTH + 5 + 40
         }, 3);
         break;
         case 1:
         //right
         g.fillPolygon(new int[]{
         x + player.getX() + WIDTH + 5,
         x + player.getX() + WIDTH + 5 + 80,
         x + player.getX() + WIDTH + 5 + 40
         }, new int[]{
         y + (player.getY()) * LENGTH + WIDTH + 5,
         y + (player.getY()) * LENGTH + WIDTH + 5,
         y + (player.getY()) * LENGTH + WIDTH + 5 + 80
         }, 3);
         break;
         case 2:
         //bottom
         g.fillPolygon(new int[]{
         x + player.getX() + WIDTH + 5 + 80,
         x + player.getX() + WIDTH + 5 + 80,
         x + player.getX() + WIDTH + 5
         }, new int[]{
         y + (player.getY()) * LENGTH + WIDTH + 5,
         y + (player.getY()) * LENGTH + WIDTH + 5 + 80,
         y + (player.getY()) * LENGTH + WIDTH + 5 + 40
         }, 3);
         break;
         case 3:
         //left
         g.fillPolygon(new int[]{
         x + player.getX() + WIDTH + 5,
         x + player.getX() + WIDTH + 5 + 80,
         x + player.getX() + WIDTH + 5 + 40
         }, new int[]{
         y + (player.getY()) * LENGTH + WIDTH + 5 + 80,
         y + (player.getY()) * LENGTH + WIDTH + 5 + 80,
         y + (player.getY()) * LENGTH + WIDTH + 5
         }, 3);

         }*/
    }
}

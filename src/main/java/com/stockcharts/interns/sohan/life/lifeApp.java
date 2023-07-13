package com.stockcharts.interns.sohan.life;
import java.awt.Color;
import edu.princeton.cs.introcs.Picture;
import edu.princeton.cs.introcs.StdDraw;

public class lifeApp {
    private static final int WIDTH = 200;
    private static final int HEIGHT = 100;
    private static final int SCALING_FACTOR = 6;
    private static final Picture screen = new Picture(WIDTH * SCALING_FACTOR, HEIGHT * SCALING_FACTOR);
    private static final double RANDOM_FACTOR = 0.5D;

    public static void main(String[] args) {
        StdDraw.setCanvasSize((WIDTH * SCALING_FACTOR), (HEIGHT * SCALING_FACTOR));
        StdDraw.setXscale(0, WIDTH);
        StdDraw.setYscale(0, HEIGHT);

        boolean[][] grid;
        //grid = generateRandomGrid();
        grid = generateCenterGrid();

        while (true) {
            clearScreen();
            Color c;
            for (int j = 0; j < HEIGHT; j++) {
                for (int i = 0; i < WIDTH; i++) {
                    if (grid[i][j]) {
                        c = Color.white;
                    } else {
                        c = Color.black;
                    }
                    drawBox(i, j, c);
                }
            }
            screen.show();
            grid = updateGrid(grid);
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private static void clearScreen() {
        Color background = Color.WHITE;
        for (int i = 0; i < screen.width(); i++) {
            for (int j = 0; j < screen.height(); j++) {
                screen.set(i, j, background);
            }
        }
    }

    private static void drawBox(int x, int y, Color c) {
        int i = x * SCALING_FACTOR;
        int j = y * SCALING_FACTOR;

        if (x > 0) {
            for (int a = 0; a < SCALING_FACTOR; a++) {
                for (int b = 0; b < SCALING_FACTOR; b++) {
                    screen.set(i + a, j + b, c);
                }
            }
        } else {
            for (int a = 0; a < SCALING_FACTOR; a++) {
                for (int b = 0; b < SCALING_FACTOR; b++) {
                    screen.set(i + a, j + b, Color.BLACK);
                }
            }
        }

    }

    private static boolean[][] generateCenterGrid() {
        boolean[][] grid = new boolean[WIDTH][HEIGHT];
        int centerRow = HEIGHT / 2;
        int centerCol = WIDTH / 2;
        grid[centerCol][centerRow] = true;
        grid[centerCol + 1][centerRow] = true;
        grid[centerCol - 1][centerRow - 1] = true;
        grid[centerCol][centerRow - 1] = true;
        grid[centerCol][centerRow + 1] = true;
        return grid; 
    }

    private static boolean[][] generateRandomGrid() {
        boolean[][] grid = new boolean[WIDTH][HEIGHT];
        for (int j = 0; j < HEIGHT; j++) {
            for (int i = 0; i < WIDTH; i++) {
                grid[i][j] = Math.random() < RANDOM_FACTOR;
            }
        }
        return grid;
    }

    private static boolean[][] updateGrid(boolean[][] grid) {
        boolean[][] newGrid = new boolean[WIDTH][HEIGHT];
        for (int j = 0; j < HEIGHT; j++) {
            for (int i = 0; i < WIDTH; i++) {
                int aliveNeighbors = countAliveNeighbors(grid, i, j);
                if (grid[i][j]) {
                    if (aliveNeighbors == 2 || aliveNeighbors == 3) {
                        newGrid[i][j] = true;
                    } else {
                        newGrid[i][j] = false;
                    }
                } else {
                    if (aliveNeighbors == 3) {
                        newGrid[i][j] = true;
                    }
                    else {
                        newGrid[i][j] = false;
                    }
                }
            }
        }
        return newGrid;
    }

    private static int countAliveNeighbors(boolean[][] grid, int x, int y) {
        int count = 0;
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (i == 0 && j == 0) {
                    continue;
                }
                int neighborX = x + i;
                int neighborY = y + j;
                if (isValidCell(neighborX, neighborY) && grid[neighborX][neighborY]) {
                    count++;
                }
            }
        }
        return count;
    }

    private static boolean isValidCell(int x, int y) {
        return x >= 0 && x < WIDTH && y >= 0 && y < HEIGHT;
    }
}



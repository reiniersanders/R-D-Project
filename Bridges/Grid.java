package project.randd;

import android.graphics.Color;

import java.util.ArrayList;
import java.util.List;

public class Grid {
    int selectX = -1;
    int selectY = -1;
    int[][] grid;
    int size;
    GameActivity gameActivity;
    List<Bridge> bridges;

    public Grid(String[] grid, int size, GameActivity gameActivity){
        this.gameActivity = gameActivity;
        this.grid = new int[size][size];
        this.size = size;
        bridges = new ArrayList();
        for(int row = 0; row < size; row++){
            for(int col = 0; col < size; col++){
                if (grid[row*size+col] == "")
                    this.grid[col][row] = -1;
                else
                    this.grid[col][row] = Integer.parseInt(grid[row*size+col]);
            }
        }
    }

    public void onClick(int x, int y) {
        if (selectX == -1){
            if (grid[x][y] != -1){
                if (grid[x][y] == 0)
                    gameActivity.showToast("point has maximum number of bridges");
                else {
                    selectX = x;
                    selectY = y;
                    gameActivity.setColor(x, y, Color.CYAN);
                }
            }
        }else if (x == selectX && y == selectY){
            if (existBridgeWithPoint(selectX, selectY)) {
                gameActivity.setColor(selectX, selectY, Color.RED);
            } else {
                gameActivity.setColor(selectX, selectY, Color.WHITE);
            }
            selectX = -1;
            selectY = -1;
        }else if (x != selectX && y != selectY){
            if (existBridgeWithPoint(selectX, selectY)) {
                gameActivity.setColor(selectX, selectY, Color.RED);
            } else {
                gameActivity.setColor(selectX, selectY, Color.WHITE);
            }
            selectX = -1;
            selectY = -1;
            gameActivity.showToast("bridges must be in a straight line");
        }else if (grid[x][y] != -1){
            if (freeSpaceBetweenPoints(selectX, selectY, x, y)) {
                if (grid[x][y] == 0) {
                    gameActivity.showToast("point has maximum number of bridges");
                    if (existBridgeWithPoint(selectX, selectY)) {
                        gameActivity.setColor(selectX, selectY, Color.RED);
                    } else {
                        gameActivity.setColor(selectX, selectY, Color.WHITE);
                    }
                } else {
                    bridges.add(new Bridge(selectX, selectY, x, y));
                    grid[selectX][selectY] -= 1;
                    grid[x][y] -= 1;
                    colorPoints(selectX, selectY, x, y, Color.YELLOW);
                    gameActivity.setColor(selectX, selectY, Color.RED);
                    gameActivity.setColor(x, y, Color.RED);
                }
                selectX = -1;
                selectY = -1;
            }else if (existExactlyOneBridge(selectX, selectY, x, y)){
                bridges.add(new Bridge(selectX, selectY, x, y));
                grid[selectX][selectY] -= 1;
                grid[x][y] -= 1;
                colorPoints(selectX, selectY, x, y, Color.MAGENTA);
                gameActivity.setColor(selectX, selectY, Color.RED);
                gameActivity.setColor(x, y, Color.RED);
                selectX = -1;
                selectY = -1;
            }else{
                gameActivity.showToast("something obstructs bridge");
                if (existBridgeWithPoint(selectX, selectY)) {
                    gameActivity.setColor(selectX, selectY, Color.RED);
                } else {
                    gameActivity.setColor(selectX, selectY, Color.WHITE);
                }
                selectX = -1;
                selectY = -1;
            }
            if (finished()){
                gameActivity.showToast("You completed the game!");
            }
        }
    }

    public boolean finished(){
        for(int row = 0; row < size; row++){
            for(int col = 0; col < size; col++){
                if (grid[col][row] > 0) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean freeSpaceBetweenPoints(int x1, int y1, int x2, int y2){
        if (y1 == y2){
            for(int x = Math.min(x1, x2)+1; x < Math.max(x1, x2); x++){
                if (grid[x][y1] != -1)
                    return false;
            }
        }else if (x1 == x2){
            for(int y = Math.min(y1, y2)+1; y < Math.max(y1, y2); y++){
                if (grid[x1][y] != -1)
                    return false;
            }
        }
        return true;
    }

    public boolean existBridgeWithPoint(int x, int y){
        for(Bridge b : bridges){
            if (b.hasPoint(x, y))
                return true;
        }
        return false;
    }

    public boolean existExactlyOneBridge(int x1, int y1, int x2, int y2){
        boolean exist = false;
        for(Bridge b : bridges){
            if (b.hasPoint(x1, y1) && b.hasPoint(x2, y2)){
                if (exist)
                    return false;
                else
                    exist = true;
            }
        }
        return exist;
    }

    public void colorPoints(int x1, int y1, int x2, int y2, int color){
        if (y1 == y2){
            for(int x = Math.min(x1, x2)+1; x < Math.max(x1, x2); x++){
                grid[x][y1] -= 1;
                gameActivity.setColor(x, y1, color);
            }
        }else if (x1 == x2){
            for(int y = Math.min(y1, y2)+1; y < Math.max(y1, y2); y++){
                grid[x1][y] -= 1;
                gameActivity.setColor(x1, y, color);
            }
        }
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg4inaline;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author Blake
 */
public class GameBoard {
    
    private GameTile[][] board;
    
    public GameBoard() {
        board = new GameTile[8][8];
    }
    
    private GameBoard(GameTile[][] newBoard) {
        board = newBoard;
    }
    
    public void printBoard() {
        for(int i = 0; i < 8; i++) {
            for(int j = 0; j < 8; j++) {
                if(board[i][j] == null) {
                    System.out.print("_ ");
                } else {
                    board[i][j].printTile();
                }
            }
            System.out.println();
        }
        System.out.println();
    }
    
    public void move(int x, int y, boolean player) {
        board[x][y] = new GameTile(player);
    }
    
    public void remove(int x, int y) {
        board[x][y] = null;
    }
    
    public int alphaBeta(int minimax, boolean opponent) {
        int value = 0;
        for(int i = 0; i < 8; i++) {
            for(int j = 0; j < 8; j++) {
                if(board[i][j] != null && board[i][j].getPlayer() == opponent) {
                    value += checkAroundDefense(i, j, opponent);
                    value += checkAroundOffense(i, j, opponent);
                }
            }
        }
        return value;
    }
    
    private int checkAroundDefense(int i, int j, boolean opponent) {
        int value = 0;
        if((i+1) < 8 && board[i+1][j] != null && board[i+1][j].getPlayer() == opponent) {
            value++;
        }
        if((i-1) > -1 && board[i-1][j] != null && board[i-1][j].getPlayer() == opponent) {
            value++;
        }
        if((j+1) < 8 && board[i][j+1] != null && board[i][j+1].getPlayer() == opponent) {
            value++;
        }
        if((j-1 > -1) && board[i][j-1] != null && board[i][j-1].getPlayer() == opponent) {
            value++;
        }
        System.out.println("def: " + value);
        return value;
    }
 
    private int checkAroundOffense(int i, int j, boolean opponent) {
        boolean player = !opponent;
        int value = 0;
        if((i+1) < 8 && board[i+1][j] != null && board[i+1][j].getPlayer() == player) {
            value--;
        }
        if((i-1) > -1 && board[i-1][j] != null && board[i-1][j].getPlayer() == player) {
            value--;
        }
        if((j+1) < 8 && board[i][j+1] != null && board[i][j+1].getPlayer() == player) {
            value--;
        }
        if((j-1 > -1) && board[i][j-1] != null && board[i][j-1].getPlayer() == player) {
            value--;
        }
        System.out.println("off: " + value);
        return value;
    }
    
    public boolean checkIfWin(boolean player) {
        int col = 0;
        int[] row = {0, 0, 0, 0, 0, 0, 0, 0};
        for(int i = 0; i < 8; i++) {
            for(int j = 0; j < 8; j++) {
                if(board[i][j] != null && board[i][j].getPlayer() == player) {
                    col++;
                    if(col == 4) {
                        return true;
                    }
                    row[j]++;
                    if(row[j] == 4) {
                        return true;
                    }
                } else {
                    row[j] = 0;
                    col = 0;
                }
            }
            col = 0;
        }
        return false;
    }
    
    public List<GameBoard> getNextMoves(boolean player) {
        List<GameBoard> boards = new ArrayList<>();
        Map<Integer, List<Integer>> pointMap = new HashMap<>();
        for(int i = 0; i < 8; i++) {
            for(int j = 0; j < 8; j++) {
                if(board[i][j] != null) {
                    if((i+1) < 8 && board[i+1][j] == null && (pointMap.get(i+1) == null || !pointMap.get(i+1).contains(j))) {
                        if(pointMap.get(i+1) == null) {
                            pointMap.put(i+1, new ArrayList<>());
                        }
                        pointMap.get(i+1).add(j);
                    }
                    if((i-1) > -1 && board[i-1][j] == null && (pointMap.get(i-1) == null || !pointMap.get(i-1).contains(j))) {
                        if(pointMap.get(i-1) == null) {
                            pointMap.put(i-1, new ArrayList<>());
                        }
                        pointMap.get(i-1).add(j);
                    }
                    if((j+1) < 8 && board[i][j+1] == null && (pointMap.get(i) == null || !pointMap.get(i).contains(j+1))) {
                        if(pointMap.get(i) == null) {
                            pointMap.put(i, new ArrayList<>());
                        }
                        pointMap.get(i).add(j+1);
                    }
                    if((j-1) > -1 && board[i][j-1] == null && (pointMap.get(i) == null || !pointMap.get(i).contains(j-1))) {
                        if(pointMap.get(i) == null) {
                            pointMap.put(i, new ArrayList<>());
                        }
                        pointMap.get(i).add(j-1);
                    }
                }
            }
        }
        Set<Integer> pointx = pointMap.keySet();
        for(int x : pointx) {
            List<Integer> pointy = pointMap.get(x);
            for(int y : pointy) {
                boards.add(getChildBoard(x, y, player));
            }
        }
        return boards;
    }

    private GameBoard getChildBoard(int i, int j, boolean player) {
        GameBoard newGameBoard = new GameBoard(cloneBoard());
        newGameBoard.move(i, j, player);
        return newGameBoard;
    }
    
    private GameTile[][] cloneBoard() {
        GameTile[][] clonedGameBoard = new GameTile[8][8];
        for(int i = 0; i < 8; i++) {
            clonedGameBoard[i] = board[i].clone();
        }
        return clonedGameBoard;
    }
    
}

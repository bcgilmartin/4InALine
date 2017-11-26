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
    private int h;
    
    public GameBoard() {
        board = new GameTile[8][8];
    }
    
    //will calculate h for new board
    private GameBoard(GameTile[][] newBoard, boolean player) {
        board = newBoard;
        h = this.alphaBeta(0, !player);
    }
    
    //prints board i = rows and j = cols
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
    
    //player places pieces on coordinate x,y
    public void move(int x, int y, boolean player) {
        board[x][y] = new GameTile(player);
    }
    
    //if needed, remove piece from x,y
    public void remove(int x, int y) {
        board[x][y] = null;
    }
    
    //calculates heuristic
    public int alphaBeta(int minimax, boolean opponent) {
        int value = 0;
        for(int i = 0; i < 8; i++) {
            for(int j = 0; j < 8; j++) {
                if(board[i][j] != null && board[i][j].getPlayer() == opponent) {
                    value += checkAroundDefense(i, j, opponent);
                } else if(board[i][j] != null && board[i][j].getPlayer() == !opponent) {
                    value += checkAroundOffense(i, j, opponent);
                }
            }
        }
        return value;
    }
    
    //checks around piece at i,j for similar pieces (DEFENSE)
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
 
    //checks around i,j for friendly pieces (OFFENSE)
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
    
    //returns true if the player entered has 4 in a row
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
    
    //returns list of gameboards that stem from this one
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

    //gets child board, must call setH because move is being called after construction
    private GameBoard getChildBoard(int i, int j, boolean player) {
        GameBoard newGameBoard = new GameBoard(cloneBoard(), player);
        newGameBoard.move(i, j, player);
        newGameBoard.setH(newGameBoard.alphaBeta(0, !player));
        return newGameBoard;
    }
    
    //clones the gameboard
    private GameTile[][] cloneBoard() {
        GameTile[][] clonedGameBoard = new GameTile[8][8];
        for(int i = 0; i < 8; i++) {
            clonedGameBoard[i] = board[i].clone();
        }
        return clonedGameBoard;
    }
    
    //gets the heuristic value
    public int getH() {
        return h;
    }

    //sets the H when needed
    public void setH(int newH) {
        h = newH;
    }
    
}

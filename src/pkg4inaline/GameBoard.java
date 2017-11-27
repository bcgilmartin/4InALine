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
    
    //prints board i = rows and j = cols
    public void printBoard(GameTile[][] board) {
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
    public GameTile[][] move(int x, int y, boolean player, GameTile[][] board) {
        board[x][y] = new GameTile(player);
        return board;
    }
    
    //if needed, remove piece from x,y
    public void remove(int x, int y, GameTile[][] board) {
        board[x][y] = null;
    }
    
    //calculates heuristic for true player
    public int alphaBeta(GameTile[][] board) {
        int value = 0;
        boolean player = true;
        for(int i = 0; i < 8; i++) {
            for(int j = 0; j < 8; j++) {
                if(board[i][j] != null && board[i][j].getPlayer() == !player) {
                    value += checkAroundDefense(i, j, !player, board);
                } else if(board[i][j] != null && board[i][j].getPlayer() == player) {
                    value += checkAroundOffense(i, j, !player, board);
                }
            }
        }
        return value;
    }
    
    //checks around piece at i,j for similar pieces (DEFENSE)
    private int checkAroundDefense(int i, int j, boolean opponent, GameTile[][] board) {
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
    private int checkAroundOffense(int i, int j, boolean opponent, GameTile[][] board) {
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
    public boolean checkIfWin(boolean player, GameTile[][] board) {
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
    public List<GameTile[][]> getNextMoves(boolean player, GameTile[][] board) {
        List<GameTile[][]> boards = new ArrayList<>();
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
                boards.add(getChildBoard(x, y, player, board));
            }
        }
        return boards;
    }

    //gets child board, must call setH because move is being called after construction
    private GameTile[][] getChildBoard(int i, int j, boolean player, GameTile[][] board) {
        GameTile[][] newBoard = cloneBoard(board);
        return move(i, j, player, newBoard);
    }
    
    //clones the gameboard
    private GameTile[][] cloneBoard(GameTile[][] board) {
        GameTile[][] clonedGameBoard = new GameTile[8][8];
        for(int i = 0; i < 8; i++) {
            clonedGameBoard[i] = board[i].clone();
        }
        return clonedGameBoard;
    }
    
}
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg4inaline;

import java.util.List;

/**
 *
 * @author Blake
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        GameTile[][] board = new GameTile[8][8];
        GameBoard gb = new GameBoard();
        
        gb.move(4, 4, true, board);
        gb.move(5, 3, false, board);
        System.out.println(gb.alphaBeta(0, board));
        gb.printBoard(board);
        System.out.println(gb.checkIfWin(false, board));
        List<GameTile[][]> childBoards = gb.getNextMoves(true, board);
        for(GameTile[][] childBoard : childBoards) {
            gb.printBoard(childBoard);
            System.out.println(gb.alphaBeta(0, childBoard));
        }
    }
    
}

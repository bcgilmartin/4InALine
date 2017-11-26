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
        GameBoard board = new GameBoard();
        board.move(4, 4, true);
        board.move(5, 3, false);
        System.out.println(board.alphaBeta(0, false));
        board.printBoard();
        System.out.println(board.checkIfWin(false));
        List<GameBoard> childBoards = board.getNextMoves(true);
        for(GameBoard childBoard : childBoards) {
            childBoard.printBoard();
            System.out.println(childBoard.alphaBeta(0, false));
        }
    }
    
}

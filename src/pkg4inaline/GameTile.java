/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg4inaline;

/**
 *
 * @author Blake
 */
public class GameTile {
    
    private boolean tile;
    
    public GameTile(boolean player) {
        tile = player;
    }
    
    public boolean getPlayer() {
        return tile;
    }

    void printTile() {
        if(tile) {
            System.out.print("X ");
        } else {
            System.out.print("O ");
        }
    }
    
}

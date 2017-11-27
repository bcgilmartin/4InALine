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

public class TestDriver {

	public static void main(String[] args) {
	GameBoard board = new GameBoard();
	final int depth = 3;
	GameTile[][] gt = new GameTile[8][8];
	for(int i = 0; i < 8; i++){
		for(int j = 0; j < 8; j++){
			gt[i][j] = null;
		}
	}
	AlphaBetaEngine ab = AlphaBetaEngine.getInstance();
	gt[5][5] = new GameTile(true);
	Node root = new Node(gt);
	Tree tree = new Tree(root);
	//pupulates tree to a depth of 4.
	tree.makeChildren(depth);
	
	
	
	
	
	
	//runs alpha beta pruning once. Prints best move with a depth of 4. 
	board.printBoard(ab.alphaBeta(root, 4, Integer.MIN_VALUE, Integer.MAX_VALUE, true).gt);
	
	
	
	
	
	}

}

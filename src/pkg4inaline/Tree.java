import java.util.List;

public class Tree {
	private Node root;
	private Node bestMove;
	private GameBoard board = new GameBoard();
	public Tree(Node root){
		this.root = root;
		bestMove = null;
	}
	public void makeChildren(int depth){
		List<GameTile[][]> children = board.getNextMoves(false, root.gt);
		for(int i = 0; i < children.size(); i++){
			Node temp = new Node(root, children.get(i));
			List<GameTile[][]> nextChildren = board.getNextMoves(true, children.get(i));
			for(int j = 0; j < nextChildren.size(); j++){
				Node nextTemp = new Node(temp, nextChildren.get(j));
				List<GameTile[][]> nextNextChildren = board.getNextMoves(false, nextChildren.get(j));
				for(int k = 0; k < nextNextChildren.size(); k++){
					Node nextNextTemp = new Node(nextTemp, nextNextChildren.get(k));
					List<GameTile[][]> nextNextNextChildren = board.getNextMoves(true, nextChildren.get(j));
					for(int l = 0; l < nextNextNextChildren.size(); l++){
						Node nextNextNextTemp = new Node(nextNextTemp, nextNextNextChildren.get(l));
						nextNextTemp.addChild(nextNextNextTemp);
					}

					nextTemp.addChild(nextNextTemp);
				}
				temp.addChild(nextTemp);
			}
			root.addChild(temp);	
		}		
	}
	
	public Node getRoot(){
		return root;
	}
	
	public void setBestMove(Node bestMove){
		this.bestMove = bestMove;
	}
	
	public void makeMove(){
		root = bestMove;
		bestMove = null;
	}
}

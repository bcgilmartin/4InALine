import java.util.List;

public class AlphaBetaEngine {
	private static AlphaBetaEngine instance;
	private GameBoard board;
	private AlphaBetaEngine(){
		board = new GameBoard();
	}
	
	public static AlphaBetaEngine getInstance(){
		if(instance == null){
			instance = new AlphaBetaEngine();
			return instance;
		}
		else{
			return instance;
		}

	}
	
	
	public Node alphaBeta(Node node, int depth, int alpha, int beta, boolean maximizingPlayer, boolean signn){
		boolean sign = signn;
		if(depth == 0){
			return node;
		}
		Node ret = node; 
		if(maximizingPlayer){
			int v = Integer.MIN_VALUE;
			List<GameTile[][]> children = board.getNextMoves(sign, node.gt);
			for(int i = 0; i < children.size(); i++){
				Node temp = new Node(node, children.get(i));
				node.addChild(temp);
			}
			List<Node> childrenNode = node.getChildren(false);
			for(int i = 0; i < childrenNode.size(); i++){
				Node temp = alphaBeta(childrenNode.get(i), depth-1, alpha, beta, false, sign);
				if(temp.getHeuristic() > v){
					ret = childrenNode.get(i);
					v = temp.getHeuristic();
				}
				if(v > alpha){
					alpha = v;
				}
	
				
				if(beta <= alpha){
					break;
				}
			}
			ret.setV(v);
			return ret;
		}
		else{
			int v = Integer.MAX_VALUE;
			List<GameTile[][]> children = board.getNextMoves(!sign, node.gt);
			for(int i = 0; i < children.size(); i++){
				Node temp = new Node(node, children.get(i));
				node.addChild(temp);
			}
			List<Node> childrenNode = node.getChildren(false);
			for(int i = 0; i < childrenNode.size(); i++){
				Node temp = alphaBeta(childrenNode.get(i), depth-1, alpha, beta, true, sign);
				if(temp.getHeuristic() < v){
					ret = childrenNode.get(i);
					v = temp.getHeuristic();
				}
				if(v < beta){
					beta = v;
				}
	
				
				if(beta <= alpha){
					break;
				}
			}
			ret.setV(v);
			return ret;
			
			
		}
	}
	
	
	

}

import java.util.List;

public class AlphaBetaEngine {
	private static AlphaBetaEngine instance;
	
	private AlphaBetaEngine(){
		
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
	
	
	public Node alphaBeta(Node node, int depth, int alpha, int beta, boolean maximizingPlayer){
		if(depth == 0){
			return node;
		}
		Node ret = node; 
		if(maximizingPlayer){
			int v = Integer.MIN_VALUE;
			List<Node> children = node.getChildren(false);
			for(int i = 0; i < children.size(); i++){
				Node temp = alphaBeta(children.get(i), depth-1, alpha, beta, false);
				if(temp.getHeuristic() > v){
					ret = children.get(i);
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
			List<Node> children = node.getChildren(true);
			for(int i = 0; i < children.size(); i++){
				Node temp = alphaBeta(children.get(i), depth-1, alpha, beta, true);
				if(temp.getHeuristic() < v){
					ret = children.get(i);
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

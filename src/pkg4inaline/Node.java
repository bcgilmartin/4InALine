import java.util.ArrayList;
import java.util.List;

public class Node {
	private Node parent;
	private List<Node> children;
	private int heuristic;
	private int alpha;
	private int beta;
	private GameBoard board = new GameBoard();
	private int v;
	public GameTile[][] gt;
	
	
	public Node(GameTile[][] gt){
		children = new ArrayList<Node>();
		this.gt = new GameTile[gt.length][];
		for(int i = 0; i < gt.length; i++)
		{
		  GameTile[] temp = gt[i];
		  this.gt[i] = new GameTile[gt.length];
		  System.arraycopy(temp, 0, this.gt[i], 0, gt.length);
		}
		parent = null;
		this.heuristic = board.alphaBeta(gt);// heuristic
		
	}
	public Node (Node parent, GameTile[][] gt){
		children = new ArrayList<Node>();
		this.gt = new GameTile[gt.length][];
		for(int i = 0; i < gt.length; i++)
		{
		  GameTile[] temp = gt[i];
		  this.gt[i] = new GameTile[gt.length];
		  System.arraycopy(temp, 0, this.gt[i], 0, gt.length);
		}
		this.parent = parent;
		this.heuristic = board.alphaBeta(gt);
	}
	public int getBeta() {
		return beta;
	}
	public void addChild(Node child){
		children.add(child);
		
	}
	public List<Node> getChildren(boolean player){
		List<GameTile[][]> nextMoves = board.getNextMoves(player, gt);
		for(int i = 0; i < nextMoves.size(); i++){
			children.add(new Node(this, nextMoves.get(i)));
		}
		return children;
	}
	public void setBeta(int beta) {
		this.beta = beta;
	}
	public int getAlpha() {
		return alpha;
	}
	public void setAlpha(int alpha) {
		this.alpha = alpha;
	}
	public int getHeuristic(){
		return heuristic;
	}
	public int getV() {
		return v;
	}
	public void setV(int v) {
		this.v = v;
	}
	
	
	
	
	
	
}

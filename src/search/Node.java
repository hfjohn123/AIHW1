package search;

public class Node {
	int g_cost, h_cost,s_cost;
	int corrd;
	public Node(){
		g_cost = 0;
		h_cost =0;
		s_cost = 0;
	}
	public Node(int corrd, int s_cost,int g_cost){
		this.corrd=corrd;
		this.s_cost=s_cost;
		this.g_cost=g_cost;
	}
	
}

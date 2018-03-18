import java.util.ArrayList;


public class Node {

	private ArrayList<Node> children;
	private String name;
	private int x, y;
	
	Node(int x, int y){
        this.x = x;
        this.y = y;
    }


    int getX(){
        return this.x;
    }

    int getY(){
        return this.y;
    }
	
	public Node(String name){
		children = new ArrayList<Node>();
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public boolean equals(Node other){
		return (this.getName().compareTo(other.getName())==0);
	}
}
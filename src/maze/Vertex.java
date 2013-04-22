/**
 * 
 * @author Mike Kucharski & Ashley Packard
 * 
 */

package maze;

import java.util.ArrayList;

class Vertex<T> implements VertexInterface<T>  {
	
	private T label;
	private ArrayList<T> adjList;
	private boolean visited;
	private T predecessor;
	
	// ctor
	public Vertex(T label, T predecessor){
		visited = false;
		this.label = label;
		adjList = new ArrayList<T>();
		predecessor = null;
	}
	
	// *********accessor and mutator methods*********
	@Override
	public T getPredecessor(){
		return predecessor;
	}
	@Override
	public void setPredecessor(T label){
		predecessor = label;
	}
	public void addNeighbor(T neighbor){
		adjList.add(neighbor);
	}
	@Override
	public T getLabel(){
		return label;
	}
	public ArrayList<T> getNeighbors(){
		return adjList;
	}
	
	// returns true if neighbors are present
	@Override
	public boolean hasNeighbors(){
		return !adjList.isEmpty();
	}
	
	// return true if vertex has been visited
	@Override
	public boolean isVisited(){
		return visited;
	}
	
	// mark as visited
	@Override
	public void visit(){
		visited = true;
	}
	
	// mark as unvisited
	@Override
	public void unVisit(){
		visited = false;
	}
	
	// to print out a vertex, print out the label and adjecency list
	public String toString(){
		String vert = "";
		vert += "Label: '" + label + "' " + " Neighbors: " + adjList;
		return vert;
	}

}

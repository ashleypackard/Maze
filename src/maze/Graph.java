/**
 * 
 * @author Mike Kucharski & Ashley Packard
 * 
 */

package maze;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Queue;
import java.util.Scanner;
import java.util.Stack;
import java.util.concurrent.LinkedBlockingQueue;

public class Graph
{
	// represent graph as a list of vertices
	private ArrayList<Vertex<Character>> vertices;
	private File vertexFile;
//	private Scanner in;
	
	public Graph(String inputFile)
	{
		vertexFile = new File(inputFile);
		vertices = new ArrayList<Vertex<Character>>();
	}
	
	// read vertices and their neighbors in from a text file
	public boolean populateVertices(){
		try {
			Scanner in = new Scanner(vertexFile);
			String line;
			while(in.hasNextLine())
			{
				// normalize input
				line = in.nextLine();
				line.trim();
				if (line.isEmpty())
					continue;
				line.replaceAll(" ", "").toUpperCase();

				// use ':' as the delimiter for vertices and their neighbors
				String[] data = line.split(":");
				if (data[0].length() != 1) {
					return false;
				} else {
					Vertex<Character> currentVertex = new Vertex<Character>(data[0].charAt(0), null);
					
					for (int i = 0; i < data[1].length(); i++)
						currentVertex.addNeighbor(data[1].charAt(i));

					vertices.add(currentVertex);
				}
			}
			in.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return true;
	}
	
	// mutator and accessor methods
	public void addVertices(Vertex<Character> vertex){
		vertices.add(vertex);
	}
	public ArrayList<Vertex<Character>> getVertices(){
		return vertices;
	}
	
	//Breadth First Search finds the shortest path from a given start and end position
	 public Queue<Character> breadthFirstTraversal(Character origin, Character endVertex)
	 {
		 // keep track of when we have found the endVertex
		Boolean done = false;
		// reset the vertices to be not visited
		resetVertices();
		// used to hold the shortest path
		Stack<Character> path = new Stack<Character>();
		// used to hold the vertexes that we come upon
	    LinkedBlockingQueue<Vertex<Character>> vertexQueue = 
	                               new  LinkedBlockingQueue<Vertex<Character>>();
	    
	    findVertex(origin).visit();
	    vertexQueue.add(findVertex(origin)); // enqueue vertex
	    
	    // cycle through all the vertexes in the queue until the end has been found
	    while (!done && !vertexQueue.isEmpty())
	    {
	    	Vertex<Character> frontVertex;
			try {

				// take the first element and make it the front vertex and get
				// it's neighbors
				frontVertex = vertexQueue.take();
				ArrayList<Character> neighbors = frontVertex.getNeighbors();

				// while we haven't found the endVertex keep searching!
				while (!done && !neighbors.isEmpty())
				{
					// get the first neighbor in the list and remove
					Vertex<Character> nextNeighbor = findVertex(neighbors.get(0));
					neighbors.remove(0);

					// if we haven't already been there then visit him!
					if (!nextNeighbor.isVisited()) 
					{
						nextNeighbor.visit();
						nextNeighbor.setPredecessor(frontVertex.getLabel());
						vertexQueue.add(nextNeighbor);
					} // end if

					// the end position was found, exit the while loops
					if (nextNeighbor.getLabel().equals(endVertex)) {done = true;}

				     } // end for
			     
			} catch (InterruptedException e) 
			{
				e.printStackTrace();
			}  // end catch
	      
	    } // end while
	    
	    // start with the end vertex and travel backwards using predecessors to find the shortest path
	    path.push(endVertex);
	    Vertex<Character> vertex = findVertex(endVertex);
	    while(vertex.getPredecessor() != null)
	    {
	    	vertex = findVertex(vertex.getPredecessor());
	    	path.push(vertex.getLabel());
	    }
	    //  used to put the path in reverse order
	    LinkedBlockingQueue<Character> traversalOrder = new LinkedBlockingQueue<Character>();
	    int size = path.size();
	    for(int i = 0; i < size; i++){
	    	traversalOrder.add(path.pop());
	    }
	    return traversalOrder;
	} // end getBreadthFirstTraversal

	// unvisit every vertex in list
	private void resetVertices() 
	{
		for(int i = 0; i < vertices.size(); i++)
			vertices.get(i).unVisit();
	}

	// to print out a graph, cycle through every vertex and print it out
	public String toString(){
		String vertList = "";
		for(int i = 0; i < vertices.size(); i++)
			vertList += vertices.get(i) + " \n";
		return vertList;
	}
	
	// return the first vertex (origin)
	public Vertex<Character> getOriginVertex(){
		return vertices.get(0);
	}
	
	public Queue<Vertex<Character>> depthFirstTraversal(Vertex<Character> originVertex){
		
		resetVertices(); // reset vertices in case previously traversed
		// data structures used to implement algorithm
		LinkedBlockingQueue<Vertex<Character>> traversalOrder = new LinkedBlockingQueue<Vertex<Character>>();
		Stack<Vertex<Character>> vertexStack = new Stack<Vertex<Character>>();
		
		// start at origin vertex
		originVertex.visit();
		traversalOrder.add(originVertex);
		vertexStack.push(originVertex);
		
		while(!vertexStack.isEmpty()){ //until all vertices are visited
			Vertex<Character> topVertex = vertexStack.peek();
			
			// keep track of all unvisited neighbors
			ArrayList<Character> neighbors = topVertex.getNeighbors();
			ArrayList<Vertex<Character>> unvisitedNeighborVertices = new ArrayList<Vertex<Character>>();
			for(int i = 0; i < neighbors.size(); i++){
				Vertex<Character> v  = findVertex(neighbors.get(i));
				if(!v.isVisited())
					unvisitedNeighborVertices.add(v);
			}
			
			// if there are no unvisited neighbors, move on from this vertex 
			// else visit all unvisted neighbors
			if(unvisitedNeighborVertices.isEmpty()) {
				vertexStack.pop();
			}else{
				Vertex<Character> nextNeighbor = unvisitedNeighborVertices.get(0);
				nextNeighbor.visit();
				traversalOrder.add(nextNeighbor);
				vertexStack.push(nextNeighbor);
			}
		}
		
		return traversalOrder;
		
	}
	
	// get the vertex representation of a character
	public Vertex<Character> findVertex(char c){
		for(int i = 0; i < vertices.size(); i++){
			if(vertices.get(i).getLabel() == c) return vertices.get(i);
		}
		return null;
		
	}
}

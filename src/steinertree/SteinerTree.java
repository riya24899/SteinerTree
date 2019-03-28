package steinertree;

import java.util.ArrayList;
import java.util.Scanner;

class node{
    int vertex;
    int weight;
    
    node(int vertex, int weight){
        this.vertex=vertex;
        this.weight=weight;
        
    }
    
    public boolean equals(Object O) {
    	if (O.getClass()!=this.getClass()) {
    		return false;
    	}
    	else {
    		node temp=(node)O;
    		if(temp.vertex==this.vertex)
    			return true;
    	}
    	return false;	
    }
}

class Dijkstra{
    
    int[] dist;
    int [] parent;
    ArrayList <Integer> unvisited;
    
    Dijkstra(int N){
    	parent= new int[N];
        dist = new int[N];
        unvisited = new ArrayList<Integer>();
        for (int i = 0; i < N; i++) 
        { 
        	
            dist[i] = Integer.MAX_VALUE; 
            unvisited.add(i);
        } 
        
    }
    
    public void calcDist(ArrayList<ArrayList<node>> graph, int source){
 
       this.dist[source-1]=0;
       parent[source-1]=-1;
       int nextNode=0;
       int par=0;
      
       while(unvisited.size()>0) {
    	   
    	   int min=Integer.MAX_VALUE;
    	   for(int i=0; i<dist.length;i++) {   //finding node with min distance
    		   if (min >= dist[i] && unvisited.contains(i)) 
               { 
                   min = dist[i]; 
                   nextNode=i;
               } 
    	   }
    	  parent[nextNode]=par;
    	  par=nextNode;
    	  int remove=unvisited.indexOf(nextNode);
    	  unvisited.remove(remove);
    	  
    	  for(int i=0;i<unvisited.size();i++) {  //updating unvisited nodes
    		  int temp=unvisited.get(i);
    		  node ind= new node(nextNode, 0);
    		  int index=graph.get(temp).indexOf(ind);
    		  if (index!=-1) {
    			  int newdist= min+graph.get(temp).get(index).weight;
        		  if(newdist<dist[temp]) {
        			  dist[temp]=newdist;
        		  }
    		  }
    		  
    	  }
    	  
       
       }
       
    }
    
   
}


public class SteinerTree {

    
    
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        
        //Taking input in STP format
        String Sec = sc.nextLine();
                
        int N= Integer.parseInt(sc.nextLine().split(" ")[1]);
        int E= Integer.parseInt(sc.nextLine().split(" ")[1]);
        ArrayList<ArrayList<node>> graph= new ArrayList<ArrayList<node>>();
        
        for(int i = 0;i<N;i++) graph.add(new ArrayList<node>()) ;
        
        for(int i=1;i<=E;i++){
            String[] inputEdge=sc.nextLine().split(" ");
            int x = Integer.parseInt(inputEdge[1])-1;
            int y = Integer.parseInt(inputEdge[2])-1;
            int z = Integer.parseInt(inputEdge[3]);
            graph.get(x).add(new node(y,z));
            graph.get(y).add(new node(x,z));
        }
        
        Dijkstra s=new Dijkstra(N);
        s.calcDist(graph, 1);
        System.out.println("ans");
        for (int i=0; i<s.dist.length;i++) {
        	System.out.println(s.dist[i]);
        }
        Sec = sc.nextLine();
        int T= Integer.parseInt(sc.nextLine().split(" ")[1]);
        ArrayList<Integer> Terminals = new ArrayList<Integer> ();
        for(int i =0;i<T;i++) Terminals.add(Integer.parseInt(sc.nextLine().split(" ")[1])-1);
        
        ArrayList<ArrayList<node>> Terminalgraph= new ArrayList<ArrayList<node>>();
        for(int i = 0;i<T;i++) Terminalgraph.add(new ArrayList<node>()) ;
        
//        Forming new grapf with terminals only 
        for(int i=0; i<T;i++) {
        	Dijkstra temp= new Dijkstra(T);
        	int terminal=Terminals.get(i);
        	temp.calcDist(graph, Terminals.get(i));
        	for (int j=0; j<Terminals.size();j++) {
        		int connecting=Terminals.get(j);
        		if (j!=terminal) {
        			int z= temp.dist[connecting];
        			Terminalgraph.get(terminal).add(new node(connecting,z));
                    Terminalgraph.get(connecting).add(new node(terminal,z));
        		}
        	}
        }
        
        //Dijkstra works haven't yet tested above new code tho
    }
    
}
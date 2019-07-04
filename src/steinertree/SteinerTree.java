//Angel Walia 2017132
//Riya Singh 2017309


package steinertree;

import java.util.ArrayList;
import java.util.Scanner;

class node{
    int dadvertex;
    int vertex;
    int weight;
    
    node(int vertex, int weight){
        this.vertex=vertex;
        this.weight=weight;
        
    }
    
    node(int dadvertex, int vertex,int weight){
        this.vertex=vertex;
        this.dadvertex=dadvertex;
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
      
       while(unvisited.size()>0) {
    	   
    	   int min=Integer.MAX_VALUE;
    	   for(int i=0; i<dist.length;i++) {   //finding node with min distance
    		   if (min >= dist[i] && unvisited.contains(i)) 
               { 
                   min = dist[i]; 
                   nextNode=i;
               } 
    	   }
    	  
    	  int remove=unvisited.indexOf(nextNode);
    	  unvisited.remove(remove);
    	  
    	  for(int i=0;i<unvisited.size();i++) {  //updating unvisited nodes
    		  int temp=unvisited.get(i);
    		  node ind= new node(nextNode, 0);
    		  int index=graph.get(temp).indexOf(ind);
    		  if (index!=-1) {
    			  int newdist= min+graph.get(temp).get(index).weight;
        		  if(newdist<dist[temp]) {
        			  parent[temp]=nextNode;
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
       
        
             
        
        Dijkstra s= new Dijkstra(N);
        s.calcDist(graph, 1);
        //System.out.println("ans");
        for (int i=0; i<s.dist.length;i++) {
        	//System.out.println(s.dist[i]);
        	//System.out.println("parent "+s.parent[i]);
        }
        
    
        Sec = sc.nextLine();
        Sec = sc.nextLine();
        Sec = sc.nextLine();
        int T= Integer.parseInt(sc.nextLine().split(" ")[1]);
        if (T==0) {
        	return;
        }
        ArrayList<Integer> Terminals = new ArrayList<Integer> ();
        for(int i =0;i<T;i++) Terminals.add(Integer.parseInt(sc.nextLine().split(" ")[1])-1);
        Sec = sc.nextLine(); //take the last end
        System.out.println();
        
        ArrayList<ArrayList<node>> Terminalgraph= new ArrayList<ArrayList<node>>();
        for(int i = 0;i<N;i++) Terminalgraph.add(new ArrayList<node>()) ;
        
//        Forming new graph with terminals only 
        for(int i=0; i<T;i++) {
        	Dijkstra temp= new Dijkstra(N);
        	int terminal=Terminals.get(i);
        	temp.calcDist(graph, Terminals.get(i)+1);
        	for (int j=0; j<T;j++) {
        		int connecting=Terminals.get(j);
        		if (connecting>terminal) {
        			//System.out.println(terminal + " " + connecting);
        			int z= temp.dist[connecting];
        			Terminalgraph.get(terminal).add(new node(connecting,z));
                    Terminalgraph.get(connecting).add(new node(terminal,z));
        		}
        	}
        }
        
        for (int i=0; i<N;i++) {        	
        	for (int j=0; j<Terminalgraph.get(i).size();j++) {
        		//System.out.println(i + " " + Terminalgraph.get(i).get(j).vertex + " " + Terminalgraph.get(i).get(j).weight);
        	}
        }
        
        Prims mst= new Prims(N, Terminals);
        mst.calcTree(Terminalgraph, Terminals.get(0)+1);
                
  
        ArrayList<ArrayList<node>> newgraph = new ArrayList<ArrayList<node>>();
        for(int i = 0;i<N;i++) newgraph.add(new ArrayList<node>()) ;
        
        for(int i=0;i<Terminals.size();i++){
            //System.out.println("Terminal: "+Terminals.get(i));
            if(mst.parent[Terminals.get(i)]!=-1){
                int from = mst.parent[Terminals.get(i)];
                int to = Terminals.get(i);
                //System.out.println("From: "+from+" To: "+to);
                Dijkstra d = new Dijkstra(N);
                
                d.calcDist(graph, Terminals.get(i)+1);
                int curpar= d.parent[from];
                //for(int p=0;p<N;p++) System.out.println(d.parent[p]); System.out.println();
                while(from!=to){
                    //System.out.println(curpar);
                    for(int y = 0;y<graph.get(from).size();y++){
                        if(graph.get(from).get(y).vertex==curpar){
                            //System.out.println(curpar + " "+from+" "+graph.get(from).get(y).weight);
                            if(!newgraph.get(from).contains(new node(curpar,graph.get(from).get(y).weight)))
                            newgraph.get(from).add(new node(curpar,graph.get(from).get(y).weight));
                            if(!newgraph.get(curpar).contains(new node(from,graph.get(from).get(y).weight)))
                            newgraph.get(curpar).add(new node(from,graph.get(from).get(y).weight));

                        }
                    }
                    
                    
                    from=curpar;
                    curpar=d.parent[from];
                    
                }
            }
        }
        
        
        
        
        //prims to remove cycles form the graph
        ArrayList<Integer> topass=new ArrayList<Integer>();
        for(int i=0;i<N;i++) topass.add(i);
        Prims no_cycle_mst = new Prims(N,topass);
        no_cycle_mst.calcTree(newgraph, Terminals.get(0)+1);
        
        ArrayList<node> L = new ArrayList<node> ();
        
        int cost =0;
        for(int i =0 ;i <N;i++){
            //System.out.println(no_cycle_mst.parent[i] + " " + no_cycle_mst.weight[i]);
            if(no_cycle_mst.weight[i] != Integer.MAX_VALUE && no_cycle_mst.parent[i]!= -1 ){
                //System.out.println(no_cycle_mst.parent[i] + " " + no_cycle_mst.weight[i]);
                L.add(new node(i+1,no_cycle_mst.parent[i]+1,no_cycle_mst.weight[i]));
                cost+=no_cycle_mst.weight[i];
            }
        }
        
        System.out.println("Section GRAPH");
        System.out.println("Nodes "+ L.size());
        System.out.println("Edges "+ (L.size()-1));
        for(int i =0;i<L.size();i++){
            System.out.println("E "+L.get(i).dadvertex + " "+ L.get(i).vertex + " " + L.get(i).weight);
        } 
        System.out.println("END");
        System.out.println();
        System.out.println("Section TERMINALS");
        System.out.println("Terminals "+ Terminals.size());
        for(int i= 0 ;i < Terminals.size();i++) System.out.println("T " + (Terminals.get(i)+1));
        System.out.println("END");
        System.out.println();
        System.out.println("COST");
        System.out.println(cost);
        System.out.println("END");
        
        
        
        
    
    }
}
package steinertree;

public class Prims {

	int[] weight;
    int [] parent;
    ArrayList <Integer> unadded;
    
    Dijkstra(int N){
    	weight= new int[N];
        parent = new int[N];
        unadded = new ArrayList<Integer>();
        for (int i = 0; i < N; i++) 
        { 
            weight[i] = Integer.MAX_VALUE; 
            unadded.add(i);
        } 
        
    }
    
    public void calcTree(ArrayList<ArrayList<node>> graph, int source){
    	 
        weight[source-1]=0;
        parent[source-1]=-1;
        int nextNode=0;
       
        while(unadded.size()>0) {
     	   
     	   int min=Integer.MAX_VALUE;
     	   for(int i=0; i<dist.length;i++) {   //finding node with min distance
     		   if (min >= dist[i] && unadded.contains(i)) 
                { 
                    min = dist[i]; 
                    nextNode=i;
                } 
     	   }
  
     	  int remove=unadded.indexOf(nextNode);
     	  unadded.remove(remove);
     	  
     	  for(int i=0;i<unadded.size();i++) {  //updating unvisited nodes
     		  int temp=unadded.get(i);
     		  node ind= new node(nextNode, 0);
     		  int index=graph.get(temp).indexOf(ind);
     		  if (index!=-1) {
     			  int ist= graph.get(temp).get(index).weight;
         		  if(dist<weight[temp]) {
         			  parent[temp]=nextNode;
         			  weight[temp]=dist;
         		  }
     		  }
     		  
     	  }
     	  
        
        }
        
     }
	
	public static void main(String[] args) {
		
	}
}

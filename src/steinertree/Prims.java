//Angel Walia 2017132
//Riya Singh 2017309
package steinertree;

import java.util.ArrayList;

public class Prims {

    int[] weight;
    int [] parent;
    ArrayList <Integer> unadded;
    
    Prims(int N, ArrayList<Integer> list){
    	weight= new int[N];
        parent = new int[N];
        ArrayList<Integer> temp= new ArrayList<Integer>();
        temp.addAll(list);
        unadded = temp;
        for (int i = 0; i < N; i++) 
        { 
            weight[i] = Integer.MAX_VALUE; 
        } 
        
    }
    
    public void calcTree(ArrayList<ArrayList<node>> graph, int source){
    	 
        weight[source-1]=0;
        parent[source-1]=-1;
        int nextNode=0;
       
        while(unadded.size()>0) {
     	   
     	   int min=Integer.MAX_VALUE;
     	   for(int i=0; i<weight.length;i++) {   //finding node with min distance
     		   if (min >= weight[i] && unadded.contains(i)) 
                { 
                    min = weight[i]; 
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
     			  int dist= graph.get(temp).get(index).weight;
         		  if(dist<weight[temp]) {
         			 
         			  parent[temp]=nextNode;
         			  weight[temp]=dist;
         		  }
     		  }
     		  
     	  }
     	  
        
        }
        
     }
	
}
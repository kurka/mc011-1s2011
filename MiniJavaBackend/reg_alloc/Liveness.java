package reg_alloc;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Hashtable;

import temp.Temp;
import util.List;
import flow_graph.FlowGraph;
import graph.Node;

public class Liveness extends InterferenceGraph
{
    private FlowGraph graph;
    
    public void addEdge(Node src, Node dst)
    {
        if ( src != dst && !dst.comesFrom(src) && !src.comesFrom(dst))
            super.addEdge(src, dst);
    }
    
    // estruturas usadas para computar a DFA
    private Hashtable<Node, HashSet<Temp>> in;
    private Hashtable<Node, HashSet<Temp>> out;
    private Hashtable<Node, HashSet<Temp>> gen;
    private Hashtable<Node, HashSet<Temp>> kill;
        
    public void show(PrintStream o)
    {       
        for ( List<Node> aux = this.nodes(); aux != null; aux = aux.tail )
        {
            Temp t = revMap.get(aux.head);
            
            o.print(t + ": [ ");
            for ( List<Node> adjs = aux.head.adj(); adjs != null; adjs = adjs.tail )
                o.print( revMap.get(adjs.head) + " ");
            o.println("]");
        }
    }
    
    // coisas uteis
    private MoveList moveList = null;
    
    private Hashtable<Node, Temp> revMap = new Hashtable<Node, Temp>();
    private Hashtable<Temp, Node> map = new Hashtable<Temp, Node>();
    
    public Liveness(FlowGraph g)
    {
        super();
    
        graph = g;
            
        computeGenKill();
        computeDFA();
        buildInterference();
    }

    public void dump(PrintStream outStream)
    {
        int c=0;
        for(List<Node> aux = graph.nodes(); aux != null; aux = aux.tail, c++)
        {
            HashSet<Temp> i = in.get(aux.head);
            HashSet<Temp> o = out.get(aux.head);
            HashSet<Temp> g = gen.get(aux.head);
            HashSet<Temp> k = kill.get(aux.head);
            
            outStream.println(c+": gen:"+g+" kill:"+k+" in:"+i+" out:"+o);
        }
    }
    
    private void computeGenKill()
    {
        kill = new Hashtable<Node, HashSet<Temp>>();
        gen  = new Hashtable<Node, HashSet<Temp>>();
        
        for(List<Node> nodes = graph.nodes(); nodes != null; nodes = nodes.tail)
        {
            HashSet<Temp> k = new HashSet<Temp>();
            HashSet<Temp> g = new HashSet<Temp>();
            
            // kill
            for ( List<Temp> aux = graph.def(nodes.head); aux != null; aux = aux.tail )
                k.add(aux.head);
            
            // gen
            for ( List<Temp> aux = graph.use(nodes.head); aux != null; aux = aux.tail )
                g.add(aux.head);
            
            kill.put(nodes.head, k);
            gen.put(nodes.head, g);
        }
    }
    
    private void computeDFA()
    {	
        // Create in, out, inprime, outprime
        in = new Hashtable<Node, HashSet<Temp>>();
        out = new Hashtable<Node, HashSet<Temp>>();
        Hashtable<Node, HashSet<Temp>> inprime = new Hashtable<Node, HashSet<Temp>>();
        Hashtable<Node, HashSet<Temp>> outprime = new Hashtable<Node, HashSet<Temp>>();
        HashSet<Temp> outTest = new HashSet<Temp>();
        HashSet<Temp> inTest = new HashSet<Temp>();

        // insert all nodes in reverse order, in a ArrayList
        ArrayList<Node> nodes = new ArrayList<Node>();
        for ( List<Node> aux = graph.nodes(); aux != null; aux = aux.tail )
        {
            nodes.add(0, aux.head); //insert head in the beginning of nodes array
            in.put(aux.head, new HashSet<Temp>()); // Initialize with [] 
            out.put(aux.head, new HashSet<Temp>());
        }
                
        boolean equal;
        do {
            equal = true;
            for (Node n : nodes) {
                //compare if (in[n] != inprime[n] || out[n] != outprime[n]) for all n)
                try {
                  //add the same set two times, to see if they are equal
                  //(addAll returns false if they are equal)
                  outTest.clear();
                  outTest.addAll(outprime.get(n)); //add the old set
                  if(outTest.addAll(out.get(n))){ //add again for testing
                    equal = false;
                  }
                  //the same for in
                  inTest.clear();
                  inTest.addAll(inprime.get(n));
                  if(inTest.addAll(in.get(n))){ 
                    equal = false;
                  }
                }
                catch (NullPointerException e){//in the first loop, the sets are empty
                  equal = false;
                }
                
                // out'[n] <- out[n];
                outprime.put(n, out.get(n));
                // in'[n] <- in[n]
				HashSet<Temp> i;
                i = inprime.put(n, in.get(n));
				//System.out.println("I = " + i); 
            }

            for (Node n : nodes) {
                // in[n] <- use[n] U (out[n] - def[n]);
                HashSet<Temp> t = new HashSet<Temp>();
                t.addAll(gen.get(n));
                t.addAll(out_minus_def(n));
                in.put(n, t);
                
                // out[n] <- U(s in succ[n]) (in[s]) 
                HashSet<Temp> r = new HashSet<Temp>();
                for ( List<Node> succ = n.succ(); succ != null; succ = succ.tail )
                {
                    Node s = succ.head;
                    r.addAll(in.get(s));
                } 
                out.put(n, r);
            }            
 
        } while (equal==false);
        System.out.println("Saiu");
    }
    
    private HashSet<Temp> out_minus_def(Node n) {
        HashSet<Temp> res = (HashSet<Temp>) out.get(n).clone();
        res.removeAll(kill.get(n));
        return res;
    }
    
    private Node getNode(Temp t)
    {
        Node n = map.get(t);
        
        if ( n == null )
        {
            n = this.newNode();
            
            map.put(t, n);
            revMap.put(n, t);
        }
        
        return n;
    }
    
    private void handle(Node instr)
    {
        for( List<Temp> defs = graph.def(instr); defs != null; defs = defs.tail )
        {
            Node currentTemp = this.getNode(defs.head);
            
            for( Temp liveOut : out.get(instr) )
            {                
                Node currentLiveOut = this.getNode(liveOut);
                this.addEdge(currentTemp, currentLiveOut);
            }
        }
    }
    
    private void handleMove(Node instr)
    {
        Node dst = this.getNode(graph.def(instr).head);
        Node src = this.getNode(graph.use(instr).head);
        
        moveList = new MoveList(src, dst, moveList);
        
        for( Temp t : out.get(instr) )
        {
            Node currentOut = this.getNode(t);
            
            if ( currentOut != src )
            {
                //this.addEdge(currentOut, dst);
                this.addEdge(dst, currentOut);
            }
        }
    }
    
    private void buildInterference()
    {
        // Estamos sentados sobre ombros de um gigante...
        // Aqui, nos temos uma lista sobre todos os temporarios
        // vivos no fim de cada no. Desta forma, eh relativamente
        // facil construir a lista de adjacencia.
        
        for ( List<Node> instrs = graph.nodes(); instrs != null; instrs = instrs.tail )
        {
            Node current = instrs.head;
            
            if ( graph.isMove(current))
                handleMove(current);
            else
                handle(current);
        }
    }
    
    public Node tnode(Temp temp)
    {
        Node n = map.get(temp);
        
        if ( n == null )
        {
            map.put(temp, n = newNode() );
            revMap.put(n, temp);
        }
        
        return n;
    }

    public Temp gtemp(Node node)
    {
        return revMap.get(node);
    }

    public MoveList moves()
    {
        return moveList;
    }

    public HashSet<Temp> Out(Node node)
    {
        return out.get(node);
    }    
}

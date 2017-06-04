package comp2;


import java.io.IOException;
import java.util.*;
import java.util.regex.Pattern;

import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.DefaultGraph;
import org.graphstream.stream.file.FileSinkDOT;

public class AutomataOperations {
	private static String getEClosure(Node n, String currState){
		String states = currState + "%" + n.getId();
		for(Edge e : n.getEachEdge()){
			String trans = e.getAttribute("label");
			if(trans.equals("epsilon") || trans.equals("Epsilon")){
				String endNode = e.getTargetNode().getId();
				if(endNode.equals(n.getId()))continue;
				if(states.contains(endNode))continue;
				states = getEClosure(e.getTargetNode(),states); 
			}
		}
		return states;
	}
	
	private static ArrayList<Node> checkTransition(Node n, String transiction){
		ArrayList<Node> finalState = new ArrayList<Node>();
		for(Edge e : n.getEachEdge()){
			String trans = e.getAttribute("label");
			if(!trans.equals(transiction))continue;
			String endNode = e.getTargetNode().getId();
			if(endNode.equals(n.getId()) && !endNode.equals(e.getSourceNode().getId()))continue;
			finalState.add(e.getTargetNode());
		}
		return finalState;
	}
	
	private static String generateString(HashSet<String> hs){
		String ret = "";
		for(String s: hs){
			if(s.equals(""))continue;
			ret += "%" + s;
		}
		return ret;
	}
	
	private static ArrayList<String> getNewStates(Automata a, String curr){
		ArrayList<String> oldStates = new ArrayList<>(Arrays.asList(curr.split("%")));
		ArrayList<String> newStates = new ArrayList<>();
		oldStates.remove(0);//o 1� � um espa�o
		for(String trans: a.transValues){
			HashSet<String> tmpStates = new HashSet<>();
			for(String s : oldStates){
				Node n = a.g.getNode(s);
				ArrayList<Node> nf = checkTransition(n,trans);
				if(nf.size() != 0){
					for(Node nn :nf){
						tmpStates.addAll(Arrays.asList(getEClosure(nn,"").split("%")));
					}
				}
			}
			newStates.add(generateString(tmpStates));
		}
		return newStates;
	}
	
	private static void addNodeEdge(Graph g,String trans, String b4node, String state, boolean hasNew){
		String newNode = state.replace("%", "-");
		if(hasNew)g.addNode(newNode);
		//System.out.println("Transaction From: " + b4node + " to " + newNode + " when: " + trans);
		String edge = trans + "" + b4node + "" + newNode;
		g.addEdge(edge, b4node, newNode, true);//<3 prof
		g.getEdge(edge).setAttribute("label", trans);
	}

	public static void addDeathState(Automata a, Graph g){
	    boolean hasDeath = false;
        for(int i = 0; i < a.g.getNodeCount(); i++){
            Node n = a.g.getNode(i);
            String startNode = n.getId();
            if(Automata.deathState.matcher(startNode).matches())continue;
            Iterator<Edge> edges = n.getEdgeSet().iterator();
            HashSet<String> states = new HashSet<>();
            while(edges.hasNext()){
                Edge e = edges.next();
                String trans = e.getAttribute("label");
                String endNode = e.getTargetNode().getId();
                if(endNode.equals(startNode) && !endNode.equals(e.getSourceNode().getId()))continue;
                states.add(trans);
            }
            if(states.size() < a.transValues.size()){
                for(String trans : a.transValues){
                    if(!states.contains(trans)){
                        addNodeEdge(g,trans,startNode,"new-death-node",!hasDeath);
                        hasDeath = true;
                    }
                }
            }
        }
    }

	public static void convert2DFA(Automata a){
		if(a.type < 1)return;
		Graph ret = new DefaultGraph(a.g.getId());
		String start = getEClosure(a.start,"");
		HashMap<String, Boolean> states = new HashMap<String, Boolean>();
		ret.addNode(start.replace("%", "-"));
		states.put(start, false);
		while(states.containsValue(false)){
			String curr = "";
			for(String state: states.keySet()){
				curr = (!states.get(state)) ? state : "";
				if(!curr.equals(""))break;
			}
			ArrayList<String> newStates = getNewStates(a,curr);
			String b4node = curr.replace("%", "-");
			for(int i = 0; i < newStates.size(); i++){
				String state = newStates.get(i);
				if(state.equals(""))continue;
				String trans = a.transValues.get(i);
				if(states.containsKey(state)){
					addNodeEdge(ret,trans,b4node,state,false);
					continue;
				}
				addNodeEdge(ret,trans,b4node,state,true);
				states.put(state, false);
			}
			states.put(curr, true);
		}
		addDeathState(a,ret);
        ret.display();
	}
	
	//verify tem de ser do genero trans1%trans2%trans3
	//converter para dfa antes :)
	public static boolean acceptString(Automata a, String verify){
		ArrayList<String> transactions = new ArrayList<String>(Arrays.asList(verify.split("%")));
		Node n = a.start;
		for(String trans : transactions){
			boolean hasT = false;
			for(Edge e : n.getEachEdge()){
				Node end = e.getTargetNode();
				if(end.equals(n) && !end.equals(e.getSourceNode()))continue;
				if(trans.equals(e.getAttribute("label"))){
					hasT = true;
					n = end;
					break;
				}
			}
			if(!hasT) return false;
		}
		return Pattern.compile(".*_end.*").matcher(n.getId()).matches();
	}
	
	public static void exportAutomata(Graph g, String fileName){
		FileSinkDOT fsd = new FileSinkDOT();
		fsd.setDirected(true);
		try {
			fsd.writeAll(g, fileName);
		} catch (IOException e) {
			System.err.println("Error: Saving dot file");
		}
	}

    public static Automata getComplement(Automata in) {
        Automata out = new Automata();
        out.start = in.start;
        out.transValues = in.transValues;
        Graph g = in.g;
        out.g = new DefaultGraph("!");

        for(int i = 0; i < in.g.getNodeCount(); i++){
            Node n = in.g.getNode(i);
            String nodeOppType = reverseNode(n);
            out.g.addNode(nodeOppType);
        }

        for(int i = 0; i <  g.getEdgeCount(); i++){
            Edge e = g.getEdge(i);
            Node start = e.getNode0();
            Node end = e.getNode1();

            String startNodeType = reverseNode(start);
            String endNodeType = reverseNode(end);
            out.g.addEdge(e.getId(), startNodeType, endNodeType, true);
            out.g.getEdge(e.getId()).setAttribute("label",e.getAttribute("label"));
        }

        return out;
    }

    // MORGAN LAW -> L1 ? L2 = not(not(L1) ? not(L2))
	/*public Automata diff(Automata in) {

		Automata notL1 = this.complement();
		Automata notL2 = in.complement();
		Automata union = notL1.Union(notL2);
		Automata diff = union.complement();
		return diff;
	}*/



    public static Automata getUnion (final Automata in1, final Automata in2) {
        Automata out = new Automata();
        out.g = new DefaultGraph("union");
        out.g.addNode(in1.start.getId() + " " + in2.start.getId());
        in1.start = out.g.getNode(0);

        ArrayList<String> transactions = in1.transValues;
        for(String s : in2.transValues){
            if(!transactions.contains(s))
                transactions.add(s);
        }
        out.transValues = transactions;
        int max = (in1.g.getNodeCount() > in2.g.getNodeCount()) ? in1.g.getNodeCount() : in2.g.getNodeCount();
        for(int i = 0; i < max; i++) {
            Node a = null;
            Node b = null;

            if(i < in1.g.getNodeCount())
                a = in1.g.getNode(i);
            if(i < in2.g.getNodeCount())
                b = in2.g.getNode(i);

            if(a != null && b != null){
                String source;
                for(String trans : transactions){
                    ArrayList<Node> t1 = checkTransition(a,trans);
                    ArrayList<Node> t2 = checkTransition(b,trans);
                    String target;
                    if(t1.size() > 0 && t2.size() > 0){
                        source = a.getId() + " " + b.getId();
                        String n1 = t1.get(0).getId();
                        String n2 = t2.get(0).getId();
                        target = n1 + " " + n2 + " " + trans;
                    }
                    else{
                        source = (t1.size() > 0) ? a.getId() : b.getId();
                        if(out.g.getNode(source) == null) source = a.getId() + " " + b.getId();
                        if(t1.size() == 0 && t2.size() == 0) continue;
                        target = (t1.size() == 0) ? t2.get(0).getId() : t1.get(0).getId();
                        target += " " + trans;
                    }
                    if(i == 0) source = a.getId() + " " + b.getId();
                    if(i != 0) source += " " + trans;
                    boolean addNode = out.g.getNode(target) == null;
                    addNodeEdge(out.g,trans,source,target,addNode);
                }
            }
            else{
                if( a == null && b == null) continue;
                String source = (a == null) ? b.getId() : a.getId();
                Node c = (a == null) ? b : a;
                for(String trans: transactions){
                    ArrayList<Node> t = checkTransition(c,trans);
                    if(t.size() > 0){
                        source += " " + trans;
                        String target = t.get(0).getId();
                        boolean addNode = out.g.getNode(target) == null;
                        addNodeEdge(out.g,trans,source,target,addNode);
                    }
                }
            }
        }
        exportAutomata(out.g,"lmao3.dot");
        return out;
    }

    private static String reverseNode(Node n){
        String nodeType = n.getId();
        nodeType = (Automata.endState.matcher(nodeType).matches()) ?
                nodeType.replaceAll("_end", "") : nodeType + "_end";
        return nodeType;
    }
}

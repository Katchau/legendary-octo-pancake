package comp2;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.DefaultGraph;
import org.graphstream.stream.file.FileSource;
import org.graphstream.stream.file.FileSourceFactory;

public class Teste {

	public static void main(String[] args) {
		String file = "test.dot";
		Graph g = new DefaultGraph("g");
		FileSource fs = null;
		try {
			fs = FileSourceFactory.sourceFor(file);
			fs.addSink(g);
			
			fs.begin(file);
			while(fs.nextEvents()){
				
			}
			
		} catch (IOException e) {
			System.err.println("Error: No such file" + file);
		}
		
		g.display();
//		ArrayList<String> states = new ArrayList<String>();
		
		for(int i = 0; i < g.getNodeCount(); i++){
			Node n = g.getNode(i);
			System.out.println("Node "+ i + " : " + g.getNode(i).getId());
			Iterator<Edge> edges = n.getEdgeSet().iterator();
			while(edges.hasNext()){
				Edge e = edges.next();
				System.out.println("Edge " + e.getAttribute("label"));
			}
		}
		
		//ter isto separado porcausa da biblioteca. ver http://graphstream-project.org/doc/Tutorials/Reading-files-using-FileSource/ 
		try {
			fs.end();
		} catch( IOException e) {
			e.printStackTrace();
		} finally {
			fs.removeSink(g);
		}
	}

}

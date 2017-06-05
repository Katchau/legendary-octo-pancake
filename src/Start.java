import java.util.HashMap;

/* Generated By:JJTree: Do not edit this line. start.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=false,TRACK_TOKENS=false,NODE_PREFIX=,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
public class Start extends SimpleNode {
    public static HashMap<String, Automata> curAutomatas = new HashMap<>();

    public Start(int id) {
        super(id);
    }

    public Start(AutoAnalyserParser p, int id) {
        super(p, id);
    }

    public Automata execute() {
    System.out.println("Start");
        for(int i = 0; i < children.length ; i++) {
            Node child = children[i];
            if(child instanceof Read){
                ((Read) child).addAutomata();
            }
            if(child instanceof Operations){
                child.execute();
            }
            if(child instanceof Saves){
                ((Saves) child).exportAutomata();
            }
        }
        return null;
    }

}
/* JavaCC - OriginalChecksum=598994b6409c3d3955b1bdd4b4c2af2c (do not edit this line) */

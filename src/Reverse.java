/* Generated By:JJTree: Do not edit this line. Reverse.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=false,TRACK_TOKENS=false,NODE_PREFIX=,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
public
class Reverse extends SimpleNode {
    public Reverse(int id) {
      super(id);
    }

    public Reverse(AutoAnalyserParser p, int id) {
      super(p, id);
    }

    public Automata execute(){
        Automata at = null;
        Automata out = null;

        for(int i=0; i < children.length; i++) {
            if (children[i] instanceof Expr3){
                at = children[i].execute();
            } else {
                AutoAnalyser.addToResult("Shouldn't go here! (Reverse)");
            }
        }
        if(at == null){
            AutoAnalyser.addToResult("Can't do Reverse since the automata is invalid!");
        }
        else{
            out = AutomataOperations.getReverse(at);
        }

        return out;
    }
}
/* JavaCC - OriginalChecksum=5482a5d2823f1fb85f495cb8a0613baa (do not edit this line) */
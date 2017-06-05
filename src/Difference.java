/* Generated By:JJTree: Do not edit this line. Difference.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=false,TRACK_TOKENS=false,NODE_PREFIX=,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
public
class Difference extends SimpleNode {
  public Difference(int id) {
    super(id);
  }

  public Difference(AutoAnalyserParser p, int id) {
    super(p, id);
  }

    public Automata execute(){
        Automata aut1 = null;
        Automata aut2 = null;
        Automata out = null;

        for(int i=0; i < children.length; i++) {
            if (children[i] instanceof Expr2){
                aut1 = children[i].execute();
            } else if (children[i] instanceof Expr1){
                aut2 = children[i].execute();
            } else {
                System.out.println("Shouldn't go here! (Difference)");
            }
        }
        if(aut1 == null || aut2 == null){
            System.err.println("Can't do Difference since one off the automatas is invalid!");
        }
        else{
            out = AutomataOperations.getDifference(aut1,aut2);
            if(out == null){
                System.out.println("Difference produced an invalid automata! (no finish states!)");
            }
        }
        return out;
    }

}
/* JavaCC - OriginalChecksum=4a439b02e328d7d81e444274e104f168 (do not edit this line) */
/* Generated By:JJTree: Do not edit this line. Operations.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=false,TRACK_TOKENS=false,NODE_PREFIX=,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
public
class Operations extends SimpleNode {
  public Operations(int id) {
    super(id);
  }

  public Operations(AutoAnalyserParser p, int id) {
    super(p, id);
  }

    public Automata execute() {
        String varName = "";
        Automata a = null;

        for(int i=0; i < children.length; i++) {
            if(children[i] instanceof Identifier){
                varName = ((Identifier) children[i]).name;
            } else if(children[i] instanceof Expr1) {
                a = children[i].execute();
            } else if(children[i] instanceof Accept){
                children[i].execute();
            } else {
                AutoAnalyser.addToResult("Shouldn't go here! Expected semantic error at Operations!");
            }
        }

        if(a != null){
            Start.curAutomatas.put(varName,a);
        }
        return null;
    }
}
/* JavaCC - OriginalChecksum=a7204e20a77e3b9c22fffa6547326ffd (do not edit this line) */

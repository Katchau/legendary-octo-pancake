/* Generated By:JJTree: Do not edit this line. Expr2.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=false,TRACK_TOKENS=false,NODE_PREFIX=,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
public
class Expr2 extends SimpleNode {
  public Expr2(int id) {
    super(id);
  }

  public Expr2(AutoAnalyserParser p, int id) {
    super(p, id);
  }

    public Automata execute() {
        Automata res = null;

        for(int i=0; i < children.length; i++) {
            if (children[i] instanceof Expr3){
                res = children[i].execute();
            } else if (children[i] instanceof Complement) {
                res = children[i].execute();
            } else {
                System.out.println("Shouldn't go here (Expr2)");
            }
        }
        return res;
    }

}
/* JavaCC - OriginalChecksum=8cc311cc0beead58362999ffc16fb08f (do not edit this line) */

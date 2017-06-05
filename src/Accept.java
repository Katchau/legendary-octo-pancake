/* Generated By:JJTree: Do not edit this line. Accept.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=false,TRACK_TOKENS=false,NODE_PREFIX=,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
public
class Accept extends SimpleNode {
  public Accept(int id) {
    super(id);
  }

  public Accept(AutoAnalyserParser p, int id) {
    super(p, id);
  }

  public Automata execute() {
      String id = "";
      String in = "";

      for(int i=0; i < children.length; i++) {
          if (children[i] instanceof Identifier){
              id = ((Identifier) children[i]).name;
          }else if (children[i] instanceof Input){
              in = ((Input) children[i]).name;
          } else {
              System.out.println("Shouldn't go here! (Accept)");
          }
      }
      in = in.substring(1,in.length()-1);
      Automata a =  Start.curAutomatas.get(id);
      if(a != null){
          if(AutomataOperations.acceptString(a,in))
              System.out.println("String introduced " + in + " was accepted!");
          else
              System.err.println("String introduced " + in + " wasn't accepted!");
      }
      else{
          System.err.println("No such Identifier " + id);
      }
      return null;
  }
}
/* JavaCC - OriginalChecksum=092406c825d9494b4ca9040bf5245eb7 (do not edit this line) */

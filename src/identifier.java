/* Generated By:JJTree: Do not edit this line. identifier.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=false,TRACK_TOKENS=false,NODE_PREFIX=,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
public
class identifier extends SimpleNode {

  public String name = null;

  public identifier(int id) {
    super(id);
  }

  public identifier(AutoAnalyserParser p, int id) {
    super(p, id);
  }

  public Object jjtGetValue() { return name; }

  public String toString() { return AutoAnalyserParserTreeConstants.jjtNodeName[id] + " " + name; }

}
/* JavaCC - OriginalChecksum=2f17256bf7e587070898a7ad5d4d5811 (do not edit this line) */

package com.biosimilarity.lift.lib.kvdbJSON.Absyn; // Java Package generated by the BNF Converter.

public abstract class URIRsrcLocation implements java.io.Serializable {
  public abstract <R,A> R accept(URIRsrcLocation.Visitor<R,A> v, A arg);
  public interface Visitor <R,A> {
    public R visit(com.biosimilarity.lift.lib.kvdbJSON.Absyn.URIRsrcPortLoc p, A arg);
    public R visit(com.biosimilarity.lift.lib.kvdbJSON.Absyn.URIRsrcLoc p, A arg);

  }

}

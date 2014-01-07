package com.biosimilarity.lift.lib.kvdbJSON.Absyn; // Java Package generated by the BNF Converter.

public abstract class URI implements java.io.Serializable {
  public abstract <R,A> R accept(URI.Visitor<R,A> v, A arg);
  public interface Visitor <R,A> {
    public R visit(com.biosimilarity.lift.lib.kvdbJSON.Absyn.TokenURI p, A arg);
    public R visit(com.biosimilarity.lift.lib.kvdbJSON.Absyn.BasicURI p, A arg);
    public R visit(com.biosimilarity.lift.lib.kvdbJSON.Absyn.NullURI p, A arg);

  }

}

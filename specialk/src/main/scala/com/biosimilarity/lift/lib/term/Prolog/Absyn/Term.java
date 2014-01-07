package com.biosimilarity.lift.lib.term.Prolog.Absyn; // Java Package generated by the BNF Converter.

public abstract class Term implements java.io.Serializable {
  public abstract <R,A> R accept(Term.Visitor<R,A> v, A arg);
  public interface Visitor <R,A> {
    public R visit(com.biosimilarity.lift.lib.term.Prolog.Absyn.TAtom p, A arg);
    public R visit(com.biosimilarity.lift.lib.term.Prolog.Absyn.VarT p, A arg);
    public R visit(com.biosimilarity.lift.lib.term.Prolog.Absyn.Complex p, A arg);
    public R visit(com.biosimilarity.lift.lib.term.Prolog.Absyn.TList p, A arg);

  }

}

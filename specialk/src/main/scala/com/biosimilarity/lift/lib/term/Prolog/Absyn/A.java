package com.biosimilarity.lift.lib.term.Prolog.Absyn; // Java Package generated by the BNF Converter.

public class A extends Var {
  public final String wild_;

  public A(String p1) { wild_ = p1; }

  public <R,A> R accept(com.biosimilarity.lift.lib.term.Prolog.Absyn.Var.Visitor<R,A> v, A arg) { return v.visit(this, arg); }

  public boolean equals(Object o) {
    if (this == o) return true;
    if (o instanceof com.biosimilarity.lift.lib.term.Prolog.Absyn.A) {
      com.biosimilarity.lift.lib.term.Prolog.Absyn.A x = (com.biosimilarity.lift.lib.term.Prolog.Absyn.A)o;
      return this.wild_.equals(x.wild_);
    }
    return false;
  }

  public int hashCode() {
    return this.wild_.hashCode();
  }


}

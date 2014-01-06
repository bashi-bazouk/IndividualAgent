package com.biosimilarity.lift.lib.term.Prolog.Absyn; // Java Package generated by the BNF Converter.

public class FltAtm extends Atom {
  public final Double double_;

  public FltAtm(Double p1) { double_ = p1; }

  public <R,A> R accept(com.biosimilarity.lift.lib.term.Prolog.Absyn.Atom.Visitor<R,A> v, A arg) { return v.visit(this, arg); }

  public boolean equals(Object o) {
    if (this == o) return true;
    if (o instanceof com.biosimilarity.lift.lib.term.Prolog.Absyn.FltAtm) {
      com.biosimilarity.lift.lib.term.Prolog.Absyn.FltAtm x = (com.biosimilarity.lift.lib.term.Prolog.Absyn.FltAtm)o;
      return this.double_.equals(x.double_);
    }
    return false;
  }

  public int hashCode() {
    return this.double_.hashCode();
  }


}

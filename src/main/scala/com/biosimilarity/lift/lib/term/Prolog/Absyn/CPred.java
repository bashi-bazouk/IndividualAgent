package com.biosimilarity.lift.lib.term.Prolog.Absyn; // Java Package generated by the BNF Converter.

public class CPred extends Predicate {
  public final Functor functor_;
  public final ListTerm listterm_;

  public CPred(Functor p1, ListTerm p2) { functor_ = p1; listterm_ = p2; }

  public <R,A> R accept(com.biosimilarity.lift.lib.term.Prolog.Absyn.Predicate.Visitor<R,A> v, A arg) { return v.visit(this, arg); }

  public boolean equals(Object o) {
    if (this == o) return true;
    if (o instanceof com.biosimilarity.lift.lib.term.Prolog.Absyn.CPred) {
      com.biosimilarity.lift.lib.term.Prolog.Absyn.CPred x = (com.biosimilarity.lift.lib.term.Prolog.Absyn.CPred)o;
      return this.functor_.equals(x.functor_) && this.listterm_.equals(x.listterm_);
    }
    return false;
  }

  public int hashCode() {
    return 37*(this.functor_.hashCode())+this.listterm_.hashCode();
  }


}

package com.biosimilarity.lift.model.specialK.Absyn; // Java Package generated by the BNF Converter.

public class Variable extends Pattern {
  public final Variation variation_;

  public Variable(Variation p1) { variation_ = p1; }

  public <R,A> R accept(com.biosimilarity.lift.model.specialK.Absyn.Pattern.Visitor<R,A> v, A arg) { return v.visit(this, arg); }

  public boolean equals(Object o) {
    if (this == o) return true;
    if (o instanceof com.biosimilarity.lift.model.specialK.Absyn.Variable) {
      com.biosimilarity.lift.model.specialK.Absyn.Variable x = (com.biosimilarity.lift.model.specialK.Absyn.Variable)o;
      return this.variation_.equals(x.variation_);
    }
    return false;
  }

  public int hashCode() {
    return this.variation_.hashCode();
  }


}

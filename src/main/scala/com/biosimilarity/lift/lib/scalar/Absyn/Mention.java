package com.biosimilarity.lift.lib.scalar.Absyn; // Java Package generated by the BNF Converter.

public class Mention extends LambdaExpr {
  public final VariableExpr variableexpr_;

  public Mention(VariableExpr p1) { variableexpr_ = p1; }

  public <R,A> R accept(com.biosimilarity.lift.lib.scalar.Absyn.LambdaExpr.Visitor<R,A> v, A arg) { return v.visit(this, arg); }

  public boolean equals(Object o) {
    if (this == o) return true;
    if (o instanceof com.biosimilarity.lift.lib.scalar.Absyn.Mention) {
      com.biosimilarity.lift.lib.scalar.Absyn.Mention x = (com.biosimilarity.lift.lib.scalar.Absyn.Mention)o;
      return this.variableexpr_.equals(x.variableexpr_);
    }
    return false;
  }

  public int hashCode() {
    return this.variableexpr_.hashCode();
  }


}

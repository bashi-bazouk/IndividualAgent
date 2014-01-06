package com.biosimilarity.seleKt.model.ill.lang.illtl.Absyn; // Java Package generated by the BNF Converter.

public class DuplicationPtn extends RLLPtrn {
  public final FormalExpr formalexpr_1, formalexpr_2;

  public DuplicationPtn(FormalExpr p1, FormalExpr p2) { formalexpr_1 = p1; formalexpr_2 = p2; }

  public <R,A> R accept(com.biosimilarity.seleKt.model.ill.lang.illtl.Absyn.RLLPtrn.Visitor<R,A> v, A arg) { return v.visit(this, arg); }

  public boolean equals(Object o) {
    if (this == o) return true;
    if (o instanceof com.biosimilarity.seleKt.model.ill.lang.illtl.Absyn.DuplicationPtn) {
      com.biosimilarity.seleKt.model.ill.lang.illtl.Absyn.DuplicationPtn x = (com.biosimilarity.seleKt.model.ill.lang.illtl.Absyn.DuplicationPtn)o;
      return this.formalexpr_1.equals(x.formalexpr_1) && this.formalexpr_2.equals(x.formalexpr_2);
    }
    return false;
  }

  public int hashCode() {
    return 37*(this.formalexpr_1.hashCode())+this.formalexpr_2.hashCode();
  }


}

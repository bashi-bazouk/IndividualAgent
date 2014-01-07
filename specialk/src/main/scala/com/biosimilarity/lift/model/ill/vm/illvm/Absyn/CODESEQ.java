package com.biosimilarity.seleKt.model.ill.vm.illvm.Absyn; // Java Package generated by the BNF Converter.

public class CODESEQ extends ILLCode {
  public final ListInstruction listinstruction_;

  public CODESEQ(ListInstruction p1) { listinstruction_ = p1; }

  public <R,A> R accept(com.biosimilarity.seleKt.model.ill.vm.illvm.Absyn.ILLCode.Visitor<R,A> v, A arg) { return v.visit(this, arg); }

  public boolean equals(Object o) {
    if (this == o) return true;
    if (o instanceof com.biosimilarity.seleKt.model.ill.vm.illvm.Absyn.CODESEQ) {
      com.biosimilarity.seleKt.model.ill.vm.illvm.Absyn.CODESEQ x = (com.biosimilarity.seleKt.model.ill.vm.illvm.Absyn.CODESEQ)o;
      return this.listinstruction_.equals(x.listinstruction_);
    }
    return false;
  }

  public int hashCode() {
    return this.listinstruction_.hashCode();
  }


}

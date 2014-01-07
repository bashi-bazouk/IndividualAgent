package com.biosimilarity.seleKt.model.ill.vm.illvm.Absyn; // Java Package generated by the BNF Converter.

public class READ extends Instruction {
  public final String illread_;

  public READ(String p1) { illread_ = p1; }

  public <R,A> R accept(com.biosimilarity.seleKt.model.ill.vm.illvm.Absyn.Instruction.Visitor<R,A> v, A arg) { return v.visit(this, arg); }

  public boolean equals(Object o) {
    if (this == o) return true;
    if (o instanceof com.biosimilarity.seleKt.model.ill.vm.illvm.Absyn.READ) {
      com.biosimilarity.seleKt.model.ill.vm.illvm.Absyn.READ x = (com.biosimilarity.seleKt.model.ill.vm.illvm.Absyn.READ)o;
      return this.illread_.equals(x.illread_);
    }
    return false;
  }

  public int hashCode() {
    return this.illread_.hashCode();
  }


}

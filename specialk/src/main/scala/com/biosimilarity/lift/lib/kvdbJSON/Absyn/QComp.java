package com.biosimilarity.lift.lib.kvdbJSON.Absyn; // Java Package generated by the BNF Converter.

public class QComp extends QryValue {
  public final QryTerm qryterm_;

  public QComp(QryTerm p1) { qryterm_ = p1; }

  public <R,A> R accept(com.biosimilarity.lift.lib.kvdbJSON.Absyn.QryValue.Visitor<R,A> v, A arg) { return v.visit(this, arg); }

  public boolean equals(Object o) {
    if (this == o) return true;
    if (o instanceof com.biosimilarity.lift.lib.kvdbJSON.Absyn.QComp) {
      com.biosimilarity.lift.lib.kvdbJSON.Absyn.QComp x = (com.biosimilarity.lift.lib.kvdbJSON.Absyn.QComp)o;
      return this.qryterm_.equals(x.qryterm_);
    }
    return false;
  }

  public int hashCode() {
    return this.qryterm_.hashCode();
  }


}

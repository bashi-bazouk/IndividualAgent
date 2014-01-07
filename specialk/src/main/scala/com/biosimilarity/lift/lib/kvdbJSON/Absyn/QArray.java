package com.biosimilarity.lift.lib.kvdbJSON.Absyn; // Java Package generated by the BNF Converter.

public class QArray extends QryArray {
  public final ListQryElem listqryelem_;

  public QArray(ListQryElem p1) { listqryelem_ = p1; }

  public <R,A> R accept(com.biosimilarity.lift.lib.kvdbJSON.Absyn.QryArray.Visitor<R,A> v, A arg) { return v.visit(this, arg); }

  public boolean equals(Object o) {
    if (this == o) return true;
    if (o instanceof com.biosimilarity.lift.lib.kvdbJSON.Absyn.QArray) {
      com.biosimilarity.lift.lib.kvdbJSON.Absyn.QArray x = (com.biosimilarity.lift.lib.kvdbJSON.Absyn.QArray)o;
      return this.listqryelem_.equals(x.listqryelem_);
    }
    return false;
  }

  public int hashCode() {
    return this.listqryelem_.hashCode();
  }


}

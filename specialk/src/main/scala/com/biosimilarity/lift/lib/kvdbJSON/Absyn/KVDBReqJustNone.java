package com.biosimilarity.lift.lib.kvdbJSON.Absyn; // Java Package generated by the BNF Converter.

public class KVDBReqJustNone extends ReqJust {

  public KVDBReqJustNone() { }

  public <R,A> R accept(com.biosimilarity.lift.lib.kvdbJSON.Absyn.ReqJust.Visitor<R,A> v, A arg) { return v.visit(this, arg); }

  public boolean equals(Object o) {
    if (this == o) return true;
    if (o instanceof com.biosimilarity.lift.lib.kvdbJSON.Absyn.KVDBReqJustNone) {
      return true;
    }
    return false;
  }

  public int hashCode() {
    return 37;
  }


}

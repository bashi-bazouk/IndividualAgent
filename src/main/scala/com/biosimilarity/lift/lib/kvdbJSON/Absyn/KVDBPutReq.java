package com.biosimilarity.lift.lib.kvdbJSON.Absyn; // Java Package generated by the BNF Converter.

public class KVDBPutReq extends TellReq {

  public KVDBPutReq() { }

  public <R,A> R accept(com.biosimilarity.lift.lib.kvdbJSON.Absyn.TellReq.Visitor<R,A> v, A arg) { return v.visit(this, arg); }

  public boolean equals(Object o) {
    if (this == o) return true;
    if (o instanceof com.biosimilarity.lift.lib.kvdbJSON.Absyn.KVDBPutReq) {
      return true;
    }
    return false;
  }

  public int hashCode() {
    return 37;
  }


}

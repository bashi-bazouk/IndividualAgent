package com.biosimilarity.lift.model.specialK.Absyn; // Java Package generated by the BNF Converter.

public class Ingestion extends Agent {
  public final Pattern pattern_;
  public final Abstraction abstraction_;

  public Ingestion(Pattern p1, Abstraction p2) { pattern_ = p1; abstraction_ = p2; }

  public <R,A> R accept(com.biosimilarity.lift.model.specialK.Absyn.Agent.Visitor<R,A> v, A arg) { return v.visit(this, arg); }

  public boolean equals(Object o) {
    if (this == o) return true;
    if (o instanceof com.biosimilarity.lift.model.specialK.Absyn.Ingestion) {
      com.biosimilarity.lift.model.specialK.Absyn.Ingestion x = (com.biosimilarity.lift.model.specialK.Absyn.Ingestion)o;
      return this.pattern_.equals(x.pattern_) && this.abstraction_.equals(x.abstraction_);
    }
    return false;
  }

  public int hashCode() {
    return 37*(this.pattern_.hashCode())+this.abstraction_.hashCode();
  }


}


//----------------------------------------------------
// The following code was generated by CUP v0.11a beta 20060608
// Mon Oct 24 11:57:52 PDT 2011
//----------------------------------------------------

package com.biosimilarity.rlambdaDC.lang.rlambdaDC;


/** CUP v0.11a beta 20060608 generated parser.
  * @version Mon Oct 24 11:57:52 PDT 2011
  */
public class parser extends java_cup.runtime.lr_parser {

  /** Default constructor. */
  public parser() {super();}

  /** Constructor which sets the default scanner. */
  public parser(java_cup.runtime.Scanner s) {super(s);}

  /** Constructor which sets the default scanner. */
  public parser(java_cup.runtime.Scanner s, java_cup.runtime.SymbolFactory sf) {super(s,sf);}

  /** Production table. */
  protected static final short _production_table[][] = 
    unpackFromStrings(new String[] {
    "\000\022\000\002\002\004\000\002\002\004\000\002\002" +
    "\003\000\002\003\003\000\002\003\003\000\002\003\003" +
    "\000\002\003\006\000\002\003\005\000\002\004\003\000" +
    "\002\004\005\000\002\004\005\000\002\004\005\000\002" +
    "\005\006\000\002\005\003\000\002\006\003\000\002\007" +
    "\002\000\002\007\003\000\002\007\005" });

  /** Access to production table. */
  public short[][] production_table() {return _production_table;}

  /** Parse-action table. */
  protected static final short[][] _action_table = 
    unpackFromStrings(new String[] {
    "\000\042\000\024\005\010\007\007\013\005\014\004\015" +
    "\021\016\020\017\016\020\017\021\014\001\002\000\032" +
    "\002\ufff9\005\ufff9\006\ufff9\007\ufff9\011\ufff9\013\ufff9\014" +
    "\ufff9\015\ufff9\016\ufff9\017\ufff9\020\ufff9\021\ufff9\001\002" +
    "\000\010\004\ufff2\007\007\021\014\001\002\000\032\002" +
    "\ufffe\005\ufffe\006\ufffe\007\ufffe\011\ufffe\013\ufffe\014\ufffe" +
    "\015\ufffe\016\ufffe\017\ufffe\020\ufffe\021\ufffe\001\002\000" +
    "\004\010\034\001\002\000\024\005\010\007\007\013\005" +
    "\014\004\015\021\016\020\017\016\020\017\021\014\001" +
    "\002\000\026\002\030\005\010\007\007\013\005\014\004" +
    "\015\021\016\020\017\016\020\017\021\014\001\002\000" +
    "\030\002\uffff\005\uffff\006\uffff\007\uffff\013\uffff\014\uffff" +
    "\015\uffff\016\uffff\017\uffff\020\uffff\021\uffff\001\002\000" +
    "\032\002\ufffd\005\ufffd\006\ufffd\007\ufffd\011\ufffd\013\ufffd" +
    "\014\ufffd\015\ufffd\016\ufffd\017\ufffd\020\ufffd\021\ufffd\001" +
    "\002\000\036\002\ufff4\004\ufff4\005\ufff4\006\ufff4\007\ufff4" +
    "\011\ufff4\012\ufff4\013\ufff4\014\ufff4\015\ufff4\016\ufff4\017" +
    "\ufff4\020\ufff4\021\ufff4\001\002\000\032\002\ufffc\005\ufffc" +
    "\006\ufffc\007\ufffc\011\ufffc\013\ufffc\014\ufffc\015\ufffc\016" +
    "\ufffc\017\ufffc\020\ufffc\021\ufffc\001\002\000\024\005\010" +
    "\007\007\013\005\014\004\015\021\016\020\017\016\020" +
    "\017\021\014\001\002\000\032\002\ufff3\005\ufff3\006\ufff3" +
    "\007\ufff3\011\ufff3\013\ufff3\014\ufff3\015\ufff3\016\ufff3\017" +
    "\ufff3\020\ufff3\021\ufff3\001\002\000\024\005\010\007\007" +
    "\013\005\014\004\015\021\016\020\017\016\020\017\021" +
    "\014\001\002\000\024\005\010\007\007\013\005\014\004" +
    "\015\021\016\020\017\016\020\017\021\014\001\002\000" +
    "\024\005\010\007\007\013\005\014\004\015\021\016\020" +
    "\017\016\020\017\021\014\001\002\000\032\002\ufff8\005" +
    "\ufff8\006\ufff8\007\ufff8\011\ufff8\013\ufff8\014\ufff8\015\ufff8" +
    "\016\ufff8\017\ufff8\020\ufff8\021\ufff8\001\002\000\024\005" +
    "\010\007\007\013\005\014\004\015\021\016\020\017\016" +
    "\020\017\021\014\001\002\000\032\002\ufff6\005\ufff6\006" +
    "\ufff6\007\ufff6\011\ufff6\013\ufff6\014\ufff6\015\ufff6\016\ufff6" +
    "\017\ufff6\020\ufff6\021\ufff6\001\002\000\024\005\010\007" +
    "\007\013\005\014\004\015\021\016\020\017\016\020\017" +
    "\021\014\001\002\000\032\002\ufff7\005\ufff7\006\ufff7\007" +
    "\ufff7\011\ufff7\013\ufff7\014\ufff7\015\ufff7\016\ufff7\017\ufff7" +
    "\020\ufff7\021\ufff7\001\002\000\004\002\001\001\002\000" +
    "\030\002\000\005\000\006\000\007\000\013\000\014\000" +
    "\015\000\016\000\017\000\020\000\021\000\001\002\000" +
    "\026\005\010\006\033\007\007\013\005\014\004\015\021" +
    "\016\020\017\016\020\017\021\014\001\002\000\032\002" +
    "\ufffa\005\ufffa\006\ufffa\007\ufffa\011\ufffa\013\ufffa\014\ufffa" +
    "\015\ufffa\016\ufffa\017\ufffa\020\ufffa\021\ufffa\001\002\000" +
    "\024\005\010\007\007\013\005\014\004\015\021\016\020" +
    "\017\016\020\017\021\014\001\002\000\004\011\036\001" +
    "\002\000\036\002\ufff5\004\ufff5\005\ufff5\006\ufff5\007\ufff5" +
    "\011\ufff5\012\ufff5\013\ufff5\014\ufff5\015\ufff5\016\ufff5\017" +
    "\ufff5\020\ufff5\021\ufff5\001\002\000\006\004\ufff1\012\043" +
    "\001\002\000\004\004\041\001\002\000\024\005\010\007" +
    "\007\013\005\014\004\015\021\016\020\017\016\020\017" +
    "\021\014\001\002\000\032\002\ufffb\005\ufffb\006\ufffb\007" +
    "\ufffb\011\ufffb\013\ufffb\014\ufffb\015\ufffb\016\ufffb\017\ufffb" +
    "\020\ufffb\021\ufffb\001\002\000\010\004\ufff2\007\007\021" +
    "\014\001\002\000\004\004\ufff0\001\002" });

  /** Access to parse-action table. */
  public short[][] action_table() {return _action_table;}

  /** <code>reduce_goto</code> table. */
  protected static final short[][] _reduce_table = 
    unpackFromStrings(new String[] {
    "\000\042\000\014\002\010\003\011\004\014\005\005\006" +
    "\012\001\001\000\002\001\001\000\006\005\036\007\037" +
    "\001\001\000\002\001\001\000\002\001\001\000\014\002" +
    "\031\003\011\004\014\005\005\006\012\001\001\000\012" +
    "\003\030\004\014\005\005\006\012\001\001\000\002\001" +
    "\001\000\002\001\001\000\002\001\001\000\002\001\001" +
    "\000\012\003\025\004\014\005\005\006\012\001\001\000" +
    "\002\001\001\000\012\003\023\004\014\005\005\006\012" +
    "\001\001\000\012\003\021\004\014\005\005\006\012\001" +
    "\001\000\012\003\022\004\014\005\005\006\012\001\001" +
    "\000\002\001\001\000\012\003\024\004\014\005\005\006" +
    "\012\001\001\000\002\001\001\000\012\003\026\004\014" +
    "\005\005\006\012\001\001\000\002\001\001\000\002\001" +
    "\001\000\002\001\001\000\012\003\030\004\014\005\005" +
    "\006\012\001\001\000\002\001\001\000\012\003\034\004" +
    "\014\005\005\006\012\001\001\000\002\001\001\000\002" +
    "\001\001\000\002\001\001\000\002\001\001\000\012\003" +
    "\041\004\014\005\005\006\012\001\001\000\002\001\001" +
    "\000\006\005\036\007\043\001\001\000\002\001\001" });

  /** Access to <code>reduce_goto</code> table. */
  public short[][] reduce_table() {return _reduce_table;}

  /** Instance of action encapsulation class. */
  protected CUP$parser$actions action_obj;

  /** Action encapsulation object initializer. */
  protected void init_actions()
    {
      action_obj = new CUP$parser$actions(this);
    }

  /** Invoke a user supplied parse action. */
  public java_cup.runtime.Symbol do_action(
    int                        act_num,
    java_cup.runtime.lr_parser parser,
    java.util.Stack            stack,
    int                        top)
    throws java.lang.Exception
  {
    /* call code in generated class */
    return action_obj.CUP$parser$do_action(act_num, parser, stack, top);
  }

  /** Indicates start state. */
  public int start_state() {return 0;}
  /** Indicates start production. */
  public int start_production() {return 0;}

  /** <code>EOF</code> Symbol index. */
  public int EOF_sym() {return 0;}

  /** <code>error</code> Symbol index. */
  public int error_sym() {return 1;}



  public com.biosimilarity.rlambdaDC.lang.rlambdaDC.Absyn.Expression pExpression() throws Exception
  {
	java_cup.runtime.Symbol res = parse();
	return (com.biosimilarity.rlambdaDC.lang.rlambdaDC.Absyn.Expression) res.value;
  }

public <B,A extends java.util.LinkedList<? super B>> A cons_(B x, A xs) { xs.addFirst(x); return xs; }

public void syntax_error(java_cup.runtime.Symbol cur_token)
{
	report_error("Syntax Error, trying to recover and continue parse...", cur_token);
}

public void unrecovered_syntax_error(java_cup.runtime.Symbol cur_token) throws java.lang.Exception
{
	throw new Exception("Unrecoverable Syntax Error");
}


}

/** Cup generated class to encapsulate user supplied action code.*/
class CUP$parser$actions {
  private final parser parser;

  /** Constructor */
  CUP$parser$actions(parser parser) {
    this.parser = parser;
  }

  /** Method with the actual generated action code. */
  public final java_cup.runtime.Symbol CUP$parser$do_action(
    int                        CUP$parser$act_num,
    java_cup.runtime.lr_parser CUP$parser$parser,
    java.util.Stack            CUP$parser$stack,
    int                        CUP$parser$top)
    throws java.lang.Exception
    {
      /* Symbol object for return from actions */
      java_cup.runtime.Symbol CUP$parser$result;

      /* select the action based on the action number */
      switch (CUP$parser$act_num)
        {
          /*. . . . . . . . . . . . . . . . . . . .*/
          case 17: // ListVariableExpr ::= VariableExpr _SYMB_6 ListVariableExpr 
            {
              com.biosimilarity.rlambdaDC.lang.rlambdaDC.Absyn.ListVariableExpr RESULT =null;
		com.biosimilarity.rlambdaDC.lang.rlambdaDC.Absyn.VariableExpr p_1 = (com.biosimilarity.rlambdaDC.lang.rlambdaDC.Absyn.VariableExpr)((java_cup.runtime.Symbol) CUP$parser$stack.elementAt(CUP$parser$top-2)).value;
		com.biosimilarity.rlambdaDC.lang.rlambdaDC.Absyn.ListVariableExpr p_3 = (com.biosimilarity.rlambdaDC.lang.rlambdaDC.Absyn.ListVariableExpr)((java_cup.runtime.Symbol) CUP$parser$stack.peek()).value;
		 RESULT = p_3; p_3.addFirst(p_1); 
              CUP$parser$result = parser.getSymbolFactory().newSymbol("ListVariableExpr",5, RESULT);
            }
          return CUP$parser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 16: // ListVariableExpr ::= VariableExpr 
            {
              com.biosimilarity.rlambdaDC.lang.rlambdaDC.Absyn.ListVariableExpr RESULT =null;
		com.biosimilarity.rlambdaDC.lang.rlambdaDC.Absyn.VariableExpr p_1 = (com.biosimilarity.rlambdaDC.lang.rlambdaDC.Absyn.VariableExpr)((java_cup.runtime.Symbol) CUP$parser$stack.peek()).value;
		 RESULT = new com.biosimilarity.rlambdaDC.lang.rlambdaDC.Absyn.ListVariableExpr(); RESULT.addLast(p_1); 
              CUP$parser$result = parser.getSymbolFactory().newSymbol("ListVariableExpr",5, RESULT);
            }
          return CUP$parser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 15: // ListVariableExpr ::= 
            {
              com.biosimilarity.rlambdaDC.lang.rlambdaDC.Absyn.ListVariableExpr RESULT =null;
		 RESULT = new com.biosimilarity.rlambdaDC.lang.rlambdaDC.Absyn.ListVariableExpr(); 
              CUP$parser$result = parser.getSymbolFactory().newSymbol("ListVariableExpr",5, RESULT);
            }
          return CUP$parser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 14: // ValueExpr ::= _INTEGER_ 
            {
              com.biosimilarity.rlambdaDC.lang.rlambdaDC.Absyn.ValueExpr RESULT =null;
		Integer p_1 = (Integer)((java_cup.runtime.Symbol) CUP$parser$stack.peek()).value;
		 RESULT = new com.biosimilarity.rlambdaDC.lang.rlambdaDC.Absyn.Numeric(p_1); 
              CUP$parser$result = parser.getSymbolFactory().newSymbol("ValueExpr",4, RESULT);
            }
          return CUP$parser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 13: // VariableExpr ::= _IDENT_ 
            {
              com.biosimilarity.rlambdaDC.lang.rlambdaDC.Absyn.VariableExpr RESULT =null;
		String p_1 = (String)((java_cup.runtime.Symbol) CUP$parser$stack.peek()).value;
		 RESULT = new com.biosimilarity.rlambdaDC.lang.rlambdaDC.Absyn.AtomLiteral(p_1); 
              CUP$parser$result = parser.getSymbolFactory().newSymbol("VariableExpr",3, RESULT);
            }
          return CUP$parser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 12: // VariableExpr ::= _SYMB_3 _SYMB_4 Expression1 _SYMB_5 
            {
              com.biosimilarity.rlambdaDC.lang.rlambdaDC.Absyn.VariableExpr RESULT =null;
		com.biosimilarity.rlambdaDC.lang.rlambdaDC.Absyn.Expression p_3 = (com.biosimilarity.rlambdaDC.lang.rlambdaDC.Absyn.Expression)((java_cup.runtime.Symbol) CUP$parser$stack.elementAt(CUP$parser$top-1)).value;
		 RESULT = new com.biosimilarity.rlambdaDC.lang.rlambdaDC.Absyn.Transcription(p_3); 
              CUP$parser$result = parser.getSymbolFactory().newSymbol("VariableExpr",3, RESULT);
            }
          return CUP$parser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 11: // ContinueExpr ::= _SYMB_10 Expression1 Expression1 
            {
              com.biosimilarity.rlambdaDC.lang.rlambdaDC.Absyn.ContinueExpr RESULT =null;
		com.biosimilarity.rlambdaDC.lang.rlambdaDC.Absyn.Expression p_2 = (com.biosimilarity.rlambdaDC.lang.rlambdaDC.Absyn.Expression)((java_cup.runtime.Symbol) CUP$parser$stack.elementAt(CUP$parser$top-1)).value;
		com.biosimilarity.rlambdaDC.lang.rlambdaDC.Absyn.Expression p_3 = (com.biosimilarity.rlambdaDC.lang.rlambdaDC.Absyn.Expression)((java_cup.runtime.Symbol) CUP$parser$stack.peek()).value;
		 RESULT = new com.biosimilarity.rlambdaDC.lang.rlambdaDC.Absyn.PushSubCont(p_2,p_3); 
              CUP$parser$result = parser.getSymbolFactory().newSymbol("ContinueExpr",2, RESULT);
            }
          return CUP$parser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 10: // ContinueExpr ::= _SYMB_11 Expression1 Expression1 
            {
              com.biosimilarity.rlambdaDC.lang.rlambdaDC.Absyn.ContinueExpr RESULT =null;
		com.biosimilarity.rlambdaDC.lang.rlambdaDC.Absyn.Expression p_2 = (com.biosimilarity.rlambdaDC.lang.rlambdaDC.Absyn.Expression)((java_cup.runtime.Symbol) CUP$parser$stack.elementAt(CUP$parser$top-1)).value;
		com.biosimilarity.rlambdaDC.lang.rlambdaDC.Absyn.Expression p_3 = (com.biosimilarity.rlambdaDC.lang.rlambdaDC.Absyn.Expression)((java_cup.runtime.Symbol) CUP$parser$stack.peek()).value;
		 RESULT = new com.biosimilarity.rlambdaDC.lang.rlambdaDC.Absyn.Subcontinuation(p_2,p_3); 
              CUP$parser$result = parser.getSymbolFactory().newSymbol("ContinueExpr",2, RESULT);
            }
          return CUP$parser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 9: // ContinueExpr ::= _SYMB_9 Expression1 Expression1 
            {
              com.biosimilarity.rlambdaDC.lang.rlambdaDC.Absyn.ContinueExpr RESULT =null;
		com.biosimilarity.rlambdaDC.lang.rlambdaDC.Absyn.Expression p_2 = (com.biosimilarity.rlambdaDC.lang.rlambdaDC.Absyn.Expression)((java_cup.runtime.Symbol) CUP$parser$stack.elementAt(CUP$parser$top-1)).value;
		com.biosimilarity.rlambdaDC.lang.rlambdaDC.Absyn.Expression p_3 = (com.biosimilarity.rlambdaDC.lang.rlambdaDC.Absyn.Expression)((java_cup.runtime.Symbol) CUP$parser$stack.peek()).value;
		 RESULT = new com.biosimilarity.rlambdaDC.lang.rlambdaDC.Absyn.PushPrompt(p_2,p_3); 
              CUP$parser$result = parser.getSymbolFactory().newSymbol("ContinueExpr",2, RESULT);
            }
          return CUP$parser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 8: // ContinueExpr ::= _SYMB_8 
            {
              com.biosimilarity.rlambdaDC.lang.rlambdaDC.Absyn.ContinueExpr RESULT =null;
		 RESULT = new com.biosimilarity.rlambdaDC.lang.rlambdaDC.Absyn.Prompt(); 
              CUP$parser$result = parser.getSymbolFactory().newSymbol("ContinueExpr",2, RESULT);
            }
          return CUP$parser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 7: // Expression1 ::= _SYMB_1 Expression _SYMB_2 
            {
              com.biosimilarity.rlambdaDC.lang.rlambdaDC.Absyn.Expression RESULT =null;
		com.biosimilarity.rlambdaDC.lang.rlambdaDC.Absyn.Expression p_2 = (com.biosimilarity.rlambdaDC.lang.rlambdaDC.Absyn.Expression)((java_cup.runtime.Symbol) CUP$parser$stack.elementAt(CUP$parser$top-1)).value;
		 RESULT = p_2; 
              CUP$parser$result = parser.getSymbolFactory().newSymbol("Expression1",1, RESULT);
            }
          return CUP$parser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 6: // Expression1 ::= _SYMB_7 ListVariableExpr _SYMB_0 Expression1 
            {
              com.biosimilarity.rlambdaDC.lang.rlambdaDC.Absyn.Expression RESULT =null;
		com.biosimilarity.rlambdaDC.lang.rlambdaDC.Absyn.ListVariableExpr p_2 = (com.biosimilarity.rlambdaDC.lang.rlambdaDC.Absyn.ListVariableExpr)((java_cup.runtime.Symbol) CUP$parser$stack.elementAt(CUP$parser$top-2)).value;
		com.biosimilarity.rlambdaDC.lang.rlambdaDC.Absyn.Expression p_4 = (com.biosimilarity.rlambdaDC.lang.rlambdaDC.Absyn.Expression)((java_cup.runtime.Symbol) CUP$parser$stack.peek()).value;
		 RESULT = new com.biosimilarity.rlambdaDC.lang.rlambdaDC.Absyn.Abstraction(p_2,p_4); 
              CUP$parser$result = parser.getSymbolFactory().newSymbol("Expression1",1, RESULT);
            }
          return CUP$parser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 5: // Expression1 ::= ContinueExpr 
            {
              com.biosimilarity.rlambdaDC.lang.rlambdaDC.Absyn.Expression RESULT =null;
		com.biosimilarity.rlambdaDC.lang.rlambdaDC.Absyn.ContinueExpr p_1 = (com.biosimilarity.rlambdaDC.lang.rlambdaDC.Absyn.ContinueExpr)((java_cup.runtime.Symbol) CUP$parser$stack.peek()).value;
		 RESULT = new com.biosimilarity.rlambdaDC.lang.rlambdaDC.Absyn.Continuation(p_1); 
              CUP$parser$result = parser.getSymbolFactory().newSymbol("Expression1",1, RESULT);
            }
          return CUP$parser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 4: // Expression1 ::= ValueExpr 
            {
              com.biosimilarity.rlambdaDC.lang.rlambdaDC.Absyn.Expression RESULT =null;
		com.biosimilarity.rlambdaDC.lang.rlambdaDC.Absyn.ValueExpr p_1 = (com.biosimilarity.rlambdaDC.lang.rlambdaDC.Absyn.ValueExpr)((java_cup.runtime.Symbol) CUP$parser$stack.peek()).value;
		 RESULT = new com.biosimilarity.rlambdaDC.lang.rlambdaDC.Absyn.Value(p_1); 
              CUP$parser$result = parser.getSymbolFactory().newSymbol("Expression1",1, RESULT);
            }
          return CUP$parser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 3: // Expression1 ::= VariableExpr 
            {
              com.biosimilarity.rlambdaDC.lang.rlambdaDC.Absyn.Expression RESULT =null;
		com.biosimilarity.rlambdaDC.lang.rlambdaDC.Absyn.VariableExpr p_1 = (com.biosimilarity.rlambdaDC.lang.rlambdaDC.Absyn.VariableExpr)((java_cup.runtime.Symbol) CUP$parser$stack.peek()).value;
		 RESULT = new com.biosimilarity.rlambdaDC.lang.rlambdaDC.Absyn.Mention(p_1); 
              CUP$parser$result = parser.getSymbolFactory().newSymbol("Expression1",1, RESULT);
            }
          return CUP$parser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 2: // Expression ::= Expression1 
            {
              com.biosimilarity.rlambdaDC.lang.rlambdaDC.Absyn.Expression RESULT =null;
		com.biosimilarity.rlambdaDC.lang.rlambdaDC.Absyn.Expression p_1 = (com.biosimilarity.rlambdaDC.lang.rlambdaDC.Absyn.Expression)((java_cup.runtime.Symbol) CUP$parser$stack.peek()).value;
		 RESULT = p_1; 
              CUP$parser$result = parser.getSymbolFactory().newSymbol("Expression",0, RESULT);
            }
          return CUP$parser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 1: // Expression ::= Expression Expression1 
            {
              com.biosimilarity.rlambdaDC.lang.rlambdaDC.Absyn.Expression RESULT =null;
		com.biosimilarity.rlambdaDC.lang.rlambdaDC.Absyn.Expression p_1 = (com.biosimilarity.rlambdaDC.lang.rlambdaDC.Absyn.Expression)((java_cup.runtime.Symbol) CUP$parser$stack.elementAt(CUP$parser$top-1)).value;
		com.biosimilarity.rlambdaDC.lang.rlambdaDC.Absyn.Expression p_2 = (com.biosimilarity.rlambdaDC.lang.rlambdaDC.Absyn.Expression)((java_cup.runtime.Symbol) CUP$parser$stack.peek()).value;
		 RESULT = new com.biosimilarity.rlambdaDC.lang.rlambdaDC.Absyn.Application(p_1,p_2); 
              CUP$parser$result = parser.getSymbolFactory().newSymbol("Expression",0, RESULT);
            }
          return CUP$parser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 0: // $START ::= Expression EOF 
            {
              Object RESULT =null;
		com.biosimilarity.rlambdaDC.lang.rlambdaDC.Absyn.Expression start_val = (com.biosimilarity.rlambdaDC.lang.rlambdaDC.Absyn.Expression)((java_cup.runtime.Symbol) CUP$parser$stack.elementAt(CUP$parser$top-1)).value;
		RESULT = start_val;
              CUP$parser$result = parser.getSymbolFactory().newSymbol("$START",0, RESULT);
            }
          /* ACCEPT */
          CUP$parser$parser.done_parsing();
          return CUP$parser$result;

          /* . . . . . .*/
          default:
            throw new Exception(
               "Invalid action number found in internal parse table");

        }
    }
}


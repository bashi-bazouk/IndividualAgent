
//----------------------------------------------------
// The following code was generated by CUP v0.11a beta 20060608
// Tue Mar 13 07:18:32 PDT 2012
//----------------------------------------------------

package com.biosimilarity.lift.lib.scalar;


/** CUP v0.11a beta 20060608 generated parser.
  * @version Tue Mar 13 07:18:32 PDT 2012
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
    "\000\045\000\002\002\004\000\002\002\005\000\002\002" +
    "\002\000\002\002\010\000\002\003\003\000\002\003\005" +
    "\000\002\004\005\000\002\004\003\000\002\005\005\000" +
    "\002\005\003\000\002\006\004\000\002\006\003\000\002" +
    "\007\006\000\002\007\003\000\002\010\011\000\002\010" +
    "\003\000\002\010\003\000\002\010\005\000\002\011\003" +
    "\000\002\011\006\000\002\012\005\000\002\012\003\000" +
    "\002\012\003\000\002\012\003\000\002\013\003\000\002" +
    "\013\003\000\002\014\003\000\002\014\003\000\002\015" +
    "\002\000\002\015\003\000\002\015\005\000\002\016\002" +
    "\000\002\016\003\000\002\016\005\000\002\017\002\000" +
    "\002\017\003\000\002\017\005" });

  /** Access to production table. */
  public short[][] production_table() {return _production_table;}

  /** Parse-action table. */
  protected static final short[][] _action_table = 
    unpackFromStrings(new String[] {
    "\000\102\000\034\002\uffff\006\025\012\024\013\023\016" +
    "\022\021\021\024\016\025\014\026\012\027\006\030\004" +
    "\031\020\032\011\001\002\000\024\004\uffe8\007\uffe8\010" +
    "\uffe8\011\uffe8\013\uffe8\014\uffe8\020\uffe8\022\uffe8\023\uffe8" +
    "\001\002\000\024\004\ufff4\007\ufff4\010\ufff4\011\ufff4\013" +
    "\077\014\ufff4\020\ufff4\022\ufff4\023\ufff4\001\002\000\024" +
    "\004\uffea\007\uffea\010\uffea\011\uffea\013\uffea\014\uffea\020" +
    "\uffea\022\uffea\023\uffea\001\002\000\020\004\ufff8\007\ufff8" +
    "\010\ufff8\011\ufff8\020\ufff8\022\ufff8\023\ufff8\001\002\000" +
    "\020\004\ufffa\007\ufffa\010\ufffa\011\046\020\ufffa\022\ufffa" +
    "\023\ufffa\001\002\000\026\004\uffef\005\uffef\007\uffef\010" +
    "\uffef\011\uffef\013\uffef\014\uffef\020\uffef\022\uffef\023\uffef" +
    "\001\002\000\006\016\022\032\011\001\002\000\020\004" +
    "\ufff6\007\ufff6\010\ufff6\011\ufff6\020\ufff6\022\ufff6\023\ufff6" +
    "\001\002\000\024\004\uffe7\007\uffe7\010\uffe7\011\uffe7\013" +
    "\uffe7\014\uffe7\020\uffe7\022\uffe7\023\uffe7\001\002\000\014" +
    "\004\ufffd\010\041\020\ufffd\022\ufffd\023\ufffd\001\002\000" +
    "\024\004\uffe6\007\uffe6\010\uffe6\011\uffe6\013\uffe6\014\uffe6" +
    "\020\uffe6\022\uffe6\023\uffe6\001\002\000\024\004\uffec\007" +
    "\uffec\010\uffec\011\uffec\013\uffec\014\uffec\020\uffec\022\uffec" +
    "\023\uffec\001\002\000\024\004\uffe9\007\uffe9\010\uffe9\011" +
    "\uffe9\013\uffe9\014\uffe9\020\uffe9\022\uffe9\023\uffe9\001\002" +
    "\000\032\006\025\012\024\013\023\016\022\021\021\022" +
    "\uffe2\024\016\025\014\027\006\030\004\031\020\032\011" +
    "\001\002\000\004\017\062\001\002\000\010\014\uffe5\016" +
    "\022\032\011\001\002\000\030\006\044\012\024\013\023" +
    "\016\022\021\021\024\016\025\014\027\006\030\004\031" +
    "\020\032\011\001\002\000\034\006\025\007\uffff\012\024" +
    "\013\023\016\022\021\021\024\016\025\014\026\012\027" +
    "\006\030\004\031\020\032\011\001\002\000\024\004\ufff2" +
    "\007\ufff2\010\ufff2\011\ufff2\013\ufff2\014\ufff2\020\ufff2\022" +
    "\ufff2\023\ufff2\001\002\000\024\004\ufff1\007\ufff1\010\ufff1" +
    "\011\ufff1\013\ufff1\014\ufff1\020\ufff1\022\ufff1\023\ufff1\001" +
    "\002\000\024\004\uffeb\007\uffeb\010\uffeb\011\uffeb\013\uffeb" +
    "\014\uffeb\020\uffeb\022\uffeb\023\uffeb\001\002\000\004\002" +
    "\035\001\002\000\004\004\033\001\002\000\036\002\uffff" +
    "\006\025\007\uffff\012\024\013\023\016\022\021\021\024" +
    "\016\025\014\026\012\027\006\030\004\031\020\032\011" +
    "\001\002\000\006\002\000\007\000\001\002\000\004\002" +
    "\001\001\002\000\010\004\ufffd\007\042\010\041\001\002" +
    "\000\004\007\040\001\002\000\012\004\ufffc\020\ufffc\022" +
    "\ufffc\023\ufffc\001\002\000\030\006\044\012\024\013\023" +
    "\016\022\021\021\024\016\025\014\027\006\030\004\031" +
    "\020\032\011\001\002\000\024\004\ufff0\007\ufff0\010\ufff0" +
    "\011\ufff0\013\ufff0\014\ufff0\020\ufff0\022\ufff0\023\ufff0\001" +
    "\002\000\020\004\ufffb\007\ufffb\010\ufffb\011\046\020\ufffb" +
    "\022\ufffb\023\ufffb\001\002\000\030\006\044\012\024\013" +
    "\023\016\022\021\021\024\016\025\014\027\006\030\004" +
    "\031\020\032\011\001\002\000\006\007\042\010\041\001" +
    "\002\000\030\006\044\012\024\013\023\016\022\021\021" +
    "\024\016\025\014\027\006\030\004\031\020\032\011\001" +
    "\002\000\020\004\ufff9\007\ufff9\010\ufff9\011\ufff9\020\ufff9" +
    "\022\ufff9\023\ufff9\001\002\000\020\004\ufff7\007\ufff7\010" +
    "\ufff7\011\ufff7\020\ufff7\022\ufff7\023\ufff7\001\002\000\006" +
    "\014\uffe4\023\060\001\002\000\004\014\053\001\002\000" +
    "\004\015\054\001\002\000\004\006\055\001\002\000\034" +
    "\006\025\007\uffff\012\024\013\023\016\022\021\021\024" +
    "\016\025\014\026\012\027\006\030\004\031\020\032\011" +
    "\001\002\000\004\007\057\001\002\000\024\004\ufff3\007" +
    "\ufff3\010\ufff3\011\ufff3\013\ufff3\014\ufff3\020\ufff3\022\ufff3" +
    "\023\ufff3\001\002\000\010\014\uffe5\016\022\032\011\001" +
    "\002\000\004\014\uffe3\001\002\000\030\006\025\012\024" +
    "\013\023\016\022\021\021\024\016\025\014\027\006\030" +
    "\004\031\020\032\011\001\002\000\004\020\064\001\002" +
    "\000\026\004\uffee\005\uffee\007\uffee\010\uffee\011\uffee\013" +
    "\uffee\014\uffee\020\uffee\022\uffee\023\uffee\001\002\000\004" +
    "\022\071\001\002\000\006\022\uffe1\023\067\001\002\000" +
    "\032\006\025\012\024\013\023\016\022\021\021\022\uffe2" +
    "\024\016\025\014\027\006\030\004\031\020\032\011\001" +
    "\002\000\004\022\uffe0\001\002\000\024\004\uffed\007\uffed" +
    "\010\uffed\011\uffed\013\uffed\014\uffed\020\uffed\022\uffed\023" +
    "\uffed\001\002\000\004\005\073\001\002\000\030\006\025" +
    "\012\024\013\023\016\022\021\021\024\016\025\014\027" +
    "\006\030\004\031\020\032\011\001\002\000\004\004\075" +
    "\001\002\000\036\002\uffff\006\025\007\uffff\012\024\013" +
    "\023\016\022\021\021\024\016\025\014\026\012\027\006" +
    "\030\004\031\020\032\011\001\002\000\006\002\ufffe\007" +
    "\ufffe\001\002\000\030\006\044\013\023\014\uffdf\016\022" +
    "\021\021\024\016\025\014\027\006\030\004\031\020\032" +
    "\011\001\002\000\006\014\uffde\023\103\001\002\000\004" +
    "\014\102\001\002\000\022\004\ufff5\007\ufff5\010\ufff5\011" +
    "\ufff5\014\ufff5\020\ufff5\022\ufff5\023\ufff5\001\002\000\030" +
    "\006\044\013\023\014\uffdf\016\022\021\021\024\016\025" +
    "\014\027\006\030\004\031\020\032\011\001\002\000\004" +
    "\014\uffdd\001\002" });

  /** Access to parse-action table. */
  public short[][] action_table() {return _action_table;}

  /** <code>reduce_goto</code> table. */
  protected static final short[][] _reduce_table = 
    unpackFromStrings(new String[] {
    "\000\102\000\030\002\030\003\031\004\014\005\007\006" +
    "\006\007\012\010\004\011\025\012\026\013\016\014\027" +
    "\001\001\000\002\001\001\000\002\001\001\000\002\001" +
    "\001\000\002\001\001\000\002\001\001\000\002\001\001" +
    "\000\004\011\071\001\001\000\002\001\001\000\002\001" +
    "\001\000\002\001\001\000\002\001\001\000\002\001\001" +
    "\000\002\001\001\000\030\003\065\004\014\005\007\006" +
    "\006\007\012\010\004\011\025\012\026\013\016\014\027" +
    "\016\064\001\001\000\002\001\001\000\006\011\050\015" +
    "\051\001\001\000\020\006\047\007\012\010\004\011\025" +
    "\012\026\013\016\014\027\001\001\000\030\002\036\003" +
    "\031\004\035\005\007\006\006\007\012\010\004\011\025" +
    "\012\026\013\016\014\027\001\001\000\002\001\001\000" +
    "\002\001\001\000\002\001\001\000\002\001\001\000\002" +
    "\001\001\000\030\002\033\003\031\004\014\005\007\006" +
    "\006\007\012\010\004\011\025\012\026\013\016\014\027" +
    "\001\001\000\002\001\001\000\002\001\001\000\002\001" +
    "\001\000\002\001\001\000\002\001\001\000\022\005\042" +
    "\006\006\007\012\010\004\011\025\012\026\013\016\014" +
    "\027\001\001\000\002\001\001\000\002\001\001\000\024" +
    "\004\044\005\007\006\006\007\012\010\004\011\025\012" +
    "\026\013\016\014\027\001\001\000\002\001\001\000\020" +
    "\006\046\007\012\010\004\011\025\012\026\013\016\014" +
    "\027\001\001\000\002\001\001\000\002\001\001\000\002" +
    "\001\001\000\002\001\001\000\002\001\001\000\002\001" +
    "\001\000\030\002\055\003\031\004\014\005\007\006\006" +
    "\007\012\010\004\011\025\012\026\013\016\014\027\001" +
    "\001\000\002\001\001\000\002\001\001\000\006\011\050" +
    "\015\060\001\001\000\002\001\001\000\026\003\062\004" +
    "\014\005\007\006\006\007\012\010\004\011\025\012\026" +
    "\013\016\014\027\001\001\000\002\001\001\000\002\001" +
    "\001\000\002\001\001\000\002\001\001\000\030\003\065" +
    "\004\014\005\007\006\006\007\012\010\004\011\025\012" +
    "\026\013\016\014\027\016\067\001\001\000\002\001\001" +
    "\000\002\001\001\000\002\001\001\000\026\003\073\004" +
    "\014\005\007\006\006\007\012\010\004\011\025\012\026" +
    "\013\016\014\027\001\001\000\002\001\001\000\030\002" +
    "\075\003\031\004\014\005\007\006\006\007\012\010\004" +
    "\011\025\012\026\013\016\014\027\001\001\000\002\001" +
    "\001\000\020\007\077\010\004\011\025\012\026\013\016" +
    "\014\027\017\100\001\001\000\002\001\001\000\002\001" +
    "\001\000\002\001\001\000\020\007\077\010\004\011\025" +
    "\012\026\013\016\014\027\017\103\001\001\000\002\001" +
    "\001" });

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



  public com.biosimilarity.lift.lib.scalar.Absyn.Program pProgram() throws Exception
  {
	java_cup.runtime.Symbol res = parse();
	return (com.biosimilarity.lift.lib.scalar.Absyn.Program) res.value;
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
          case 36: // ListLambdaExpr ::= LambdaExpr _SYMB_15 ListLambdaExpr 
            {
              com.biosimilarity.lift.lib.scalar.Absyn.ListLambdaExpr RESULT =null;
		com.biosimilarity.lift.lib.scalar.Absyn.LambdaExpr p_1 = (com.biosimilarity.lift.lib.scalar.Absyn.LambdaExpr)((java_cup.runtime.Symbol) CUP$parser$stack.elementAt(CUP$parser$top-2)).value;
		com.biosimilarity.lift.lib.scalar.Absyn.ListLambdaExpr p_3 = (com.biosimilarity.lift.lib.scalar.Absyn.ListLambdaExpr)((java_cup.runtime.Symbol) CUP$parser$stack.peek()).value;
		 RESULT = p_3; p_3.addFirst(p_1); 
              CUP$parser$result = parser.getSymbolFactory().newSymbol("ListLambdaExpr",13, RESULT);
            }
          return CUP$parser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 35: // ListLambdaExpr ::= LambdaExpr 
            {
              com.biosimilarity.lift.lib.scalar.Absyn.ListLambdaExpr RESULT =null;
		com.biosimilarity.lift.lib.scalar.Absyn.LambdaExpr p_1 = (com.biosimilarity.lift.lib.scalar.Absyn.LambdaExpr)((java_cup.runtime.Symbol) CUP$parser$stack.peek()).value;
		 RESULT = new com.biosimilarity.lift.lib.scalar.Absyn.ListLambdaExpr(); RESULT.addLast(p_1); 
              CUP$parser$result = parser.getSymbolFactory().newSymbol("ListLambdaExpr",13, RESULT);
            }
          return CUP$parser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 34: // ListLambdaExpr ::= 
            {
              com.biosimilarity.lift.lib.scalar.Absyn.ListLambdaExpr RESULT =null;
		 RESULT = new com.biosimilarity.lift.lib.scalar.Absyn.ListLambdaExpr(); 
              CUP$parser$result = parser.getSymbolFactory().newSymbol("ListLambdaExpr",13, RESULT);
            }
          return CUP$parser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 33: // ListExpression ::= Expression _SYMB_15 ListExpression 
            {
              com.biosimilarity.lift.lib.scalar.Absyn.ListExpression RESULT =null;
		com.biosimilarity.lift.lib.scalar.Absyn.Expression p_1 = (com.biosimilarity.lift.lib.scalar.Absyn.Expression)((java_cup.runtime.Symbol) CUP$parser$stack.elementAt(CUP$parser$top-2)).value;
		com.biosimilarity.lift.lib.scalar.Absyn.ListExpression p_3 = (com.biosimilarity.lift.lib.scalar.Absyn.ListExpression)((java_cup.runtime.Symbol) CUP$parser$stack.peek()).value;
		 RESULT = p_3; p_3.addFirst(p_1); 
              CUP$parser$result = parser.getSymbolFactory().newSymbol("ListExpression",12, RESULT);
            }
          return CUP$parser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 32: // ListExpression ::= Expression 
            {
              com.biosimilarity.lift.lib.scalar.Absyn.ListExpression RESULT =null;
		com.biosimilarity.lift.lib.scalar.Absyn.Expression p_1 = (com.biosimilarity.lift.lib.scalar.Absyn.Expression)((java_cup.runtime.Symbol) CUP$parser$stack.peek()).value;
		 RESULT = new com.biosimilarity.lift.lib.scalar.Absyn.ListExpression(); RESULT.addLast(p_1); 
              CUP$parser$result = parser.getSymbolFactory().newSymbol("ListExpression",12, RESULT);
            }
          return CUP$parser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 31: // ListExpression ::= 
            {
              com.biosimilarity.lift.lib.scalar.Absyn.ListExpression RESULT =null;
		 RESULT = new com.biosimilarity.lift.lib.scalar.Absyn.ListExpression(); 
              CUP$parser$result = parser.getSymbolFactory().newSymbol("ListExpression",12, RESULT);
            }
          return CUP$parser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 30: // ListVariableExpr ::= VariableExpr _SYMB_15 ListVariableExpr 
            {
              com.biosimilarity.lift.lib.scalar.Absyn.ListVariableExpr RESULT =null;
		com.biosimilarity.lift.lib.scalar.Absyn.VariableExpr p_1 = (com.biosimilarity.lift.lib.scalar.Absyn.VariableExpr)((java_cup.runtime.Symbol) CUP$parser$stack.elementAt(CUP$parser$top-2)).value;
		com.biosimilarity.lift.lib.scalar.Absyn.ListVariableExpr p_3 = (com.biosimilarity.lift.lib.scalar.Absyn.ListVariableExpr)((java_cup.runtime.Symbol) CUP$parser$stack.peek()).value;
		 RESULT = p_3; p_3.addFirst(p_1); 
              CUP$parser$result = parser.getSymbolFactory().newSymbol("ListVariableExpr",11, RESULT);
            }
          return CUP$parser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 29: // ListVariableExpr ::= VariableExpr 
            {
              com.biosimilarity.lift.lib.scalar.Absyn.ListVariableExpr RESULT =null;
		com.biosimilarity.lift.lib.scalar.Absyn.VariableExpr p_1 = (com.biosimilarity.lift.lib.scalar.Absyn.VariableExpr)((java_cup.runtime.Symbol) CUP$parser$stack.peek()).value;
		 RESULT = new com.biosimilarity.lift.lib.scalar.Absyn.ListVariableExpr(); RESULT.addLast(p_1); 
              CUP$parser$result = parser.getSymbolFactory().newSymbol("ListVariableExpr",11, RESULT);
            }
          return CUP$parser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 28: // ListVariableExpr ::= 
            {
              com.biosimilarity.lift.lib.scalar.Absyn.ListVariableExpr RESULT =null;
		 RESULT = new com.biosimilarity.lift.lib.scalar.Absyn.ListVariableExpr(); 
              CUP$parser$result = parser.getSymbolFactory().newSymbol("ListVariableExpr",11, RESULT);
            }
          return CUP$parser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 27: // Logical ::= _SYMB_16 
            {
              com.biosimilarity.lift.lib.scalar.Absyn.Logical RESULT =null;
		 RESULT = new com.biosimilarity.lift.lib.scalar.Absyn.Absurdity(); 
              CUP$parser$result = parser.getSymbolFactory().newSymbol("Logical",10, RESULT);
            }
          return CUP$parser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 26: // Logical ::= _SYMB_17 
            {
              com.biosimilarity.lift.lib.scalar.Absyn.Logical RESULT =null;
		 RESULT = new com.biosimilarity.lift.lib.scalar.Absyn.Verity(); 
              CUP$parser$result = parser.getSymbolFactory().newSymbol("Logical",10, RESULT);
            }
          return CUP$parser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 25: // Numeric ::= _INTEGER_ 
            {
              com.biosimilarity.lift.lib.scalar.Absyn.Numeric RESULT =null;
		Integer p_1 = (Integer)((java_cup.runtime.Symbol) CUP$parser$stack.peek()).value;
		 RESULT = new com.biosimilarity.lift.lib.scalar.Absyn.Count(p_1); 
              CUP$parser$result = parser.getSymbolFactory().newSymbol("Numeric",9, RESULT);
            }
          return CUP$parser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 24: // Numeric ::= _DOUBLE_ 
            {
              com.biosimilarity.lift.lib.scalar.Absyn.Numeric RESULT =null;
		Double p_1 = (Double)((java_cup.runtime.Symbol) CUP$parser$stack.peek()).value;
		 RESULT = new com.biosimilarity.lift.lib.scalar.Absyn.Measure(p_1); 
              CUP$parser$result = parser.getSymbolFactory().newSymbol("Numeric",9, RESULT);
            }
          return CUP$parser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 23: // ValueExpr ::= _STRING_ 
            {
              com.biosimilarity.lift.lib.scalar.Absyn.ValueExpr RESULT =null;
		String p_1 = (String)((java_cup.runtime.Symbol) CUP$parser$stack.peek()).value;
		 RESULT = new com.biosimilarity.lift.lib.scalar.Absyn.Utterance(p_1); 
              CUP$parser$result = parser.getSymbolFactory().newSymbol("ValueExpr",8, RESULT);
            }
          return CUP$parser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 22: // ValueExpr ::= Logical 
            {
              com.biosimilarity.lift.lib.scalar.Absyn.ValueExpr RESULT =null;
		com.biosimilarity.lift.lib.scalar.Absyn.Logical p_1 = (com.biosimilarity.lift.lib.scalar.Absyn.Logical)((java_cup.runtime.Symbol) CUP$parser$stack.peek()).value;
		 RESULT = new com.biosimilarity.lift.lib.scalar.Absyn.Quality(p_1); 
              CUP$parser$result = parser.getSymbolFactory().newSymbol("ValueExpr",8, RESULT);
            }
          return CUP$parser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 21: // ValueExpr ::= Numeric 
            {
              com.biosimilarity.lift.lib.scalar.Absyn.ValueExpr RESULT =null;
		com.biosimilarity.lift.lib.scalar.Absyn.Numeric p_1 = (com.biosimilarity.lift.lib.scalar.Absyn.Numeric)((java_cup.runtime.Symbol) CUP$parser$stack.peek()).value;
		 RESULT = new com.biosimilarity.lift.lib.scalar.Absyn.Quantity(p_1); 
              CUP$parser$result = parser.getSymbolFactory().newSymbol("ValueExpr",8, RESULT);
            }
          return CUP$parser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 20: // ValueExpr ::= _SYMB_13 ListExpression _SYMB_14 
            {
              com.biosimilarity.lift.lib.scalar.Absyn.ValueExpr RESULT =null;
		com.biosimilarity.lift.lib.scalar.Absyn.ListExpression p_2 = (com.biosimilarity.lift.lib.scalar.Absyn.ListExpression)((java_cup.runtime.Symbol) CUP$parser$stack.elementAt(CUP$parser$top-1)).value;
		 RESULT = new com.biosimilarity.lift.lib.scalar.Absyn.Listed(p_2); 
              CUP$parser$result = parser.getSymbolFactory().newSymbol("ValueExpr",8, RESULT);
            }
          return CUP$parser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 19: // VariableExpr ::= _SYMB_10 _SYMB_11 Expression _SYMB_12 
            {
              com.biosimilarity.lift.lib.scalar.Absyn.VariableExpr RESULT =null;
		com.biosimilarity.lift.lib.scalar.Absyn.Expression p_3 = (com.biosimilarity.lift.lib.scalar.Absyn.Expression)((java_cup.runtime.Symbol) CUP$parser$stack.elementAt(CUP$parser$top-1)).value;
		 RESULT = new com.biosimilarity.lift.lib.scalar.Absyn.Transcription(p_3); 
              CUP$parser$result = parser.getSymbolFactory().newSymbol("VariableExpr",7, RESULT);
            }
          return CUP$parser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 18: // VariableExpr ::= _IDENT_ 
            {
              com.biosimilarity.lift.lib.scalar.Absyn.VariableExpr RESULT =null;
		String p_1 = (String)((java_cup.runtime.Symbol) CUP$parser$stack.peek()).value;
		 RESULT = new com.biosimilarity.lift.lib.scalar.Absyn.Atom(p_1); 
              CUP$parser$result = parser.getSymbolFactory().newSymbol("VariableExpr",7, RESULT);
            }
          return CUP$parser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 17: // LambdaExpr1 ::= _SYMB_2 ArithmeticExpr _SYMB_3 
            {
              com.biosimilarity.lift.lib.scalar.Absyn.LambdaExpr RESULT =null;
		com.biosimilarity.lift.lib.scalar.Absyn.ArithmeticExpr p_2 = (com.biosimilarity.lift.lib.scalar.Absyn.ArithmeticExpr)((java_cup.runtime.Symbol) CUP$parser$stack.elementAt(CUP$parser$top-1)).value;
		 RESULT = new com.biosimilarity.lift.lib.scalar.Absyn.Association(p_2); 
              CUP$parser$result = parser.getSymbolFactory().newSymbol("LambdaExpr1",6, RESULT);
            }
          return CUP$parser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 16: // LambdaExpr1 ::= ValueExpr 
            {
              com.biosimilarity.lift.lib.scalar.Absyn.LambdaExpr RESULT =null;
		com.biosimilarity.lift.lib.scalar.Absyn.ValueExpr p_1 = (com.biosimilarity.lift.lib.scalar.Absyn.ValueExpr)((java_cup.runtime.Symbol) CUP$parser$stack.peek()).value;
		 RESULT = new com.biosimilarity.lift.lib.scalar.Absyn.Value(p_1); 
              CUP$parser$result = parser.getSymbolFactory().newSymbol("LambdaExpr1",6, RESULT);
            }
          return CUP$parser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 15: // LambdaExpr1 ::= VariableExpr 
            {
              com.biosimilarity.lift.lib.scalar.Absyn.LambdaExpr RESULT =null;
		com.biosimilarity.lift.lib.scalar.Absyn.VariableExpr p_1 = (com.biosimilarity.lift.lib.scalar.Absyn.VariableExpr)((java_cup.runtime.Symbol) CUP$parser$stack.peek()).value;
		 RESULT = new com.biosimilarity.lift.lib.scalar.Absyn.Mention(p_1); 
              CUP$parser$result = parser.getSymbolFactory().newSymbol("LambdaExpr1",6, RESULT);
            }
          return CUP$parser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 14: // LambdaExpr1 ::= _SYMB_7 ListVariableExpr _SYMB_8 _SYMB_9 _SYMB_2 Program _SYMB_3 
            {
              com.biosimilarity.lift.lib.scalar.Absyn.LambdaExpr RESULT =null;
		com.biosimilarity.lift.lib.scalar.Absyn.ListVariableExpr p_2 = (com.biosimilarity.lift.lib.scalar.Absyn.ListVariableExpr)((java_cup.runtime.Symbol) CUP$parser$stack.elementAt(CUP$parser$top-5)).value;
		com.biosimilarity.lift.lib.scalar.Absyn.Program p_6 = (com.biosimilarity.lift.lib.scalar.Absyn.Program)((java_cup.runtime.Symbol) CUP$parser$stack.elementAt(CUP$parser$top-1)).value;
		 RESULT = new com.biosimilarity.lift.lib.scalar.Absyn.Abstraction(p_2,p_6); 
              CUP$parser$result = parser.getSymbolFactory().newSymbol("LambdaExpr1",6, RESULT);
            }
          return CUP$parser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 13: // LambdaExpr ::= LambdaExpr1 
            {
              com.biosimilarity.lift.lib.scalar.Absyn.LambdaExpr RESULT =null;
		com.biosimilarity.lift.lib.scalar.Absyn.LambdaExpr p_1 = (com.biosimilarity.lift.lib.scalar.Absyn.LambdaExpr)((java_cup.runtime.Symbol) CUP$parser$stack.peek()).value;
		 RESULT = p_1; 
              CUP$parser$result = parser.getSymbolFactory().newSymbol("LambdaExpr",5, RESULT);
            }
          return CUP$parser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 12: // LambdaExpr ::= LambdaExpr1 _SYMB_7 ListLambdaExpr _SYMB_8 
            {
              com.biosimilarity.lift.lib.scalar.Absyn.LambdaExpr RESULT =null;
		com.biosimilarity.lift.lib.scalar.Absyn.LambdaExpr p_1 = (com.biosimilarity.lift.lib.scalar.Absyn.LambdaExpr)((java_cup.runtime.Symbol) CUP$parser$stack.elementAt(CUP$parser$top-3)).value;
		com.biosimilarity.lift.lib.scalar.Absyn.ListLambdaExpr p_3 = (com.biosimilarity.lift.lib.scalar.Absyn.ListLambdaExpr)((java_cup.runtime.Symbol) CUP$parser$stack.elementAt(CUP$parser$top-1)).value;
		 RESULT = new com.biosimilarity.lift.lib.scalar.Absyn.Application(p_1,p_3); 
              CUP$parser$result = parser.getSymbolFactory().newSymbol("LambdaExpr",5, RESULT);
            }
          return CUP$parser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 11: // ArithmeticExpr2 ::= LambdaExpr 
            {
              com.biosimilarity.lift.lib.scalar.Absyn.ArithmeticExpr RESULT =null;
		com.biosimilarity.lift.lib.scalar.Absyn.LambdaExpr p_1 = (com.biosimilarity.lift.lib.scalar.Absyn.LambdaExpr)((java_cup.runtime.Symbol) CUP$parser$stack.peek()).value;
		 RESULT = new com.biosimilarity.lift.lib.scalar.Absyn.Function(p_1); 
              CUP$parser$result = parser.getSymbolFactory().newSymbol("ArithmeticExpr2",4, RESULT);
            }
          return CUP$parser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 10: // ArithmeticExpr2 ::= _SYMB_6 ArithmeticExpr2 
            {
              com.biosimilarity.lift.lib.scalar.Absyn.ArithmeticExpr RESULT =null;
		com.biosimilarity.lift.lib.scalar.Absyn.ArithmeticExpr p_2 = (com.biosimilarity.lift.lib.scalar.Absyn.ArithmeticExpr)((java_cup.runtime.Symbol) CUP$parser$stack.peek()).value;
		 RESULT = new com.biosimilarity.lift.lib.scalar.Absyn.Negation(p_2); 
              CUP$parser$result = parser.getSymbolFactory().newSymbol("ArithmeticExpr2",4, RESULT);
            }
          return CUP$parser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 9: // ArithmeticExpr1 ::= ArithmeticExpr2 
            {
              com.biosimilarity.lift.lib.scalar.Absyn.ArithmeticExpr RESULT =null;
		com.biosimilarity.lift.lib.scalar.Absyn.ArithmeticExpr p_1 = (com.biosimilarity.lift.lib.scalar.Absyn.ArithmeticExpr)((java_cup.runtime.Symbol) CUP$parser$stack.peek()).value;
		 RESULT = p_1; 
              CUP$parser$result = parser.getSymbolFactory().newSymbol("ArithmeticExpr1",3, RESULT);
            }
          return CUP$parser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 8: // ArithmeticExpr1 ::= ArithmeticExpr1 _SYMB_5 ArithmeticExpr2 
            {
              com.biosimilarity.lift.lib.scalar.Absyn.ArithmeticExpr RESULT =null;
		com.biosimilarity.lift.lib.scalar.Absyn.ArithmeticExpr p_1 = (com.biosimilarity.lift.lib.scalar.Absyn.ArithmeticExpr)((java_cup.runtime.Symbol) CUP$parser$stack.elementAt(CUP$parser$top-2)).value;
		com.biosimilarity.lift.lib.scalar.Absyn.ArithmeticExpr p_3 = (com.biosimilarity.lift.lib.scalar.Absyn.ArithmeticExpr)((java_cup.runtime.Symbol) CUP$parser$stack.peek()).value;
		 RESULT = new com.biosimilarity.lift.lib.scalar.Absyn.Multiplication(p_1,p_3); 
              CUP$parser$result = parser.getSymbolFactory().newSymbol("ArithmeticExpr1",3, RESULT);
            }
          return CUP$parser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 7: // ArithmeticExpr ::= ArithmeticExpr1 
            {
              com.biosimilarity.lift.lib.scalar.Absyn.ArithmeticExpr RESULT =null;
		com.biosimilarity.lift.lib.scalar.Absyn.ArithmeticExpr p_1 = (com.biosimilarity.lift.lib.scalar.Absyn.ArithmeticExpr)((java_cup.runtime.Symbol) CUP$parser$stack.peek()).value;
		 RESULT = p_1; 
              CUP$parser$result = parser.getSymbolFactory().newSymbol("ArithmeticExpr",2, RESULT);
            }
          return CUP$parser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 6: // ArithmeticExpr ::= ArithmeticExpr _SYMB_4 ArithmeticExpr1 
            {
              com.biosimilarity.lift.lib.scalar.Absyn.ArithmeticExpr RESULT =null;
		com.biosimilarity.lift.lib.scalar.Absyn.ArithmeticExpr p_1 = (com.biosimilarity.lift.lib.scalar.Absyn.ArithmeticExpr)((java_cup.runtime.Symbol) CUP$parser$stack.elementAt(CUP$parser$top-2)).value;
		com.biosimilarity.lift.lib.scalar.Absyn.ArithmeticExpr p_3 = (com.biosimilarity.lift.lib.scalar.Absyn.ArithmeticExpr)((java_cup.runtime.Symbol) CUP$parser$stack.peek()).value;
		 RESULT = new com.biosimilarity.lift.lib.scalar.Absyn.Summation(p_1,p_3); 
              CUP$parser$result = parser.getSymbolFactory().newSymbol("ArithmeticExpr",2, RESULT);
            }
          return CUP$parser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 5: // Expression ::= _SYMB_2 Program _SYMB_3 
            {
              com.biosimilarity.lift.lib.scalar.Absyn.Expression RESULT =null;
		com.biosimilarity.lift.lib.scalar.Absyn.Program p_2 = (com.biosimilarity.lift.lib.scalar.Absyn.Program)((java_cup.runtime.Symbol) CUP$parser$stack.elementAt(CUP$parser$top-1)).value;
		 RESULT = new com.biosimilarity.lift.lib.scalar.Absyn.Embedding(p_2); 
              CUP$parser$result = parser.getSymbolFactory().newSymbol("Expression",1, RESULT);
            }
          return CUP$parser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 4: // Expression ::= ArithmeticExpr 
            {
              com.biosimilarity.lift.lib.scalar.Absyn.Expression RESULT =null;
		com.biosimilarity.lift.lib.scalar.Absyn.ArithmeticExpr p_1 = (com.biosimilarity.lift.lib.scalar.Absyn.ArithmeticExpr)((java_cup.runtime.Symbol) CUP$parser$stack.peek()).value;
		 RESULT = new com.biosimilarity.lift.lib.scalar.Absyn.Calculation(p_1); 
              CUP$parser$result = parser.getSymbolFactory().newSymbol("Expression",1, RESULT);
            }
          return CUP$parser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 3: // Program ::= _SYMB_18 VariableExpr _SYMB_1 Expression _SYMB_0 Program 
            {
              com.biosimilarity.lift.lib.scalar.Absyn.Program RESULT =null;
		com.biosimilarity.lift.lib.scalar.Absyn.VariableExpr p_2 = (com.biosimilarity.lift.lib.scalar.Absyn.VariableExpr)((java_cup.runtime.Symbol) CUP$parser$stack.elementAt(CUP$parser$top-4)).value;
		com.biosimilarity.lift.lib.scalar.Absyn.Expression p_4 = (com.biosimilarity.lift.lib.scalar.Absyn.Expression)((java_cup.runtime.Symbol) CUP$parser$stack.elementAt(CUP$parser$top-2)).value;
		com.biosimilarity.lift.lib.scalar.Absyn.Program p_6 = (com.biosimilarity.lift.lib.scalar.Absyn.Program)((java_cup.runtime.Symbol) CUP$parser$stack.peek()).value;
		 RESULT = new com.biosimilarity.lift.lib.scalar.Absyn.Binding(p_2,p_4,p_6); 
              CUP$parser$result = parser.getSymbolFactory().newSymbol("Program",0, RESULT);
            }
          return CUP$parser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 2: // Program ::= 
            {
              com.biosimilarity.lift.lib.scalar.Absyn.Program RESULT =null;
		 RESULT = new com.biosimilarity.lift.lib.scalar.Absyn.Completion(); 
              CUP$parser$result = parser.getSymbolFactory().newSymbol("Program",0, RESULT);
            }
          return CUP$parser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 1: // Program ::= Expression _SYMB_0 Program 
            {
              com.biosimilarity.lift.lib.scalar.Absyn.Program RESULT =null;
		com.biosimilarity.lift.lib.scalar.Absyn.Expression p_1 = (com.biosimilarity.lift.lib.scalar.Absyn.Expression)((java_cup.runtime.Symbol) CUP$parser$stack.elementAt(CUP$parser$top-2)).value;
		com.biosimilarity.lift.lib.scalar.Absyn.Program p_3 = (com.biosimilarity.lift.lib.scalar.Absyn.Program)((java_cup.runtime.Symbol) CUP$parser$stack.peek()).value;
		 RESULT = new com.biosimilarity.lift.lib.scalar.Absyn.Progression(p_1,p_3); 
              CUP$parser$result = parser.getSymbolFactory().newSymbol("Program",0, RESULT);
            }
          return CUP$parser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 0: // $START ::= Program EOF 
            {
              Object RESULT =null;
		com.biosimilarity.lift.lib.scalar.Absyn.Program start_val = (com.biosimilarity.lift.lib.scalar.Absyn.Program)((java_cup.runtime.Symbol) CUP$parser$stack.elementAt(CUP$parser$top-1)).value;
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


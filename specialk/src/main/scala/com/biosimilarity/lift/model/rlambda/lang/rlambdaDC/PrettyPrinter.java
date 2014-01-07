package com.biosimilarity.rlambdaDC.lang.rlambdaDC;
import com.biosimilarity.rlambdaDC.lang.rlambdaDC.Absyn.*;

public class PrettyPrinter
{
  //For certain applications increasing the initial size of the buffer may improve performance.
  private static final int INITIAL_BUFFER_SIZE = 128;
  //You may wish to change the parentheses used in precedence.
  private static final String _L_PAREN = new String("(");
  private static final String _R_PAREN = new String(")");
  //You may wish to change render
  private static void render(String s)
  {
    if (s.equals("{"))
    {
       buf_.append("\n");
       indent();
       buf_.append(s);
       _n_ = _n_ + 2;
       buf_.append("\n");
       indent();
    }
    else if (s.equals("(") || s.equals("["))
       buf_.append(s);
    else if (s.equals(")") || s.equals("]"))
    {
       backup();
       buf_.append(s);
       buf_.append(" ");
    }
    else if (s.equals("}"))
    {
       _n_ = _n_ - 2;
       backup();
       backup();
       buf_.append(s);
       buf_.append("\n");
       indent();
    }
    else if (s.equals(","))
    {
       backup();
       buf_.append(s);
       buf_.append(" ");
    }
    else if (s.equals(";"))
    {
       backup();
       buf_.append(s);
       buf_.append("\n");
       indent();
    }
    else if (s.equals("")) return;
    else
    {
       buf_.append(s);
       buf_.append(" ");
    }
  }


  //  print and show methods are defined for each category.
  public static String print(com.biosimilarity.rlambdaDC.lang.rlambdaDC.Absyn.Expression foo)
  {
    pp(foo, 0);
    trim();
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  public static String show(com.biosimilarity.rlambdaDC.lang.rlambdaDC.Absyn.Expression foo)
  {
    sh(foo);
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  public static String print(com.biosimilarity.rlambdaDC.lang.rlambdaDC.Absyn.ContinueExpr foo)
  {
    pp(foo, 0);
    trim();
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  public static String show(com.biosimilarity.rlambdaDC.lang.rlambdaDC.Absyn.ContinueExpr foo)
  {
    sh(foo);
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  public static String print(com.biosimilarity.rlambdaDC.lang.rlambdaDC.Absyn.VariableExpr foo)
  {
    pp(foo, 0);
    trim();
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  public static String show(com.biosimilarity.rlambdaDC.lang.rlambdaDC.Absyn.VariableExpr foo)
  {
    sh(foo);
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  public static String print(com.biosimilarity.rlambdaDC.lang.rlambdaDC.Absyn.ValueExpr foo)
  {
    pp(foo, 0);
    trim();
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  public static String show(com.biosimilarity.rlambdaDC.lang.rlambdaDC.Absyn.ValueExpr foo)
  {
    sh(foo);
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  public static String print(com.biosimilarity.rlambdaDC.lang.rlambdaDC.Absyn.ListVariableExpr foo)
  {
    pp(foo, 0);
    trim();
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  public static String show(com.biosimilarity.rlambdaDC.lang.rlambdaDC.Absyn.ListVariableExpr foo)
  {
    sh(foo);
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  /***   You shouldn't need to change anything beyond this point.   ***/

  private static void pp(com.biosimilarity.rlambdaDC.lang.rlambdaDC.Absyn.Expression foo, int _i_)
  {
    if (foo instanceof com.biosimilarity.rlambdaDC.lang.rlambdaDC.Absyn.Application)
    {
       com.biosimilarity.rlambdaDC.lang.rlambdaDC.Absyn.Application _application = (com.biosimilarity.rlambdaDC.lang.rlambdaDC.Absyn.Application) foo;
       if (_i_ > 0) render(_L_PAREN);
       pp(_application.expression_1, 0);
       pp(_application.expression_2, 1);
       if (_i_ > 0) render(_R_PAREN);
    }
    else     if (foo instanceof com.biosimilarity.rlambdaDC.lang.rlambdaDC.Absyn.Mention)
    {
       com.biosimilarity.rlambdaDC.lang.rlambdaDC.Absyn.Mention _mention = (com.biosimilarity.rlambdaDC.lang.rlambdaDC.Absyn.Mention) foo;
       if (_i_ > 1) render(_L_PAREN);
       pp(_mention.variableexpr_, 0);
       if (_i_ > 1) render(_R_PAREN);
    }
    else     if (foo instanceof com.biosimilarity.rlambdaDC.lang.rlambdaDC.Absyn.Value)
    {
       com.biosimilarity.rlambdaDC.lang.rlambdaDC.Absyn.Value _value = (com.biosimilarity.rlambdaDC.lang.rlambdaDC.Absyn.Value) foo;
       if (_i_ > 1) render(_L_PAREN);
       pp(_value.valueexpr_, 0);
       if (_i_ > 1) render(_R_PAREN);
    }
    else     if (foo instanceof com.biosimilarity.rlambdaDC.lang.rlambdaDC.Absyn.Continuation)
    {
       com.biosimilarity.rlambdaDC.lang.rlambdaDC.Absyn.Continuation _continuation = (com.biosimilarity.rlambdaDC.lang.rlambdaDC.Absyn.Continuation) foo;
       if (_i_ > 1) render(_L_PAREN);
       pp(_continuation.continueexpr_, 0);
       if (_i_ > 1) render(_R_PAREN);
    }
    else     if (foo instanceof com.biosimilarity.rlambdaDC.lang.rlambdaDC.Absyn.Abstraction)
    {
       com.biosimilarity.rlambdaDC.lang.rlambdaDC.Absyn.Abstraction _abstraction = (com.biosimilarity.rlambdaDC.lang.rlambdaDC.Absyn.Abstraction) foo;
       if (_i_ > 1) render(_L_PAREN);
       render("lambda");
       pp(_abstraction.listvariableexpr_, 0);
       render(".");
       pp(_abstraction.expression_, 1);
       if (_i_ > 1) render(_R_PAREN);
    }
  }

  private static void pp(com.biosimilarity.rlambdaDC.lang.rlambdaDC.Absyn.ContinueExpr foo, int _i_)
  {
    if (foo instanceof com.biosimilarity.rlambdaDC.lang.rlambdaDC.Absyn.Prompt)
    {
       com.biosimilarity.rlambdaDC.lang.rlambdaDC.Absyn.Prompt _prompt = (com.biosimilarity.rlambdaDC.lang.rlambdaDC.Absyn.Prompt) foo;
       if (_i_ > 0) render(_L_PAREN);
       render("newPrompt");
       if (_i_ > 0) render(_R_PAREN);
    }
    else     if (foo instanceof com.biosimilarity.rlambdaDC.lang.rlambdaDC.Absyn.PushPrompt)
    {
       com.biosimilarity.rlambdaDC.lang.rlambdaDC.Absyn.PushPrompt _pushprompt = (com.biosimilarity.rlambdaDC.lang.rlambdaDC.Absyn.PushPrompt) foo;
       if (_i_ > 0) render(_L_PAREN);
       render("pushPrompt");
       pp(_pushprompt.expression_1, 1);
       pp(_pushprompt.expression_2, 1);
       if (_i_ > 0) render(_R_PAREN);
    }
    else     if (foo instanceof com.biosimilarity.rlambdaDC.lang.rlambdaDC.Absyn.Subcontinuation)
    {
       com.biosimilarity.rlambdaDC.lang.rlambdaDC.Absyn.Subcontinuation _subcontinuation = (com.biosimilarity.rlambdaDC.lang.rlambdaDC.Absyn.Subcontinuation) foo;
       if (_i_ > 0) render(_L_PAREN);
       render("withSubCont");
       pp(_subcontinuation.expression_1, 1);
       pp(_subcontinuation.expression_2, 1);
       if (_i_ > 0) render(_R_PAREN);
    }
    else     if (foo instanceof com.biosimilarity.rlambdaDC.lang.rlambdaDC.Absyn.PushSubCont)
    {
       com.biosimilarity.rlambdaDC.lang.rlambdaDC.Absyn.PushSubCont _pushsubcont = (com.biosimilarity.rlambdaDC.lang.rlambdaDC.Absyn.PushSubCont) foo;
       if (_i_ > 0) render(_L_PAREN);
       render("pushSubCont");
       pp(_pushsubcont.expression_1, 1);
       pp(_pushsubcont.expression_2, 1);
       if (_i_ > 0) render(_R_PAREN);
    }
  }

  private static void pp(com.biosimilarity.rlambdaDC.lang.rlambdaDC.Absyn.VariableExpr foo, int _i_)
  {
    if (foo instanceof com.biosimilarity.rlambdaDC.lang.rlambdaDC.Absyn.Transcription)
    {
       com.biosimilarity.rlambdaDC.lang.rlambdaDC.Absyn.Transcription _transcription = (com.biosimilarity.rlambdaDC.lang.rlambdaDC.Absyn.Transcription) foo;
       if (_i_ > 0) render(_L_PAREN);
       render("@");
       render("<");
       pp(_transcription.expression_, 1);
       render(">");
       if (_i_ > 0) render(_R_PAREN);
    }
    else     if (foo instanceof com.biosimilarity.rlambdaDC.lang.rlambdaDC.Absyn.AtomLiteral)
    {
       com.biosimilarity.rlambdaDC.lang.rlambdaDC.Absyn.AtomLiteral _atomliteral = (com.biosimilarity.rlambdaDC.lang.rlambdaDC.Absyn.AtomLiteral) foo;
       if (_i_ > 0) render(_L_PAREN);
       pp(_atomliteral.ident_, 0);
       if (_i_ > 0) render(_R_PAREN);
    }
  }

  private static void pp(com.biosimilarity.rlambdaDC.lang.rlambdaDC.Absyn.ValueExpr foo, int _i_)
  {
    if (foo instanceof com.biosimilarity.rlambdaDC.lang.rlambdaDC.Absyn.Numeric)
    {
       com.biosimilarity.rlambdaDC.lang.rlambdaDC.Absyn.Numeric _numeric = (com.biosimilarity.rlambdaDC.lang.rlambdaDC.Absyn.Numeric) foo;
       if (_i_ > 0) render(_L_PAREN);
       pp(_numeric.integer_, 0);
       if (_i_ > 0) render(_R_PAREN);
    }
  }

  private static void pp(com.biosimilarity.rlambdaDC.lang.rlambdaDC.Absyn.ListVariableExpr foo, int _i_)
  {
     for (java.util.Iterator<VariableExpr> it = foo.iterator(); it.hasNext();)
     {
       pp(it.next(), 0);
       if (it.hasNext()) {
         render(",");
       } else {
         render("");
       }
     }
  }


  private static void sh(com.biosimilarity.rlambdaDC.lang.rlambdaDC.Absyn.Expression foo)
  {
    if (foo instanceof com.biosimilarity.rlambdaDC.lang.rlambdaDC.Absyn.Application)
    {
       com.biosimilarity.rlambdaDC.lang.rlambdaDC.Absyn.Application _application = (com.biosimilarity.rlambdaDC.lang.rlambdaDC.Absyn.Application) foo;
       render("(");
       render("Application");
       sh(_application.expression_1);
       sh(_application.expression_2);
       render(")");
    }
    if (foo instanceof com.biosimilarity.rlambdaDC.lang.rlambdaDC.Absyn.Mention)
    {
       com.biosimilarity.rlambdaDC.lang.rlambdaDC.Absyn.Mention _mention = (com.biosimilarity.rlambdaDC.lang.rlambdaDC.Absyn.Mention) foo;
       render("(");
       render("Mention");
       sh(_mention.variableexpr_);
       render(")");
    }
    if (foo instanceof com.biosimilarity.rlambdaDC.lang.rlambdaDC.Absyn.Value)
    {
       com.biosimilarity.rlambdaDC.lang.rlambdaDC.Absyn.Value _value = (com.biosimilarity.rlambdaDC.lang.rlambdaDC.Absyn.Value) foo;
       render("(");
       render("Value");
       sh(_value.valueexpr_);
       render(")");
    }
    if (foo instanceof com.biosimilarity.rlambdaDC.lang.rlambdaDC.Absyn.Continuation)
    {
       com.biosimilarity.rlambdaDC.lang.rlambdaDC.Absyn.Continuation _continuation = (com.biosimilarity.rlambdaDC.lang.rlambdaDC.Absyn.Continuation) foo;
       render("(");
       render("Continuation");
       sh(_continuation.continueexpr_);
       render(")");
    }
    if (foo instanceof com.biosimilarity.rlambdaDC.lang.rlambdaDC.Absyn.Abstraction)
    {
       com.biosimilarity.rlambdaDC.lang.rlambdaDC.Absyn.Abstraction _abstraction = (com.biosimilarity.rlambdaDC.lang.rlambdaDC.Absyn.Abstraction) foo;
       render("(");
       render("Abstraction");
       render("[");
       sh(_abstraction.listvariableexpr_);
       render("]");
       sh(_abstraction.expression_);
       render(")");
    }
  }

  private static void sh(com.biosimilarity.rlambdaDC.lang.rlambdaDC.Absyn.ContinueExpr foo)
  {
    if (foo instanceof com.biosimilarity.rlambdaDC.lang.rlambdaDC.Absyn.Prompt)
    {
       com.biosimilarity.rlambdaDC.lang.rlambdaDC.Absyn.Prompt _prompt = (com.biosimilarity.rlambdaDC.lang.rlambdaDC.Absyn.Prompt) foo;
       render("Prompt");
    }
    if (foo instanceof com.biosimilarity.rlambdaDC.lang.rlambdaDC.Absyn.PushPrompt)
    {
       com.biosimilarity.rlambdaDC.lang.rlambdaDC.Absyn.PushPrompt _pushprompt = (com.biosimilarity.rlambdaDC.lang.rlambdaDC.Absyn.PushPrompt) foo;
       render("(");
       render("PushPrompt");
       sh(_pushprompt.expression_1);
       sh(_pushprompt.expression_2);
       render(")");
    }
    if (foo instanceof com.biosimilarity.rlambdaDC.lang.rlambdaDC.Absyn.Subcontinuation)
    {
       com.biosimilarity.rlambdaDC.lang.rlambdaDC.Absyn.Subcontinuation _subcontinuation = (com.biosimilarity.rlambdaDC.lang.rlambdaDC.Absyn.Subcontinuation) foo;
       render("(");
       render("Subcontinuation");
       sh(_subcontinuation.expression_1);
       sh(_subcontinuation.expression_2);
       render(")");
    }
    if (foo instanceof com.biosimilarity.rlambdaDC.lang.rlambdaDC.Absyn.PushSubCont)
    {
       com.biosimilarity.rlambdaDC.lang.rlambdaDC.Absyn.PushSubCont _pushsubcont = (com.biosimilarity.rlambdaDC.lang.rlambdaDC.Absyn.PushSubCont) foo;
       render("(");
       render("PushSubCont");
       sh(_pushsubcont.expression_1);
       sh(_pushsubcont.expression_2);
       render(")");
    }
  }

  private static void sh(com.biosimilarity.rlambdaDC.lang.rlambdaDC.Absyn.VariableExpr foo)
  {
    if (foo instanceof com.biosimilarity.rlambdaDC.lang.rlambdaDC.Absyn.Transcription)
    {
       com.biosimilarity.rlambdaDC.lang.rlambdaDC.Absyn.Transcription _transcription = (com.biosimilarity.rlambdaDC.lang.rlambdaDC.Absyn.Transcription) foo;
       render("(");
       render("Transcription");
       sh(_transcription.expression_);
       render(")");
    }
    if (foo instanceof com.biosimilarity.rlambdaDC.lang.rlambdaDC.Absyn.AtomLiteral)
    {
       com.biosimilarity.rlambdaDC.lang.rlambdaDC.Absyn.AtomLiteral _atomliteral = (com.biosimilarity.rlambdaDC.lang.rlambdaDC.Absyn.AtomLiteral) foo;
       render("(");
       render("AtomLiteral");
       sh(_atomliteral.ident_);
       render(")");
    }
  }

  private static void sh(com.biosimilarity.rlambdaDC.lang.rlambdaDC.Absyn.ValueExpr foo)
  {
    if (foo instanceof com.biosimilarity.rlambdaDC.lang.rlambdaDC.Absyn.Numeric)
    {
       com.biosimilarity.rlambdaDC.lang.rlambdaDC.Absyn.Numeric _numeric = (com.biosimilarity.rlambdaDC.lang.rlambdaDC.Absyn.Numeric) foo;
       render("(");
       render("Numeric");
       sh(_numeric.integer_);
       render(")");
    }
  }

  private static void sh(com.biosimilarity.rlambdaDC.lang.rlambdaDC.Absyn.ListVariableExpr foo)
  {
     for (java.util.Iterator<VariableExpr> it = foo.iterator(); it.hasNext();)
     {
       sh(it.next());
       if (it.hasNext())
         render(",");
     }
  }


  private static void pp(Integer n, int _i_) { buf_.append(n); buf_.append(" "); }
  private static void pp(Double d, int _i_) { buf_.append(d); buf_.append(" "); }
  private static void pp(String s, int _i_) { buf_.append(s); buf_.append(" "); }
  private static void pp(Character c, int _i_) { buf_.append("'" + c.toString() + "'"); buf_.append(" "); }
  private static void sh(Integer n) { render(n.toString()); }
  private static void sh(Double d) { render(d.toString()); }
  private static void sh(Character c) { render(c.toString()); }
  private static void sh(String s) { printQuoted(s); }
  private static void printQuoted(String s) { render("\"" + s + "\""); }
  private static void indent()
  {
    int n = _n_;
    while (n > 0)
    {
      buf_.append(" ");
      n--;
    }
  }
  private static void backup()
  {
     if (buf_.charAt(buf_.length() - 1) == ' ') {
      buf_.setLength(buf_.length() - 1);
    }
  }
  private static void trim()
  {
     while (buf_.length() > 0 && buf_.charAt(0) == ' ')
        buf_.deleteCharAt(0); 
    while (buf_.length() > 0 && buf_.charAt(buf_.length()-1) == ' ')
        buf_.deleteCharAt(buf_.length()-1);
  }
  private static int _n_ = 0;
  private static StringBuilder buf_ = new StringBuilder(INITIAL_BUFFER_SIZE);
}


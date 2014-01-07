// This JLex file was machine-generated by the BNF converter
package com.biosimilarity.seleKt.model.ill.lang.illtl;
import java_cup.runtime.*;


public class Yylex implements java_cup.runtime.Scanner {
	private final int YY_BUFFER_SIZE = 512;
	private final int YY_F = -1;
	private final int YY_NO_STATE = -1;
	private final int YY_NOT_ACCEPT = 0;
	private final int YY_START = 1;
	private final int YY_END = 2;
	private final int YY_NO_ANCHOR = 4;
	private final int YY_BOL = 65536;
	private final int YY_EOF = 65537;

  String pstring = new String();
  public int line_num() { return (yyline+1); }
  public String buff() { return new String(yy_buffer,yy_buffer_index,10).trim(); }
	private java.io.BufferedReader yy_reader;
	private int yy_buffer_index;
	private int yy_buffer_read;
	private int yy_buffer_start;
	private int yy_buffer_end;
	private char yy_buffer[];
	private int yyline;
	private boolean yy_at_bol;
	private int yy_lexical_state;

	public Yylex (java.io.Reader reader) {
		this ();
		if (null == reader) {
			throw (new Error("Error: Bad input stream initializer."));
		}
		yy_reader = new java.io.BufferedReader(reader);
	}

	public Yylex (java.io.InputStream instream) {
		this ();
		if (null == instream) {
			throw (new Error("Error: Bad input stream initializer."));
		}
		yy_reader = new java.io.BufferedReader(new java.io.InputStreamReader(instream));
	}

	private Yylex () {
		yy_buffer = new char[YY_BUFFER_SIZE];
		yy_buffer_read = 0;
		yy_buffer_index = 0;
		yy_buffer_start = 0;
		yy_buffer_end = 0;
		yyline = 0;
		yy_at_bol = true;
		yy_lexical_state = YYINITIAL;
	}

	private boolean yy_eof_done = false;
	private final int STRING = 5;
	private final int ESCAPED = 6;
	private final int YYINITIAL = 0;
	private final int COMMENT = 1;
	private final int CHAREND = 4;
	private final int CHARESC = 3;
	private final int CHAR = 2;
	private final int yy_state_dtrans[] = {
		0,
		57,
		58,
		58,
		58,
		59,
		60
	};
	private void yybegin (int state) {
		yy_lexical_state = state;
	}
	private int yy_advance ()
		throws java.io.IOException {
		int next_read;
		int i;
		int j;

		if (yy_buffer_index < yy_buffer_read) {
			return yy_buffer[yy_buffer_index++];
		}

		if (0 != yy_buffer_start) {
			i = yy_buffer_start;
			j = 0;
			while (i < yy_buffer_read) {
				yy_buffer[j] = yy_buffer[i];
				++i;
				++j;
			}
			yy_buffer_end = yy_buffer_end - yy_buffer_start;
			yy_buffer_start = 0;
			yy_buffer_read = j;
			yy_buffer_index = j;
			next_read = yy_reader.read(yy_buffer,
					yy_buffer_read,
					yy_buffer.length - yy_buffer_read);
			if (-1 == next_read) {
				return YY_EOF;
			}
			yy_buffer_read = yy_buffer_read + next_read;
		}

		while (yy_buffer_index >= yy_buffer_read) {
			if (yy_buffer_index >= yy_buffer.length) {
				yy_buffer = yy_double(yy_buffer);
			}
			next_read = yy_reader.read(yy_buffer,
					yy_buffer_read,
					yy_buffer.length - yy_buffer_read);
			if (-1 == next_read) {
				return YY_EOF;
			}
			yy_buffer_read = yy_buffer_read + next_read;
		}
		return yy_buffer[yy_buffer_index++];
	}
	private void yy_move_end () {
		if (yy_buffer_end > yy_buffer_start &&
		    '\n' == yy_buffer[yy_buffer_end-1])
			yy_buffer_end--;
		if (yy_buffer_end > yy_buffer_start &&
		    '\r' == yy_buffer[yy_buffer_end-1])
			yy_buffer_end--;
	}
	private boolean yy_last_was_cr=false;
	private void yy_mark_start () {
		int i;
		for (i = yy_buffer_start; i < yy_buffer_index; ++i) {
			if ('\n' == yy_buffer[i] && !yy_last_was_cr) {
				++yyline;
			}
			if ('\r' == yy_buffer[i]) {
				++yyline;
				yy_last_was_cr=true;
			} else yy_last_was_cr=false;
		}
		yy_buffer_start = yy_buffer_index;
	}
	private void yy_mark_end () {
		yy_buffer_end = yy_buffer_index;
	}
	private void yy_to_mark () {
		yy_buffer_index = yy_buffer_end;
		yy_at_bol = (yy_buffer_end > yy_buffer_start) &&
		            ('\r' == yy_buffer[yy_buffer_end-1] ||
		             '\n' == yy_buffer[yy_buffer_end-1] ||
		             2028/*LS*/ == yy_buffer[yy_buffer_end-1] ||
		             2029/*PS*/ == yy_buffer[yy_buffer_end-1]);
	}
	private java.lang.String yytext () {
		return (new java.lang.String(yy_buffer,
			yy_buffer_start,
			yy_buffer_end - yy_buffer_start));
	}
	private int yylength () {
		return yy_buffer_end - yy_buffer_start;
	}
	private char[] yy_double (char buf[]) {
		int i;
		char newbuf[];
		newbuf = new char[2*buf.length];
		for (i = 0; i < buf.length; ++i) {
			newbuf[i] = buf[i];
		}
		return newbuf;
	}
	private final int YY_E_INTERNAL = 0;
	private final int YY_E_MATCH = 1;
	private java.lang.String yy_error_string[] = {
		"Error: Internal error.\n",
		"Error: Unmatched input.\n"
	};
	private void yy_error (int code,boolean fatal) {
		java.lang.System.out.print(yy_error_string[code]);
		java.lang.System.out.flush();
		if (fatal) {
			throw new Error("Fatal Error.\n");
		}
	}
	private int[][] unpackFromString(int size1, int size2, String st) {
		int colonIndex = -1;
		String lengthString;
		int sequenceLength = 0;
		int sequenceInteger = 0;

		int commaIndex;
		String workString;

		int res[][] = new int[size1][size2];
		for (int i= 0; i < size1; i++) {
			for (int j= 0; j < size2; j++) {
				if (sequenceLength != 0) {
					res[i][j] = sequenceInteger;
					sequenceLength--;
					continue;
				}
				commaIndex = st.indexOf(',');
				workString = (commaIndex==-1) ? st :
					st.substring(0, commaIndex);
				st = st.substring(commaIndex+1);
				colonIndex = workString.indexOf(':');
				if (colonIndex == -1) {
					res[i][j]=Integer.parseInt(workString);
					continue;
				}
				lengthString =
					workString.substring(colonIndex+1);
				sequenceLength=Integer.parseInt(lengthString);
				workString=workString.substring(0,colonIndex);
				sequenceInteger=Integer.parseInt(workString);
				res[i][j] = sequenceInteger;
				sequenceLength--;
			}
		}
		return res;
	}
	private int yy_acpt[] = {
		/* 0 */ YY_NOT_ACCEPT,
		/* 1 */ YY_NO_ANCHOR,
		/* 2 */ YY_NO_ANCHOR,
		/* 3 */ YY_NO_ANCHOR,
		/* 4 */ YY_NO_ANCHOR,
		/* 5 */ YY_NO_ANCHOR,
		/* 6 */ YY_NO_ANCHOR,
		/* 7 */ YY_NO_ANCHOR,
		/* 8 */ YY_NO_ANCHOR,
		/* 9 */ YY_NO_ANCHOR,
		/* 10 */ YY_NO_ANCHOR,
		/* 11 */ YY_NO_ANCHOR,
		/* 12 */ YY_NO_ANCHOR,
		/* 13 */ YY_NO_ANCHOR,
		/* 14 */ YY_NO_ANCHOR,
		/* 15 */ YY_NO_ANCHOR,
		/* 16 */ YY_NO_ANCHOR,
		/* 17 */ YY_NO_ANCHOR,
		/* 18 */ YY_NO_ANCHOR,
		/* 19 */ YY_NO_ANCHOR,
		/* 20 */ YY_NO_ANCHOR,
		/* 21 */ YY_NO_ANCHOR,
		/* 22 */ YY_NO_ANCHOR,
		/* 23 */ YY_NO_ANCHOR,
		/* 24 */ YY_NO_ANCHOR,
		/* 25 */ YY_NO_ANCHOR,
		/* 26 */ YY_NO_ANCHOR,
		/* 27 */ YY_NO_ANCHOR,
		/* 28 */ YY_NO_ANCHOR,
		/* 29 */ YY_NO_ANCHOR,
		/* 30 */ YY_NO_ANCHOR,
		/* 31 */ YY_NO_ANCHOR,
		/* 32 */ YY_NO_ANCHOR,
		/* 33 */ YY_NO_ANCHOR,
		/* 34 */ YY_NO_ANCHOR,
		/* 35 */ YY_NO_ANCHOR,
		/* 36 */ YY_NO_ANCHOR,
		/* 37 */ YY_NO_ANCHOR,
		/* 38 */ YY_NO_ANCHOR,
		/* 39 */ YY_NO_ANCHOR,
		/* 40 */ YY_NO_ANCHOR,
		/* 41 */ YY_NO_ANCHOR,
		/* 42 */ YY_NOT_ACCEPT,
		/* 43 */ YY_NO_ANCHOR,
		/* 44 */ YY_NO_ANCHOR,
		/* 45 */ YY_NO_ANCHOR,
		/* 46 */ YY_NOT_ACCEPT,
		/* 47 */ YY_NO_ANCHOR,
		/* 48 */ YY_NOT_ACCEPT,
		/* 49 */ YY_NO_ANCHOR,
		/* 50 */ YY_NOT_ACCEPT,
		/* 51 */ YY_NO_ANCHOR,
		/* 52 */ YY_NOT_ACCEPT,
		/* 53 */ YY_NO_ANCHOR,
		/* 54 */ YY_NOT_ACCEPT,
		/* 55 */ YY_NO_ANCHOR,
		/* 56 */ YY_NOT_ACCEPT,
		/* 57 */ YY_NOT_ACCEPT,
		/* 58 */ YY_NOT_ACCEPT,
		/* 59 */ YY_NOT_ACCEPT,
		/* 60 */ YY_NOT_ACCEPT,
		/* 61 */ YY_NO_ANCHOR,
		/* 62 */ YY_NO_ANCHOR,
		/* 63 */ YY_NO_ANCHOR,
		/* 64 */ YY_NO_ANCHOR,
		/* 65 */ YY_NO_ANCHOR,
		/* 66 */ YY_NO_ANCHOR
	};
	private int yy_cmap[] = unpackFromString(1,65538,
"31:9,39,30,31,39,29,31:18,39,9,35,31:4,38,2,4,13,31,6,37,8,28,34:10,31,1,5," +
"10,7,31,11,32:26,31,36,31:2,12,31,17,14,16,24,15,27,33:2,19,33:2,21,23,20,2" +
"6,33:2,22,18,25,33:3,3,33:2,31:69,32:23,31,32:7,33:24,31,33:8,31:65280,0:2")[0];

	private int yy_rmap[] = unpackFromString(1,67,
"0,1:2,2,3,1:10,4,5,1:2,3,6,3,1:2,3:3,1,7,3:2,1:11,8,9,10,11,12,13,14,15,16," +
"17,18,19,20,21,10,22,23,24,25,26,27,28,29,30,31")[0];

	private int yy_nxt[][] = unpackFromString(32,40,
"1,2,3,4,5,6,7,8,9,10,42,11,12,13,43,4,64,4:2,47,4,61,4:4,49,4,46,14:2,-1,15" +
",4,16,17,-1:3,14,-1:43,48,-1:39,4,-1:8,4,-1,4:14,-1:4,4:3,-1:3,4,-1:4,15,-1" +
":8,15,-1,15:14,-1:4,15:3,-1:3,4,-1:9,52,-1:25,16,-1:8,4,-1:8,4,-1,4:7,24,25" +
",4:5,-1:4,4:3,-1:3,4,-1:16,54,-1:18,28,-1:12,18,-1:35,4,-1:8,4,-1,4,19,4:12" +
",-1:4,4:3,-1:3,4,-1:35,44,-1:33,33,-1:24,22,-1:14,50,-1:14,4,-1:8,4,-1,4:6," +
"20,4:7,-1:4,4:3,-1:3,4,-1:5,23,-1:38,4,-1:8,4,-1,4:13,21,-1:4,4:3,-1:3,4,-1" +
":2,50:29,27,50:9,-1:3,4,-1:8,4,-1,4:11,26,4:2,-1:4,4:3,-1:3,4,-1:35,28,-1:8" +
",4,-1:8,4,-1,4,29,4:12,-1:4,4:3,-1:3,4,-1:35,44,-1:2,56,-1:5,4,-1:8,4,-1,4:" +
"3,30,4:10,-1:4,4:3,-1:3,4,-1,1,31:12,45,31:15,-1,32,31:9,1,-1:39,1,34:28,-1" +
":2,34:4,35,36,34:3,1,37:19,38,37:4,39,37:3,-1:2,37:4,40,41,37:3,-1:3,4,-1:8" +
",4,-1,4,51,4,66,4:10,-1:4,4:3,-1:3,4,-1:4,4,-1:8,4,-1,4:4,53,4:9,-1:4,4:3,-" +
"1:3,4,-1:4,4,-1:8,4,-1,4:10,55,4:3,-1:4,4:3,-1:3,4,-1:4,4,-1:8,4,-1,4:3,62," +
"4:10,-1:4,4:3,-1:3,4,-1:4,4,-1:8,4,-1,63,4:13,-1:4,4:3,-1:3,4,-1:4,4,-1:8,4" +
",-1,4:9,65,4:4,-1:4,4:3,-1:3,4,-1");

	public java_cup.runtime.Symbol next_token ()
		throws java.io.IOException {
		int yy_lookahead;
		int yy_anchor = YY_NO_ANCHOR;
		int yy_state = yy_state_dtrans[yy_lexical_state];
		int yy_next_state = YY_NO_STATE;
		int yy_last_accept_state = YY_NO_STATE;
		boolean yy_initial = true;
		int yy_this_accept;

		yy_mark_start();
		yy_this_accept = yy_acpt[yy_state];
		if (YY_NOT_ACCEPT != yy_this_accept) {
			yy_last_accept_state = yy_state;
			yy_mark_end();
		}
		while (true) {
			if (yy_initial && yy_at_bol) yy_lookahead = YY_BOL;
			else yy_lookahead = yy_advance();
			yy_next_state = YY_F;
			yy_next_state = yy_nxt[yy_rmap[yy_state]][yy_cmap[yy_lookahead]];
			if (YY_EOF == yy_lookahead && true == yy_initial) {
				return null;
			}
			if (YY_F != yy_next_state) {
				yy_state = yy_next_state;
				yy_initial = false;
				yy_this_accept = yy_acpt[yy_state];
				if (YY_NOT_ACCEPT != yy_this_accept) {
					yy_last_accept_state = yy_state;
					yy_mark_end();
				}
			}
			else {
				if (YY_NO_STATE == yy_last_accept_state) {
					throw (new Error("Lexical Error: Unmatched Input."));
				}
				else {
					yy_anchor = yy_acpt[yy_last_accept_state];
					if (0 != (YY_END & yy_anchor)) {
						yy_move_end();
					}
					yy_to_mark();
					switch (yy_last_accept_state) {
					case 1:
						
					case -2:
						break;
					case 2:
						{ return new Symbol(sym._SYMB_0); }
					case -3:
						break;
					case 3:
						{ return new Symbol(sym._SYMB_6); }
					case -4:
						break;
					case 4:
						{ return new Symbol(sym._IDENT_, yytext().intern()); }
					case -5:
						break;
					case 5:
						{ return new Symbol(sym._SYMB_7); }
					case -6:
						break;
					case 6:
						{ return new Symbol(sym._SYMB_2); }
					case -7:
						break;
					case 7:
						{ return new Symbol(sym._SYMB_3); }
					case -8:
						break;
					case 8:
						{ return new Symbol(sym._SYMB_4); }
					case -9:
						break;
					case 9:
						{ return new Symbol(sym._SYMB_5); }
					case -10:
						break;
					case 10:
						{ return new Symbol(sym._SYMB_8); }
					case -11:
						break;
					case 11:
						{ return new Symbol(sym._SYMB_10); }
					case -12:
						break;
					case 12:
						{ return new Symbol(sym._SYMB_11); }
					case -13:
						break;
					case 13:
						{ return new Symbol(sym._SYMB_12); }
					case -14:
						break;
					case 14:
						{ /* ignore white space. */ }
					case -15:
						break;
					case 15:
						{ return new Symbol(sym.UIdent, yytext().intern()); }
					case -16:
						break;
					case 16:
						{ return new Symbol(sym._INTEGER_, new Integer(yytext())); }
					case -17:
						break;
					case 17:
						{ yybegin(STRING); }
					case -18:
						break;
					case 18:
						{ return new Symbol(sym._SYMB_9); }
					case -19:
						break;
					case 19:
						{ return new Symbol(sym._SYMB_13); }
					case -20:
						break;
					case 20:
						{ return new Symbol(sym._SYMB_15); }
					case -21:
						break;
					case 21:
						{ return new Symbol(sym._SYMB_20); }
					case -22:
						break;
					case 22:
						{ yybegin(COMMENT); }
					case -23:
						break;
					case 23:
						{ return new Symbol(sym._SYMB_1); }
					case -24:
						break;
					case 24:
						{ return new Symbol(sym._SYMB_16); }
					case -25:
						break;
					case 25:
						{ return new Symbol(sym._SYMB_17); }
					case -26:
						break;
					case 26:
						{ return new Symbol(sym._SYMB_19); }
					case -27:
						break;
					case 27:
						{ /* BNFC single-line comment */ }
					case -28:
						break;
					case 28:
						{ return new Symbol(sym._DOUBLE_, new Double(yytext())); }
					case -29:
						break;
					case 29:
						{ return new Symbol(sym._SYMB_14); }
					case -30:
						break;
					case 30:
						{ return new Symbol(sym._SYMB_18); }
					case -31:
						break;
					case 31:
						{ }
					case -32:
						break;
					case 32:
						{ }
					case -33:
						break;
					case 33:
						{ yybegin(YYINITIAL); }
					case -34:
						break;
					case 34:
						{ pstring += yytext(); }
					case -35:
						break;
					case 35:
						{ String foo = pstring; pstring = new String(); yybegin(YYINITIAL); return new Symbol(sym._STRING_, foo.intern()); }
					case -36:
						break;
					case 36:
						{ yybegin(ESCAPED); }
					case -37:
						break;
					case 37:
						{ pstring += yytext(); yybegin(STRING); }
					case -38:
						break;
					case 38:
						{ pstring +=  "\n"; yybegin(STRING); }
					case -39:
						break;
					case 39:
						{ pstring += "\t"; yybegin(STRING); }
					case -40:
						break;
					case 40:
						{ pstring += "\""; yybegin(STRING); }
					case -41:
						break;
					case 41:
						{ pstring += "\\"; yybegin(STRING); }
					case -42:
						break;
					case 43:
						{ return new Symbol(sym._IDENT_, yytext().intern()); }
					case -43:
						break;
					case 44:
						{ return new Symbol(sym._DOUBLE_, new Double(yytext())); }
					case -44:
						break;
					case 45:
						{ }
					case -45:
						break;
					case 47:
						{ return new Symbol(sym._IDENT_, yytext().intern()); }
					case -46:
						break;
					case 49:
						{ return new Symbol(sym._IDENT_, yytext().intern()); }
					case -47:
						break;
					case 51:
						{ return new Symbol(sym._IDENT_, yytext().intern()); }
					case -48:
						break;
					case 53:
						{ return new Symbol(sym._IDENT_, yytext().intern()); }
					case -49:
						break;
					case 55:
						{ return new Symbol(sym._IDENT_, yytext().intern()); }
					case -50:
						break;
					case 61:
						{ return new Symbol(sym._IDENT_, yytext().intern()); }
					case -51:
						break;
					case 62:
						{ return new Symbol(sym._IDENT_, yytext().intern()); }
					case -52:
						break;
					case 63:
						{ return new Symbol(sym._IDENT_, yytext().intern()); }
					case -53:
						break;
					case 64:
						{ return new Symbol(sym._IDENT_, yytext().intern()); }
					case -54:
						break;
					case 65:
						{ return new Symbol(sym._IDENT_, yytext().intern()); }
					case -55:
						break;
					case 66:
						{ return new Symbol(sym._IDENT_, yytext().intern()); }
					case -56:
						break;
					default:
						yy_error(YY_E_INTERNAL,false);
					case -1:
					}
					yy_initial = true;
					yy_state = yy_state_dtrans[yy_lexical_state];
					yy_next_state = YY_NO_STATE;
					yy_last_accept_state = YY_NO_STATE;
					yy_mark_start();
					yy_this_accept = yy_acpt[yy_state];
					if (YY_NOT_ACCEPT != yy_this_accept) {
						yy_last_accept_state = yy_state;
						yy_mark_end();
					}
				}
			}
		}
	}
}

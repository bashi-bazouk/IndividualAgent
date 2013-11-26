package m3.jq;

@:native("window.jQuery.mobile")
extern class JQMobile extends JQ {

	@:overload(function<T>(cmd : String):T{})
	@:overload(function(cmd:String, opt:Dynamic):Dynamic{})
	@:overload(function(cmd:String, opt:String, newVal:Dynamic):JQMobile{})
	function controlgroup(  ) : JQMobile;

	@:overload(function<T>(cmd : String):T{})
	@:overload(function(cmd:String, opt:Dynamic):Dynamic{})
	@:overload(function(cmd:String, opt:String, newVal:Dynamic):JQMobile{})
	function checkboxradio(  ) : JQMobile;

	@:overload(function<T>(cmd : String):T{})
	@:overload(function(cmd:String, opt:Dynamic):Dynamic{})
	@:overload(function(cmd:String, opt:String, newVal:Dynamic):JQMobile{})
	function textinput(  ) : JQMobile;

	@:overload(function<T>(cmd : String):T{})
	@:overload(function(cmd:String, opt:Dynamic):Dynamic{})
	@:overload(function(cmd:String, opt:String, newVal:Dynamic):JQMobile{})
	function table(  ) : JQMobile;

	@:overload(function<T>(cmd : String):T{})
	@:overload(function(cmd:String, opt:Dynamic):Dynamic{})
	@:overload(function(cmd:String, opt:String, newVal:Dynamic):JQMobile{})
	function selectmenu(  ) : JQMobile;

	@:overload(function<T>(cmd : String):T{})
	@:overload(function(cmd:String, opt:Dynamic):Dynamic{})
	@:overload(function(cmd:String, opt:String, newVal:Dynamic):JQMobile{})
	function collapsibleset(  ) : JQMobile;

	@:overload(function<T>(cmd : String):T{})
	@:overload(function(cmd:String, opt:Dynamic):Dynamic{})
	@:overload(function(cmd:String, opt:String, newVal:Dynamic):JQMobile{})
	function popup(  ) : JQMobile;

	// @:overload(function<T>(cmd : String):T{})
	// @:overload(function(cmd:String, opt:String):Dynamic{})
	// @:overload(function(cmd:String, opt:String, newVal:Dynamic):JQMobile{})
	// function slider(  ) : JQMobile;

	public static function changePage(page: String): Void {
		untyped __js__("jQuery.mobile.changePage(page)");
	}

	public static inline function hideLoader(): Void {
		untyped __js__("$.mobile.loading( 'hide' )");
	}

	public static inline function showLoader(): Void {
		untyped __js__("$.mobile.loading( 'show' )");
	}

	// private static function __init__() : Void untyped {
	// 	JQMobile = window.jQuery.mobile;
	// }

}
package m3.jq;

import m3.jq.JQ;
import m3.widget.Widgets;

typedef UIJQDialog = {
	helper: JQ, 
	position: UIPosition, 
	offset: UIPosition
}

typedef JQDialogOptions = {
	@:optional var autoOpen:Bool;
	@:optional var height:Dynamic;
	@:optional var width: Dynamic;
	@:optional var modal: Bool;
	@:optional var title: String;
	@:optional var buttons: Dynamic;
	@:optional var beforeClose: JQEvent->UIJQDialog->Dynamic;
	@:optional var close: JQEvent->UIJQDialog->Void;
	@:optional var position: Array<Float>;
}

@:native("$")
extern class JQDialog extends JQ {
	
	@:overload(function<T>(cmd : String):T{})
	@:overload(function<T>(cmd:String, opt:String):T{})
	function dialog(opts: JQDialogOptions): JQDialog;

	static var cur(get, null): JQDialog;
	private static inline function get_cur() : JQDialog {
		return untyped __js__("$(this)");
	}
}
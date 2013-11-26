package m3.util;

import m3.CrossMojo;
import m3.jq.JQ;
import m3.jq.M3Dialog;

using m3.helper.StringHelper;

@:expose
class JqueryUtil {
	public static function isAttached(elem: JQ): Bool {
		return elem.parents("body").length > 0;
		// return JQ.contains(js.Lib.document.body, (Reflect.hasField(elem, "jquery") ? elem[0] : cast elem) );
	}

	public static function labelSelect(elem: JQ, str: String): Void {
		try {
			untyped CrossMojo.jq('option', elem).filter(function() {
		       return JQ.cur.text() == str;
		    })[0].selected = true;
		} catch (err: Dynamic) {
			//TODO generic log access
			// App.LOGGER.error("Attempted to select " + str + " but it was not a valid option");
		}
	}

	public static function getOrCreateDialog(selector:String, ?dlgOptions: M3DialogOptions, ?createdFcn: JQ->Void): M3Dialog {
		if(selector.isBlank()) {
	        selector = "dlg" + UidGenerator.create(10);
	    }
	    var dialog: M3Dialog = new M3Dialog(selector);
	    if(dlgOptions == null) {
	        dlgOptions = {
	            autoOpen: false,
	            height: 380,
	            width: 320,
	            modal: true
	        };
	    }
	    if(!dialog.exists()) {
	        dialog = new M3Dialog("<div id=" + selector.substr(1) + " style='display:none;'></div>");
	        if(Reflect.isFunction(createdFcn)) {
	        	createdFcn(dialog);
	        }
	        new JQ('body').append(dialog);
	        dialog.m3dialog(dlgOptions);
	    } else if(!dialog.is(':data(dialog)') ) {
	        dialog.m3dialog(dlgOptions);
	    }
	    return dialog;
	}

	public static function getWindowWidth(): Int {
		return new JQ(js.Browser.window).width();
	}

	public static function getWindowHeight(): Int {
		return new JQ(js.Browser.window).height();
	}

	public static function getDocumentWidth(): Int {
		return new JQ(js.Browser.document).width();
	}

	public static function getDocumentHeight(): Int {
		return new JQ(js.Browser.document).height();
	}

	public static function getEmptyDiv(): JQ {
		return new JQ("<div></div>");
	}

	public static function getEmptyTable():JQ {
		return new JQ("<table style='margin:auto; text-align: center; width: 100%;'></table>");
	}

	public static function getEmptyRow():JQ {
		return new JQ("<tr></tr>");
	}
	
	public static function getEmptyCell():JQ {
		return new JQ("<td></td>");
	}


}
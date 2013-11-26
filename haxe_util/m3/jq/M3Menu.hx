package m3.jq;

import m3.jq.JQ;
import m3.widget.Widgets;

using m3.helper.StringHelper;
using m3.jq.JQMenu;

typedef MenuOption = {
	var label: String;
	@:optional var icon: String;
	var action: JQEvent->M3Menu->Void;
}

typedef M3MenuOptions = {
	var menuOptions: Array<MenuOption>;	
	@:optional var width: Int;
	@:optional var close: Void->Void;
}

typedef M3MenuWidgetDef = {
	var options: M3MenuOptions;
	var _create: Void->Void;
	var destroy: Void->Void;
}

@:native("$")
extern class M3Menu extends JQ {
	
	@:overload(function<T>(cmd : String):T{})
	@:overload(function<T>(cmd:String, opt:String, ?newVal: Dynamic):T{})
	function m3menu(opts: M3MenuOptions): M3Menu;

	static var cur(get, null): M3Menu;
	private static inline function get_cur() : M3Menu {
		return untyped __js__("$(this)");
	}

	private static function __init__(): Void {
		var defineWidget: Void->M3MenuWidgetDef = function(): M3MenuWidgetDef {
			return {
		        options: {
		        	menuOptions: null
			        , width: 200
		        },

		        _create: function(): Void {
		        	var self: M3MenuWidgetDef = Widgets.getSelf();
					var selfElement: M3Menu = Widgets.getSelfElement();

					selfElement.addClass("m3menu nonmodalPopup");
					selfElement.width(self.options.width);

					for(menuOption in self.options.menuOptions) {
						var icon: String = {
							if(menuOption.icon.isNotBlank()) "<span class='ui-icon " + menuOption.icon + "'></span>"; 
							else "";
						};
						new JQ("<li><a href='#'>" + icon + menuOption.label + "</a></li>")
							.appendTo(selfElement)
							.click(function(evt: JQEvent): Void {
									menuOption.action(evt, selfElement);
								});
					}

		        	cast (JQ.curNoWrap)._super('create');

		        	// selfElement.hide();
		        },

		        destroy: function() {
		            untyped JQ.Widget.prototype.destroy.call( JQ.curNoWrap );
		        }
		    };
		}
		JQ.widget( "ui.m3menu", JQ.ui.menu, defineWidget());
	}
}
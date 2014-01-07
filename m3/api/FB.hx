package m3.api;

@:native("FB")
extern class FB {
	//CORE METHODS
	@:overload(function(path: String, method: String, cb: FBResponse->Void):Void{})
	@:overload(function(path: String, method: String, params: Dynamic, cb: FBResponse->Void):Void{})
	public static function api(path: String, cb: FBResponse->Void): Void;
	public static function init(opts: FBInitOptions): Void;
	public static function ui(params: Dynamic, cb: Void->Void): Void;

	//AUTH METHODS
	public static function getAuthResponse(): Void;
	public static function getLoginStatus(cb: FBResponse->Void, force: Bool): Void;
	public static function login(cb: FBResponse->Void, opts: {scope: String}): Void;
	public static function logout(): Void;
}

@:native("FB.Event")
extern class FBEvent {
	public static function subscribe(name: String, cb: Dynamic->Void): Void;
	public static function unsubscribe(name: String, cb: Dynamic->Void): Void;
}

@:native("FB.XFBML")
extern class XFBML {
	public static function parse(): Void;
}

typedef FBInitOptions = {
	@:optional var appId: String;//null
	@:optional var cookie: Bool;//false -> true to enable cookie support
	@:optional var logging: Bool;//true -> false to disable logging
	@:optional var status: Bool;//true -> true to fetch status
	@:optional var xfbml: Bool;//false -> true to parse xfbml tags
	@:optional var channelUrl: String;//null -> Specifies the URL of a custom URL channel file
	@:optional var authResponse: Dynamic;//true -> Manually set the object retrievable from getAuthResponse
	@:optional var frictionlessRequests: Bool;//false
	@:optional var hideFlashCallback: Dynamic;//null
	@:optional var useCachedDialogs: Bool;
	@:optional var nativeInterface: Dynamic;
}
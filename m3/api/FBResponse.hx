package m3.api;

class FBResponse {
	public var id: String;
}

class FBLoginResponse {
	public var status: String;
	public var authResponse: {
		accessToken: String, 
		expiresIn: Int,
		signedRequest: String,
		userId: String
	};
}

class FBFriendsResponse extends FBResponse {
	public var data: Array<FBProfile>;
	public var paging: FBPaging;
}

class FBProfile extends FBResponse {
	public var name: String;
	public var first_name: String;
	public var last_name: String;
	public var gender: String;
	public var age_range: Dynamic;
	public var picture: FBPicture;
	public var birthday: String;
	public var location: {id: String, name: String};
}

class FBPicture {
	public var data: FBPictureData;
}

class FBPictureData {
	public var url: String;
	public var is_silhouette: Bool;
	@:optional public var height: Int;
	@:optional public var width: Int;
}

class FBPaging {
	public var previous: String;
	public var next: String;
	public var cursors: FBCursors;
}

class FBCursors {
	public var after: String;
	public var before: String;
}
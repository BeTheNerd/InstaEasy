package com.example.helloworld;

import com.blinxbox.restinstagram.Instagram;

public class ExtendedUser {
	@Instagram
	public String id;
	@Instagram
	public String username;
	@Instagram
	public String full_name;
	@Instagram
	public String profile_picture;
	@Instagram
	public String bio;
	@Instagram
	public String website;
	
	public static class Counts {
		@Instagram
		public int media;
		@Instagram
		public int follows;
		@Instagram
		public int followed_by;
    };
    
    @Instagram
	public Counts counts;
}

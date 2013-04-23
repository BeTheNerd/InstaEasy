package com.example.helloworld;

import java.util.List;

import com.blinxbox.restinstagram.types.MediaPost;

public interface MediaListener {
	void OnMedia(List<MediaPost> medias);
}

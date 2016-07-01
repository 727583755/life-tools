package com.lifetools.commons.utils;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.regex.Matcher;

import com.mpatric.mp3agic.ID3v1;
import com.mpatric.mp3agic.ID3v2;
import com.mpatric.mp3agic.Mp3File;

public class MP3Utils {

	public static MP3Info getMP3INfo(String mp3FilePath) {
		try {
			if (StringUtils.isBlankOrEmpty(mp3FilePath)) {
				return null;
			}
			File mp3File = new File(mp3FilePath);
			return getMP3INfo(mp3File);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static MP3Info getMP3INfo(File mp3File) {
		try {
			if (mp3File == null) {
				return null;
			}
			MP3Info info = new MP3Info();
			Mp3File mp3file = new Mp3File(mp3File);
			info.setDuration(mp3file.getLengthInMilliseconds());
			info.setSize(mp3file.getLength());
			info.setBitrate(mp3file.getBitrate());
			if (mp3file.hasId3v2Tag()) {
				ID3v2 id3v2Tag = mp3file.getId3v2Tag();
				info.setAlbum(processString(id3v2Tag.getAlbum()));
				info.setArtist(processString(id3v2Tag.getArtist()));
				info.setTitle(processString(id3v2Tag.getTitle()));
				info.setYear(processString(id3v2Tag.getYear()));
				processString(processString(id3v2Tag.getTitle()));
			} else if (mp3file.hasId3v1Tag()) {
				ID3v1 id3v1Tag = mp3file.getId3v1Tag();
				info.setAlbum(processString(id3v1Tag.getAlbum()));
				info.setArtist(processString(id3v1Tag.getArtist()));
				info.setTitle(processString(id3v1Tag.getTitle()));
				info.setYear(processString(id3v1Tag.getYear()));
				processString(processString(id3v1Tag.getTitle()));
			}
			return info;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	
	public static String processString(String str) {
		if (StringUtils.isBlankOrEmpty(str)) {
			return str;
		}
		if (hasSpecialCharacter(str)) {
			String s = "";
			try {
				s = new String(str.getBytes("ISO-8859-1"),"GB2312");
			} catch (UnsupportedEncodingException e) {
				
			}
			if (!hasSpecialCharacter(s)) {
				str = s;
			}
		}
		return str;
	}

	public static boolean hasSpecialCharacter(String str) {
		if (StringUtils.isBlankOrEmpty(str)) {
			return false;
		}
		Matcher m = RegexUtils.doMatcher(str, "[^ 0-9a-zA-Z\\-\u4E00-\u9FA5\u0800-\u4e00\u3130-\u318F\uAC00-\uD7A3\\.\\(\\)（）：:＿,，\\[\\]【】\\p{Punct}]");// \u0800-\u4e00 日语   
		if (m.find()) {
			return true;
		}
		return false;
	}

}

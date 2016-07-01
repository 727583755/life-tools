package com.lifetools.commons.utils;

/**
 * mp3相关信息
 * 
 * @author zk
 * @date 2015年10月24日 下午2:47:12
 */
public class MP3Info {
	/** 时长（毫秒） */
	private Long duration;
	/** 歌名 */
	private String Title;
	/** 唱片集 */
	private String album;
	/** 歌曲年份 */
	private String year;
	/** 歌手 */
	private String artist;
	/** 比特率(kbps) */
	private Integer bitrate;
	/** 文件大小 (B)*/
	private Long size;

	public Long getDuration() {
		return duration;
	}

	public void setDuration(Long duration) {
		this.duration = duration;
	}

	public String getTitle() {
		return Title;
	}

	public void setTitle(String title) {
		Title = title;
	}

	public String getAlbum() {
		return album;
	}

	public void setAlbum(String album) {
		this.album = album;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getArtist() {
		return artist;
	}

	public void setArtist(String artist) {
		this.artist = artist;
	}

	public Long getSize() {
		return size;
	}

	public void setSize(Long size) {
		this.size = size;
	}

	public Integer getBitrate() {
		return bitrate;
	}

	public void setBitrate(Integer bitrate) {
		this.bitrate = bitrate;
	}
}

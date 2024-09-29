package swe2040ProjectGUI;

public class Music {
    private Long id;
    private String songTitle;
    private String lyrics;
    
    
    public Music() {
    	
    }
	public Music(Long id, String songTitle, String lyrics) {
		this.id = id;
		this.songTitle = songTitle;
		this.lyrics = lyrics;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getSongTitle() {
		return songTitle;
	}
	public void setSongTitle(String songTitle) {
		this.songTitle = songTitle;
	}
	public String getLyrics() {
		return lyrics;
	}
	public void setLyrics(String lyrics) {
		this.lyrics = lyrics;
	}


}

   
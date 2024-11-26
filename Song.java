package songpack;

public class Song implements Comparable<Song> {
    private String title;
    private String tag;
    private String artist;
    private int year;
    private int views;
    private String lyrics;
    
    public Song(String title, String tag, String artist, int year, int views, String lyrics)
    {
        this.setTitle(title);
        this.setTag(tag);
        this.setArtist(artist);
        this.setYear(year);
        this.setViews(views);
        this.setLyrics(lyrics);
    }

    /**
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title the title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return the tag
     */
    public String getTag() {
        return tag;
    }

    /**
     * @param tag the tag to set
     */
    public void setTag(String tag) {
        this.tag = tag;
    }

    /**
     * @return the artist
     */
    public String getArtist() {
        return artist;
    }

    /**
     * @param artist the artist to set
     */
    public void setArtist(String artist) {
        this.artist = artist;
    }

    /**
     * @return the year
     */
    public int getYear() {
        return year;
    }

    /**
     * @param year the year to set
     */
    public void setYear(int year) {
        this.year = year;
    }

    /**
     * @return the views
     */
    public int getViews() {
        return views;
    }

    /**
     * @param views the views to set
     */
    public void setViews(int views) {
        this.views = views;
    }

    /**
     * @return the lyrics
     */
    public String getLyrics() {
        return lyrics;
    }

    /**
     * @param lyrics the lyrics to set
     */
    public void setLyrics(String lyrics) {
        this.lyrics = lyrics;
    }

    @Override
    public int compareTo(Song otherSong) {
        // TODO Auto-generated method stub
        return Integer.compare(this.views, otherSong.getViews());
    }
}

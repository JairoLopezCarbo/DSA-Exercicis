package dsa.upc.edu.listapp.api;

public class Track {
    private String id;
    private String title;
    private String singer;

    public Track(String id, String title, String singer)
    {
        this.id = id;
        this.title = title;
        this.singer = singer;
    }

    public Track() {}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSinger() {
        return singer;
    }

    public void setSinger(String singer) {
        this.singer = singer;
    }
}
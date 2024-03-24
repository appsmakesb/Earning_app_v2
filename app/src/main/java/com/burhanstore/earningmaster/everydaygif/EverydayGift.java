package com.burhanstore.earningmaster.everydaygif;

public class EverydayGift {

    private String title , poster , overview;
    private Double rating;

    public EverydayGift(String title , String poster , String overview , Double rating){
        this.title = title;
        this.poster = poster;
        this.overview = overview;
        this.rating = rating;
    }

    public String getTitle() {
        return title;
    }

    public String getPoster() {
        return poster;
    }

    public String getOverview() {
        return overview;
    }

    public Double getRating() {
        return rating;
    }
}

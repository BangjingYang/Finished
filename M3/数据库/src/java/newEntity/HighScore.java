/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package newEntity;

import java.util.Date;

/**
 *
 * @author bangjingyang
 */
public class HighScore {
    private String movieName;
    private Double movieScore;
    private Date movieRelease;

    public HighScore() {
    }

    public HighScore(String movieName, Double movieScore, Date movieRelease) {
        this.movieName = movieName;
        this.movieScore = movieScore;
        this.movieRelease = movieRelease;
    }

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public Double getMovieScore() {
        return movieScore;
    }

    public void setMovieScore(Double movieScore) {
        this.movieScore = movieScore;
    }

    public Date getMovieRelease() {
        return movieRelease;
    }

    public void setMovieRelease(Date movieRelease) {
        this.movieRelease = movieRelease;
    }
    
    
}

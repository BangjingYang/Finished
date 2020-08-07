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
public class ReleaseEqualWatch {
    private String movieName;
    private Date movieRelease;

    public ReleaseEqualWatch() {
    }

    public ReleaseEqualWatch(String movieName, Date movieRelease) {
        this.movieName = movieName;
        this.movieRelease = movieRelease;
    }

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public Date getMovieRelease() {
        return movieRelease;
    }

    public void setMovieRelease(Date movieRelease) {
        this.movieRelease = movieRelease;
    }
    
    
    
}

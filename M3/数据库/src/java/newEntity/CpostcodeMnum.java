/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package newEntity;

/**
 *
 * @author bangjingyang
 */
public class CpostcodeMnum {
    private int cposcode;
    private int mnum;
    
    public CpostcodeMnum(int cposcode, long mnum) {
        this.cposcode = cposcode;
        this.mnum = (int)mnum;
    }

    public CpostcodeMnum() {
    }

    public int getCposcode() {
        return cposcode;
    }

    public void setCposcode(int cposcode) {
        this.cposcode = cposcode;
    }

    public int getMnum() {
        return mnum;
    }

    public void setMnum(int mnum) {
        this.mnum = mnum;
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author bangjingyang
 */
@Entity
@Table(name = "MEMOIR")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Memoir.findAll", query = "SELECT m FROM Memoir m")
    , @NamedQuery(name = "Memoir.findByMid", query = "SELECT m FROM Memoir m WHERE m.mid = :mid")
    , @NamedQuery(name = "Memoir.findByMrelease", query = "SELECT m FROM Memoir m WHERE m.mrelease = :mrelease")
    , @NamedQuery(name = "Memoir.findByMcomment", query = "SELECT m FROM Memoir m WHERE m.mcomment = :mcomment")
    , @NamedQuery(name = "Memoir.findByMscore", query = "SELECT m FROM Memoir m WHERE m.mscore = :mscore")
    , @NamedQuery(name = "Memoir.findByMwatchdt", query = "SELECT m FROM Memoir m WHERE m.mwatchdt = :mwatchdt")
    , @NamedQuery(name = "Memoir.findByMname", query = "SELECT m FROM Memoir m WHERE m.mname = :mname")
    , @NamedQuery(name = "Memoir.sortByMwatchdt", query = "SELECT m FROM Memoir m WHERE m.pid.pid = :pid order by m.mwatchdt desc")
    , @NamedQuery(name = "Memoir.sortByMscore", query = "SELECT m FROM Memoir m WHERE m.pid.pid = :pid order by m.mscore desc")
    , @NamedQuery(name = "Memoir.sortByMrelease", query = "SELECT m FROM Memoir m WHERE m.pid.pid = :pid order by m.mrelease desc")
    , @NamedQuery(name = "Memoir.findByMnameANDCpostcode", query = "SELECT m FROM Memoir m WHERE m.mname = :mname AND m.cid.cpostcode = :cpostcode")})
public class Memoir implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "MID")
    private Integer mid;
    @Column(name = "MRELEASE")
    @Temporal(TemporalType.DATE)
    private Date mrelease;
    @Column(name = "MADDDATE")
    @Temporal(TemporalType.DATE)
    private Date madddate;
    @Size(max = 300)
    @Column(name = "MCOMMENT")
    private String mcomment;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "MSCORE")
    private Double mscore;
    @Column(name = "MWATCHDT")
    @Temporal(TemporalType.TIMESTAMP)
    private Date mwatchdt;
    @Size(max = 50)
    @Column(name = "MNAME")
    private String mname;
    @JoinColumn(name = "CID", referencedColumnName = "CID")
    @ManyToOne
    private Cinema cid;
    @JoinColumn(name = "PID", referencedColumnName = "PID")
    @ManyToOne
    private Person pid;

    public Memoir() {
    }

    public Memoir(Integer mid) {
        this.mid = mid;
    }

    public Integer getMid() {
        return mid;
    }

    public void setMid(Integer mid) {
        this.mid = mid;
    }

    public Date getMrelease() {
        return mrelease;
    }

    public void setMrelease(Date mrelease) {
        this.mrelease = mrelease;
    }
    public Date getMadddate() {
        return madddate;
    }

    public void setMadddate(Date madddate) {
        this.madddate = madddate;
    }

    public String getMcomment() {
        return mcomment;
    }

    public void setMcomment(String mcomment) {
        this.mcomment = mcomment;
    }

    public Double getMscore() {
        return mscore;
    }

    public void setMscore(Double mscore) {
        this.mscore = mscore;
    }

    public Date getMwatchdt() {
        return mwatchdt;
    }

    public void setMwatchdt(Date mwatchdt) {
        this.mwatchdt = mwatchdt;
    }

    public String getMname() {
        return mname;
    }

    public void setMname(String mname) {
        this.mname = mname;
    }

    public Cinema getCid() {
        return cid;
    }

    public void setCid(Cinema cid) {
        this.cid = cid;
    }

    public Person getPid() {
        return pid;
    }

    public void setPid(Person pid) {
        this.pid = pid;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (mid != null ? mid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Memoir)) {
            return false;
        }
        Memoir other = (Memoir) object;
        if ((this.mid == null && other.mid != null) || (this.mid != null && !this.mid.equals(other.mid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "rest.Memoir[ mid=" + mid + " ]";
    }
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author bangjingyang
 */
@Entity
@Table(name = "CINEMA")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Cinema.findAll", query = "SELECT c FROM Cinema c")
    , @NamedQuery(name = "Cinema.findByCid", query = "SELECT c FROM Cinema c WHERE c.cid = :cid")
    , @NamedQuery(name = "Cinema.findByCname", query = "SELECT c FROM Cinema c WHERE c.cname = :cname")
    , @NamedQuery(name = "Cinema.findByCpostcode", query = "SELECT c FROM Cinema c WHERE c.cpostcode = :cpostcode")})
public class Cinema implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "CID")
    private Integer cid;
    @Size(max = 50)
    @Column(name = "CNAME")
    private String cname;
    @Size(max = 50)
    @Column(name = "CSURBURB")
    private String csurburb;
    @Column(name = "CPOSTCODE")
    private Integer cpostcode;
    @OneToMany(mappedBy = "cid")
    private Collection<Memoir> memoirCollection;

    public Cinema() {
    }

    public Cinema(Integer cid) {
        this.cid = cid;
    }

    public Integer getCid() {
        return cid;
    }

    public void setCid(Integer cid) {
        this.cid = cid;
    }

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }
    public String getCsurburb() {
        return csurburb;
    }

    public void setCsurburb(String csurburb) {
        this.csurburb = csurburb;
    }

    public Integer getCpostcode() {
        return cpostcode;
    }

    public void setCpostcode(Integer cpostcode) {
        this.cpostcode = cpostcode;
    }

    @XmlTransient
    public Collection<Memoir> getMemoirCollection() {
        return memoirCollection;
    }

    public void setMemoirCollection(Collection<Memoir> memoirCollection) {
        this.memoirCollection = memoirCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (cid != null ? cid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Cinema)) {
            return false;
        }
        Cinema other = (Cinema) object;
        if ((this.cid == null && other.cid != null) || (this.cid != null && !this.cid.equals(other.cid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "rest.Cinema[ cid=" + cid + " ]";
    }
    
}

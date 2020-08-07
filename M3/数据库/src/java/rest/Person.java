/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author bangjingyang
 */
@Entity
@Table(name = "PERSON")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Person.findAll", query = "SELECT p FROM Person p")
    , @NamedQuery(name = "Person.findByPid", query = "SELECT p FROM Person p WHERE p.pid = :pid")
    , @NamedQuery(name = "Person.findByPfname", query = "SELECT p FROM Person p WHERE p.pfname = :pfname")
    , @NamedQuery(name = "Person.findByPsname", query = "SELECT p FROM Person p WHERE p.psname = :psname")
    , @NamedQuery(name = "Person.findByPgender", query = "SELECT p FROM Person p WHERE p.pgender = :pgender")
    , @NamedQuery(name = "Person.findByPdob", query = "SELECT p FROM Person p WHERE p.pdob = :pdob")
    , @NamedQuery(name = "Person.findByPaddress", query = "SELECT p FROM Person p WHERE p.paddress = :paddress")
    , @NamedQuery(name = "Person.findByPstate", query = "SELECT p FROM Person p WHERE p.pstate = :pstate")
    , @NamedQuery(name = "Person.findByPpostcode", query = "SELECT p FROM Person p WHERE p.ppostcode = :ppostcode")})
public class Person implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "PID")
    private Integer pid;
    @Size(max = 15)
    @Column(name = "PFNAME")
    private String pfname;
    @Size(max = 15)
    @Column(name = "PSNAME")
    private String psname;
    @Size(max = 6)
    @Column(name = "PGENDER")
    private String pgender;
    @Column(name = "PDOB")
    @Temporal(TemporalType.DATE)
    private Date pdob;
    @Size(max = 75)
    @Column(name = "PADDRESS")
    private String paddress;
    @Size(max = 3)
    @Column(name = "PSTATE")
    private String pstate;
    @Column(name = "PPOSTCODE")
    private Integer ppostcode;
    @OneToMany(mappedBy = "pid")
    private Collection<Credentials> credentialsCollection;
    @OneToMany(mappedBy = "pid")
    private Collection<Memoir> memoirCollection;

    public Person() {
    }

    public Person(Integer pid) {
        this.pid = pid;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public String getPfname() {
        return pfname;
    }

    public void setPfname(String pfname) {
        this.pfname = pfname;
    }

    public String getPsname() {
        return psname;
    }

    public void setPsname(String psname) {
        this.psname = psname;
    }

    public String getPgender() {
        return pgender;
    }

    public void setPgender(String pgender) {
        this.pgender = pgender;
    }

    public Date getPdob() {
        return pdob;
    }

    public void setPdob(Date pdob) {
        this.pdob = pdob;
    }

    public String getPaddress() {
        return paddress;
    }

    public void setPaddress(String paddress) {
        this.paddress = paddress;
    }

    public String getPstate() {
        return pstate;
    }

    public void setPstate(String pstate) {
        this.pstate = pstate;
    }

    public Integer getPpostcode() {
        return ppostcode;
    }

    public void setPpostcode(Integer ppostcode) {
        this.ppostcode = ppostcode;
    }

    @XmlTransient
    public Collection<Credentials> getCredentialsCollection() {
        return credentialsCollection;
    }

    public void setCredentialsCollection(Collection<Credentials> credentialsCollection) {
        this.credentialsCollection = credentialsCollection;
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
        hash += (pid != null ? pid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Person)) {
            return false;
        }
        Person other = (Person) object;
        if ((this.pid == null && other.pid != null) || (this.pid != null && !this.pid.equals(other.pid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "rest.Person[ pid=" + pid + " ]";
    }
    
}

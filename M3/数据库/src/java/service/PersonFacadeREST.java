/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import java.sql.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import rest.Person;

/**
 *
 * @author bangjingyang
 */
@Stateless
@Path("rest.person")
public class PersonFacadeREST extends AbstractFacade<Person> {

    @PersistenceContext(unitName = "5046A1FinalPU")
    private EntityManager em;

    public PersonFacadeREST() {
        super(Person.class);
    }

    @POST
    @Override
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void create(Person entity) {
        super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void edit(@PathParam("id") Integer id, Person entity) {
        super.edit(entity);
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Integer id) {
        super.remove(super.find(id));
    }

    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Person find(@PathParam("id") Integer id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Person> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Person> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return super.findRange(new int[]{from, to});
    }

    @GET
    @Path("count")
    @Produces(MediaType.TEXT_PLAIN)
    public String countREST() {
        return String.valueOf(super.count());
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
    @GET
    @Path("findByPfname/{pfname}")
    @Produces({"application/json"})
    public List<Person> findByPfname(@PathParam("pfname") String pfname) {
        Query query = em.createNamedQuery("Person.findByPfname");
        query.setParameter("pfname", pfname);
        return query.getResultList();
    }

    @GET
    @Path("findByPsname/{psname}")
    @Produces({"application/json"})
    public List<Person> findByPsname(@PathParam("psname") String psname) {
        Query query = em.createNamedQuery("Person.findByPsname");
        query.setParameter("psname", psname);
        return query.getResultList();
    }

    @GET
    @Path("findByPgender/{pgender}")
    @Produces({"application/json"})
    public List<Person> findByPgender(@PathParam("pgender") String pgender) {
        Query query = em.createNamedQuery("Person.findByPgender");
        query.setParameter("pgender", pgender);
        return query.getResultList();
    }

    @GET
    @Path("findByPdob/{pdob}")
    @Produces({"application/json"})
    public List<Person> findByPdob(@PathParam("pdob") Date pdob) {
        Query query = em.createNamedQuery("Person.findByPdob");
        query.setParameter("pdob", pdob);
        return query.getResultList();
    }

    @GET
    @Path("findByPaddress/{paddress}")
    @Produces({"application/json"})
    public List<Person> findByPaddress(@PathParam("paddress") String paddress) {
        Query query = em.createNamedQuery("Person.findByPaddress");
        query.setParameter("paddress", paddress);
        return query.getResultList();
    }

    @GET
    @Path("findByPstate/{pstate}")
    @Produces({"application/json"})
    public List<Person> findByPstate(@PathParam("pstate") String pstate) {
        Query query = em.createNamedQuery("Person.findByPstate");
        query.setParameter("pstate", pstate);
        return query.getResultList();
    }

    @GET
    @Path("findByPpostcode/{ppostcode}")
    @Produces({"application/json"})
    public List<Person> findByPpostcode(@PathParam("ppostcode") Integer ppostcode) {
        Query query = em.createNamedQuery("Person.findByPpostcode");
        query.setParameter("ppostcode", ppostcode);
        return query.getResultList();
    }
    
    //Task3b
    @GET
    @Path("findByPwholeaddress/{paddress}/{pstate}/{ppostcode}")
    @Produces({"application/json"})
    public List<Person> findByPwholeaddress(@PathParam("paddress") String paddress,
            @PathParam("pstate") String pstate, @PathParam("ppostcode") Integer ppostcode) {
        TypedQuery<Person> query = em.createQuery("SELECT p FROM Person p WHERE p.paddress = :paddress "
                + "AND p.pstate = :pstate AND p.ppostcode = :ppostcode", Person.class);
        query.setParameter("paddress", paddress);
        query.setParameter("pstate", pstate);
        query.setParameter("ppostcode", ppostcode);
        return query.getResultList();
    }

}

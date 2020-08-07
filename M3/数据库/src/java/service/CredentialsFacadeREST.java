/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import java.sql.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import rest.Credentials;

/**
 *
 * @author bangjingyang
 */
@Stateless
@Path("rest.credentials")
public class CredentialsFacadeREST extends AbstractFacade<Credentials> {

    @PersistenceContext(unitName = "5046A1FinalPU")
    private EntityManager em;

    public CredentialsFacadeREST() {
        super(Credentials.class);
    }

    @POST
    @Override
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void create(Credentials entity) {
        super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void edit(@PathParam("id") String id, Credentials entity) {
        super.edit(entity);
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") String id) {
        super.remove(super.find(id));
    }

    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Credentials find(@PathParam("id") String id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Credentials> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Credentials> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
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
    @Path("findByUsername/{username}")
    @Produces({"application/json"})
    public List<Credentials> findByUsername(@PathParam("username") String username) {
        Query query = em.createNamedQuery("Credentials.findByUsername");
        query.setParameter("username", username);
        return query.getResultList();
    }
    @GET
    @Path("getAllUsername")
    @Produces({"application/json"})
    public JsonArray getAllUsername() {
        Query query = em.createNamedQuery("Credentials.getAllUsername");
        List<Credentials> q = query.getResultList();
        JsonArrayBuilder b = Json.createArrayBuilder();
        for(Credentials c : q)
        {
            b.add(Json.createObjectBuilder().add("username",c.getUsername()).build());
        }
        JsonArray j = b.build();
        return j;
        
    }

    @GET
    @Path("findByPasswd/{passwd}")
    @Produces({"application/json"})
    public List<Credentials> findByPasswd(@PathParam("passwd") String passwd) {
        Query query = em.createNamedQuery("Credentials.findByPasswd");
        query.setParameter("passwd", passwd);
        return query.getResultList();
    }
    @GET
    @Path("findByUsernameAndPasswd/{username}/{passwd}")
    @Produces({"application/json"})
    public List<Credentials> findByUsernameAndPasswd(@PathParam("username") String username, @PathParam("passwd") String passwd) {
        Query query = em.createNamedQuery("Credentials.findByUsernameAndPasswd");
        query.setParameter("username", username);
        query.setParameter("passwd", passwd);
        return query.getResultList();
    }

    @GET
    @Path("findBySigndate/{signdate}")
    @Produces({"application/json"})
    public List<Credentials> findBySigndate(@PathParam("signdate") Date signdate) {
        Query query = em.createNamedQuery("Credentials.findBySigndate");
        query.setParameter("signdate", signdate);
        return query.getResultList();
    }
    
}

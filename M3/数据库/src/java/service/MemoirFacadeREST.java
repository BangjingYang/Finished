/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;
import javax.ejb.Stateless;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
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
import newEntity.CpostcodeMnum;
import newEntity.HighScore;
import newEntity.ReleaseEqualWatch;
import rest.Memoir;

/**
 *
 * @author bangjingyang
 */
@Stateless
@Path("rest.memoir")
public class MemoirFacadeREST extends AbstractFacade<Memoir> {

    @PersistenceContext(unitName = "5046A1FinalPU")
    private EntityManager em;

    public MemoirFacadeREST() {
        super(Memoir.class);
    }

    @POST
    @Override
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void create(Memoir entity) {
        super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void edit(@PathParam("id") Integer id, Memoir entity) {
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
    public Memoir find(@PathParam("id") Integer id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Memoir> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Memoir> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
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
    @Path("findByMrelease/{mrelease}")
    @Produces({"application/json"})
    public List<Memoir> findByMrelease(@PathParam("mrelease") Date mrelease) {
        Query query = em.createNamedQuery("Memoir.findByMrelease");
        query.setParameter("mrelease", mrelease);
        return query.getResultList();
    }

    @GET
    @Path("findByMcomment/{mcomment}")
    @Produces({"application/json"})
    public List<Memoir> findByMcomment(@PathParam("mcomment") String mcomment) {
        Query query = em.createNamedQuery("Memoir.findByMcomment");
        query.setParameter("mcomment", mcomment);
        return query.getResultList();
    }

    @GET
    @Path("findByMscore/{mscore}")
    @Produces({"application/json"})
    public List<Memoir> findByMscore(@PathParam("mscore") Double mscore) {
        Query query = em.createNamedQuery("Memoir.findByMscore");
        query.setParameter("mscore", mscore);
        return query.getResultList();
    }

    @GET
    @Path("findByMwatchdt/{mwatchdt}")
    @Produces({"application/json"})
    public List<Memoir> findByMwatchdt(@PathParam("mwatchdt") Timestamp mwatchdt) {
        Query query = em.createNamedQuery("Memoir.findByMwatchdt");
        query.setParameter("mwatchdt", mwatchdt);
        return query.getResultList();  
    }

    @GET
    @Path("findByMname/{mname}")
    @Produces({"application/json"})
    public List<Memoir> findByMname(@PathParam("mname") String mname) {
        Query query = em.createNamedQuery("Memoir.findByMname");
        query.setParameter("mname", mname);
        return query.getResultList();
    }
    
    //task3c
    @GET
    @Path("findByMnameANDCname/{mname}/{cname}")
    @Produces({"application/json"})
    public List<Memoir> findByMnameANDCname(@PathParam("mname") String mname, @PathParam("cname") String cname) {
        TypedQuery<Memoir> query = em.createQuery("SELECT m FROM Memoir m WHERE m.mname = :mname AND m.cid.cname = :cname", Memoir.class);
        query.setParameter("mname", mname);
        query.setParameter("cname", cname);
        return query.getResultList();
    }
    
    //task3d
    @GET
    @Path("findByMnameANDCpostcode/{mname}/{cpostcode}")
    @Produces({"application/json"})
    public List<Memoir> findByMnameANDCpostcode(@PathParam("mname") String mname, @PathParam("cpostcode") Integer cpostcode) {
        Query query = em.createNamedQuery("Memoir.findByMnameANDCpostcode");
        query.setParameter("mname", mname);
        query.setParameter("cpostcode", cpostcode);
        return query.getResultList();
    }
    
    //task4a
    @GET
    @Path("findMnumByPidANDTime/{pid}/{startdate}/{enddate}")
    @Produces({"application/json"})
    public List<CpostcodeMnum> findMnumByPidANDTime(@PathParam("pid") Integer pid, 
            @PathParam("startdate") Date startdate, @PathParam("enddate") Date enddate){
        TypedQuery<CpostcodeMnum> query = em.createQuery("SELECT new newEntity.CpostcodeMnum(m.cid.cpostcode, COUNT(m.mname)) FROM Memoir m "
                + "WHERE m.pid.pid = :pid And m.mwatchdt >= :startdate AND m.mwatchdt <= :enddate "
                + "GROUP BY m.cid.cpostcode", CpostcodeMnum.class);
        query.setParameter("pid", pid);
        query.setParameter("startdate", startdate);
        query.setParameter("enddate", enddate);
        return query.getResultList();
    }

//task4b
    @GET
    @Path("findMnumByPidANDYear/{pid}/{year}")
    @Produces({"application/json"})
    public Object findMnumByPidANDYear(@PathParam("pid") Integer pid, @PathParam("year") Integer year){
        TypedQuery<Object[]> query = em.createQuery("SELECT EXTRACT(MONTH m.mwatchdt), COUNT(m.mname) FROM Memoir m "
                + "WHERE m.pid.pid = :pid And EXTRACT(YEAR m.mwatchdt) = :year "
                + "GROUP BY EXTRACT(MONTH m.mwatchdt)",Object[].class);
        query.setParameter("pid", pid);
        query.setParameter("year", year);
        List<Object[]> queryList = query.getResultList();
        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder(); 
        for (Object[] row : queryList) {
            Number numOfMovie = (Number) row[1];
            String month = "" + row[0];
//            switch((int)row[0]){
//                case 1: 
//                    month = "January";break;
//                case 2:
//                    month = "February";break;
//                case 3:
//                    month = "March";break;
//                case 4:
//                    month = "April";break;
//                case 5:
//                    month = "May";break;
//                case 6:
//                    month = "June";break;
//                case 7:
//                    month = "July";break;
//                case 8:
//                    month = "August";break;
//                case 9:
//                    month = "September";break;
//                case 10:
//                    month = "October";break;
//                case 11:
//                    month = "Novement";break;
//                case 12:
//                    month = "December";break;
//            }
            JsonObject memoirObject = Json.createObjectBuilder()
                  .add("month", month)
                  .add("number of movie", numOfMovie.intValue()).build();
            arrayBuilder.add(memoirObject);
        }
        JsonArray jArray = arrayBuilder.build();
        return jArray;
    }

    //task4c
    @GET
    @Path("findHighScoreByPid/{pid}")
    @Produces({"application/json"})
    public List<HighScore> findHighScoreByPid(@PathParam("pid") Integer pid){
        TypedQuery<HighScore> query = em.createQuery("SELECT new newEntity.HighScore(m.mname, m.mscore, m.mrelease) FROM Memoir m "
                + "WHERE m.pid.pid = :pid AND "
                + "m.mscore = (SELECT Max(me.mscore) From Memoir me WHERE me.pid.pid = :pid)",HighScore.class);
        query.setParameter("pid", pid);
        return query.getResultList();
    }
    
    //task4d
    @GET
    @Path("findReleaseEqualWatchByPid/{pid}")
    @Produces({"application/json"})
    public List<ReleaseEqualWatch> findReleaseEqualWatchByPid(@PathParam("pid") Integer pid){
        TypedQuery<ReleaseEqualWatch> query = em.createQuery("SELECT DISTINCT new newEntity.ReleaseEqualWatch(m.mname, m.mrelease) FROM Memoir m "
                + "WHERE m.pid.pid = :pid AND "
                + "EXTRACT(YEAR m.mwatchdt) = EXTRACT(YEAR m.mrelease)",ReleaseEqualWatch.class);
        query.setParameter("pid", pid);
        return query.getResultList(); 
    }

    //task4e

    @GET
    @Path("findWatchedRemakeByPid/{pid}")
    @Produces({"application/json"})
    public List<ReleaseEqualWatch> findWatchedRemakeByPid(@PathParam("pid") Integer pid){
        TypedQuery<ReleaseEqualWatch> query = em.createQuery("SELECT new newEntity.ReleaseEqualWatch(m1.mname, m1.mrelease) FROM Memoir m1 JOIN Memoir m2 ON m1.mname = m2.mname "
                + "WHERE m1.pid.pid = :pid AND EXTRACT(YEAR m1.mrelease) != EXTRACT(YEAR m2.mrelease)",ReleaseEqualWatch.class);
        query.setParameter("pid", pid);
        return query.getResultList();
    }

    //task4f
    
    @GET
    @Path("findTopFiveInRecentByPid/{pid}")
    @Produces({"application/json"})
    public Object findTopFiveInRecentByPid(@PathParam("pid") Integer pid){
        TypedQuery<Object[]> query = em.createQuery("SELECT m.mname, m.mrelease, m.mscore FROM Memoir m "
                + "WHERE m.pid.pid = :pid ORDER BY m.mscore DESC", Object[].class);
        query.setParameter("pid", pid);
        List<Object[]> queryList = query.getResultList();
        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder(); 
        int i = 0;
        for (Object[] row : queryList) {
            
            String releaseDate = row[1].toString();
            String MMdd = releaseDate.substring(4, 10);//Fri Jun 24 00:00:00 AEST 1994
            String yyyy = releaseDate.substring(25);
            Calendar now = Calendar.getInstance();
            String recentYear = "" + now.get(Calendar.YEAR);
            if (yyyy.equals(recentYear)){
                releaseDate = MMdd + " " + yyyy;
                JsonObject memoirObject = Json.createObjectBuilder()
                    .add("Movie name", (String)row[0])
                    .add("Release Date", (String)releaseDate)
                    .add("Movie Score", (Double)row[2]).build();
                  arrayBuilder.add(memoirObject);
                 i++;
                 
            }
            if (i == 5)
                break;
        }
        JsonArray jArray = arrayBuilder.build();
        return jArray; 
    }
    
    @GET
    @Path("sortByMwatchdt/{pid}")
    @Produces({"application/json"})
    public List<Memoir> sortByMwatchdt(@PathParam("pid") Integer pid) {
        Query query = em.createNamedQuery("Memoir.sortByMwatchdt");
        query.setParameter("pid", pid);
        return query.getResultList();
    }
    
    @GET
    @Path("sortByMscore/{pid}")
    @Produces({"application/json"})
    public List<Memoir> sortByMscore(@PathParam("pid") Integer pid) {
        Query query = em.createNamedQuery("Memoir.sortByMscore");
        query.setParameter("pid", pid);
        return query.getResultList();
    }
    
    @GET
    @Path("sortByMrelease/{pid}")
    @Produces({"application/json"})
    public List<Memoir> sortByMrelease(@PathParam("pid") Integer pid) {
        Query query = em.createNamedQuery("Memoir.sortByMrelease");
        query.setParameter("pid", pid);
        return query.getResultList();
    }
}

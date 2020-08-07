package rest;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import rest.Cinema;
import rest.Person;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2020-05-31T22:26:45")
@StaticMetamodel(Memoir.class)
public class Memoir_ { 

    public static volatile SingularAttribute<Memoir, Date> madddate;
    public static volatile SingularAttribute<Memoir, String> mcomment;
    public static volatile SingularAttribute<Memoir, Double> mscore;
    public static volatile SingularAttribute<Memoir, Integer> mid;
    public static volatile SingularAttribute<Memoir, Person> pid;
    public static volatile SingularAttribute<Memoir, Date> mrelease;
    public static volatile SingularAttribute<Memoir, String> mname;
    public static volatile SingularAttribute<Memoir, Date> mwatchdt;
    public static volatile SingularAttribute<Memoir, Cinema> cid;

}
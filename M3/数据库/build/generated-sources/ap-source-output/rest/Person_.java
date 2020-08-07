package rest;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import rest.Credentials;
import rest.Memoir;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2020-05-31T22:26:45")
@StaticMetamodel(Person.class)
public class Person_ { 

    public static volatile SingularAttribute<Person, Date> pdob;
    public static volatile CollectionAttribute<Person, Credentials> credentialsCollection;
    public static volatile CollectionAttribute<Person, Memoir> memoirCollection;
    public static volatile SingularAttribute<Person, String> psname;
    public static volatile SingularAttribute<Person, String> pstate;
    public static volatile SingularAttribute<Person, Integer> pid;
    public static volatile SingularAttribute<Person, Integer> ppostcode;
    public static volatile SingularAttribute<Person, String> pfname;
    public static volatile SingularAttribute<Person, String> pgender;
    public static volatile SingularAttribute<Person, String> paddress;

}
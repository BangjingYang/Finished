package rest;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import rest.Person;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2020-05-31T22:26:45")
@StaticMetamodel(Credentials.class)
public class Credentials_ { 

    public static volatile SingularAttribute<Credentials, String> passwd;
    public static volatile SingularAttribute<Credentials, Person> pid;
    public static volatile SingularAttribute<Credentials, Date> signdate;
    public static volatile SingularAttribute<Credentials, String> username;

}
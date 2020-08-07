package rest;

import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import rest.Memoir;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2020-05-31T22:26:45")
@StaticMetamodel(Cinema.class)
public class Cinema_ { 

    public static volatile CollectionAttribute<Cinema, Memoir> memoirCollection;
    public static volatile SingularAttribute<Cinema, String> cname;
    public static volatile SingularAttribute<Cinema, String> csurburb;
    public static volatile SingularAttribute<Cinema, Integer> cpostcode;
    public static volatile SingularAttribute<Cinema, Integer> cid;

}
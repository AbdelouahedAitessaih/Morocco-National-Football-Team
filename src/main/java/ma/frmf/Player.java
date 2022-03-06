package ma.frmf;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

import javax.inject.Inject;
import javax.persistence.*;

@Entity
@NamedQuery(name = "MaxAgeByPosition", query = "SELECT MAX(p.age) FROM Player p WHERE p.position = :position")
public class Player extends PanacheEntity {

    public String name;
    public int age;
    public String position;
    public String team;
    @SequenceGenerator(name = "giftSeq", sequenceName = "gift_id_seq", allocationSize = 1, initialValue = 1)
    @GeneratedValue(generator = "giftSeq")
    public int number;

    //public static Player findMaxAge(String position){
        //return find("#MaxAgeByPosition", position).firstResult();
    //}

}

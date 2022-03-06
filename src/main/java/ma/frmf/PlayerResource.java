package ma.frmf;

import io.quarkus.hibernate.orm.panache.Panache;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/players")
public class PlayerResource {

    @Inject
    EntityManager entityManager;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Player> getPlayers(){

        return Player.listAll();
    }

    @POST
    @RolesAllowed("admin")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Transactional
    public Player savePlayer(Player player){
        player.persist();
        return player;
    }

    @GET
    @Path("/{position}")
    @RolesAllowed("admin")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Player> findByPosition(@PathParam("position") String position){
        return Player.find("position" + "", position).list();
    }

    @GET
    @Path("/max/{position}")
    @Produces(MediaType.APPLICATION_JSON)
    public Integer getMaxAgeByPosition(@PathParam("position") String position){
        //return entityManager.createNamedQuery("MaxAgePlayer").getResultList();
        return (Integer) entityManager.createQuery("SELECT MAX(p.age) FROM Player p WHERE p.position = :position").setParameter("position",position).getSingleResult();
    }

    @PUT
    @RolesAllowed("admin")
    @Transactional
    public Player updatePlayer(Player player){
        Panache.getEntityManager().merge(player);
        return player;
    }

    @DELETE
    @Path("/{id}")
    @RolesAllowed("admin")
    @Transactional
    public Response deletePlayer(@PathParam("id") Long id){
        Player pl = Player.findById(id);
        pl.delete();
        return Response.noContent().build();
    }
}

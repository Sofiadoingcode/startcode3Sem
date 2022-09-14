package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dtos.PersonDTO;
import utils.EMF_Creator;
import facades.PersonFacade;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

//Todo Remove or change relevant parts before ACTUAL use
@Path("person")
public class PersonResource {

    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();
       
    private static final PersonFacade FACADE =  PersonFacade.getPersonFacade(EMF);
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    //READ
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String demo() {

        return "{\"msg\":\"Hello World\"}";
    }

    @GET
    @Path("/all")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getAllPersons() {

        return Response.ok().entity(GSON.toJson(FACADE.getAll())).build();
    }

    @Path("/username/{username}")
    @GET
    @Produces("text/plain")
    public String getUser(@PathParam("username") String userName) {

        return "Hello";
    }

    //CREATE
    @POST
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    public Response postExample(String jsonInput){
        PersonDTO personDTO = GSON.fromJson(jsonInput, PersonDTO.class);
        System.out.println(personDTO);
        PersonDTO returned = FACADE.create(personDTO);
        return Response.ok().entity(GSON.toJson(returned)).build();
    }

    @PUT
    @Path(("{id}"))
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    public Response putExample(@PathParam("id") long id, String jsonInput){
        PersonDTO personDTO = GSON.fromJson(jsonInput, PersonDTO.class);
        personDTO.setId(id);
        System.out.println(personDTO);
        PersonDTO returned = FACADE.update(personDTO);
        return Response.ok().entity(GSON.toJson(returned)).build();
    }
}

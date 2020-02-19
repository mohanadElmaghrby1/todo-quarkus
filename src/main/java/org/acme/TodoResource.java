package org.acme;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import io.quarkus.panache.common.Sort;

import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import java.net.URI;
import java.util.List;
import org.jboss.logging.Logger;


/**
 * @author Emmanuel Bernard emmanuel@hibernate.org
 */
@Path("/api/todos")
@Produces(MediaType.APPLICATION_JSON)
public class TodoResource {
    private static final Logger LOGGER = Logger.getLogger(TodoResource.class);

    @GET
    public List<Todo> getAllTodos() {
        LOGGER.info("get all Todos");
        return Todo.listAll();
    }

    @POST
    @Transactional
    public Response addTodo(Todo todo) {
        todo.persist();
        LOGGER.info("Create new TODO with "+ todo);
        return Response.created(URI.create("/" + todo.id)).entity(todo).build();
    }

    @PUT
    @Transactional
    public Response updateTodo(Todo updatedTodo) {
        Todo todo = Todo.findById(updatedTodo.id);
       todo.title=updatedTodo.title;
       todo.url=updatedTodo.url;
       todo.completed=updatedTodo.completed;
        LOGGER.info("update new TODO with "+ todo);
        return Response.ok(todo).build();
    }


    @DELETE
    @Path("/{id}")
    @Transactional
    public Response deleteTodo(
            @PathParam("id") Long id) {
        Todo todoToDelete = Todo.findById(id);
        if (todoToDelete != null) {
            todoToDelete.delete();
            return Response.noContent().build();
        }
        LOGGER.info("todo deleted with " + id);
        return Response.status(Response.Status.NOT_FOUND).entity("{\nmessage:\"todo with id: \"+id+\" not found\"\n}").build();
    }

    @GET
    @Path("/search/{word}/{page}")
    public List<Todo> search(@PathParam("word") String word, @PathParam("page") Integer pageNumber) {
        return Todo.search(word, pageNumber);
    }
}

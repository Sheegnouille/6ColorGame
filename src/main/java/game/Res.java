package game;

import game.board.Board;
import game.color.Color;
import game.color.ColorGeneratorRandom;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static game.board.RectangularBoard.RectangularBoardBuilder.aRectangularBoard;

@Path("/yolo")
public class Res {
    @GET
    @Path("/ping")
    public Response ping() {
        return Response.ok().entity("Service online").build();
    }

    @GET
    @Path("/newGame/{width}x{height}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getNotification(
            @PathParam("width") int width,
            @PathParam("height") int height) {
        Board board = aRectangularBoard()
                .withWidth(width)
                .withHeight(height)
                .withColorGenerator(new ColorGeneratorRandom())
                .build();
        return Response.ok()
                .entity(new Game(board))
                .build();
    }

    @POST
    @Path("/addPlayer/{playerName}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response postNotification(@PathParam("playerName") String name) {
        return Response.status(201).entity(name).build();
    }

    @POST
    @Path("/play/{color}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response postNotification(@PathParam("color") Color color) {
        return Response.status(201).entity(color).build();
    }
}

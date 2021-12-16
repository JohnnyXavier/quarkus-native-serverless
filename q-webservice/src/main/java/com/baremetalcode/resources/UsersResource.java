package com.baremetalcode.resources;

import com.baremetalcode.db.domain.User;
import com.baremetalcode.db.dynamo.repos.UsersRepo;
import io.smallrye.mutiny.Uni;

import javax.inject.Inject;
import javax.ws.rs.*;
import java.util.List;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

@Path("/users")
@Produces(APPLICATION_JSON)
@Consumes(APPLICATION_JSON)
public class UsersResource {

    @Inject
    UsersRepo usersRepo;

    @GET
    public Uni<List<User>> getAllUsers() {
        return usersRepo.findAll();
    }

    @GET
    @Path("{userUuid}")
    public Uni<User> getUserById(@PathParam("userUuid") final String userUuid) {
        return usersRepo.findById(userUuid);
    }

    @GET
    @Path("/country/{countryIsoId}")
    public Uni<List<User>> getUserByCountryIsoId(@PathParam("countryIsoId") final String countryIsoId) {
        return usersRepo.findUserByCountryIsoId(countryIsoId);
    }

    @POST
    public Uni<List<User>> postUser(final User user) {
        return usersRepo.putAsync(user);
    }

}
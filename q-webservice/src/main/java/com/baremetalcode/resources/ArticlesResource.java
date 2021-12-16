package com.baremetalcode.resources;

import com.baremetalcode.db.dynamo.repos.ArticlesRepo;
import com.baremetalcode.db.domain.Article;
import io.smallrye.mutiny.Uni;

import javax.inject.Inject;
import javax.ws.rs.*;
import java.util.List;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

@Path("/articles")
@Produces(APPLICATION_JSON)
@Consumes(APPLICATION_JSON)
public class ArticlesResource {

    @Inject
    ArticlesRepo articlesRepo;

    @GET
    public Uni<List<Article>> getAllArticles() {
        return articlesRepo.findAll();
    }

    @GET
    @Path("{articleUuId}")
    public Uni<Article> getArticleById(@PathParam("articleUuId") final String articleUuid) {
        return articlesRepo.findById(articleUuid);
    }

    @GET
    @Path("/user/{userUuId}")
    public Uni<List<Article>> getArticlesByUserId(@PathParam("userUuId") final String userUuid) {
        return articlesRepo.findArticleByUserId(userUuid);
    }

    @POST
    public Uni<List<Article>> postArticle(final Article article) {
        return articlesRepo.putAsync(article);
    }

}
package com.learning.tacocloud.api.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.hateoas.server.LinkBuilder;
import org.springframework.hateoas.server.mvc.BasicLinkBuilder;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Service;

import javax.servlet.ServletContext;
import java.net.URI;

// This class is required because of the bug in Spring Data REST
// Links made in a @RepositoryRestController class are not using the base path of Spring Data REST (/api in this app)
// See: https://github.com/spring-projects/spring-hateoas/issues/434
@Service
public class BasePathAwareLinks {

    private final URI contextBaseURI;
    private final URI restBaseURI;

    @Autowired
    public BasePathAwareLinks(ServletContext servletContext, RepositoryRestConfiguration config) {
        contextBaseURI = URI.create(servletContext.getContextPath());
        restBaseURI = config.getBasePath();
    }

    public LinkBuilder underBasePath(WebMvcLinkBuilder linkBuilder) {
        return BasicLinkBuilder.linkToCurrentMapping()
                .slash(restBaseURI)
                .slash(contextBaseURI.relativize(URI.create(linkBuilder.toUri().getPath())));
    }
}

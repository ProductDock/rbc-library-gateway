package com.productdock.library.gateway.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.web.reactive.config.ResourceHandlerRegistry;
import org.springframework.web.reactive.config.WebFluxConfigurer;
import org.springframework.web.reactive.resource.ResourceResolver;
import org.springframework.web.reactive.resource.ResourceResolverChain;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Configuration
public class StaticResourceConfig implements WebFluxConfigurer {


    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        ResourceResolver resolver = new ReactResourceResolver();
        registry.addResourceHandler("/**")
                .resourceChain(true)
                .addResolver(resolver);
    }

    public class ReactResourceResolver implements ResourceResolver {

        private static final String STATIC_DIR = "/static";
        public static final String LANDING_PAGE_PATH = "/";

        private Resource landingPage = new ClassPathResource(STATIC_DIR + "/landing/landing.html");
        private Resource indexPage = new ClassPathResource(STATIC_DIR + "/" + "index.html");
        private List<String> rootStaticFiles = Arrays.asList("favicon.svg",
                "asset-manifest.json", "manifest.json", "service-worker.js");
        private List<String> staticFilesExtensions = Arrays.asList(".svg",
                ".png", ".jpg", ".js", ".css", ".ico");

        @Override
        public Mono<Resource> resolveResource(ServerWebExchange exchange, String requestPath, List<? extends Resource> locations, ResourceResolverChain chain) {
           return Mono.just(resolve(requestPath, locations));
        }

        @Override
        public Mono<String> resolveUrlPath(String resourcePath, List<? extends Resource> locations, ResourceResolverChain chain) {
            Resource resolvedResource = resolve(resourcePath, locations);
            if (resolvedResource == null) {
                return null;
            }
            try {
                return Mono.just(resolvedResource.getURL().toString()) ;
            } catch (IOException e) {
                return Mono.just(resolvedResource.getFilename());
            }
      }

        private Resource resolve(String requestPath, List<? extends Resource> locations) {

            if (requestPath == null) return null;

            if (isStaticResource(requestPath)) {
                if (isLandingResource(requestPath)) {
                    return new ClassPathResource(STATIC_DIR + "/landing/" + requestPath);
                }
                return new ClassPathResource(STATIC_DIR + "/" + requestPath);
            }
            else if (isLandingPage(requestPath)) {
                return landingPage;
            }

            return indexPage;
        }

        private boolean isStaticResource(String requestPath) {
            return rootStaticFiles.contains(requestPath)
                    || requestPath.startsWith(STATIC_DIR)
                    || containsStaticExtension(requestPath);
        }

        private boolean isLandingResource(String requestedPath) {
            return requestedPath.toLowerCase().contains("landing");
        }

        private boolean isLandingPage(String requestPath) {
            return requestPath.equals(LANDING_PAGE_PATH);
        }

        private boolean containsStaticExtension(String requestPath) {
            for (String extension : staticFilesExtensions) {
                if (requestPath.endsWith(extension)) {
                    return true;
                }
            }
            return false;
        }
    }

}


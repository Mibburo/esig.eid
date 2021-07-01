package com.example.esig.eid.security;

import lombok.extern.slf4j.Slf4j;
import org.keycloak.adapters.KeycloakConfigResolver;
import org.keycloak.adapters.KeycloakDeployment;
import org.keycloak.adapters.KeycloakDeploymentBuilder;
import org.keycloak.adapters.OIDCHttpFacade;
import org.thymeleaf.util.StringUtils;

import java.io.InputStream;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
public class PathBasedKeycloakConfigResolver implements KeycloakConfigResolver {

    private Map<String, KeycloakDeployment> cache = new ConcurrentHashMap<String, KeycloakDeployment>();

    public final static String BASE_URL = StringUtils.isEmpty(System.getenv("BASE_URL"))?"http://localhost:7001/":System.getenv("BASE_URL");

    @Override
    public KeycloakDeployment resolve(OIDCHttpFacade.Request request) {

        String path = request.getURI();
        log.info("xxxxxxxxxxxxxxxxxxxxxxxxxxxx path :{}", path);
        if (    request.getRelativePath().equals("/")
                || request.getRelativePath().equals("/error")
                || request.getRelativePath().equals("/rest/nonce")
                || request.getRelativePath().contains("/js")
                || request.getRelativePath().contains("/images")
                || request.getRelativePath().contains("/img")
                || request.getRelativePath().contains("/css")
                || request.getRelativePath().contains("/style.css")
                || request.getRelativePath().contains("/favicon.ico")
                || request.getRelativePath().contains("/register")
                || request.getRelativePath().contains("/team")
                || request.getRelativePath().contains("/sso")
                || request.getRelativePath().contains("/esig")
                || request.getRelativePath().contains("/userExists")
                || request.getRelativePath().contains("/errorPage")
                || request.getRelativePath().contains("/logout")//pending selectConf logout roomaccess
                || request.getRelativePath().contains("/roomaccess")//pending selectConf logout roomaccess

                //
        ) {
            //essentially this returns the root configuration, but since these endpoints are not configured in the
            // springsecurity config class, their access is free.. if this is not included then an error is thrown...
            return KeycloakDeploymentBuilder.build(getClass().getResourceAsStream("/esig.json"));
        }

        log.info("zzzzzzzzzzzzzzzzzzzzzzzzzz path :{}", path);

        if (request.getHeader("referer") != null && !(path.contains("esig") && path.contains("eidas")
                 && path.contains("admin")
                 &&  path.contains("linkedIn"))) {
            log.info("aaaaaaaaaaaaaaaaaaaaaaa request.getHeader(\"referer\" :{}", request.getHeader("referer"));

            path = request.getHeader("referer");
            if ( request.getHeader("referer").endsWith("error")) {
                return KeycloakDeploymentBuilder.build(getClass().getResourceAsStream("/esig.json"));
            }
        }
        log.info("11111111111111111111111111 path :{}", path);
        int multitenantIndex = path.indexOf("multi/");

        String realm = "";
        if (multitenantIndex != -1) {
            realm = path.substring(path.indexOf("multi/")).split("/")[1];
        } else {
            realm = (request.getRelativePath().split("/"))[1];
        }
        log.info("222222222222222222222222 path :{}", path);
        if (realm.contains("?")) {
            realm = realm.split("\\?")[0];
        }

        KeycloakDeployment deployment = cache.get(realm);
        log.info("33333333333333333333333333333 deployment :{}", deployment);
        if (null == deployment) {
            // not found on the simple cache, try to load it from the file system
            InputStream is = getClass().getResourceAsStream("/" + realm + ".json");
            if (is == null) {
                throw new IllegalStateException("Not able to find the file /" + realm + ".json");
            }
            deployment = KeycloakDeploymentBuilder.build(is);
            cache.put(realm, deployment);

        }
        cache.put("sso", deployment);
        log.info("yyyyyyyyyyyyyyyyyy cache :{}", cache);

        return deployment;
    }

}

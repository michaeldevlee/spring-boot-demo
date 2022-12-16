FROM jboss/keycloak
COPY demo.json /tmp/demo.json
ENV KEYCLOAK_USER admin
ENV KEYCLOAK_PASSWORD password
ENV KEYCLOAK_FRONTEND_URL=http://localhost:8080/auth
ENV PROXY_ADDRESS_FORWARDING=true
ENV KEYCLOAK_IMPORT /tmp/demo.json
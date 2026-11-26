FROM grafana/grafana:11.2.0
ENV GF_AUTH_ANONYMOUS_ENABLED=true
ENV GF_AUTH_ANONYMOUS_ORG_ROLE=Viewer
ENV GF_SECURITY_ALLOW_EMBEDDING=true
ENV PROMETHEUS_URL=http://localhost:9090
COPY provisioning /etc/grafana/provisioning
EXPOSE 3000
ENTRYPOINT ["sh", "-c", "export GF_SERVER_HTTP_PORT=${PORT:-3000} && exec /run.sh"]

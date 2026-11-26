FROM prom/prometheus:v2.54.1
COPY prometheus.yml /etc/prometheus/prometheus.yml
COPY alert_rules.yml /etc/prometheus/alert_rules.yml
EXPOSE 9090
ENTRYPOINT ["sh", "-c", "exec /bin/prometheus --config.file=/etc/prometheus/prometheus.yml --storage.tsdb.path=/prometheus --web.listen-address=0.0.0.0:${PORT:-9090} --web.enable-lifecycle"]

FROM centos
RUN \
  yum update -y && \
  yum install -y java-1.8.0-openjdk && \
  yum install -y wget && \
  mkdir -p /data/ces-literal-response-filter-api \
  mkdir -p /data/ces-literal-response-filter-api/work \
  mkdir -p /data/ces-literal-response-filter-api/target

WORKDIR data/ces-literal-response-filter-api

COPY start.sh /data/ces-literal-response-filter-api
COPY target/cesliteralresponsefilter-0.0.1-SNAPSHOT.jar /data/ces-literal-response-filter-api/target
COPY cesliteralresponsefilter-configuration.yml /data/ces-literal-response-filter-api

EXPOSE 9110
EXPOSE 9111

ENV JAVA_HOME /usr/lib/jvm/jre-1.8.0-openjdk
CMD /data/ces-literal-response-filter-api/start.sh

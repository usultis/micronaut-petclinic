FROM oracle/graalvm-ce:1.0.0-rc16 as graalvm
WORKDIR /work
COPY ./dynamic-proxy ./dynamic-proxy
COPY ./target/micronaut-petclinic-*.jar .
RUN native-image --no-server -cp micronaut-petclinic-*.jar

FROM frolvlad/alpine-glibc
EXPOSE 8080
WORKDIR /app
COPY --from=graalvm /work/petclinic .
ENTRYPOINT ["/app/petclinic"]

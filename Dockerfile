FROM oracle/graalvm-ce:19.2.0.1 as graalvm
RUN gu install native-image

WORKDIR /work
COPY ./target/micronaut-petclinic-*.jar .
RUN native-image --no-server -cp micronaut-petclinic-*.jar

FROM frolvlad/alpine-glibc
EXPOSE 8080
WORKDIR /app
COPY --from=graalvm /work/petclinic .
ENTRYPOINT ["/app/petclinic"]

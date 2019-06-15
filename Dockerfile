#FROM oracle/graalvm-ce:19.0.0 as graalvm
#RUN gu install native-image
FROM bufferings/build-graalvm-docker as graalvm
WORKDIR /work
COPY ./target/micronaut-petclinic-*.jar .
RUN native-image --no-server -cp micronaut-petclinic-*.jar

FROM frolvlad/alpine-glibc
EXPOSE 8080
WORKDIR /app
COPY --from=graalvm /work/petclinic .
ENTRYPOINT ["/app/petclinic"]

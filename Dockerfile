# we will use openjdk 8 with alpine as it is a very small linux distro
FROM openjdk:8-jre-alpine3.9
 
# copy the packaged jar file into our docker image
COPY target/ProPetsValidation-0.0.1-SNAPSHOT.jar /ProPetsValidation-0.0.1-SNAPSHOT.jar

ENV DB_URL=mongodb+srv://user-read-only:LsE8V2xxPX5o3fix@cluster0.orivv.mongodb.net/proPetsAccountingDB?retryWrites=true&w=majority
ENV ORIGIN_URL=*
ENV SECRET=uj89347593487nctj98347f983475fn9w8ecscfevtr34
 
# set the startup command to execute the jar
CMD ["java", "-jar", "/ProPetsValidation-0.0.1-SNAPSHOT.jar"]
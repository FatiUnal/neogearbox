# Ubuntu tabanlı bir temel imaj kullanıyoruz
FROM ubuntu:20.04

# Java ve Maven kurulumları
RUN apt-get update -y && apt-get install -y openjdk-21-jdk maven

# Proje dosyalarını konteynıra kopyalıyoruz
COPY target/neogearbox-0.0.1-SNAPSHOT.jar neogearbox-0.0.1-SNAPSHOT.jar

# Build sırasında oluşturulan JAR dosyasını çalıştırıyoruz
ENTRYPOINT ["java", "-jar", "neogearbox-0.0.1-SNAPSHOT.jar"]

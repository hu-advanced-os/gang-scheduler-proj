# Use the official OpenJDK 19 image as the base image
FROM gradle:jdk19

# Set the working directory in the container
WORKDIR /gang-scheduler-proj/generator-folder

# Copy the Gradle files to the container
# COPY generator/build.gradle generator/.
# COPY gradle.properties .

# Copy the rest of your Java project
COPY . .

# Build your Java project using Gradle
RUN chmod -R 777 ./generator
RUN cd generator && ./gradlew clean build

# Command to run your Java application
CMD cd generator/bin && ./r

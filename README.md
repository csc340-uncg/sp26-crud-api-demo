# Student CRUD MVC Application - primed for deployment using Docker

## Notes:
This version of the project is primed for deploying using a Docker container.

### Setup Instructions:

#### Local Setup:
- Make sure you have Docker installed on your machine. You can download it from the official Docker website: https://www.docker.com/get-started
- Clone the project repository to your local machine if you haven't already.
- Navigate to the project directory in your terminal.
- Add a .env file to your project root with the following content:
```
SPRING_DATASOURCE_URL=YOUR_NEON_DATABASE_URL
```
- Make sure to replace `YOUR_NEON_DATABASE_URL` with the actual connection string for your Neon database. This allows the application to connect to the database when running as it will read the `DATABASE_URL` environment variable at runtime.`
- Ensure that your application is working correctly by running it locally and testing all the endpoints and views. This is important to do before building the Docker image, as it will help you identify any issues that need to be fixed before deployment.

#### Docker Setup:
- The `Dockerfile` is used to build a Docker image of the application, which can then be run as a container. This allows you to deploy the application in a consistent environment, regardless of where it's run (e.g., on your local machine, on a server, or in the cloud), since the container includes all the necessary dependencies and configurations.
- Stage 1 of the `Dockerfile` uses a lightweight OpenJDK image to build the application using Maven. It copies the source code and the `pom.xml` file into the container, runs `mvn clean package` to build the application, and produces a JAR file.
- Stage 2 of the `Dockerfile` uses another OpenJDK image to run the application. It copies the JAR file from the first stage into the second stage and sets the entry point to run the JAR file when the container starts.
- Use Docker to build and run the application.
     - To build the Docker image, you would run a command like
     ```
     docker build -t sp26-crud-api-demo .
     ```
     in the terminal. This command tells Docker to build an image with the tag `sp26-crud-api-demo` using the `Dockerfile` in the current directory (indicated by the `.`).
     - To run the Docker container, you would use a command like
     ```
     docker run -p 8080:8080 --env-file .env sp26-crud-api-demo
     ```
     which maps port 8080 of the container to port 8080 on your local machine and passes the environment variables from the `.env` file to the container.
     - This allows you to access the application at `http://localhost:8080` in your web browser.

#### Deployment to Hosting Platform:
- On a hosting platform such as Heroku or Render, you would typically push your code to a Git repository, connect that repository to the hosting platform, and configure the platform to build and run the Docker container based on your `Dockerfile`.
- The hosting platform will handle the deployment process, including building the Docker image and running the container in their environment.
- You will also need to set the `DATABASE_URL` environment variable in the hosting platform's configuration settings, so that the application can connect to the Neon database when running in the cloud.

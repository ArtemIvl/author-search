# author-search
The Author Search App is a Spring Boot application that allows users to search for authors and their works. The application first checks the local database for the requested data and, if not found, fetches it from an external API and saves it to the database.

## Prerequisites
Before you begin, ensure you have met the following requirements:

- Java Development Kit (JDK) 11 or later: You can download it from the Oracle website or use an OpenJDK distribution.
- Maven: Download it from the Maven website or use the bundled version in your IDE.
- Database: Ensure you have a running instance of MySQL. Create a database named `author_db`. The application will create the necessary tables automatically.
- Integrated Development Environment (IDE): Use an IDE like IntelliJ IDEA, Eclipse, or Visual Studio Code.

## Configuration
- Configure your database and other properties in src/main/resources/application.properties. Make sure that the database URL, username, and password meet your local setup.

## Running the Application
- Clone the Git repository to your local machine.
git clone https://github.com/ArtemIvl/author-search.git
cd author-search/author-search-app
- Open the project in your IDE.
- Build the project by running mvn clean install command in the terminal.
- Run the application by right-clicking on the AuthorSearchAppApplication class and select Run 'AuthorSearchAppApplication'. Alternatively, you can run the application from the terminal by executing the following command: mvn spring-boot:run.

## Accessing the Application
Once the application is running, you can access the endpoints using a tool like Postman or your web browser.

Example endpoint to find authors by name:
GET http://localhost:8080/api/authors/search?name=j%20k%20rowling

Example endpoint to find works by author ID:
GET http://localhost:8080/api/works/search?authorId=1

By following the steps above, you should be able to build, run, and test the Author Search App successfully.



<h1>Playground Management System</h1>
<h2>Project Overview</h2>
<p>This project is a Playground Management System designed to manage play sites, kids, and attractions in a playground setting. Built using Java with Spring Boot, it leverages an H2 in-memory database for efficient data persistence and management. The system provides a series of REST API endpoints to perform various operations related to playground management, offering a robust backend solution for playground administrators.</p>
<h2>Technologies Used</h2>
<ul>
   <li>Java: The primary programming language used to develop the application.</li>
   <li>Spring Boot: The framework used to create the REST API, providing simplicity and rapid development for enterprise applications.</li>
   <li>H2 Database: An in-memory database used for data persistence, enabling quick access and modifications to the stored data.</li>
   <li>Gradle: A build automation tool used to manage dependencies and build the project.</li>
</ul>
<h2>Running the Project</h2>
<p>To run the project, execute the following command in your terminal:</p>
<b>./gradlew bootRun</b>
</code></div></div></pre>
<h2>API Endpoints</h2>
<h3>Play Sites</h3>
<ul>
   <li><code>POST /api/v1/play-sites</code>: Create a new play site. This endpoint accepts a JSON object containing list of attraction IDs and returns the created play site object.</li>
   <li><code>GET /api/v1/play-sites</code>: Retrieve a list of all play sites. This endpoint returns an array of play site objects.</li>
   <li><code>GET /api/v1/play-sites/{id}</code>: Retrieve a play site by its unique ID. This endpoint returns a single play site object.</li>
   <li><code>PATCH /api/v1/play-sites/{id}/add-kid/{kidId}</code>: Add a kid to a specific play site. This modifies the play site to include a new kid.</li>
   <li><code>PATCH /api/v1/play-sites/{id}/remove-kid/{kidId}</code>: Remove a kid from a specific play site. This modifies the play site by removing an existing kid.</li>
   <li><code>PATCH /api/v1/play-sites/{id}/add-attraction/{attractionId}</code>: Add an attraction to a play site. This updates the play site with a new attraction.</li>
   <li><code>PATCH /api/v1/play-sites/{id}/remove-attraction/{attractionId}</code>: Remove an attraction from a play site. This updates the play site by removing an attraction.</li>
</ul>
<h3>Kids</h3>
<ul>
   <li><code>POST /api/v1/kids</code>: Create a new kid profile. This endpoint accepts a JSON object representing a kid and returns the created kid object.</li>
   <li><code>GET /api/v1/kids</code>: Retrieve a list of all kids. This endpoint returns an array of kid objects.</li>
   <li><code>GET /api/v1/kids/{id}</code>: Retrieve a kid by their unique ID. This endpoint returns a single kid object.</li>
</ul>
<h3>Attractions</h3>
<ul>
   <li><code>POST /api/v1/attractions</code>: Create a new attraction. This endpoint accepts a JSON object representing an attraction and returns the created attraction object.</li>
   <li><code>GET /api/v1/attractions</code>: Retrieve a list of all attractions. This endpoint returns an array of attraction objects.</li>
   <li><code>GET /api/v1/attractions/{id}</code>: Retrieve an attraction by its unique ID. This endpoint returns a single attraction object.</li>
</ul>
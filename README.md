# Product Listing Web Application
A web application that allows users to view, upload, update, delete, search, sort and filter the products.
* Frontend Service: AngularJS + Bootstrap
* Backend Service: Undertow + Java Servlet + MySQL(deployed in AWS)

## Main Features
* View all products uploaded so far (sorted by uploaded time).
* Search products by product name.
* Update and delete product by product name and username.
* Sort products by product name or price.
* Filter products by price range.

## Architecture
* Based on MVC pattern, each page corresponds to a controller, which defines different functions in it.
* Use undertow framework with Java Servlet to serve the requests and talk to the database.
* MySQL database is deployed in AWS RDS to hold the data.

## Getting Started
Simply clone the Product-Listing-App repository and install the dependencies:
### Prerequisites
You need git to clone the repository. You can get it from <a href="https://git-scm.com/">here</a>.

### Clone Product-Listing-App
```
git clone https://github.com/dibugger/Product-Listing-App.git
cd Product-Listing-App
```
### Install dependencies
* Install npm, <a href="https://www.npmjs.com/">the Node package manager</a>.
* After that, simply run:
```
npm install
```
* You should have the <a href="https://en.wikipedia.org/wiki/Java_Development_Kit">Java Development Kit (JDK)</a> installed on your machine.
* Install <a href="https://maven.apache.org/install.html">Maven</a> in your machine.

### Run the application
#### Run Frontend Service
```
cd FrontendService
npm start
```
#### Run Backend Service
Open a new terminal, navigate to the Product-Listing-App folder and run the commands:
```
cd BackendService
sudo mvn clean compile
sudo mvn exec:java
```
The backend service is listing to the port 9000 in localhost, you should spare the port for the backend. Since the backend service is running in localhost:9000 and the frontend service is running in localhost:8000, we recommend you to use **Chrome browser** with <a href="https://chrome.google.com/webstore/detail/allow-control-allow-origi/nlfbmbojpeacfghkpbjhddihlkkiljbi">Allow-Control-Allow-Origin Plugin</a> to avoid the communication problem between the services. Now go to localhost:8000/index.html to play with the app!

# RAG with Spring, Java, and MongoDB Atlas

This project demonstrates how to build a **Retrieval-Augmented Generation (RAG)** search application using **Spring Java** and **MongoDB Atlas**. The application stores and queries data using MongoDB's vector store for semantic search. It exposes REST APIs that allow users to add and search for recipes, but the architecture can easily be adapted to a variety of use cases.

## Features

- **Add Recipe**: Allows users to add new recipes to the vector store using the `addRecipe` REST API.
- **Search Recipes**: Users can search for similar recipes using the `getRecipe` REST API.
- **MongoDB Atlas Integration**: The project uses MongoDB Atlas for storing vectorized data and performing semantic search operations.
- **Scalable Architecture**: The architecture is flexible and can be extended to other domains such as book search, movie recommendations, and more.

## Technologies Used

- **Spring Boot**: Framework for building the back-end REST API.
- **MongoDB Atlas**: Cloud-based NoSQL database to store vectorized data and perform semantic search.
- **Java**: Core programming language used for application development.
- **Vector Search**: MongoDB is used for storing high-dimensional vectors and performing similarity search.

## Getting Started

### Prerequisites

- **MongoDB Atlas**: You need a MongoDB Atlas account to set up your cloud database.
- **Java**: Make sure you have Java 8 or higher installed.
- **Spring Boot**: The project is built with Spring Boot, so ensure you have it set up locally or use Spring Initializr to create a new Spring Boot project.

### Setting up the Database

1. Create a MongoDB Atlas cluster and get the connection string:
    - Go to MongoDB Atlas and set up a cluster.
    - Click on **Get Connection String** to copy the connection details for your project.

2. Use this connection string in your Spring application to connect to MongoDB. Make sure you configure the connection settings properly using **MongoTemplate**.

### Running the Application

1. Clone the repository:
   ```bash
   git clone <repository-url>
   cd <project-directory>

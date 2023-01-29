<a name="readme-top"></a>

[![Contributors][contributors-shield]][contributors-url]
[![Forks][forks-shield]][forks-url]
[![Stargazers][stars-shield]][stars-url]
[![Issues][issues-shield]][issues-url]
[![LinkedIn][linkedin-shield]][linkedin-url]

<!-- PROJECT LOGO -->
<br />
<div align="center">
  <a href="https://github.com/andrsdt/no-time-for-heroes">
    <img src="./react/public/images/logo_ntfh.png" alt="Logo" width="400">
  </a>

  <h3 align="center">No Time for Heroes</h3>

  <p align="center">
    The board game, now online!
    <br />
    <br />
    <a href="https://github.com/andrsdt/no-time-for-heroes/issues">Report Bug</a>
    ·
    <a href="https://github.com/andrsdt/no-time-for-heroes/issues">Request Feature</a>
  </p>
</div>

<!-- TABLE OF CONTENTS -->
<details>
  <summary>Table of Contents</summary>
  <ol>
    <li>
      <a href="#about-the-project">About The Project</a>
      <ul>
        <li><a href="#built-with">Built With</a></li>
      </ul>
    </li>
    <li>
      <a href="#getting-started">Getting Started</a>
      <ul>
        <li><a href="#server">Server</a></li>
        <li><a href="#client">Client</a></li>
      </ul>
    </li>
    <li><a href="#contact">Contact</a></li>
  </ol>
</details>

<!-- ABOUT THE PROJECT -->

## About The Project

![Gameplay showcase](https://i.imgur.com/oe6Ab5N.gif)

No Time for Heroes is a card game in which each participant takes the role of an archetypal hero with the goal of being the one who gets the most points by eliminating orcs. The project arises as a way to learn and practice the technologies used in the development of a web application, being the main project of the subject "Design and Testing I" of the Software Engineering degree at the University of Seville.

The project is divided into two parts, the server and the client. The server is a REST API developed in Java with Spring Boot, which is responsible for managing the game logic and the database. The client is a web application developed in React.js, which is responsible for the user interface and the communication with the server.

The development has been carried out in an agile way, using the Scrum methodology. It has been developed in a team of 5 people, with the roles of Product Owner, Scrum Master and 3 developers.

<p align="right">(<a href="#readme-top">back to top</a>)</p>

### Built With

#### Server

- ![Java](https://img.shields.io/badge/java-ed8b00?style=for-the-badge&logo=openjdk&logocolor=white)
- ![Spring Boot](https://img.shields.io/badge/Spring-6DB33F?style=for-the-badge&logo=spring&logoColor=white)
- ![JUnit](https://img.shields.io/badge/Junit-323330?style=for-the-badge&logo=junit)
- ![Maven](https://img.shields.io/badge/maven-c71a36?style=for-the-badge&logo=apache-maven&logocolor=white)
- ![MySQL](https://img.shields.io/badge/mysql-00000f?style=for-the-badge&logo=mysql&logocolor=white)
- ![Hibernate](https://img.shields.io/badge/Hibernate-59666C?style=for-the-badge&logo=Hibernate&logoColor=white)

#### Client

- ![HTML](https://img.shields.io/badge/HTML-239120?style=for-the-badge&logo=html5&logoColor=white)
- ![TailwindCSS](https://img.shields.io/badge/Tailwind_CSS-38B2AC?style=for-the-badge&logo=tailwind-css&logoColor=white)
- ![Node.js](https://img.shields.io/badge/node.js-43853d?style=for-the-badge&logo=node.js&logoColor=white)
- ![React](https://img.shields.io/badge/react-20232a?style=for-the-badge&logo=react&logocolor=61dafb)

#### Other tools

- ![Git](https://img.shields.io/badge/git-F05032?style=for-the-badge&logo=git&logoColor=white)
- ![GitHub](https://img.shields.io/badge/github-181717?style=for-the-badge&logo=github&logocolor=white)
- ![GitHub Actions](https://img.shields.io/badge/GitHub_Actions-2088FF?style=for-the-badge&logo=github-actions&logoColor=white)
- ![Visual Studio Code](https://img.shields.io/badge/Visual_Studio_Code-007ACC?style=for-the-badge&logo=visual-studio-code&logoColor=white)
- ![Postman](https://img.shields.io/badge/Postman-FF6C37?style=for-the-badge&logo=postman&logoColor=white)

<p align="right">(<a href="#readme-top">back to top</a>)</p>

<!-- GETTING STARTED -->

## Getting Started

As this application is split in two services (spring-boot for backend and database and react for frontend), we need to initialize both services individually.

### Server

From the source folder, navigate to the spring-boot directory:

```
cd ./spring-boot/
```

Now we execute the service with the appropiate maven command:

```
mvn spring-boot:run
```

From here on out, the spring-boot proyect will be located at localhost:8080, with the console at localhost:8080/h2-console.
To access the console follow this image:
![console-tutorial](https://i.imgur.com/rQuYCm4.png)

### Client

Now, for the client. Similar to spring-boot, we navigate to the `/react` directory:

```
cd ./react/
```

Now we execute the service with the appropiate nodejs command:

```
npm run build
```

After that, we will se a message like this one:

```
The build folder is ready to be deployed.
You may serve it with a static server:

  npm install -g serve
  serve -s build
```

Following these steps, we will see the last message, telling us that everything was OK:

```
   ┌──────────────────────────────────────────────────┐
   │                                                  │
   │   Serving!                                       │
   │                                                  │
   │   - Local:            http://localhost:3000      │
   │   - On Your Network:  http://192.168.1.48:3000   │
   │                                                  │
   │   Copied local address to clipboard!             │
   │                                                  │
   └──────────────────────────────────────────────────┘
```

The application is now ready to run. Enjoy!

<p align="right">(<a href="#readme-top">back to top</a>)</p>

<!-- CONTACT -->

## Contact

Andrés Durán - [@andresdt](https://twitter.com/andresdt) - andresdt@pm.me

Project Link: [https://github.com/andrsdt/no-time-for-heroes](https://github.com/andrsdt/no-time-for-heroes)

<p align="right">(<a href="#readme-top">back to top</a>)</p>

[contributors-shield]: https://img.shields.io/github/contributors/andrsdt/no-time-for-heroes.svg?style=for-the-badge
[contributors-url]: https://github.com/andrsdt/no-time-for-heroes/graphs/contributors
[forks-shield]: https://img.shields.io/github/forks/andrsdt/no-time-for-heroes.svg?style=for-the-badge
[forks-url]: https://github.com/andrsdt/no-time-for-heroes/network/members
[stars-shield]: https://img.shields.io/github/stars/andrsdt/no-time-for-heroes.svg?style=for-the-badge
[stars-url]: https://github.com/andrsdt/no-time-for-heroes/stargazers
[issues-shield]: https://img.shields.io/github/issues/andrsdt/no-time-for-heroes.svg?style=for-the-badge
[issues-url]: https://github.com/andrsdt/no-time-for-heroes/issues
[linkedin-shield]: https://img.shields.io/badge/-LinkedIn-black.svg?style=for-the-badge&logo=linkedin&colorB=555
[linkedin-url]: https://linkedin.com/in/andrsdt

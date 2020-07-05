# The API Boilerplate

The API Boilerplate (TAB) is a pre-configured boilerplate for APIs with the Spring Framework.

# Anatomy

Here's an explanation of the package structure. You can see examples on the source code.

There are 4 main packages for now. Alphabetically, they are:

- `api` Holds everything related to how to interact with the clients.
- `config` Everything that needs configuration goes here. Mostly beans and configurations of libraries.
- `domain` Contains the business code.
- `infrastructure` May contain code related to databases or third-party services, often related to persistence. 

```
.
├── api
│   ├── controller -- Behaviour related to client requests (how to respond).
│   ├── exceptionhandler -- Specifies how to handle exceptions for the API layer.
│   └── model -- Specifies what the client gets from the API, basicallly.
│       ├── input -- Specifies what the client sends to the API, basically.
│       └── mapper -- Maps domain, model and input objects between each other.
├── config
├── domain
│   ├── exception -- Domain exceptions.
│   ├── model -- Specifies the business model, generally what's persisted.
│   ├── repository -- Contracts on how to interact with persisted data.
│   └── service -- Implementations of business rules and code.
├── infrastructure -- Implementations on how to interact with persisted data.
└── TheApiBoilerplateApplication.java -- the main method and other app-wide information/configuration.
```

# Pre-configured libraries

| Library | Details | Version |
| ------- | ------- | ------- |
| Spring Boot | Starters `-web`, `-data-mongodb`, `-validation` | 2.3.1.RELEASE |
| Model Mapper | Maps objects from one type to another (http://modelmapper.org/) | 2.3.0 |
| SpringFox | Generates OpenAPI (Swagger) documentation of the API (https://springfox.github.io/springfox/) | 2.9.2 |
| Lombok | Java compile-time boilerplate code generator (https://projectlombok.org/) | 1.18.12 |
| Apache Commons | I'm using ExceptionUtils for now (http://commons.apache.org/proper/commons-lang/) | 3.10 |

# To-do

- [ ] Add database migration tool 
- [ ] Add security

# v-server

A lightweight HTTP server built using Java 21 virtual threads and socket programming.  
This project supports **static file sharing**, **custom route mapping**, and is designed to be **easily extensible** for other protocols like FTP and WebSocket in the future.

---

## 🚀 Features

- Built from scratch using Java 21 Virtual Threads
- Supports:
  - Static file serving (HTML, CSS, etc.)
  - HTTP methods: `GET`, `POST`, `PUT` (extensible)
  - Route-based configuration via `YAML`
- Highly modular and extensible
- Clean architecture with factory, builder, and singleton design patterns
- Exception-safe and resource-safe (using try-with-resources)
- Built as a **Maven project** with preview features enabled

---

## 📂 Project Structure
```
v-server/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/ij3rry/
│   │   │       ├── temp-app.controller/
│   │   │       ├── vserver/
│   │   │       │   ├── builders/
│   │   │       │   ├── concurrent/
│   │   │       │   ├── data/
│   │   │       │   ├── enums/
│   │   │       │   ├── exceptions/
│   │   │       │   ├── factories/
│   │   │       │   ├── generators/
│   │   │       │   ├── handlers/
│   │   │       │   ├── http/
│   │   │       │   ├── readers/
│   │   │       │   └── utils/
│   │   ├── resources/
│   │   │   ├── http/
│   │   │   │   └── request-mapper.yaml
│   │   │   └── WEB-INF/
│   │   │       └── css/
│   │   │           ├── index.html
│   │   │           └── style.css
├── pom.xml
└── README.md
```

---

## 🧠 Learnings

- Java 21 **Virtual Threads** and how they scale under high concurrency
- Understanding the **HTTP protocol**, request/response lifecycle, headers, etc.
- Low-level **Socket Programming**
- Handling **Input/Output streams**
- Using **try-with-resources** for safe and clean resource management
- Implemented patterns:
  - **Builder Pattern**
  - **Factory Pattern**
  - **Singleton Pattern**
- Proper **exception handling**
- Designing **loosely coupled** and maintainable packages for future protocol support

---

## ⚙️ How to Run

Make sure you're using **Java 21** with `--enable-preview`.

### 🧪 Maven Build

mvn clean install

### ▶️ Starting the Server
ConnectionHandler connectionHandler =
    new ConnectionHandler.ConnectionHandlerBuilder()
        .setPort(8080)                  // default
        .setMaxConcurrentTask(1000)     // default
        .setTimeOutMilliSec(500)        // default
        .build();

connectionHandler.start();

---

### 🗺️ Request Mapping (YAML)
Define supported routes in resources/http/request-mapper.yaml.

Example:

<pre>
http:
  version: 1.1
  routes:
    GET:
      - endpoint: /home
        type: file
        path: WEB-INF/css/index.html
      - endpoint: /style
        type: file
        path: WEB-INF/css/style.css
    POST:
      - endpoint: /upload
        type: controller
        path: com.ij3rry.temp_app.controller.FileUploadController
    PUT:
      - endpoint: /update
        type: controller
        path: com.ij3rry.temp_app.controller.UpdateController
</pre>

---

### 🏗️ Future Plans
Add dynamic response generation via controller classes

Support FTP and WebSocket protocols

Write unit test cases for all components

Optional: CLI tool for route management

---

### 📄 License
This project is open-source and free to use.

---

### ✨ Author
@ij3rry
Happy hacking! 💻

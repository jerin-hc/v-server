# v-server

A lightweight HTTP server built using Java 21 virtual threads and socket programming.  
This project supports **static file sharing**, **custom route mapping**, and is designed to be **easily extensible** for other protocols like FTP and WebSocket in the future.

---

## 🚀 Features

- Built from scratch using Java 21 Virtual Threads
- Supports:
  - Static file serving (HTML, CSS, etc.)
  - HTTP methods: `GET`, `POST`, `PUT`, `PATCH`, `DELETE`
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
├── pom.xml
└── README.md
```

---

## ⚙️ How to Run

Make sure you're using **Java 21** with `--enable-preview`.

### 🧪 Maven Build

mvn clean install


### ▶️ Starting the Server

### pom.xml
    <dependency>
        <groupId>com.ij3rry</groupId>
        <artifactId>v-server</artifactId>
        <version>1.0.0-alpha-1</version>
    </dependency>
### resources/http/request-mapper.yaml
<pre>
http:
  version: 1.1
  routes:
    GET:
      - endpoint: /index
        type: file
        path: /WEB-INF
      - endpoint: /style
        type: file
        path: /WEB-INF/css
    POST:
      - endpoint: /upload
        type: controller
        path: com.example.controller.FileUploadController
    PUT:
      - endpoint: /update
        type: controller
        path: com.example.controller.UpdateController
</pre>
### main()
```
ConnectionHandler connectionHandler =
    new ConnectionHandler.ConnectionHandlerBuilder()
        .setPort(8080)                  // default
        .setMaxConcurrentTask(1000)     // default
        .setTimeOutMilliSec(500)        // default
        .setupHttpServer()              // enable http server
        .build();

connectionHandler.start();
```
### Implement RESTController
```
package com.ij3rry.vserver.http.controller;

import com.ij3rry.vserver.http.data.HttpContext;
import com.ij3rry.vserver.http.data.HttpResponse;

public abstract class RESTController  {
    public HttpResponse doGet(HttpContext context){
        return new HttpResponse();
    }
    public HttpResponse doPost(HttpContext context){
        return new HttpResponse();
    }
    public HttpResponse doPut(HttpContext context){
        return new HttpResponse();
    }
    public HttpResponse doPatch(HttpContext context){
        return new HttpResponse();
    }
    public HttpResponse doDelete(HttpContext context){
        return new HttpResponse();
    }
}
```


### 📄 License
This project is open-source and free to use.

---

### ✨ Author
@ij3rry
Happy hacking! 💻

# v-server

**v-server** is a custom, lightweight Java server built from scratch using **TCP sockets** and **virtual threads** (Project Loom). It’s not a servlet container — it's a raw HTTP/1.1 server meant to explore modern concurrency with a minimal footprint.

## ⚙️ Tech Stack

- Java 21+ (with `--enable-preview`)
- Virtual Threads (Project Loom)
- TCP Socket-based networking
- Maven for build automation

---

## 🚀 Current Features

- ✅ Built from scratch with **Java Virtual Threads**
- ✅ Basic **HTTP/1.1 request handling**
- ❌ No servlet container or framework dependencies
- 🧪 In active development — the core server loop and handler are working

---

## 📁 Project Structure

```
v-server/
├── src/
│   └── main/
│       └── java/
│           └── com/
│               └── ij3rry/
│                   └── vserver/
│                       └── App.java
├── pom.xml
└── README.md
```

> `App.java` is the main entry point.

---

## 🛠️ Running the Server

To run the project:

```bash
mvn clean compile exec:java -Dexec.mainClass="com.ij3rry.vserver.App" -Dexec.args="" -Dexec.jvmArgs="--enable-preview"
```

Or package and run:

```bash
mvn package
java --enable-preview -cp target/v-server-1.0-SNAPSHOT.jar com.ij3rry.vserver.App
```

> ✅ Make sure you're using **JDK 21 or later**.

---

## 🧪 Test It

After running:

```bash
curl http://localhost:8080
```

---

## 📅 Roadmap

- [x] TCP socket server loop
- [x] HTTP/1.1 parsing (basic)
- [ ] Status codes & response headers
- [ ] Routing support
- [ ] Content-Type detection
- [ ] Static file serving
- [ ] Logging & debugging options
- [ ] HTTP/2 or QUIC (experimental)

---

## 📄 License

MIT

---

## 👨‍💻 Author

Built by [ij3rry] — exploring the power of **virtual threads** and low-level server design.


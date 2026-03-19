# SDIS P2 Module 3: Secure RMI (SSL/TLS) & Streaming Auditing

<div align="center">
  
  ![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=java&logoColor=white)
  ![Secure RMI](https://img.shields.io/badge/Architecture-Secure_RMI-blue?style=for-the-badge)
  ![SSL/TLS](https://img.shields.io/badge/Security-SSL%2FTLS-success?style=for-the-badge)
  ![TCP Sockets](https://img.shields.io/badge/Streaming-TCP_Sockets-red?style=for-the-badge)
  ![Auditing](https://img.shields.io/badge/Logging-File_Auditing-yellow?style=for-the-badge)
  ![Academic](https://img.shields.io/badge/Academic_Project-2nd_Year-purple?style=for-the-badge)

</div>

> **ABOUT THIS MODULE:**
> This repository contains the final evolution (Module 3) of our Distributed Spotify architecture. It builds upon the Hybrid Streaming System from Module 2 by introducing **End-to-End Encryption (SSL/TLS)** for all RMI Control Plane communications and Callbacks. It also implements a **Persistent Auditing System** to log TCP streaming events. 
> 
> ⚠️ *Note: If you want to test the streaming architecture without generating SSL certificates, please switch to the `module-2` branch.*

<div align="center">
  <img width="400" src="https://github.com/user-attachments/assets/15393849-b703-4f3a-b168-1e9007b9dcd7" />
</div>

---

## Architecture & Key Features

* **Secure RMI (SSL/TLS):** The entire control plane (metadata fetching, playlist management, and remote triggers) is secured using `SslRMIClientSocketFactory` and `SslRMIServerSocketFactory`.
* **Encrypted Callbacks:** The server securely invokes methods on the client (like launching the media player) using bi-directional SSL handshakes.
* **Persistent Event Logging:** Implements a robust `Utils` logger that records stream connections, TCP client IPs, and total transmitted bytes to a physical `streams.txt` file for server auditing.
* **Auto-Discovery & Preloading (Bonus):** The server dynamically scans the `mp3files/origin` and `jpgfiles/` directories on startup, automatically preloading all available media files and their respective covers without manual client population.
* **Network-Ready:** The system explicitly defines the `java.rmi.server.hostname` property, preventing `BindExceptions` and allowing deployment across different physical machines on the same LAN.

---

## Project Structure

```text
Distributed-Spotify-Java-RMI
 ┣ 📂 jpgfiles/
 ┣ 📂 logs/
 ┃ ┗ 📜 streams.txt
 ┣ 📂 mp3files/
 ┃ ┣ 📂 destination/
 ┃ ┗ 📂 origin/
 ┗ 📂 src/spotify/
   ┣ 📂 media/
   ┃ ┣ 📜 Globals.java
   ┃ ┣ 📜 Media.java
   ┃ ┗ 📜 MediaPlayer.java
   ┣ 📂 rmi/
   ┃ ┣ 📂 client/
   ┃ ┃ ┣ 📜 SpotifyClientImpl.java
   ┃ ┃ ┗ 📜 SpotifyStreamingClient.java
   ┃ ┣ 📂 common/
   ┃ ┃ ┣ 📜 ServerMessages.java
   ┃ ┃ ┣ 📜 Spotify.java
   ┃ ┃ ┣ 📜 SpotifyClient.java
   ┃ ┃ ┗ 📜 SpotifyServer.java
   ┃ ┗ 📂 server/
   ┃   ┣ 📜 SpotifyLauncher.java
   ┃   ┗ 📜 SpotifyServerImpl.java
   ┣ 📂 stream/
   ┃ ┣ 📜 ClientStream.java
   ┃ ┗ 📜 ServerStream.java
   ┗ 📂 utils/
     ┣ 📜 ConcurrentMultiMap.java
     ┣ 📜 MediaDirectory.java
     ┗ 📜 Utils.java
```

## Getting Started
### 1. Generate the SSL Certificate (Mandatory)
Before compiling or running, you must generate a dummy Keystore for the SSL Handshake. Open a terminal in the root directory and run:

```bash
keytool -genkey -noprompt -alias spotify -dname "CN=localhost" -keyalg RSA -keystore keystore.jks -storepass 123456 -keypass 123456
(Ensure the keystore.jks file is present in the execution directory for both Server and Client).
```

### 2. Compilation
Compile the entire project from your src directory:

```bash
javac spotify/common/*.java spotify/media/*.java spotify/utils/*.java spotify/server/*.java spotify/client/*.java spotify/stream/*.java
```

### 3. Execution
Start the Secure Server:
The server will bind the secure registry and start logging to ./logs/streams.txt.

```bash
java spotify.server.SpotifyLauncher
```

Start the Secure Client:
```bash
java spotify.client.SpotifyStreamingClient
```

Interaction Example (Server Auditing)
When a client streams a song, the server console and the logs/streams.txt file will register:

```bash
📝 DEBUG LOGS: Escrito registro en -> .../logs/streams.txt
[2024-05-20 18:30:15]  [STREAM] Streaming to: /127.0.0.1
[2024-05-20 18:30:16]  [STREAM] Stream finished. Tx Bytes: 242449
```

### Authors

Iván Moro Cienfuegos, David Martín Sebastián, Eric Soto San José y Héctor

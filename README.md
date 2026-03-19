# SDIS P2 Module 2: Spotify Streaming, Callbacks & OS Integration

<div align="center">
  
  ![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=java&logoColor=white)
  ![RMI](https://img.shields.io/badge/Architecture-Java_RMI-blue?style=for-the-badge)
  ![TCP Sockets](https://img.shields.io/badge/Streaming-TCP_Sockets-red?style=for-the-badge)
  ![Callbacks](https://img.shields.io/badge/Logic-RMI_Callbacks-yellow?style=for-the-badge)
  ![Concurrency](https://img.shields.io/badge/Concurrency-Thread_Safe-orange?style=for-the-badge)
  ![Academic](https://img.shields.io/badge/Academic_Project-2nd_Year-purple?style=for-the-badge)

</div>

> **ABOUT THIS MODULE:**
> This repository contains Module 2 of the second Distributed Systems (SDIS) practice. It evolves our RMI architecture by implementing a **Hybrid Streaming System**. While RMI continues to handle control logic and metadata, we have integrated a high-speed **Asynchronous TCP Socket** layer to handle the transmission of binary media files (.mp3). Additionally, it features **RMI Callbacks** to allow the server to trigger actions on the client side, such as launching the OS Media Player automatically.

---

## Architecture & Key Features

This module bridges the gap between remote procedure calls and real-time data transmission:

* **Hybrid RMI-Socket Architecture:** Uses RMI for the "Control Plane" (requesting songs, managing metadata) and raw TCP Sockets for the "Data Plane" (streaming the actual bytes).
* **Asynchronous Streaming (Multi-threading):** * `ServerStream`: A dedicated thread on the server that pushes bytes from the `origin` folder.
    * `ClientStream`: A background thread on the client that receives and reconstructs the file in the `destination` folder.
* **RMI Callbacks:** The server holds a remote reference to the client (`SpotifyClient` interface). This allows the server to proactively tell the client to start its stream receiver and launch the local Media Player.
* **Dynamic Path Sabueso (Search Dog):** Implements a robust `findBaseDir()` logic in `Globals.java` to automatically detect the project structure, ensuring the server finds the `mp3files` and `jpgfiles` even when nested inside subfolders (like `Practica2/`).
* **OS & Desktop Integration:** Uses `java.awt.Desktop` for cross-platform media playback, triggering the default OS player once the stream buffer is ready.
* **Robust Metadata Management:** Enhanced `Media` DTO that supports binary image serialization via `ImageIO` and `ImageIcon` to ensure covers are transmitted correctly over RMI.

---

## Project Structure

```text
ProjectRoot/
 ┣ 📂 jpgfiles/
 ┣ 📂 mp3files/
 ┃ ┣ 📂 destination/
 ┃ ┗ 📂 origin/
 ┣ 📂 src/spotify/
 ┃ ┣ 📂 media/
 ┃ ┃ ┣ 📜 Globals.java
 ┃ ┃ ┣ 📜 Media.java
 ┃ ┃ ┗ 📜 MediaPlayer.java
 ┃ ┣ 📂 rmi/
 ┃ ┃ ┣ 📂 client/
 ┃ ┃ ┃ ┣ 📜 SpotifyClientImpl.java
 ┃ ┃ ┃ ┗ 📜 SpotifyStreamingClient.java
 ┃ ┃ ┣ 📂 common/
 ┃ ┃ ┃ ┣ 📜 ServerMessages.java
 ┃ ┃ ┃ ┣ 📜 Spotify.java
 ┃ ┃ ┃ ┣ 📜 SpotifyClient.java
 ┃ ┃ ┃ ┗ 📜 SpotifyServer.java
 ┃ ┃ ┗ 📂 server/
 ┃ ┃   ┣ 📜 SpotifyLauncher.java
 ┃ ┃   ┗ 📜 SpotifyServerImpl.java
 ┃ ┣ 📂 stream/
 ┃ ┃ ┣ 📜 ClientStream.java
 ┃ ┃ ┗ 📜 ServerStream.java
 ┃ ┗ 📂 utils/
 ┃   ┣ 📜 ConcurrentMultiMap.java
 ┃   ┣ 📜 MediaDirectory.java
 ┃   ┗ 📜 Utils.java
 ┗ 📜 README.md
```

## Getting Started
### 1. Compilation
Navigate to the root directory containing your spotify package and compile:

```bash
javac spotify/common/*.java spotify/media/*.java spotify/utils/*.java spotify/server/*.java spotify/client/*.java spotify/stream/*.java
```

### 2. Execution
Start the Streaming Server:
The server will scan the mp3files/origin folder and bind the RMI service.

```bash
java spotify.server.SpotifyLauncher
```

Start the Streaming Client:
The client will connect to the server and display the interactive menu.

```bash
java spotify.client.SpotifyStreamingClient
```

Interaction Example
Client Terminal:

```bash
--- STREAMING MENU ---
1. Play a specific song
2. Play a random song
3. View Song Info & Cover
4. Edit Metadata (Comment, Score, Like, Cover)
5. Exit
Select an option: 1
Enter song name: Pepe
MUSIC PLAYER: Waiting 1s for the song download...
SERVER: MEDIA Pepe started
```

Server Logs:

```bash
SERVER: Scanning origin folder...
 -> Added to directory: Pepe [With cover]
==========================================
SPOTIFY STREAMING SERVER READY
==========================================
SERVER STREAM: Listening port 61568
SERVER STREAM: ¡Client connected! Opening Imput Stream of MP3...
SERVER STREAM: End of transmission. Total sent: 242449 bytes.
```

### Authors
-Iván Moro Cienfuegos, David Martín Sebastián, Eric Soto San José and Héctor

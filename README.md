# SDIS P2 Module 1: Distributed Spotify via Java RMI

<div align="center">
  
  ![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=java&logoColor=white)
  ![RMI](https://img.shields.io/badge/Architecture-Java_RMI-blue?style=for-the-badge)
  ![Serialization](https://img.shields.io/badge/Data-Object_Serialization-success?style=for-the-badge)
  ![Concurrency](https://img.shields.io/badge/Concurrency-Thread_Safe-orange?style=for-the-badge)
  ![Academic](https://img.shields.io/badge/Academic_Project-2nd_Year-purple?style=for-the-badge)

</div>

> **ABOUT THIS MODULE:**
> This repository contains Module 1 of the second Distributed Systems (SDIS) practice. It evolves our previous TCP Socket architecture into a fully distributed Object-Oriented system using **Java Remote Method Invocation (RMI)**. The goal is to provide location transparency, allowing the client application to invoke methods on remote server objects exactly as if they were local.

---

## Architecture & Key Features

Transitioning from raw TCP sockets to RMI completely abstracts the network layer. This module simulates a Spotify-like backend with the following enterprise-grade features:

* **Location Transparency (RMI):** Clients interact with the `Spotify` Java interface without worrying about underlying TCP handshakes, marshaling, or unmarshaling of data.
* **Programmatic RMI Registry:** The server dynamically creates and binds itself to the RMI Registry on port `1099` using `LocateRegistry.createRegistry()`, removing the need for external terminal setups.
* **Thread-Safe State Management:** * Uses `ConcurrentHashMap` for user authentication and the master media directory.
  * Implements a custom `ConcurrentMultiMap` (powered by non-blocking `ConcurrentLinkedQueue`) to handle concurrent playlist modifications safely.
* **Rich Object Serialization:** The `Media` DTO encapsulates complex state (comments, likes, score, adult content flags, and images). It strictly implements `Serializable` for seamless transmission across the JVM boundary.
* **Unified Interactive Client:** Replaced multiple fragmented test scripts with a single, robust `SpotifyRmiClient` that handles all testing scenarios (Authentication, Adding, Reading, Draining, and Metadata enrichment) via a clean CLI menu.

---

## Project Structure

```text
src/sdis/spotify/
 ┣ 📂 client
 ┃ ┗ 📜 SpotifyRmiClient.java      # Unified interactive CLI client
 ┣ 📂 common
 ┃ ┣ 📜 Spotify.java               # Remote Interface defining the RMI contract
 ┃ ┗ 📜 ServerMessages.java        # Centralized string constants
 ┣ 📂 media
 ┃ ┗ 📜 Media.java                 # Serializable Data Transfer Object (DTO)
 ┣ 📂 server
 ┃ ┣ 📜 SpotifyLauncher.java       # RMI Registry initialization and Server binding
 ┃ ┗ 📜 SpotifyImpl.java           # Remote interface implementation (UnicastRemoteObject)
 ┗ 📂 utils
   ┣ 📜 ConcurrentMultiMap.java    # Thread-Safe generic Queue mapping for playlists
   ┗ 📜 MediaDirectory.java        # Thread-Safe global media registry
```

## Getting Started
### 1. Compilation
Navigate to the root directory containing your sdis folder and compile the entire project structure:

```bash
javac sdis/spotify/common/*.java sdis/spotify/media/*.java sdis/spotify/utils/*.java sdis/spotify/server/*.java sdis/spotify/client/*.java
```

### 2. Execution
Start the RMI Server:
Open a terminal and launch the server. It will automatically start the RMI registry on port 1099 and bind the id1 service.

```bash
java sdis.spotify.server.SpotifyLauncher
```

Start the Interactive Client:
Open a new terminal. You can pass the server IP as an argument (defaults to localhost).

```bash
java sdis.spotify.client.SpotifyRmiClient localhost
```

Interaction Example
Client Terminal:

```bash
CLIENT: Looking up RMI registry at localhost...
SERVER: Welcome to 
░██████╗██████╗░░█████╗░████████╗██╗███████╗██╗░░░██╗
██╔════╝██╔══██╗██╔══██╗╚══██╔══╝██║██╔════╝╚██╗░██╔╝
╚█████╗░██████╔╝██║░░██║░░░██║░░░██║█████╗░░░╚████╔╝░
░╚═══██╗██╔═══╝░██║░░██║░░░██║░░░██║██╔══╝░░░░╚██╔╝░░
██████╔╝██║░░░░░╚█████╔╝░░░██║░░░██║██║░░░░░░░░██║░░░
╚═════╝░╚═╝░░░░░░╚════╝░░░░╚═╝░░░╚═╝╚═╝░░░░░░░░╚═╝░░░

--- RMI SPOTIFY MENU ---
1. Login (Auth)
2. Add Song to Playlist (Add2L)
3. Read Song from Playlist (ReadL)
4. Delete Playlist (DeleteL)
5. Continuous Read (Drain Playlist)
6. Explore Metadata (Add Comments/Score)
7. View Master Directory
8. Exit
Select an option: 6
Target Internal Song Name (e.g., bohemian_rhapsody): bohemian_rhapsody
Add Comment: Masterpiece!
Server: Comment successfully added.
Add Score (1-10): 10
Server: Score successfully registered.
```

### Authors

-Iván Moro Cienfuegos, David Martín Sebastián, Eric Soto San José and Héctor

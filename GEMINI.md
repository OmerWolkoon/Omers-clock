# Gemini CLI Instructional Context: Omer's Clock

## Project Overview
**Omer's Clock** is a Minecraft mod built using the **Fabric** modding toolchain for Minecraft version **1.21.11**. It serves as a template or example mod, demonstrating basic mod initialization, client-side setup, and Mixin usage.

- **Mod ID:** `omers-clock`
- **Version:** `1.0.0`
- **Core Technologies:** Java 21, Fabric Loader, Fabric API, Gradle, Mixin (SpongePowered).
- **Architecture:**
    - **Main Entry Point (`OmersClock.java`):** Initializes the mod and logs the current local time.
    - **Client Entry Point (`OmersClockClient.java`):** Reserved for client-specific logic (rendering, input, etc.).
    - **Mixins:** Modifies `MinecraftServer.loadLevel` via `ExampleMixin`.

## Features
- **Real World Clock HUD:** Displays the current time (HH:mm:ss) in the top-left corner of the screen with a sleek translucent background.
- **Toggle Visibility:** Use the in-game command `/clock-toggle` to show or hide the clock.

## Building and Running
This project uses the Fabric Loom Gradle plugin for development.

- **Build Mod:**
  ```powershell
  ./gradlew build
  ```
  The resulting JARs are located in `build/libs/`.

- **Run Minecraft Client (with mod):**
  ```powershell
  ./gradlew runClient
  ```

- **Run Minecraft Server (with mod):**
  ```powershell
  ./gradlew runServer
  ```

- **Generate Sources JAR:**
  ```powershell
  ./gradlew sourcesJar
  ```

- **Clean Build Directory:**
  ```powershell
  ./gradlew clean
  ```

## Development Conventions
- **Naming:** Follows standard Java camelCase for methods/variables and PascalCase for classes.
- **Logging:** Uses SLF4J logger via `OmersClock.LOGGER`. Best practice is to prefix log messages with the mod ID.
- **Mixins:** Mixins are located in `com.omer.mixin` and configured in `src/main/resources/omers-clock.mixins.json`.
- **Resources:** Assets (icons, models, textures) should be placed in `src/main/resources/assets/omers-clock/`.
- **Environment:** The mod supports both logical client and server environments (`"environment": "*"` in `fabric.mod.json`).

## Key Files
- `build.gradle`: Project build script and dependencies.
- `gradle.properties`: Versioning and Fabric-specific properties.
- `src/main/resources/fabric.mod.json`: Mod metadata and entry points.
- `src/main/java/com/omer/OmersClock.java`: Main initialization class.
- `src/main/java/com/omer/mixin/ExampleMixin.java`: Example of modifying Minecraft server logic.

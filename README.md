# Kinetiq

**Fluid streaming, limitless extensions.**

Kinetiq is a modular media platform designed for Meta Quest, combining an immersive media player, an extensible source engine, and a personal media library.

## Product family

- **Kinetiq** — the platform and ecosystem.
- **Kinetiq Player** — the application. *Motion. Media. Modular.*
- **Kinetiq Engine** — the source, discovery, metadata, and extension framework. *Your streams, your modules, your rules.*

## Phase 1 status

This repository now contains the first native Android application foundation:

- Kotlin + Jetpack Compose single-activity app.
- Resizable Horizon OS 2D panel configuration for Quest 3.
- Adaptive light/dark theme with System / Light / Dark preference.
- Theme preference persisted locally with DataStore.
- Horizon OS runtime detection with Android fallback.
- Central structured logging boundary.
- Honest placeholder states for features scheduled in later roadmap phases.
- Unit test and Android lint tasks.
- GitHub Actions workflow that builds and uploads a debug APK.

Playback, source discovery, module execution, and personal library functionality are not implemented by this foundation build.

## Build workflow

The project is designed around the iPhone + Working Copy workflow:

1. Edit files in Working Copy.
2. Commit and push to `main`.
3. GitHub Actions runs tests, lint, and the debug APK build.
4. Download the `Kinetiq-Quest-Debug` artifact from the workflow run.
5. Install the APK on Meta Quest 3 and verify it in-headset.

The workflow installs Gradle 9.5.0 and generates the wrapper in CI. This avoids requiring a local Gradle or Android Studio installation for the repository owner.

## Platform baseline

- Quest target: Meta Quest 3.
- Android min SDK: 32.
- Android target SDK: 34.
- Android compile SDK: 37.
- JDK: 17.
- AGP: 9.3.0.
- Gradle: 9.5.0.
- Kotlin: 2.3.21.
- Compose BOM: 2026.08.00.

See `docs/PHASE0_TECHNICAL_AUDIT.md` for rationale, compatibility notes, dependency decisions, and open verification gates.

## Roadmap

The full master plan is in [docs/ROADMAP.md](docs/ROADMAP.md). A checked item means the decision/documentation or initial implementation exists; it does not mean the feature has shipped. Device verification remains explicit.

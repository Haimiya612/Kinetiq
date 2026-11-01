# Kinetiq Architecture

**Status:** proposed architecture; not yet implemented.

## Platform direction

- Kotlin for application and domain code.
- Jetpack Compose for Android-facing UI.
- AndroidX Media3 / ExoPlayer for playback.
- Quest-specific immersive presentation behind platform boundaries.
- Room for structured local data; DataStore for preferences.
- WorkManager only for suitable deferrable work.

Minimum SDK, Horizon OS requirements, dependency versions, and spatial SDK choice must be verified against current platform documentation before project generation.

## Logical component map

```text
Kinetiq Player
├── app-shell              Navigation, lifecycle, dependency wiring
├── design-system          Adaptive themes, typography, components
├── feature-home           Home and continue watching
├── feature-search         Search and filters
├── feature-details        Metadata and episodic navigation
├── feature-player         Playback controls and player state
├── feature-cinema         Quest screen and environment presentation
├── feature-library        History, lists, favourites, profiles
└── feature-settings       Privacy, modules, playback, accessibility

Kinetiq Engine
├── engine-api             Stable internal contracts and domain models
├── engine-discovery       Search orchestration and result normalization
├── engine-metadata        Metadata mapping and deduplication
├── engine-modules         Module lifecycle and execution boundary
├── adapter-synthetiq      Compatibility with verified Synthetiq contract
├── adapter-native         Native Kinetiq module format
├── engine-catalogue       Repository and catalogue models
└── engine-playback        Playback orchestration and media descriptors

Platform / Data
├── data-local             Room, DataStore, local file access
├── engine-network         HTTP clients and connection diagnostics
└── platform-quest         Quest-specific capabilities and immersive surfaces
```

This is a logical map, not a requirement to create every Gradle module immediately. Begin with a maintainable project and extract modules when boundaries are proven.

## Adapter-first module architecture

The internal Kinetiq Engine API must not mirror one external module format. Each external format is translated into stable Kinetiq domain types:

```text
Synthetiq package ──> Synthetiq adapter ──┐
                                          ├──> Kinetiq Engine API
Native Kinetiq module ──> Native adapter ─┘
```

Discovery, metadata, library, and playback features consume only Kinetiq domain contracts.

## Core data flow

1. UI sends a typed request to a feature layer.
2. Discovery queries enabled module adapters.
3. Module output is validated and normalized.
4. UI displays results with source attribution.
5. User selects an item and a source.
6. Adapter resolves a validated playback descriptor.
7. Playback hands the descriptor to Media3.
8. Playback progress is persisted locally.

The UI must never execute module scripts directly. Module output is untrusted data.

## Suggested domain concepts

- `MediaItem`: stable identity, title, artwork reference, metadata.
- `MediaSource`: module/repository identity and source-specific item identity.
- `EpisodeGroup` / `Episode`: optional episodic structure.
- `PlayableDescriptor`: validated URI, MIME hint, permitted request metadata, subtitle/audio information, expiry where supplied.
- `PlaybackProgress`: position, duration, completion state, update time.
- `ModuleDescriptor`: identity, version, capabilities, permissions, provenance.

Never log credentials, cookies, or complete signed media URLs.

## Playback boundary

The Player owns user controls and presentation state. A playback service/session owns playback lifecycle as required by Android. Media3 handles decoding and supported streaming protocols. Quest cinema presentation controls spatial display without duplicating playback logic.

## Quest boundary

Quest-only APIs remain behind interfaces so core logic can be tested without Quest hardware. Immersive features must fail gracefully when unavailable.

## Reliability

- Typed errors; no swallowed exceptions.
- Bounded retries and cancellation.
- Explicit loading, empty, unavailable, and failure states.
- No network work on the main thread.
- Redacted structured logs.
- Unit tests for parsing, mapping, state transitions, and persistence.

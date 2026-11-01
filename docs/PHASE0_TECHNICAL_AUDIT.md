# Phase 0 — Technical audit and decisions

**Audit date:** 2026-11-01  
**Status:** Decisions recorded; CI and physical-device verification remain open.

This document records evidence-based platform decisions. A documented decision is not the same as a shipped or device-verified feature.

## Target platform

- Initial device: Meta Quest 3.
- App model: standard Android application displayed as a resizable 2D Horizon OS panel.
- Android `minSdk`: 32.
- Android `targetSdk`: 34.
- Android `compileSdk`: 37.
- Quest manifest device declaration: `quest3` only.
- Initial window: 1024 × 640 dp; minimum 360 × 225 dp.
- No explicit Meta VR OS SDK minimum/target is declared in Phase 1 because the app does not call version-gated Horizon OS APIs directly.
- Horizon OS detection uses Meta's `com.meta.metavrx.util:util:1.0.0` library. Detection failure falls back to standard Android behavior.

Rationale:
- Meta's current release guidance recommends Android API 32 minimum and API 34 target for Quest 3-family apps.
- Apps created since 2026-03-01 must target API 34.
- Quest 3 was first supported from Horizon OS v56. The `quest3` supported-device declaration therefore establishes Quest 3 as the initial headset target.
- Meta distinguishes Android API levels from Meta VR OS SDK versions. They must not be treated as interchangeable.
- Standard 2D panels are the lowest-risk starting point. Spatial windows, immersive environments, and mixed reality remain later-phase capabilities.

## Build toolchain

Pinned versions for the initial foundation:

| Component | Version | Use |
|---|---:|---|
| JDK | 17 | CI runtime and Java bytecode target |
| Android Gradle Plugin | 9.3.0 | Android build |
| Gradle | 9.5.0 | Build engine |
| Kotlin | 2.3.21 | Application language |
| Compose BOM | 2026.08.00 | Compose dependency alignment |
| Activity Compose | 1.13.0 | Compose activity integration |
| Lifecycle | 2.11.0 | Lifecycle-aware state collection |
| DataStore Preferences | 1.3.0 | Local settings |
| Media3 / ExoPlayer | 1.11.1 | Phase 2 playback (version pinned; not yet included in the app runtime) |
| Room | 2.8.5 | Structured library/history data (version pinned; deferred until required) |
| Meta VR OS Utility | 1.0.0 | Horizon OS runtime detection |
| JUnit | 4.13.2 | Unit tests |

All versions are pinned; dynamic versions are not used. Media3 is intentionally deferred to Phase 2. Room is deferred until structured library/history data is implemented.

## Synthetiq compatibility research

The public Synthetiq module guide documents the following contract:

- Package: ZIP containing a module directory, JSON manifest, and `index.js`.
- Contract version: 3.
- Runtime entry: `index.js`.
- Runtime mode: `local`.
- Manifest identity fields include `id`, `name`, `contentType`, `contractVersion`, `moduleVersion`, `moduleFamilyId`, `moduleIdentity`, and `moduleIdentityNumber`.
- Runtime capability limits include `homeMaxResults`, `maxResponseBytes`, `timeoutMs`, and `maxConcurrentRequests`.
- Video handlers: `searchResults`, `extractDetails`, `extractEpisodes`, `extractStreamUrl`.
- Image handlers: `searchResults`, `extractDetails`, `extractChapters`, `extractImages`.
- Text handlers: `searchResults`, `extractDetails`, `extractChapters`, `extractText`.
- Handlers are exported on `globalThis`.
- The runtime is app-like JavaScript, not a browser or Node.js environment. Browser-only globals and Node imports must not be assumed.
- HTTP access is provided through the host's `fetchv2` bridge.
- Video resolution must produce direct MP4/HLS media, not an embed page. Multi-stream results use a flat label/URL list.
- Search/details/episode success is not playback proof. Runtime and final media verification are separate evidence levels.
- The public guide describes a simple HTTP repository index with module IDs and package URLs.

**Publication limitation:** the public guide links to `docs/modules/MODULE_CONTRACT.md` and `MODULE_CREATION_GUIDE_V2.md` in the Synthetiq Player repository. Those files were not present in the public repository tree inspected during this audit. The website guide and public static tester documentation are therefore the available published evidence. Unpublished runtime internals, signature semantics, and undocumented behavior remain unknown.

**Kinetiq decision:** do not mirror the external manifest or runtime as Kinetiq's internal API. Phase 3 will define stable Kinetiq contracts and implement a separately tested compatibility adapter. No Synthetiq code is bundled in Phase 1.

## Licensing policy

- Prefer stable upstream releases with explicit public licences.
- Keep a dependency inventory with version, source, and licence evidence.
- Do not bundle assets or code with unclear redistribution rights.
- Review transitive dependencies before release.
- Meta's public Android samples are MIT-licensed; the Meta VR OS Utility Library is used via its documented Maven Central coordinate. Its exact redistribution terms must be confirmed from the published artefact metadata before a public release.
- This is an initial dependency policy, not a completed legal audit.

## Build and installation workflow

- Source editing: Working Copy on iPhone.
- Source control: GitHub `Haimiya612/Kinetiq`, branch `main`.
- Build host: GitHub Actions, Ubuntu runner.
- CI: JDK 17, Android SDK API 37, Gradle 9.5.0.
- CI generates the Gradle Wrapper from the pinned Gradle distribution, runs unit tests and lint, builds the debug APK, and uploads `Kinetiq-Quest-Debug` for 14 days.
- The debug APK is for sideload testing only. It is not a release-signed Store package.
- No signing key or secret is committed. Release signing is a later secure setup task.
- Physical installation and Quest 3 verification remain pending until a successful CI artifact is installed and tested.

## Open Phase 0 gates

- [ ] Confirm the CI build passes on the actual repository.
- [ ] Install the generated APK on Quest 3.
- [ ] Verify launch, panel resizing, controller/hand navigation, and settings persistence on-device.
- [ ] Confirm the Meta VR OS Utility artefact licence metadata before public release.
- [ ] Establish a protected release-signing process before distribution.

## Evidence sources

- Meta Horizon OS Android app creation and manifest guidance: https://developers.meta.com/vr/documentation/android-apps/create-app/
- Meta application manifest requirements: https://developers.meta.com/vr/resources/publish-mobile-manifest/
- Meta minimum Horizon OS version guidance: https://developers.meta.com/vr/documentation/android-apps/min-os-versions/
- Meta VR OS Utility Library guide: https://developers.meta.com/vr/documentation/android-apps/metavrx-os-utility-library/
- Meta Android compatibility guidance: https://developers.meta.com/vr/documentation/android-apps/port-an-existing-app/
- Synthetiq module guide: https://synthetiq.uk/modules/
- Synthetiq public static tester: https://github.com/kas021/synthetiq-module-tester
- Android Gradle Plugin compatibility: https://developer.android.com/build/releases/about-agp
- Compose BOM: https://developer.android.com/develop/ui/compose/bom
- Media3 releases: https://developer.android.com/jetpack/androidx/releases/media3
- DataStore releases: https://developer.android.com/jetpack/androidx/releases/datastore
- Room releases: https://developer.android.com/jetpack/androidx/releases/room

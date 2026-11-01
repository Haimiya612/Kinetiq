# Kinetiq Development Roadmap

This is the master plan. A checked item means the decision/documentation exists, not that a feature has shipped. Implementation status must be supported by code, tests, and device verification.

## Phase 0 — Product and engineering foundation

- [x] Product name and product-family identity.
- [x] Hybrid personality: premium presentation, enthusiast capabilities.
- [x] Adaptive light/dark visual direction.
- [x] Dual module strategy: Synthetiq compatibility + native Kinetiq modules.
- [x] Initial architecture and security principles.
- [x] Confirm initial Quest 3 / Android API support baseline (documented; device verification pending).
- [x] Inspect and document the published Synthetiq module specification (public guide inspected; unpublished internals remain unknown).
- [x] Select initial dependency versions and licensing policy (release artefact licence review remains open).
- [ ] Establish verified build, signing, and Quest installation workflow.

**Exit criteria:** implementation-ready product specification and verified technical constraints.

## Phase 1 — Native application foundation

- [x] Create Kotlin/Gradle project and package structure.
- [x] Build Compose navigation and adaptive design system.
- [x] Add Quest capability detection and Android fallbacks.
- [x] Add settings persistence, structured logging, and error handling.
- [x] Add unit tests and CI build/lint checks.
- [ ] Produce and install first APK on Quest 3.

**Exit criteria:** app launches reliably, navigation works, settings persist, and CI builds it.

## Phase 2 — Playback and cinema experience

- [ ] Integrate AndroidX Media3 / ExoPlayer.
- [ ] Implement local-file playback first.
- [ ] Add playback state, buffering/error UI, retry, seek, and resume.
- [ ] Add audio/subtitle selection and subtitle styling where supported.
- [ ] Add playback speed, queue, next item, and sleep timer.
- [ ] Build Quest cinema surface and adjustable screen placement.
- [ ] Test codecs, containers, hardware decoding, and performance on-device.

**Exit criteria:** local media can be selected, played, paused, sought, resumed, and comfortably viewed in Quest.

## Phase 3 — Kinetiq Engine and module compatibility

- [ ] Inspect the Synthetiq documentation, repository manifest, package format, handlers, and runtime assumptions.
- [ ] Define Kinetiq's independent internal module API.
- [ ] Implement a Synthetiq compatibility adapter from verified specifications.
- [ ] Implement native Kinetiq module adapter and developer contract.
- [ ] Add repository catalogue validation and package integrity checks.
- [ ] Add install, enable/disable, update, rollback, and uninstall.
- [ ] Add capability declarations, execution limits, diagnostics, and test fixtures.

**Exit criteria:** both module formats can be integrated through adapters and tested without coupling the player to either format.

## Phase 4 — Discovery and catalogue

- [ ] Search across enabled modules.
- [ ] Normalize titles, aliases, artwork, and metadata.
- [ ] Deduplicate results from multiple sources.
- [ ] Add filters, sorting, categories, and source indicators.
- [ ] Add details and episodic navigation where supported.
- [ ] Handle missing and conflicting metadata gracefully.

## Phase 5 — Personal library and profiles

- [ ] Watch history and continue watching.
- [ ] Watchlists, favourites, and collections.
- [ ] Local profiles and profile-specific settings.
- [ ] Hidden titles and metadata corrections.
- [ ] Export/import and local backups.
- [ ] Optional recommendations and release reminders.

## Phase 6 — Network and personal media

- [ ] SMB and WebDAV, subject to platform feasibility.
- [ ] Personal media server integrations.
- [ ] LAN discovery with explicit consent.
- [ ] Remote server configuration and diagnostics.
- [ ] Offline downloads only where source terms and rights permit.
- [ ] Download queue and storage controls.

## Phase 7 — Expanded media

- [ ] Image/gallery viewer.
- [ ] Manga/comic reader with zoom, page fit, and reading direction.
- [ ] Music mode and background playback.
- [ ] 180°/360° and stereoscopic video.
- [ ] Optional 3D model viewer.

## Phase 8 — Advanced Quest experience

- [ ] Cinema environments and lighting presets.
- [ ] Passthrough-aware placement and mixed reality where supported.
- [ ] Controller, hand, gaze, and keyboard interaction.
- [ ] Spatial audio and accessibility.
- [ ] Multi-panel workspace and floating mini-player.
- [ ] Battery, thermal, and performance profiles.

## Phase 9 — Module ecosystem

- [ ] Developer SDK, templates, examples, and validator.
- [ ] Repository manager and update channels.
- [ ] Optional community catalogue with moderation/reporting.
- [ ] Publisher provenance and trust indicators.
- [ ] Compatibility matrix and deprecation policy.

## Phase 10 — Release and long-term maintenance

- [ ] Security and privacy review.
- [ ] Accessibility, localisation, performance, and compatibility QA.
- [ ] Release signing and reproducible release checklist.
- [ ] Crash recovery, support process, and changelog.
- [ ] Dependency and licence audits.
- [ ] Stable release and maintenance policy.

## Cross-cutting requirements

Every phase must consider privacy, accessibility, offline/error states, Quest thermals and memory, legal source access, automated tests, and clear user control.

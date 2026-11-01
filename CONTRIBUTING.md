# Contributing to Kinetiq

- Keep changes aligned with `docs/ROADMAP.md`.
- Do not mark a feature complete without implementation, automated tests where applicable, and device verification where required.
- Keep Horizon OS-specific APIs behind capability checks and Android fallbacks.
- Do not add Google Mobile Services dependencies to the Quest runtime without a verified Horizon OS-compatible alternative.
- Never commit credentials, cookies, signing keys, personal media paths, or signed media URLs.
- Treat module packages, metadata, subtitles, and network responses as untrusted input.
- Preserve the adapter boundary: external module formats must not define Kinetiq's internal API.

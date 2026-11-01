# Security and Privacy Baseline

## Non-negotiable principles

- Never request or store private SSH keys, GitHub tokens, passwords, or unrelated credentials.
- Never commit secrets, signing keys, API tokens, private URLs, or personal media credentials.
- Keep personal library data local by default.
- No undisclosed telemetry; any future telemetry must be transparent and opt-in.
- Redact authentication headers, cookies, signed URLs, and personal paths from logs.
- Treat repository metadata, modules, media metadata, subtitles, and network responses as untrusted.

## Module security

- Execute modules behind a constrained interface.
- Validate manifests and all runtime data.
- Apply timeouts, cancellation, and resource limits.
- Avoid unrestricted filesystem, process, or account access.
- Make capabilities visible and restrict them where feasible.
- Verify package integrity while distinguishing it from publisher trust.
- Allow immediate module disable/removal.
- Keep module failures isolated where feasible.

## Media and network safety

- Validate URI schemes and reject unsupported/dangerous schemes.
- Use TLS for remote connections.
- Apply redirect and host policies.
- Never log credentials or complete signed media URLs.
- Respect source authorisation, DRM, copyright, and terms.
- Offer downloads only where permitted by source and applicable rights.

## User data

Users should be able to inspect, export, and delete local history, watchlists, profiles, and cached metadata. Any future synchronisation must be optional and documented.

## Release gates

Before public release: review module isolation, test malformed manifests and hostile metadata, test timeouts and redirects, audit dependencies/licences, verify signing, review privacy disclosures, and perform Quest device QA.

# ColdEternity local cosmetics engine

This directory contains an adapted copy of the runtime engine from
https://github.com/LongCold-ColdEternity-Team/Lunar-Local-Cosmetics-Unlocker.

Original developer: ColdEternity Team.

Local changes:
- removed the standalone DllMain entry point;
- exposed an embedded-engine entry point for Vape v4.21;
- forwarded runtime completion and failure to Vape's controller;
- retained the original local-only catalog and selection behavior.

The adjacent LICENSE applies to this directory.

# Reproduction for testcontainers-java issue #3790

Issue: https://github.com/testcontainers/testcontainers-java/issues/3790

This repo (at least for me) reproduces the problem mentioned in the above issue. When running the build for the first time, it passes. When running a second time, it hangs until cancelled. After that I have to restart docker and remove the leftover containers to try again.

To reproduce run `./gradlew clean build` twice in a row.
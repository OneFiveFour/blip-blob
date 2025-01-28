# Important Gradle Tasks

```
// format code
./gradlew ktLintFormat

// run tests
./gradlew test

// generate database classes
./gradlew :core:database:generateSqlDelightInterface

// run tests with code coverage report
./gradlew :app:koverHtmlReportMerged
```

# Open TODOs
- [ ] make button animations more visible
- [ ] check if Repository.updateXXX methods can be atomized into setXXX and then be removed
- [ ] fix icon sized to be naturally sized
- [ ] check saved state handle for running session
- [ ] blur scrolling content behind primary buttons and headlines
- [ ] keep ALL shuffle selected
- [ ] think about using MVI for VM communication
- [ ] unify package structure in feature
- [ ] write unit tests for sort order
- [ ] add UI for Initial states
- [ ] add UI for Error states
- [ ] create design language for dimensions
- [ ] move common icons to core/ui package
- [ ] 'setter for statusBarColor: Int' is deprecated. Deprecated in Java
- [ ] give sessionItem a bit of color to make each session distinguishable
- [ ] infinite pulsing animation for pause state
- [ ] rename package name to blip blob
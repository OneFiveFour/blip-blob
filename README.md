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
- [ ] keep ALL shuffle selected
- [ ] think about using MVI for VM communication
- [ ] unify package structure in feature
- [ ] write unit tests for sort order
- [x] give new tasks Groups a default color
- [ ] add UI for Initial states
- [ ] add UI for Error states
- [ ] create design language for dimensions
- [ ] move common icons to core/ui package
- [ ] 'setter for statusBarColor: Int' is deprecated. Deprecated in Java
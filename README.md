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
- [ ] create onAction accordingly onNavigate to group composable action params
- [ ] clear separation on when taskGroup colors are handled as pair and when as singular units (Ui Domain Data)
- [ ] add "onXXX" colors for taskGroup colors
- [ ] check for hardcoded Dispatchers and replace them with injected DispatcherProvider interface
- [ ] check that transformation between Ui <> data <> domain models in done in the same layer everywhere (VM, UseCases, Repos?)
- [ ] check sealed classes if they can be sealed interfaces instead
- [ ] unify naming for Set[Type]Title in all VM Actions, UseCase Actions and database actions
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
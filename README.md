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

# TODOs

## Next Steps
- [ ] battery consumption verbessern
- [ ] create tutorial sessions that are sorted always on top
- [ ] implement delete task groups
- [ ] Wake Lock
- [ ] make button animations more visible
- [ ] check saved state handle for running session
- [ ] blur scrolling content behind primary buttons and headlines
- [ ] write unit tests for sort order
- [ ] add UI for Initial states
- [ ] add UI for Error states
- [ ] give sessionItem a bit of color to make each session distinguishable
- [ ] infinite pulsing animation for pause state
- [ ] rename package name to blip blob
- [ ] Complete Unit Tests

## Clean up
- [ ] unify naming style of preview data (fake or not fake prefix)
- [ ] unify sql structure (names of methods setXxxYyy or only setYyy), names of params (taskId or only id), etc.
- [ ] Decide for uppercase "Edit session" vs. "Edit Session" in the whole app
- [ ] create onAction accordingly onNavigate to group composable action params
- [ ] clear separation on when taskGroup colors are handled as pair and when as singular units (Ui Domain Data)
- [ ] check for hardcoded Dispatchers and replace them with injected DispatcherProvider interface
- [ ] check that transformation between Ui <> data <> domain models in done in the same layer everywhere (VM, UseCases, Repos?)
- [ ] check sealed classes if they can be sealed interfaces instead
- [ ] unify naming for Set[Type]Title in all VM Actions, UseCase Actions and database actions
- [ ] check if Repository.updateXXX methods can be atomized into setXXX and then be removed
- [ ] unify package structure in feature
- [ ] create design language for dimensions
- [ ] move common icons to core/ui package
- [ ] 'setter for statusBarColor: Int' is deprecated. Deprecated in Java

## Upcoming Features
- [ ] activate / deactivate task groups
- [ ] Foreground Service for running sessions
- [ ] Statistics about what task was executed when/how often/etc.
- [ ] enable/disable tasks to avoid deleting/recreate them when temporarily not needed

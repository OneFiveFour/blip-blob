@file:Suppress("UnstableApiUsage")

import org.gradle.api.Project
import org.gradle.api.artifacts.MinimalExternalModuleDependency
import org.gradle.api.artifacts.VersionCatalog
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.api.artifacts.VersionConstraint
import org.gradle.api.provider.Provider
import org.gradle.kotlin.dsl.getByType
import org.gradle.plugin.use.PluginDependency

internal val Project.libs: VersionCatalog
    get() = extensions.getByType<VersionCatalogsExtension>().named("libs")

private fun VersionCatalog.findLibraryOrThrow(name: String) =
    findLibrary(name)
        .orElseThrow { NoSuchElementException("Library $name not found in version catalog") }

private fun VersionCatalog.findPluginOrThrow(name: String) =
    findPlugin(name)
        .orElseThrow { NoSuchElementException("Plugin $name not found in version catalog") }

// Compose Compiler
internal val VersionCatalog.pluginComposeCompiler: Provider<PluginDependency>?
    get() = findPluginOrThrow("composeCompiler")

// KSP
internal val VersionCatalog.pluginKsp: Provider<PluginDependency>?
    get() = findPluginOrThrow("ksp")

// Kotlin Test Dependencies
internal val VersionCatalog.libJunit: Provider<MinimalExternalModuleDependency>
    get() = findLibraryOrThrow("junit")
internal val VersionCatalog.libCoroutinesTest: Provider<MinimalExternalModuleDependency>
    get() = findLibraryOrThrow("coroutines-test")
internal val VersionCatalog.libMockk: Provider<MinimalExternalModuleDependency>
    get() = findLibraryOrThrow("mockk")
internal val VersionCatalog.libTruth: Provider<MinimalExternalModuleDependency>
    get() = findLibraryOrThrow("truth")
internal val VersionCatalog.libAndroidXArchCoreTesting: Provider<MinimalExternalModuleDependency>
    get() = findLibraryOrThrow("androidX-arch-core-testing")
internal val VersionCatalog.libTurbine: Provider<MinimalExternalModuleDependency>
    get() = findLibraryOrThrow("turbine")

// Kotlin Dependencies
internal val VersionCatalog.libDateAndTime: Provider<MinimalExternalModuleDependency>
    get() = findLibraryOrThrow("kotlinx-datetime")

// Android Test Dependencies
internal val VersionCatalog.libRobolectric: Provider<MinimalExternalModuleDependency>
    get() = findLibraryOrThrow("robolectric")
internal val VersionCatalog.libComposeTestJUnit: Provider<MinimalExternalModuleDependency>
    get() = findLibraryOrThrow("compose-test-junit")
internal val VersionCatalog.libComposeTestManifest: Provider<MinimalExternalModuleDependency>
    get() = findLibraryOrThrow("compose-test-manifest")

// Android Dependencies
internal val VersionCatalog.libAndroidXCore: Provider<MinimalExternalModuleDependency>
    get() = findLibraryOrThrow("androidX-core")

// Hilt
internal val VersionCatalog.libHiltCore: Provider<MinimalExternalModuleDependency>
    get() = findLibraryOrThrow("hilt-core")
internal val VersionCatalog.libHiltCompiler: Provider<MinimalExternalModuleDependency>
    get() = findLibraryOrThrow("hilt-compiler")
internal val VersionCatalog.libHiltAndroid: Provider<MinimalExternalModuleDependency>
    get() = findLibraryOrThrow("hilt-android")
internal val VersionCatalog.libHiltAndroidCompiler: Provider<MinimalExternalModuleDependency>
    get() = findLibraryOrThrow("hilt-android-compiler")
internal val VersionCatalog.libHiltAndroidTesting: Provider<MinimalExternalModuleDependency>
    get() = findLibraryOrThrow("hilt-android-testing")
internal val VersionCatalog.libHiltNavigation: Provider<MinimalExternalModuleDependency>
    get() = findLibraryOrThrow("hilt-navigation")

// Compose Dependencies
internal val VersionCatalog.libComposeBom: Provider<MinimalExternalModuleDependency>
    get() = findLibraryOrThrow("androidX-compose-bom")
internal val VersionCatalog.libComposeUi: Provider<MinimalExternalModuleDependency>
    get() = findLibraryOrThrow("androidX-compose-ui")
internal val VersionCatalog.libComposeUiUtil: Provider<MinimalExternalModuleDependency>
    get() = findLibraryOrThrow("androidX-compose-ui-util")
internal val VersionCatalog.libComposeUiTooling: Provider<MinimalExternalModuleDependency>
    get() = findLibraryOrThrow("androidX-compose-ui-tooling")
internal val VersionCatalog.libComposeUiToolingPreview: Provider<MinimalExternalModuleDependency>
    get() = findLibraryOrThrow("androidX-compose-ui-tooling-preview")
internal val VersionCatalog.libComposeMaterial3: Provider<MinimalExternalModuleDependency>
    get() = findLibraryOrThrow("androidX-compose-material3")
internal val VersionCatalog.libComposeLifecycle: Provider<MinimalExternalModuleDependency>
    get() = findLibraryOrThrow("androidX-lifecycle-compose")

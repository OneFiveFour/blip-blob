plugins {
    id("st.android.library")
    id("st.android.compose")
    id("st.android.hilt")
    id("st.android.test")
    id("st.ktlint")
}

android {
    namespace = AppConfig.applicationId + ".feature.sessioneditor"
}

dependencies {

    // Core
    implementation(project(":core:common"))
    implementation(project(":core:theme"))
    implementation(project(":core:ui"))
    implementation(project(":core:usecases-api"))
    implementation(project(":feature:session-editor-api"))

    // Date Time
    implementation(libs.kotlinx.datetime)

    // Navigation
    implementation(libs.bundles.navigation)

    // Drag and Drop
    implementation(libs.reorderable)
}

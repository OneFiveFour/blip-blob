plugins {
    id("st.android.library")
    id("st.android.compose")
    id("st.android.hilt")
    id("st.ktlint")
    id("st.android.test")
}

android {
    namespace = AppConfig.applicationId + ".feature.sessionoverview"
}

dependencies {

    // Core
    implementation(project(":core:common"))
    implementation(project(":core:theme"))
    implementation(project(":core:usecases-api"))
    implementation(project(":core:ui"))

    // Date Time
    implementation(libs.kotlinx.datetime)

    // Lifecycle
    implementation(libs.androidX.lifecycle.viewmodel)
    implementation(libs.androidX.lifecycle.viewmodel.compose)

    // Drag and Drop
    implementation(libs.reorderable)
}

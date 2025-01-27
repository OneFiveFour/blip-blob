plugins {
    id("st.android.library")
    id("st.android.compose")
    id("st.android.hilt")
    id("st.ktlint")
}

android {
    namespace = AppConfig.applicationId + ".core.ui"
}

dependencies {
    implementation(project(":core:theme"))
}

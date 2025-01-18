plugins {
    id("st.kotlin.library")
    id("st.kotlin.test")
    id("st.ktlint")
}

android {
    namespace = AppConfig.applicationId + ".core.common"
}

dependencies {

    // Date Time
    implementation(libs.kotlinx.datetime)

    testImplementation(project(":core:common-test"))
}

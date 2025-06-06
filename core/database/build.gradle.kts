plugins {
    alias(libs.plugins.sqlDelight)
    id("st.kotlin.library")
    id("st.kotlin.test")
    id("st.kotlin.hilt")
    id("st.ktlint")
}

android {
    namespace = AppConfig.applicationId + ".core.database"
}

sqldelight {
    databases {
        create("Database") {
            packageName.set("net.onefivefour.sessiontimer.core.database")
        }
    }
}

dependencies {

    implementation(project(":core:common"))
    implementation(project(":core:database-api"))
    implementation(project(":core:di"))

    // Database
    implementation(libs.sqlDelight.coroutines)

    // Coroutines
    implementation(libs.coroutines.core)

    // Testing
    testImplementation(libs.sqlDelight.test)
}

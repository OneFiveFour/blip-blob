import com.android.build.api.dsl.LibraryExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.configure

class KotlinLibraryPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            // Apply all required plugins
            apply(plugin = "com.android.library")
            apply(plugin = "kotlin-android")
            apply(plugin = "st.kotlin")
            apply(plugin = "st.ktlint")

            // Configure Android library options
            extensions.configure<LibraryExtension> {
                compileSdk = AppConfig.compileSdk

                defaultConfig {
                    minSdk = AppConfig.minSdk
                }

                compileOptions {
                    targetCompatibility = JavaVersion.VERSION_21
                    sourceCompatibility = JavaVersion.VERSION_21
                }

                buildFeatures {
                    buildConfig = true
                }

                packaging {
                    resources {
                        excludes += "/META-INF/{AL2.0,LGPL2.1}"
                        excludes += "META-INF/*.kotlin_module"
                        excludes += "META-INF/LICENSE.md"
                        excludes += "META-INF/LICENSE-notice.md"
                    }
                }
            }
        }
    }
}
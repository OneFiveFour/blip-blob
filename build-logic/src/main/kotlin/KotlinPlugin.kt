import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.dsl.KotlinVersion
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

class KotlinPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            tasks.withType(KotlinCompile::class.java).configureEach {
                compilerOptions {
                    jvmTarget.set(JvmTarget.JVM_21)
                    languageVersion.set(KotlinVersion.KOTLIN_1_9)
                    apiVersion.set(KotlinVersion.KOTLIN_1_9)
                }
            }
            dependencies {
                "implementation"(libs.libDateAndTime)
            }
        }
    }
}
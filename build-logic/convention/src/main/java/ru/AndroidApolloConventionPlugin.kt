
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType

class AndroidApolloConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")
            pluginManager.apply(libs.findPlugin("apollo").get().get().pluginId)

            dependencies {
                add("implementation", libs.findLibrary("apollo.runtime").get())
                add("implementation", libs.findLibrary("apollo.normalized.cache").get())
            }
        }
    }
}

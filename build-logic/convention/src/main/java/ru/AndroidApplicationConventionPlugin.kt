import com.android.build.api.dsl.ApplicationExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType
import ru.appricot.startuphub.configureFlavors
import ru.appricot.startuphub.configureKotlinAndroid

class AndroidApplicationConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.android.application")
                apply("org.jetbrains.kotlin.android")
                apply("org.jetbrains.kotlin.plugin.compose")
            }

            extensions.configure<ApplicationExtension> {
                configureKotlinAndroid(this)
                defaultConfig.targetSdk = 36
                configureFlavors(this)
                configureAndroidCompose(this)
            }
            val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")
            dependencies {
                add("implementation", libs.findLibrary("androidx.navigation3.runtime").get())
                add("implementation", libs.findLibrary("androidx.navigation3.ui").get())
                add("implementation", libs.findLibrary("androidx.lifecycle.viewmodel.navigation3").get())
                add("implementation", libs.findLibrary("timber").get())
            }
        }
    }
}

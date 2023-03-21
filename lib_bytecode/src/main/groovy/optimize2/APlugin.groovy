package optimize2

import com.android.build.gradle.AppExtension
import org.gradle.api.Plugin
import org.gradle.api.Project

class APlugin implements Plugin<Project> {
    @Override
    void apply(Project project) {
        System.out.println("== APlugin start ==")
        AppExtension appExtension = project.extensions.getByType(AppExtension)
        ATransform transform = new ATransform()
        appExtension.registerTransform(transform)
    }
}


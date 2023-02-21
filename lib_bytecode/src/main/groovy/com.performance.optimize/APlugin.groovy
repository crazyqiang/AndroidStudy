package com.performance.optimize

import com.android.build.gradle.AppExtension
import org.gradle.api.Plugin
import org.gradle.api.Project

class APlugin implements Plugin<Project> {
    @Override
    void apply(Project target) {
        System.out.println("== APlugin start ==")
        AppExtension appExtension = target.extensions.getByType(AppExtension)
        BTransform transform = new BTransform()
        //ATransform transform = new ATransform()
        appExtension.registerTransform(transform)
    }
}


package com

import org.gradle.api.Plugin
import org.gradle.api.Project

class GreetingExtensionPlugin implements Plugin<Project> {

    @Override
    void apply(Project project) {

        // Add the 'greeting' extension object
        def extension = project.extensions.create('greeting', GreetingExtension)

        // Add a task that uses configuration from the extension object
        project.tasks.create('buildSrc') {
            doLast {
                println "${extension.message} from ${extension.greeter}"
                println project.greeting
            }
        }
    }
}

class GreetingExtension {
    String message
    String greeter
}




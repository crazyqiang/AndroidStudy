package com.fastgo.plugin

import org.gradle.api.DefaultTask
import org.gradle.api.Project
import org.gradle.api.Plugin
import org.gradle.api.tasks.TaskAction

class CustomPlugin implements Plugin<Project> {

    @Override
    void apply(Project project) {

        project.tasks.create('writeToFile', CustomPluginTask) {

            destination = { project.greetingFile }

            doLast {
                println project.file(destination).text
            }
        }
    }
}

class CustomPluginTask extends DefaultTask {

    def destination

    File getDestination() {
        //创建路径为destination的file
        project.file(destination)
    }

    @TaskAction
    def greet() {
        def file = getDestination()
        file.parentFile.mkdirs()
        //向文件中写入文本
        file.write('hello world')
    }
}
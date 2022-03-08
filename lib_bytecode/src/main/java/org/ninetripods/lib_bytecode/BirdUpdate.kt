package org.ninetripods.lib_bytecode

import javassist.ClassPool
import javassist.CtClass
import javassist.CtMethod

object BirdUpdate {

    @JvmStatic
    fun main(args: Array<String>) {
        update()
    }

    //TODO Javassist是否能操作kotlin类文件
    private fun update() {
        val pool: ClassPool = ClassPool.getDefault()
        val cc: CtClass = pool.get("org.ninetripods.lib_bytecode.Bird")
        val fly: CtMethod = cc.getDeclaredMethod("fly")
        fly.insertBefore("System.out.println(\"准备起飞\");")
        fly.insertAfter("System.out.println(\"成功落地\");")
        val bird = cc.toClass().newInstance()
        bird.javaClass.getMethod("fly").invoke(bird)
    }
}
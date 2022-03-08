package org.ninetripods.lib_bytecode

import javassist.*

/**
 * Javassist基本使用
 */
object JavassistTest {

    @JvmStatic
    fun main(args: Array<String>) {
        createPerson()
    }

    private fun createPerson() {
        val pool: ClassPool = ClassPool.getDefault()
        val person: CtClass = pool.makeClass("org.ninetripods.lib_bytecode.Person")
        val nameField: CtField = CtField(pool.get("java.lang.String"), "name", person)
        nameField.modifiers = Modifier.PRIVATE

        val ageField = CtField.make("private int age;", person)

        person.addField(nameField, CtField.Initializer.constant("小强"))
        person.addField(ageField, CtField.Initializer.constant(32))

        person.addMethod(CtNewMethod.setter("setName", nameField))
        person.addMethod(CtNewMethod.getter("getName", nameField))
        person.addMethod(CtNewMethod.setter("setAge", ageField))
        person.addMethod(CtNewMethod.getter("getAge", nameField))

        val cons: CtConstructor = CtConstructor(arrayOf<CtClass>(), person)
        cons.setBody("{name=\"老王\";age=30;}")
        person.addConstructor(cons)

        val cons2: CtConstructor =
            CtConstructor(arrayOf(pool.get("java.lang.String"), CtClass.intType), person)
        //$0=this,$1,$2,$3...代表第几个参数
        cons2.setBody("{$0.name=$1;$0.age=$2;}")
        person.addConstructor(cons2)

        //创建方法
        val printName: CtMethod =
            CtMethod(CtClass.voidType, "printName", arrayOf<CtClass>(), person)
        printName.modifiers = Modifier.PUBLIC
        printName.setBody("{System.out.println(name);}")
        person.addMethod(printName)

        val printAge: CtMethod =
            CtMethod.make("public void printAge(){System.out.println(age);}", person)
        person.addMethod(printAge)

        //输出到具体路径
        person.writeFile("./lib_bytecode/build/outputs/class")
    }

}
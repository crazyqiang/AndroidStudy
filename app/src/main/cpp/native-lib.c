#include <jni.h>

JNIEXPORT void JNICALL
Java_org_ninetripods_mq_study_jni_JNIActivity_cryptFiles(JNIEnv *env, jclass type, jstring src_,
                                                         jstring dest_) {
    const char *src = (*env)->GetStringUTFChars(env, src_, 0);
    const char *dest = (*env)->GetStringUTFChars(env, dest_, 0);

    // TODO

    (*env)->ReleaseStringUTFChars(env, src_, src);
    (*env)->ReleaseStringUTFChars(env, dest_, dest);
}

JNIEXPORT void JNICALL
Java_org_ninetripods_mq_study_jni_JNIActivity_decryptFile(JNIEnv *env, jclass type, jstring src_,
                                                          jstring dest_) {
    const char *src = (*env)->GetStringUTFChars(env, src_, 0);
    const char *dest = (*env)->GetStringUTFChars(env, dest_, 0);

    // TODO

    (*env)->ReleaseStringUTFChars(env, src_, src);
    (*env)->ReleaseStringUTFChars(env, dest_, dest);
}
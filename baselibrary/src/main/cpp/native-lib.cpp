#include <jni.h>
#include <string>

extern "C"
JNIEXPORT jstring JNICALL
Java_com_hsy_study_myproject_MainActivity_stringFromJni(JNIEnv *env, jobject instance) {

    std::string hello = "Hello from C++";
    return env->NewStringUTF(hello.c_str());
}extern "C"
JNIEXPORT void JNICALL
Java_Test_test(JNIEnv *env, jobject instance) {

    // TODO
    printf("Hello World!\n");
    return;
}
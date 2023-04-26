#include <jni.h>
#include <stdio.h>
#include "JNITest.h"

#include "OpenGL.h"

JNIEXPORT void JNICALL
Java_testing_jni_JNITest_helloWorld(JNIEnv *env, jobject obj) {
	printf("Hello world!\n");

	test();

	return;
}
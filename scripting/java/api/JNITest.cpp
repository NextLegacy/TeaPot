#include <jni.h>
#include <stdio.h>

#include "core.h"

#include "JNITest.h"

JNIEXPORT void JNICALL
Java_testing_jni_JNITest_helloWorld(JNIEnv *env, jobject obj) 
{
	printf("JNITest::helloWorld()\n");
}
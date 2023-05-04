#include <jni.h>
#include <stdio.h>
#include "JNITest.h"

#include "testing/app.h"

JNIEXPORT void JNICALL
Java_testing_jni_JNITest_helloWorld(JNIEnv *env, jobject obj) 
{
	App::Run();
}
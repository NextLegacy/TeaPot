#include <jni.h>
#include <stdio.h>

#include "core.h"

#include "CoreTest.h"

JNIEXPORT void JNICALL
Java_testing_core_CoreTest_test(JNIEnv *env, jobject obj) 
{
	printf("CoreTest::test()\n");
}
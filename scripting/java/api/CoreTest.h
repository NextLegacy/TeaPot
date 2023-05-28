#pragma once

#include <jni.h>

extern "C" 
{
/*
 * Class:     CoreTest
 * Method:    test
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_testing_core_CoreTest_test
  (JNIEnv *, jobject);
}
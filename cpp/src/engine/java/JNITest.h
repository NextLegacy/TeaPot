#include <jni.h>

extern "C" 
{
/*
 * Class:     JNITest
 * Method:    helloWorld
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_testing_jni_JNITest_helloWorld
  (JNIEnv *, jobject);
}
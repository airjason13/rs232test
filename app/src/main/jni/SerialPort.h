//
// Created by jason on 8/12/19.
//

#ifndef RS232SERVER_SERIALPORT_H
#define RS232SERVER_SERIALPORT_H
#ifdef __cplusplus
extern "C" {
#endif
/*
 * Class:     android_serialport_api_SerialPort
 * Method:    open
 * Signature: (Ljava/lang/String;II)Ljava/io/FileDescriptor;
 */
JNIEXPORT jobject JNICALL Java_com_example_serialport_SerialPort_open
  (JNIEnv *, jclass, jstring, jint, jint);

/*
 * Class:     android_serialport_api_SerialPort
 * Method:    close
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_com_example_serialport_SerialPort_close
  (JNIEnv *, jobject);

#ifdef __cplusplus
}
#endif



#endif //RS232SERVER_SERIALPORT_H

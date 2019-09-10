LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)

LOCAL_MODULE := libserialport
LOCAL_SRC_FILES := SerialPort.c
LOCAL_SHARED_LIBRARIES := liblog libcutils

include $(BUILD_SHARED_LIBRARY)

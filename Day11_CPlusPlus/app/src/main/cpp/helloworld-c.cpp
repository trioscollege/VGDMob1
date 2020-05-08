//
// Created by steven.coverdale on 2020-04-28.
//

#include <android_native_app_glue.h>
#include <jni.h>
#include <android/log.h>

#define TAG "MY_TAG"
#define LOGE(...) __android_log_print(ANDROID_LOG_ERROR,TAG, __VA_ARGS__)

extern "C" {
void handle_cmd(android_app *pApp, int32_t cmd) {
}

void android_main(struct android_app *pApp) {
    pApp->onAppCmd = handle_cmd;
    LOGE("Hello, World!");
    int events;
    android_poll_source *pSource;
    do {
        if (ALooper_pollAll(0, nullptr, &events, (void **) &pSource) >= 0) {
            if (pSource) {
                pSource->process(pApp, pSource);
            }
        }
    } while (!pApp->destroyRequested);
}
}
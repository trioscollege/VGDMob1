package com.example.opengl_day10;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ConfigurationInfo;
import android.opengl.GLSurfaceView;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    /** Hold a reference to our GLSurfaceView */
    private GLSurfaceView glSurfaceView;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        glSurfaceView = new GLSurfaceView(this);

        // Check if the system supports OpenGL ES 2.0.
        final ActivityManager activityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        final ConfigurationInfo configurationInfo = activityManager.getDeviceConfigurationInfo();
        final boolean supportsEs2 = configurationInfo.reqGlEsVersion >= 0x20000;

        if (supportsEs2)
        {
            // Request an OpenGL ES 2.0 compatible context.
            glSurfaceView.setEGLContextClientVersion(2);

            // Set the renderer to our demo renderer, defined below.
            glSurfaceView.setRenderer(new AwesomeRenderer());
        }
        else
        {
            // This is where you could create an OpenGL ES 1.x compatible
            // renderer if you wanted to support both ES 1 and ES 2.
            return;
        }

        setContentView(glSurfaceView);
    }

    @Override
    protected void onResume()
    {
        // The activity must call the GL surface view's onResume() on activity onResume().
        super.onResume();
        glSurfaceView.onResume();
    }

    @Override
    protected void onPause()
    {
        // The activity must call the GL surface view's onPause() on activity onPause().
        super.onPause();
        glSurfaceView.onPause();
    }
}
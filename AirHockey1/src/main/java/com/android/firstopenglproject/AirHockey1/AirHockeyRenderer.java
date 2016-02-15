package com.android.firstopenglproject.AirHockey1;

/**
 * Created by john on 20/01/2016.
 */


import android.content.Context;
import android.opengl.GLSurfaceView.Renderer;
import android.util.Log;

import com.airhockey.android.R;
import com.android.firstopenglproject.AirHockey1.com.airhockey.android.util.LoggerConfig;
import com.android.firstopenglproject.AirHockey1.com.airhockey.android.util.TextResourceReader;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import static android.opengl.GLES20.GL_COLOR_BUFFER_BIT;
import static android.opengl.GLES20.glClear;
import static android.opengl.GLES20.glClearColor;
import static android.opengl.GLES20.glViewport;

import static android.opengl.GLES20.GL_COLOR_BUFFER_BIT;
import static android.opengl.GLES20.GL_FLOAT;
import static android.opengl.GLES20.GL_LINES;
import static android.opengl.GLES20.GL_POINTS;
import static android.opengl.GLES20.GL_TRIANGLES;
import static android.opengl.GLES20.glClear;
import static android.opengl.GLES20.glClearColor;
import static android.opengl.GLES20.glDrawArrays;
import static android.opengl.GLES20.glEnableVertexAttribArray;
import static android.opengl.GLES20.glGetAttribLocation;
import static android.opengl.GLES20.glGetUniformLocation;
import static android.opengl.GLES20.glUniform4f;
import static android.opengl.GLES20.glUseProgram;
import static android.opengl.GLES20.glVertexAttribPointer;
import static android.opengl.GLES20.glViewport;



public class AirHockeyRenderer implements Renderer {

        private static final String A_POSITION = "a_Position";
        private int aPositionLocation;
        private static final String U_COLOR = "u_Color";
        private int uColorLocation;
        private int program;
        private static final int POSITION_COMPONENT_COUNT = 2;
        private static final int BYTES_PER_FLOAT = 4;
        private final FloatBuffer vertexData;
        private final Context context;

        public AirHockeyRenderer(Context context) {
            this.context = context;
            float[] tableVerticesWithTriangles = {
                    // Triangle 1
                    0f,  0f,
                    9f, 14f,
                    0f, 14f,

                    // Triangle 2
                    0f,  0f,
                    9f,  0f,
                    9f, 14f,


                    // Line 1
                    0f,  7f,
                    9f,  7f,

                    // Mallets
                    4.5f,  2f,
                    4.5f, 12f

            };

            vertexData = ByteBuffer
                    .allocateDirect(tableVerticesWithTriangles.length * BYTES_PER_FLOAT)
                    .order(ByteOrder.nativeOrder())
                    .asFloatBuffer();

            vertexData.put(tableVerticesWithTriangles);
        }


        @Override
        public void onSurfaceCreated(GL10 glUnused, EGLConfig config) {
            // Set the background clear color to red. The first component is
            // red, the second is green, the third is blue, and the last
            // component is alpha, which we don't use in this lesson.
            glClearColor(1.0f, 0.0f, 0.0f, 0.0f);

            String vertexShaderSource = TextResourceReader
                    .readTextFileFromResource(context, R.raw.simple_vertex_shader);
            String fragmentShaderSource = TextResourceReader
                    .readTextFileFromResource(context, R.raw.simple_fragment_shader);

            int vertexShader = com.android.firstopenglproject.AirHockey1.ShaderHelper.compileVertexShader(vertexShaderSource);
            int fragmentShader = com.android.firstopenglproject.AirHockey1.ShaderHelper.compileFragmentShader(fragmentShaderSource);

            program = com.android.firstopenglproject.AirHockey1.ShaderHelper.linkProgram(vertexShader, fragmentShader);

                if (LoggerConfig.ON){
                    com.android.firstopenglproject.AirHockey1.ShaderHelper.validateProgram(program);
                }
            glUseProgram(program);

            uColorLocation = glGetUniformLocation(program, U_COLOR);
            aPositionLocation = glGetAttribLocation(program, A_POSITION);
            // Bind our data, specified by the variable vertexData, to the vertex
            // attribute at location A_POSITION_LOCATION.
            vertexData.position(0);
            glVertexAttribPointer(aPositionLocation, POSITION_COMPONENT_COUNT, GL_FLOAT,
                    false, 0, vertexData);
        }

        @Override
        public void onSurfaceChanged(GL10 glUnused, int width, int height) {
            // Set the OpenGL viewport to fill the entire surface.
            glViewport(0, 0, width, height);
        }
        @Override
        public void onDrawFrame(GL10 glUnused) {
            // Clear the rendering surface.
            glClear(GL_COLOR_BUFFER_BIT);
        }

        /**
         * Validates an OpenGL program. Should only be called when developing the
        * application.
        */
        public static boolean validateProgram(int programObjectId) {
        glValidateProgram(programObjectId);

        final int[] validateStatus = new int[1];
        glGetProgramiv(programObjectId, GL_VALIDATE_STATUS, validateStatus, 0);
        Log.v(TAG, "Results of validating program: " + validateStatus[0]
                + "\nLog:" + glGetProgramInfoLog(programObjectId));

        return validateStatus[0] != 0;


        }














    }

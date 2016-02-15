package com.android.firstopenglproject.AirHockey1;

import android.util.Log;

import com.android.firstopenglproject.AirHockey1.com.airhockey.android.util.LoggerConfig;


/**
 * Created by john on 10/02/2016.
 */

public class ShaderHelper {

    private static final String TAG = "ShaderHelper";

    /**
     * Loads and compiles a vertex shader, returning the OpenGL object ID.
     */
    public static int compileVertexShader(String shaderCode) {
        return compileShader(GL_VERTEX_SHADER, shaderCode);
    }

    /**
     * Loads and compiles a fragment shader, returning the OpenGL object ID.
     */
    public static int compileFragmentShader(String shaderCode) {
        return compileShader(GL_FRAGMENT_SHADER, shaderCode);
    }

    /**
     * Compiles a shader, returning the OpenGL object ID.
     */
    private static int compileShader(int type, String shaderCode) {

        // Create a new shader object.
        final int shaderObjectId = glCreateShader(type);

        if (shaderObjectId == 0) {
            if (LoggerConfig.ON) {
                Log.w(TAG, "Could not create new shader.");
            }

            return 0;
        }

    return shaderObjectId; //my version of what I think it does

    glShaderSource(shaderObjectId, shaderCode);
    glCompileShader(shaderObjectId);

    final int[] compileStatus = new int[1];

    glGetshaderiv(shaderObjectId, GL_COMPILE_STATUS, compileStatus, 0);

        if (LoggerConfig.ON) {
            // Print the program info log to the Android log output.
            Log.v(TAG, "Results of compiling source: + \n" + shaderCode + "\n:"
                    + glGetShaderInfoLog(shaderObjectId));
        }

        if (compileStatus[0] == 0) {
            // If it failed, delete the program object.
            glDeleteShader(shaderObjectId);
            if (LoggerConfig.ON) {
                Log.w(TAG, "Compilation of shader failed.");
            }

            return 0;
        }

        return shaderObjectId;

    }

    public static int  linkProgram(int vertexShaderId, int fragmentShaderId) {



        final int programObjectId = glCreateProgram();

        if (programObjectId == 0) {

            if (LoggerConfig.ON) {
                Log.w(TAG, "could not create new program");
            }

            return 0;

        }

    glAttachShader(programObjectId, vertexShaderId);
    glAttachShader(programObjectId, fragmentShaderId);

    glLinkProgram(programObjectId);

    final int[] linkStatus = new int[1];

    glGetProgramiv(programObjectId, GL_LINK_STATUS, linkStatus, 0);

        if (LoggerConfig.ON) {
            Log.v(TAG, "Results of linking program:\n"
                + glGetProgramInfoLog(programObjectId));
        }


        if (linkStatus[0] == 0) {
            glDeleteProgram(programObjectId);
            if (LoggerConfig.ON) ;
            log.w(TAG, "Linking of program failed.");
            }
            return 0;


        return programObjectId;
        }
    }



}

package com.android.firstopenglproject.AirHockey1.com.airhockey.android.util;

import android.util.Log;

import java.util.ArrayList;

import static android.opengl.GLES20.GL_COMPILE_STATUS;
import static android.opengl.GLES20.GL_FRAGMENT_SHADER;
import static android.opengl.GLES20.GL_LINK_STATUS;
import static android.opengl.GLES20.GL_VALIDATE_STATUS;
import static android.opengl.GLES20.GL_VERTEX_SHADER;
import static android.opengl.GLES20.glAttachShader;
import static android.opengl.GLES20.glCompileShader;
import static android.opengl.GLES20.glCreateProgram;
import static android.opengl.GLES20.glCreateShader;
import static android.opengl.GLES20.glDeleteProgram;
import static android.opengl.GLES20.glDeleteShader;
import static android.opengl.GLES20.glGetProgramInfoLog;
import static android.opengl.GLES20.glGetProgramiv;
import static android.opengl.GLES20.glGetShaderInfoLog;
import static android.opengl.GLES20.glGetShaderiv;
import static android.opengl.GLES20.glLinkProgram;
import static android.opengl.GLES20.glShaderSource;
import static android.opengl.GLES20.glValidateProgram;

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

        //return shaderObjectId; //my version of what I think it does

    glShaderSource(shaderObjectId, shaderCode);
    glCompileShader(shaderObjectId);

    final int[] compileStatus = new int[1];
    glGetShaderiv(shaderObjectId, GL_COMPILE_STATUS, compileStatus, 0);

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
            if (LoggerConfig.ON) {
                Log.w(TAG, "Linking of program failed.");
            }
            return 0;


        }
        return programObjectId;
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

    public static String[] removeEmptyStrings(String[] data)
    {
        ArrayList<String> result = new ArrayList<String>();

        for(int i = 0; i < data.length; i++)
            if(!data[i].equals(""))
                result.add(data[i]);

        String[] res = new String[result.size()];
        result.toArray(res);

        return res;
    }

    public static int[] toIntArray(Integer[] data)
    {
        int[] result = new int[data.length];

        for(int i = 0; i < data.length; i++)
            result[i] = data[i].intValue();

        return result;
    }





}

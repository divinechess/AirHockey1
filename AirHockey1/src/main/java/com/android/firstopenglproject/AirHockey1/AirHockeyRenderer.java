package com.android.firstopenglproject.AirHockey1;

/**
 * Created by john on 20/01/2016.
 */




    import static android.opengl.GLES20.GL_COLOR_BUFFER_BIT;
    import static android.opengl.GLES20.glClear;
    import static android.opengl.GLES20.glClearColor;
    import static android.opengl.GLES20.glViewport;

    import javax.microedition.khronos.egl.EGLConfig;
    import javax.microedition.khronos.opengles.GL10;

    import android.opengl.GLSurfaceView.Renderer;
    import com.android.firstopenglproject.AirHockey1.com.airhockey.android.util.TextResourceReader;

    public class AirHockeyRenderer implements Renderer {



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















    }
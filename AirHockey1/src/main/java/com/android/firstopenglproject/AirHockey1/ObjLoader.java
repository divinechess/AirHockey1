import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.io.IOException;
import java.lang.Float;


public class OBJLoader {
    public static Mesh loadMesh(String fileName)
    {
        String[] splitArray = fileName.split("\\.");
        String ext = splitArray[splitArray.length - 1];

        if(!ext.equals("obj"))
        {
            System.err.println("Error: File format not supported for mesh data: " + ext);
            new Exception().printStackTrace();
            System.exit(1);
        }

        Float vertices [] = null; //check these later and if we need wrapper
        int vertexIndex = 0;
        int indicesIndex = 0;
        Integer indices [] = null; //check integer type


        BufferedReader meshReader = null;

        try
        {
            meshReader = new BufferedReader(new FileReader("./res/models/" + fileName));
            String line;

            while((line = meshReader.readLine()) != null) {
                String[] tokens = line.split(" ");
                tokens = Util.removeEmptyStrings(tokens);

                if (tokens.length == 0 || tokens[0].equals("#"))
                    continue;
                else if (tokens[0].equals("v"))
                {
                    vertices[vertexIndex    ] = Float.parseFloat(tokens[1]); // how big is array in loop?
                    vertices[vertexIndex + 1] = Float.parseFloat(tokens[2]);
                    vertices[vertexIndex + 2] = Float.parseFloat(tokens[3]);
                    vertexIndex += 3;
                }

                else if (tokens[0].equals("f"))
                {
                    indices[indicesIndex     ] = Integer.parseInt(tokens[1].split("/")[0]) - 1;
                    indices[indicesIndex + 1 ] = Integer.parseInt(tokens[2].split("/")[0]) - 1;
                    indices[indicesIndex + 2 ] = Integer.parseInt(tokens[3].split("/")[0]) - 1;
                    indicesIndex += 3;

                    if(tokens.length > 4)
                    {
                        indices[indicesIndex   ] = Integer.parseInt(tokens[1].split("/")[0]) - 1;
                        indices[indicesIndex + 1] = Integer.parseInt(tokens[3].split("/")[0]) - 1;
                        indices[indicesIndex + 2] = Integer.parseInt(tokens[4].split("/")[0]) - 1;
                        indicesIndex += 3;
                    }
                }
            }

            meshReader.close();

            Mesh res = new Mesh();
            Float [] vertex = new Float[vertices.length];
            vertices.push(vertex);

            Integer[] indexData = new Integer[indices.size()];
            indices.toArray(indexData);

            res.addVertices(vertexData, Util.toIntArray(indexData));

            return res;
        }
        catch(Exception e)
        {
            e.printStackTrace();
            System.exit(1);
        }

        return null;
    }
}

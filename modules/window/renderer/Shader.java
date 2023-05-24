package hiof.gameenigne2d.modules.window.renderer;

import org.joml.*;
import org.lwjgl.BufferUtils;

import java.io.IOException;
import java.nio.FloatBuffer;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.lwjgl.opengl.GL11.GL_FALSE;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL20.glGetShaderInfoLog;

public class Shader {

    private int shaderProgramID;
    private boolean beingUsed = false;
    private String filepath;
    private String vertexSource;
    private String fragmentSource;


    //Creates the shader by getting the code written in default.glsl, which is then passed on
    public Shader(String filepath) {
        this.filepath = filepath;
        try {
            String source = new String(Files.readAllBytes(Paths.get(filepath)));
            String[] splitString = source.split("(#type)( )+([a-zA-Z]+)");

            int index = source.indexOf("#type") + 6;
            int endOfLine = source.indexOf("\r\n", index);
            String firstPattern = source.substring(index, endOfLine).trim();

            index = source.indexOf("#type", endOfLine) + 6;
            endOfLine = source.indexOf("\r\n", index);
            String secondPattern = source.substring(index, endOfLine).trim();

            if (firstPattern.equals("vertex")) {
                vertexSource = splitString[1];
            } else if (firstPattern.equals("fragment")) {
                fragmentSource = splitString[1];
            } else {
                throw new IOException("Unexpected token '" + firstPattern + "'");
            }

            if (secondPattern.equals("vertex")) {
                vertexSource = splitString[2];
            } else if (secondPattern.equals("fragment")) {
                fragmentSource = splitString[2];
            } else {
                throw new IOException("Unexpected token '" + secondPattern + "'");
            }
        } catch (IOException e) {
            e.printStackTrace();
            assert false : "Error: Could not open shader file: '" + filepath + "'";
        }
    }

    //Creates the vertex and fragment shaders from the code in default.glsl, and links them together
    public void compileAndLink() {
        int vertexID, fragmentID;

        vertexID = glCreateShader(GL_VERTEX_SHADER);

        glShaderSource(vertexID, vertexSource);
        glCompileShader(vertexID);

        int success = glGetShaderi(vertexID, GL_COMPILE_STATUS);
        if (success == GL_FALSE) {
            int length = glGetShaderi(vertexID, GL_INFO_LOG_LENGTH);
            System.out.println("ERROR: Vertex Shader compilation failed, " + filepath);
            System.out.println(glGetShaderInfoLog(vertexID, length));
            assert false :"";
        }

        fragmentID = glCreateShader(GL_FRAGMENT_SHADER);

        glShaderSource(fragmentID, fragmentSource);
        glCompileShader(fragmentID);

        success = glGetShaderi(fragmentID, GL_COMPILE_STATUS);
        if (success == GL_FALSE) {
            int length = glGetShaderi(fragmentID, GL_INFO_LOG_LENGTH);
            System.out.println("ERROR: Fragment Shader compilation failed, " + filepath);
            System.out.println(glGetShaderInfoLog(fragmentID, length));
            assert false :"";
        }

        shaderProgramID = glCreateProgram();
        glAttachShader(shaderProgramID, vertexID);
        glAttachShader(shaderProgramID, fragmentID);
        glLinkProgram(shaderProgramID);

        success = glGetProgrami(shaderProgramID, GL_LINK_STATUS);
        if (success == GL_FALSE) {
            int length = glGetProgrami(shaderProgramID, GL_INFO_LOG_LENGTH);
            System.out.println("ERROR: Linking shaders failed, " + filepath);
            System.out.println(glGetProgramInfoLog(shaderProgramID, length));
            assert false :"";
        }
    }

    public void use() {
        if (!beingUsed) {
            glUseProgram(shaderProgramID);
            beingUsed = true;
        }
    }

    public void detach() {
        glUseProgram(0);
        beingUsed = false;
    }

    public void uploadMat4f(String name, Matrix4f matrix) {
        int location = glGetUniformLocation(shaderProgramID, name);
        use();
        FloatBuffer matBuffer = BufferUtils.createFloatBuffer(16);
        matrix.get(matBuffer);
        glUniformMatrix4fv(location, false, matBuffer);
    }
    public void uploadMat3f(String name, Matrix3f matrix) {
        int location = glGetUniformLocation(shaderProgramID, name);
        use();
        FloatBuffer matBuffer = BufferUtils.createFloatBuffer(9);
        matrix.get(matBuffer);
        glUniformMatrix3fv(location, false, matBuffer);
    }

    public void uploadVec4f(String name, Vector4f vector) {
        int location = glGetUniformLocation(shaderProgramID, name);
        use();
        glUniform4f(location, vector.x, vector.y, vector.z, vector.w);
    }
    public void uploadVec3f(String name, Vector3f vector) {
        int location = glGetUniformLocation(shaderProgramID, name);
        use();
        glUniform3f(location, vector.x, vector.y, vector.z);
    }
    public void uploadVec2f(String name, Vector2f vector) {
        int location = glGetUniformLocation(shaderProgramID, name);
        use();
        glUniform2f(location, vector.x, vector.y);
    }

    public void uploadFloat(String name, float value) {
        int location = glGetUniformLocation(shaderProgramID, name);
        use();
        glUniform1f(location, value);
    }
    public void uploadInt(String name, int value) {
        int location = glGetUniformLocation(shaderProgramID, name);
        use();
        glUniform1i(location, value);
    }

    public void uploadSprite(String name, int slot) {
        int location = glGetUniformLocation(shaderProgramID, name);
        use();
        glUniform1i(location, slot);
    }

    public void uploadIntArray(String name, int[] array) {
        int location = glGetUniformLocation(shaderProgramID, name);
        use();
        glUniform1iv(location, array);
    }
}

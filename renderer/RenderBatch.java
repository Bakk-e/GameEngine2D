package HIOF.GameEnigne2D.renderer;

import HIOF.GameEnigne2D.components.SpriteRenderer;
import HIOF.GameEnigne2D.modules.Window;
import HIOF.GameEnigne2D.utils.AssetPool;
import org.joml.Vector2f;
import org.joml.Vector4f;

import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;

public class RenderBatch implements Comparable<RenderBatch>{
    private final int positionSize = 2;
    private final int colorSize = 4;
    private final int spriteCordsSize = 2;
    private final int spriteIDSize = 1;
    private final int positionOffset = 0;
    private final int colorOffset = positionOffset + positionSize * Float.BYTES;
    private final int spriteCordsOffset = colorOffset + colorSize * Float.BYTES;
    private final int spriteIDOffset = spriteCordsOffset + spriteCordsSize * Float.BYTES;
    private final int vertexSize = 9;
    private final int vertexSizeBytes = vertexSize * Float.BYTES;

    private SpriteRenderer[] spriteRenderers;
    private int numSprites;
    private boolean hasRoom;
    private float[] vertices;
    private int[] textureSlots = {0, 1, 2, 3, 4, 5 ,6 ,7};

    private List<Texture> textures;
    private int vaoID, vboID;
    private int maxBatchSize;
    private Shader shader;
    private int zIndex;


    public RenderBatch(int maxBatchSize, int zIndex) {
        this.zIndex = zIndex;
        shader = AssetPool.getShader();
        shader.compileAndLink();
        this.spriteRenderers = new SpriteRenderer[maxBatchSize];
        this.maxBatchSize = maxBatchSize;

        vertices = new float[maxBatchSize * 4 * vertexSize];

        this.numSprites = 0;
        this.hasRoom = true;
        this.textures = new ArrayList<>();
    }

    //Creates vertexArrays and buffers for all the textures it has to calulate. Here it uses code from default.glsl to
    // create the vertexArrays
    public void start() {
        vaoID = glGenVertexArrays();
        glBindVertexArray(vaoID);

        vboID = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, vboID);
        glBufferData(GL_ARRAY_BUFFER, (long) vertices.length * Float.BYTES, GL_DYNAMIC_DRAW);

        int eboID = glGenBuffers();
        int[] indices = generateIndices();
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, eboID);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, indices, GL_STATIC_DRAW);

        glVertexAttribPointer(0, positionSize, GL_FLOAT, false, vertexSizeBytes, positionOffset);
        glEnableVertexAttribArray(0);

        glVertexAttribPointer(1, colorSize, GL_FLOAT, false, vertexSizeBytes, colorOffset);
        glEnableVertexAttribArray(1);

        glVertexAttribPointer(2, spriteCordsSize, GL_FLOAT, false, vertexSizeBytes, spriteCordsOffset);
        glEnableVertexAttribArray(2);

        glVertexAttribPointer(3, spriteIDSize, GL_FLOAT, false, vertexSizeBytes, spriteIDOffset);
        glEnableVertexAttribArray(3);
    }

    //Adds sprites to the RenderBatches as long as they don't already exist
    public void addSprite(SpriteRenderer sprite) {
        int index = this.numSprites;
        this.spriteRenderers[index] = sprite;
        this.numSprites++;

        if (sprite.getTexture() != null) {
            if (!textures.contains(sprite.getTexture())) {
                textures.add(sprite.getTexture());
            }
        }

        loadVertexProperties(index);

        if (numSprites >= this.maxBatchSize) {
            this.hasRoom = false;
        }
    }


    //Uses the previously created vertices and then updates the sprites already drawn and then re buffers. Then it
    // uses the shader class, adds matrices created in default.glsl and binds them to the shader. To then draw the 2
    // triangles that makes up the sprite
    public void render() {
        boolean rebufferData = false;
        for (int i = 0; i < numSprites; i++) {
            SpriteRenderer spriteR = spriteRenderers[i];
            if (spriteR.isDirty()) {
                loadVertexProperties(i);
                spriteR.setClean();
                rebufferData = true;
            }
        }

        if (rebufferData) {
            glBindBuffer(GL_ARRAY_BUFFER, vboID);
            glBufferSubData(GL_ARRAY_BUFFER, 0, vertices);
        }

        shader.use();
        shader.uploadMat4f("uProjectionMatrix", Window.getCurrentRoom().getCamera().getProjectionMatrix());
        shader.uploadMat4f("uViewMatrix", Window.getCurrentRoom().getCamera().getViewMatrix());
        for (int i = 0; i < textures.size(); i++) {
            glActiveTexture(GL_TEXTURE0 + i + 1);
            textures.get(i).bind();
        }
        shader.uploadIntArray("uTextures", textureSlots);

        glBindVertexArray(vaoID);
        glEnableVertexAttribArray(0);
        glEnableVertexAttribArray(1);

        glDrawElements(GL_TRIANGLES, this.numSprites * 6, GL_UNSIGNED_INT, 0);

        glDisableVertexAttribArray(0);
        glDisableVertexAttribArray(1);
        glBindVertexArray(0);

        for (int i = 0; i < textures.size(); i++) {
            textures.get(i).unbind();
        }
        shader.detach();
    }

    //Calculates the vertices of the sprite to locate where the triangles that make up the sprite are
    private void loadVertexProperties(int index) {
        SpriteRenderer sprite = this.spriteRenderers[index];

        int offset = index * 4 * vertexSize;

        Vector4f color = sprite.getColor();
        Vector2f[] spriteCords = sprite.getTextureCords();

        int spriteID = 0;
        if (sprite.getTexture() != null) {
            for (int i = 0; i < textures.size(); i++) {
                if (textures.get(i) == sprite.getTexture()) {
                    spriteID = i + 1;
                    break;
                }
            }
        }


        float xAdd = 1.0f;
        float yAdd = 1.0f;
        for (int i = 0; i < 4; i++) {
            if (i == 1) {
                yAdd = 0.0f;
            } else if (i == 2) {
                xAdd = 0.0f;
            } else if (i == 3) {
                yAdd = 1.0f;
            }

            vertices[offset] = sprite.gameObject.getTransform().getPosition().x + (xAdd * sprite.gameObject.getTransform().getScale().x);
            vertices[offset + 1] = sprite.gameObject.getTransform().getPosition().y + (yAdd * sprite.gameObject.getTransform().getScale().y);

            vertices[offset + 2] = color.x;
            vertices[offset + 3] = color.y;
            vertices[offset + 4] = color.z;
            vertices[offset + 5] = color.w;

            vertices[offset + 6] = spriteCords[i].x;
            vertices[offset + 7] = spriteCords[i].y;

            vertices[offset + 8] = spriteID;

            offset += vertexSize;
        }
    }

    //Creates a batch of elements which is used to create the buffer data
    private int[] generateIndices() {
        int[] elements = new int [6 * maxBatchSize];
        for (int i = 0; i < maxBatchSize; i++) {
            loadElementIndices(elements, i);
        }

        return elements;
    }

    private void loadElementIndices(int[] elements, int index) {
        int offsetArrayIndex = 6 * index;
        int offset = 4 * index;

        elements[offsetArrayIndex] = offset + 3;
        elements[offsetArrayIndex + 1] = offset + 2;
        elements[offsetArrayIndex + 2] = offset + 0;

        elements[offsetArrayIndex + 3] = offset + 0;
        elements[offsetArrayIndex + 4] = offset + 2;
        elements[offsetArrayIndex + 5] = offset + 1;
    }

    public boolean hasRoom() {
        return this.hasRoom;
    }

    public boolean hasSpriteRoom() {
        return this.textures.size() < 8;
    }

    public boolean hasSprite(Texture texture) {
        return this.textures.contains(texture);
    }

    public int zIndex() {
        return this.zIndex;
    }

    @Override
    public int compareTo(RenderBatch o) {
        return Integer.compare(this.zIndex, o.zIndex());
    }
}

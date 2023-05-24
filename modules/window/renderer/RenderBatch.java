package hiof.gameenigne2d.modules.window.renderer;

import hiof.gameenigne2d.modules.object.GameObject;
import hiof.gameenigne2d.modules.object.components.SpriteRenderer;
import hiof.gameenigne2d.modules.window.Window;
import hiof.gameenigne2d.modules.window.utils.AssetPool;
import org.joml.Matrix4f;
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

    private SpriteRenderer[] SpriteRenderers;
    private int numSprites;
    private boolean hasRoom;
    private float[] vertices;
    private int[] textureSlots = {0, 1, 2, 3, 4, 5 ,6 ,7};

    private List<Texture> Textures;
    private int vaoID, vboID;
    private int maxBatchSize;
    private Shader shader;
    private int zIndex;


    public RenderBatch(int maxBatchSize, int zIndex) {
        this.zIndex = zIndex;
        shader = AssetPool.getShader();
        shader.compileAndLink();
        this.SpriteRenderers = new SpriteRenderer[maxBatchSize];
        this.maxBatchSize = maxBatchSize;

        vertices = new float[maxBatchSize * 4 * vertexSize];

        this.numSprites = 0;
        this.hasRoom = true;
        this.Textures = new ArrayList<>();
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
        this.SpriteRenderers[index] = sprite;
        this.numSprites++;

        if (sprite.getTexture() != null) {
            if (!Textures.contains(sprite.getTexture())) {
                Textures.add(sprite.getTexture());
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
            SpriteRenderer spriteR = SpriteRenderers[i];
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
        for (int i = 0; i < Textures.size(); i++) {
            glActiveTexture(GL_TEXTURE0 + i + 1);
            Textures.get(i).bind();
        }
        shader.uploadIntArray("uTextures", textureSlots);

        glBindVertexArray(vaoID);
        glEnableVertexAttribArray(0);
        glEnableVertexAttribArray(1);

        glDrawElements(GL_TRIANGLES, this.numSprites * 6, GL_UNSIGNED_INT, 0);

        glDisableVertexAttribArray(0);
        glDisableVertexAttribArray(1);
        glBindVertexArray(0);

        for (int i = 0; i < Textures.size(); i++) {
            Textures.get(i).unbind();
        }
        shader.detach();
    }

    //Calculates the vertices of the sprite to locate where the triangles that make up the sprite are
    private void loadVertexProperties(int index) {
        SpriteRenderer sprite = this.SpriteRenderers[index];

        int offset = index * 4 * vertexSize;

        Vector4f color = sprite.getColor();
        Vector2f[] spriteCords = sprite.getTextureCords();

        int spriteID = 0;
        if (sprite.getTexture() != null) {
            for (int i = 0; i < Textures.size(); i++) {
                if (Textures.get(i) == sprite.getTexture()) {
                    spriteID = i + 1;
                    break;
                }
            }
        }

        boolean isRotated = sprite.gameObject.getTransform().getRotation()  != 0.0f;
        Matrix4f transformMatrix = new Matrix4f().identity();
        if (isRotated) {
            transformMatrix.translate(sprite.gameObject.getTransform().getPosition().x,
                    sprite.gameObject.getTransform().getPosition().y, 0);
            transformMatrix.rotate((float) Math.toRadians(sprite.gameObject.getTransform().getRotation()),
                    0, 0, 1);
            transformMatrix.scale(sprite.gameObject.getTransform().getScale().x,
                    sprite.gameObject.getTransform().getScale().y, 1);
        }

        float xAdd = 0.5f;
        float yAdd = 0.5f;
        for (int i = 0; i < 4; i++) {
            if (i == 1) {
                yAdd = -0.5f;
            } else if (i == 2) {
                xAdd = -0.5f;
            } else if (i == 3) {
                yAdd = 0.5f;
            }

            Vector4f currentPosition = new Vector4f(sprite.gameObject.getTransform().getPosition().x +
                    (xAdd * sprite.gameObject.getTransform().getScale().x), sprite.gameObject.getTransform().getPosition().y +
                    (yAdd * sprite.gameObject.getTransform().getScale().y), 0, 1);

            if (isRotated) {
                currentPosition = new Vector4f(xAdd, yAdd, 0, 1).mul(transformMatrix);
            }

            vertices[offset] = currentPosition.x;
            vertices[offset + 1] = currentPosition.y;

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
        return this.Textures.size() < 8;
    }

    public boolean hasSprite(Texture texture) {
        return this.Textures.contains(texture);
    }

    public int zIndex() {
        return this.zIndex;
    }

    @Override
    public int compareTo(RenderBatch o) {
        return Integer.compare(this.zIndex, o.zIndex());
    }

    public boolean destroyIfExists(GameObject object) {
        SpriteRenderer sprite = object.getComponent(SpriteRenderer.class);
        for (int i = 0; i < numSprites; i++) {
            if (SpriteRenderers[i] == sprite) {
                for (int j = i; j < numSprites - 1; j++) {
                    SpriteRenderers[j] = SpriteRenderers[j + 1];
                    SpriteRenderers[j].setDirty();
                }
                numSprites--;
                return true;
            }
        }

        return false;
    }
}

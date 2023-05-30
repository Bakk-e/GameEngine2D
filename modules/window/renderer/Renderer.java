package hiof.gameenigne2d.modules.window.renderer;

import hiof.gameenigne2d.modules.object.components.SpriteRenderer;
import hiof.gameenigne2d.modules.object.GameObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Renderer {
    private final int maxBatchSize = 1000;
    private List<RenderBatch> batches;


    //Creates batches and adds sprites to later be rendered by the corresponding RenderBatch
    public Renderer() {
        this.batches = new ArrayList<>();
    }

    public void add(GameObject go) {
        SpriteRenderer sprite = go.getComponent(SpriteRenderer.class);
        if (sprite != null) {
            add(sprite);
        }
    }

    private void add(SpriteRenderer spriteRenderer) {
        boolean added = false;
        for (RenderBatch batch : batches) {
            if (batch.hasRoom() && batch.zIndex() == spriteRenderer.gameObject.zIndex()) {
                Texture texture = spriteRenderer.getTexture();
                if (texture == null || (batch.hasSprite(texture) || batch.hasSpriteRoom())) {
                    batch.addSprite(spriteRenderer);
                    added = true;
                    break;
                }
            }
        }

        if (!added) {
            RenderBatch newBatch = new RenderBatch(maxBatchSize, spriteRenderer.gameObject.zIndex());
            newBatch.start();
            batches.add(newBatch);
            newBatch.addSprite(spriteRenderer);
            Collections.sort(batches);
        }
    }

    /**
     * Starts the renderer that renders the images to screen
     */
    public void render() {
        for (RenderBatch batch : batches) {
            batch.render();
        }
    }

    public void destroyObject(GameObject object) {
        if (object.getComponent(SpriteRenderer.class) == null) return;
        for (RenderBatch batch : batches) {
            if (batch.destroyIfExists(object)) {
                return;
            }
        }
    }
}

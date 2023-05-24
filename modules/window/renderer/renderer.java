package HIOF.GameEnigne2D.modules.window.renderer;

import HIOF.GameEnigne2D.modules.object.components.spriterenderer;
import HIOF.GameEnigne2D.modules.object.gameobject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class renderer {
    private final int maxBatchSize = 1000;
    private List<renderbatch> batches;


    //Creates batches and adds sprites to later be rendered by the corresponding RenderBatch
    public renderer() {
        this.batches = new ArrayList<>();
    }

    public void add(gameobject go) {
        spriterenderer sprite = go.getComponent(spriterenderer.class);
        if (sprite != null) {
            add(sprite);
        }
    }

    private void add(spriterenderer spriteRenderer) {
        boolean added = false;
        for (renderbatch batch : batches) {
            if (batch.hasRoom() && batch.zIndex() == spriteRenderer.gameObject.zIndex()) {
                texture texture = spriteRenderer.getTexture();
                if (texture == null || (batch.hasSprite(texture) || batch.hasSpriteRoom())) {
                    batch.addSprite(spriteRenderer);
                    added = true;
                    break;
                }
            }
        }

        if (!added) {
            renderbatch newBatch = new renderbatch(maxBatchSize, spriteRenderer.gameObject.zIndex());
            newBatch.start();
            batches.add(newBatch);
            newBatch.addSprite(spriteRenderer);
            Collections.sort(batches);
        }
    }

    public void render() {
        for (renderbatch batch : batches) {
            batch.render();
        }
    }

    public void destroyObject(gameobject object) {
        if (object.getComponent(spriterenderer.class) == null) return;
        for (renderbatch batch : batches) {
            if (batch.destroyIfExists(object)) {
                return;
            }
        }
    }
}

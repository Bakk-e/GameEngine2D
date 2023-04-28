package HIOF.GameEnigne2D.utils;

import HIOF.GameEnigne2D.components.Spritesheet;
import HIOF.GameEnigne2D.renderer.Shader;
import HIOF.GameEnigne2D.renderer.Texture;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class AssetPool {
    private static Map<String, Shader> shaders = new HashMap<>();
    private static Map<String, Texture> textures = new HashMap<>();
    private static Map<String, Spritesheet> spritesheets = new HashMap<>();


    //Divides the processes into HashMaps so that what should be process together is processed together. To make sure
    // the rendering is as efficient as possible
    public static Shader getShader() {
        String resourceName = "assets/shaders/default.glsl";
        File file = new File(resourceName);
        if (shaders.containsKey(file.getAbsolutePath())) {
            return shaders.get(file.getAbsolutePath());
        } else {
            Shader shader = new Shader(resourceName);
            shader.compileAndLink();
            AssetPool.shaders.put(file.getAbsolutePath(), shader);
            return shader;
        }
    }

    public static Texture getTexture(String resourceName) {
        File file = new File(resourceName);
        if (AssetPool.textures.containsKey(file.getAbsolutePath())) {
            return AssetPool.textures.get(file.getAbsolutePath());
        } else {
            Texture texture = new Texture(resourceName);
            AssetPool.textures.put(file.getAbsolutePath(), texture);
            return texture;
        }
    }

    /**
     * @param resourceName filepath
     * @param spriteWidth width of each individual sprite
     * @param spriteHeight height of each individual sprite
     * @param numberOfSprites number of individual sprites
     * @param spacing spacing between sprites
     */
    public static void addSpriteSheet(String resourceName, int spriteWidth, int spriteHeight, int numberOfSprites, int spacing) {
        File file = new File(resourceName);
        Spritesheet spritesheet = new Spritesheet(AssetPool.getTexture(resourceName), spriteWidth, spriteHeight, numberOfSprites, spacing);
        if (!AssetPool.spritesheets.containsKey(file.getAbsolutePath())) {
            AssetPool.spritesheets.put(file.getAbsolutePath(), spritesheet);
        }
    }

    /**
     * @param resourceName filepath
     * @return list of sprites
     */
    public static Spritesheet getSpritesheet(String resourceName) {
        File file = new File(resourceName);
        if (!AssetPool.spritesheets.containsKey(file.getAbsolutePath())) {
            assert false : "Error: Tried to access spritesheet that has not been added to asset pool";
        }
        return AssetPool.spritesheets.getOrDefault(file.getAbsolutePath(), null);
    }
}

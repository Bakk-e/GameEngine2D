package HIOF.GameEnigne2D.modules.window.utils;

import HIOF.GameEnigne2D.modules.object.components.spritesheet;
import HIOF.GameEnigne2D.modules.window.renderer.shader;
import HIOF.GameEnigne2D.modules.window.renderer.sound;
import HIOF.GameEnigne2D.modules.window.renderer.texture;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class assetpool {
    private static Map<String, shader> shaders = new HashMap<>();
    private static Map<String, texture> textures = new HashMap<>();
    private static Map<String, spritesheet> spritesheets = new HashMap<>();
    private static Map<String, sound> sounds = new HashMap<>();


    //Divides the processes into HashMaps so that what should be process together is processed together. To make sure
    // the rendering is as efficient as possible
    public static shader getShader() {
        String resourceName = "assets/shaders/default.glsl";
        File file = new File(resourceName);
        if (shaders.containsKey(file.getAbsolutePath())) {
            return shaders.get(file.getAbsolutePath());
        } else {
            shader shader = new shader(resourceName);
            shader.compileAndLink();
            assetpool.shaders.put(file.getAbsolutePath(), shader);
            return shader;
        }
    }

    public static texture getTexture(String resourceName) {
        File file = new File(resourceName);
        if (assetpool.textures.containsKey(file.getAbsolutePath())) {
            return assetpool.textures.get(file.getAbsolutePath());
        } else {
            texture texture = new texture(resourceName);
            assetpool.textures.put(file.getAbsolutePath(), texture);
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
        spritesheet spritesheet = new spritesheet(assetpool.getTexture(resourceName), spriteWidth, spriteHeight, numberOfSprites, spacing);
        File file = new File(resourceName);
        if (!assetpool.spritesheets.containsKey(file.getAbsolutePath())) {
            assetpool.spritesheets.put(file.getAbsolutePath(), spritesheet);
        }
    }

    /**
     * @param resourceName filepath
     * @return list of sprites
     */
    public static spritesheet getSpritesheet(String resourceName) {
        File file = new File(resourceName);
        if (!assetpool.spritesheets.containsKey(file.getAbsolutePath())) {
            assert false : "Error: Tried to access spritesheet that has not been added to asset pool";
        }
        return assetpool.spritesheets.getOrDefault(file.getAbsolutePath(), null);
    }

    public static sound getSound(String filepath) {
        File file = new File(filepath);
        if (sounds.containsKey(file.getAbsolutePath())) {
            return sounds.get(file.getAbsolutePath());
        } else {
            assert false : "Sound file not added '" + filepath + "'";
        }

        return null;
    }

    public static sound addSound(String filepath, boolean loops) {
        File file = new File(filepath);
        if (sounds.containsKey(file.getAbsolutePath())) {
            return sounds.get(file.getAbsolutePath());
        } else {
            sound sound = new sound(filepath, loops);
            assetpool.sounds.put(file.getAbsolutePath(), sound);
            return sound;
        }
    }
}

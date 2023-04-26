package HIOF.GameEnigne2D.utils;

import HIOF.GameEnigne2D.renderer.Shader;
import HIOF.GameEnigne2D.renderer.Sprite;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class AssetPool {
    private static Map<String, Shader> shaders = new HashMap<>();
    private static Map<String, Sprite> sprites = new HashMap<>();

    public static Shader getShader(String resourceName) {
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

    public static Sprite getSprite(String resourceName) {
        File file = new File(resourceName);
        if (AssetPool.sprites.containsKey(file.getAbsolutePath())) {
            return AssetPool.sprites.get(file.getAbsolutePath());
        } else {
            Sprite sprite = new Sprite(resourceName);
            AssetPool.sprites.put(file.getAbsolutePath(), sprite);
            return sprite;
        }
    }
}

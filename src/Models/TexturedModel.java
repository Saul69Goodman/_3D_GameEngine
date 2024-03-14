package Models;

import Textures.ModelTexture;

public class TexturedModel {

    private Model rawModel;
    private ModelTexture texture;

    public TexturedModel(Model rawModel, ModelTexture texture){
        this.rawModel = rawModel;
        this.texture = texture;
    }

    /**
     * Get the raw model.
     *
     * @return         	the raw model
     */
    public Model getModel() {
        return rawModel;
    }

    /**
     * Get the Texture
     *
     * @return         	the texture
     */
    public ModelTexture getTexture() {
        return texture;
    }
}

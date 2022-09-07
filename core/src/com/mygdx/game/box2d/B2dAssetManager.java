package com.mygdx.game.box2d;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;

public class B2dAssetManager {

  public final AssetManager manager = new AssetManager();

  // Textures
  public final String playerImage = "box2d/player.png";
  public final String enemyImage = "box2d/enemy.png";

  public void queueAddImages() {
    manager.load(playerImage, Texture.class);
    manager.load(enemyImage, Texture.class);
  }
}

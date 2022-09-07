package com.mygdx.game.box2d;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;

public class Box2Game extends ApplicationAdapter {
  public B2dAssetManager assMan = new B2dAssetManager();

  B2dModel model;
  OrthographicCamera cam;
  Box2DDebugRenderer debugRenderer;
  float delta = 0.02f;
  Texture playerTex;
  BodyFactory bodyFactory;
  SpriteBatch sb;

  @Override
  public void create() {
    model = new B2dModel();
    bodyFactory = BodyFactory.getInstance(model.world);
    sb = new SpriteBatch();
    cam = new OrthographicCamera(32, 24);
    debugRenderer = new Box2DDebugRenderer(true, true, true, true, true, true);
    sb.setProjectionMatrix(cam.combined);
    // tells our asset manger that we want to load the images set in loadImages method
    assMan.queueAddImages();
    // tells the asset manager to load the images and wait until finsihed loading.
    assMan.manager.finishLoading();
    // gets the images as a texture
    playerTex = assMan.manager.get("box2d/player.png");
  }

  @Override
  public void render() {
    model.logicStep(delta);
    Gdx.gl.glClearColor(0f, 0f, 0f, 1);
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    debugRenderer.render(model.world, cam.combined);
    sb.begin();
    sb.draw(playerTex, model.player.getPosition().x - 1, model.player.getPosition().y - 1, 2, 2);
    sb.end();
  }

  @Override
  public void dispose() {
  }

}

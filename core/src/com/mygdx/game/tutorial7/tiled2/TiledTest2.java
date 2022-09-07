package com.mygdx.game.tutorial7.tiled2;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class TiledTest2 extends ApplicationAdapter {
  TiledMap tiledMap;
  OrthographicCamera camera;
  TiledMapRenderer tiledMapRenderer;

  Stage stage;
  SpriteBatch sb;
  PikachuActor pikachu;

  @Override
  public void create() {
    sb = new SpriteBatch();
    float w = Gdx.graphics.getWidth();
    float h = Gdx.graphics.getHeight();
    camera = new OrthographicCamera();
    camera.setToOrtho(false, w, h);
    camera.update();
    tiledMap = new TmxMapLoader().load("tiled/first-map.tmx");
    final MapLayer collisionLayer = tiledMap.getLayers().get("collisions");
    final MapLayer buffLayer = tiledMap.getLayers().get("flowers");
    tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);

    pikachu = new PikachuActor(collisionLayer.getObjects(), buffLayer.getObjects());

    stage = new Stage();
    stage.addActor(pikachu);
  }

  @Override
  public void render() {
    Gdx.gl.glClearColor(1, 0, 0, 1);
    Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    camera.update();
    tiledMapRenderer.setView(camera);
    tiledMapRenderer.render();

    stage.draw();

  }

  @Override
  public void dispose() {
    super.dispose();
    sb.dispose();
    stage.dispose();
  }

}
package com.mygdx.game.tutorial7;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

public class TiledTest extends ApplicationAdapter implements InputProcessor {
  Texture img;
  TiledMap tiledMap;
  OrthographicCamera camera;
  TiledMapRenderer tiledMapRenderer;
  SpriteBatch sb;
  Sprite pikachuSprite;
  boolean isFlipped = false;

  @Override
  public void create() {
    sb = new SpriteBatch();
    float w = Gdx.graphics.getWidth();
    float h = Gdx.graphics.getHeight();
    camera = new OrthographicCamera();
    camera.setToOrtho(false, w, h);
    camera.update();
    tiledMap = new TmxMapLoader().load("tiled/first-map.tmx");
    tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);
    Gdx.input.setInputProcessor(this);

    pikachuSprite = new Sprite(new Texture("data/pikachu.png"));
    pikachuSprite.setPosition(0, 0);
  }

  @Override
  public void render() {
    Gdx.gl.glClearColor(1, 0, 0, 1);
    Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    camera.update();
    tiledMapRenderer.setView(camera);
    tiledMapRenderer.render();

    int speed = 2;

    if (Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT)) {
      speed *= 2;
    }

    if (Gdx.input.isKeyPressed(Input.Keys.W)) {
      pikachuSprite.translateY(speed);
    }

    if (Gdx.input.isKeyPressed(Input.Keys.D)) {
      pikachuSprite.translateX(speed);
      if (isFlipped) {
        isFlipped = false;
        pikachuSprite.flip(true, false);
      }
    }

    if (Gdx.input.isKeyPressed(Input.Keys.A)) {
      pikachuSprite.translateX(-speed);
      if (!isFlipped) {
        isFlipped = true;
        pikachuSprite.flip(true, false);
      }
    }

    if (Gdx.input.isKeyPressed(Input.Keys.S)) {
      pikachuSprite.translateY(-speed);
    }
    sb.begin();
    pikachuSprite.draw(sb);
    sb.end();

  }

  @Override
  public void dispose() {
    super.dispose();
    sb.dispose();
  }

  @Override
  public boolean keyDown(int keycode) {
    return false;
  }

  @Override
  public boolean keyUp(int keycode) {
    if (keycode == Input.Keys.LEFT) {
      camera.translate(-32, 0);
    }
    if (keycode == Input.Keys.RIGHT) {
      camera.translate(32, 0);
    }
    if (keycode == Input.Keys.UP) {
      camera.translate(0, -32);
    }
    if (keycode == Input.Keys.DOWN) {
      camera.translate(0, 32);
    }
    if (keycode == Input.Keys.NUM_1) {
      tiledMap.getLayers().get(0).setVisible(!tiledMap.getLayers().get(0).isVisible());
    }
    if (keycode == Input.Keys.NUM_2) {
      tiledMap.getLayers().get(1).setVisible(!tiledMap.getLayers().get(1).isVisible());
    }
    return false;
  }

  @Override
  public boolean keyTyped(char character) {

    return false;
  }

  @Override
  public boolean touchDown(int screenX, int screenY, int pointer, int button) {
    return false;
  }

  @Override
  public boolean touchUp(int screenX, int screenY, int pointer, int button) {
    return false;
  }

  @Override
  public boolean touchDragged(int screenX, int screenY, int pointer) {
    return false;
  }

  @Override
  public boolean mouseMoved(int screenX, int screenY) {
    return false;
  }

  @Override
  public boolean scrolled(float amountX, float amountY) {
    return false;
  }

}
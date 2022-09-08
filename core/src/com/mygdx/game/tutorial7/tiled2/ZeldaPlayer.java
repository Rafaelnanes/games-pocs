package com.mygdx.game.tutorial7.tiled2;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;

import java.util.ArrayList;
import java.util.List;

public class ZeldaPlayer extends Actor {

  public static final int VELOCITY = 100;
  private static final float FRAME_DURATION = 0.1f;
  private final Animation<TextureAtlas.AtlasRegion> idleAnimation;
  private final Animation<TextureAtlas.AtlasRegion> runRightAnimation;
  private final Animation<TextureAtlas.AtlasRegion> runLeftAnimation;
  private final Animation<TextureAtlas.AtlasRegion> runUpAnimation;
  private final Animation<TextureAtlas.AtlasRegion> runDownAnimation;
  private final List<Rectangle> rectanglesCollisions;
  private final List<Rectangle> bufferCollisions;
  private final Rectangle playerRectangle;
  float stateTime = 0f;

  public ZeldaPlayer(MapObjects collisions, MapObjects bufferCollision) {
    rectanglesCollisions = getCollisions(collisions);
    bufferCollisions = getCollisions(bufferCollision);

    TextureAtlas textureAtlas = new TextureAtlas(Gdx.files.internal("zelda/zelda.atlas"));
    idleAnimation = new Animation<>(FRAME_DURATION * 2, textureAtlas.findRegions("idle"), Animation.PlayMode.LOOP);
    runRightAnimation =
        new Animation<>(FRAME_DURATION, textureAtlas.findRegions("runRight"), Animation.PlayMode.LOOP_REVERSED);
    runLeftAnimation =
        new Animation<>(FRAME_DURATION, textureAtlas.findRegions("runLeft"), Animation.PlayMode.LOOP);
    runUpAnimation = new Animation<>(FRAME_DURATION, textureAtlas.findRegions("runUp"), Animation.PlayMode.LOOP);
    runDownAnimation = new Animation<>(FRAME_DURATION, textureAtlas.findRegions("runDown"), Animation.PlayMode.LOOP);

    final TextureAtlas.AtlasRegion anyRegion = idleAnimation.getKeyFrame(0);
    playerRectangle = new Rectangle(0, 0, anyRegion.getRegionWidth(), anyRegion.getRegionHeight());
  }

  @Override
  public void draw(Batch batch, float parentAlpha) {
    final float oldX = playerRectangle.getX();
    final float oldY = playerRectangle.getY();
    float speed = VELOCITY * Gdx.graphics.getDeltaTime();
    stateTime += Gdx.graphics.getDeltaTime();
    TextureRegion keyFrame = idleAnimation.getKeyFrame(stateTime, true);

    if (Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT)) {
      speed *= 2;
    }

    for (Rectangle rectanglesCollision : bufferCollisions) {
      if (playerRectangle.overlaps(rectanglesCollision)) {
        speed *= 2;
        break;
      }
    }

    if (Gdx.input.isKeyPressed(Input.Keys.W)) {
      playerRectangle.setY(playerRectangle.getY() + speed);
      keyFrame = runUpAnimation.getKeyFrame(stateTime, true);
    }

    if (Gdx.input.isKeyPressed(Input.Keys.D)) {
      playerRectangle.setX(playerRectangle.getX() + speed);
      keyFrame = runRightAnimation.getKeyFrame(stateTime, true);
    }

    if (Gdx.input.isKeyPressed(Input.Keys.A)) {
      playerRectangle.setX(playerRectangle.getX() - speed);
      keyFrame = runLeftAnimation.getKeyFrame(stateTime, true);
    }

    if (Gdx.input.isKeyPressed(Input.Keys.S)) {
      playerRectangle.setY(playerRectangle.getY() - speed);
      keyFrame = runDownAnimation.getKeyFrame(stateTime, true);
    }

    for (Rectangle rectanglesCollision : rectanglesCollisions) {
      if (playerRectangle.overlaps(rectanglesCollision)) {
        playerRectangle.setPosition(oldX, oldY);
        break;
      }
    }

    batch.draw(keyFrame, playerRectangle.getX(), playerRectangle.getY());

  }

  private List<Rectangle> getCollisions(MapObjects collisions) {
    final List<Rectangle> rectangles = new ArrayList<>();
    for (int i = 0; i < collisions.getCount(); i++) {
      Rectangle rectangle = ((RectangleMapObject) collisions.get(i)).getRectangle();
      rectangles.add(rectangle);
    }
    return rectangles;
  }

}

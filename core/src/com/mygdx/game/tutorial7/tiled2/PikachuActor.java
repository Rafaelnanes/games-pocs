package com.mygdx.game.tutorial7.tiled2;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;

import java.util.ArrayList;
import java.util.List;

public class PikachuActor extends Actor {

  private final List<Rectangle> rectanglesCollisions;
  private final List<Rectangle> bufferCollisions;
  public Sprite sprite;
  private boolean isFlipped = false;

  public PikachuActor(MapObjects collisions, MapObjects bufferCollision) {
    rectanglesCollisions = getCollisions(collisions);
    bufferCollisions = getCollisions(bufferCollision);
    sprite = new Sprite(new Texture("data/pikachu.png"));
    sprite.setPosition(0, 0);
    sprite.setScale(0.7f);

  }

  @Override
  public void draw(Batch batch, float parentAlpha) {
    final float oldX = sprite.getX();
    final float oldY = sprite.getY();

    int speed = 2;

    if (Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT)) {
      speed *= 2;
    }

    for (Rectangle rectanglesCollision : bufferCollisions) {
      if (sprite.getBoundingRectangle().overlaps(rectanglesCollision)) {
        speed *= 2;
        break;
      }
    }

    if (Gdx.input.isKeyPressed(Input.Keys.W)) {
      sprite.translateY(speed);
    }

    if (Gdx.input.isKeyPressed(Input.Keys.D)) {
      sprite.translateX(speed);
      if (isFlipped) {
        isFlipped = false;
        sprite.flip(true, false);
      }
    }

    if (Gdx.input.isKeyPressed(Input.Keys.A)) {
      sprite.translateX(-speed);
      if (!isFlipped) {
        isFlipped = true;
        sprite.flip(true, false);
      }
    }

    if (Gdx.input.isKeyPressed(Input.Keys.S)) {
      sprite.translateY(-speed);
    }

    for (Rectangle rectanglesCollision : rectanglesCollisions) {
      if (sprite.getBoundingRectangle().overlaps(rectanglesCollision)) {
        sprite.setPosition(oldX, oldY);
        break;
      }
    }

    sprite.draw(batch);

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

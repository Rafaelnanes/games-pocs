package com.mygdx.game.tutorial6;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;

import java.util.Random;

public class SceneManagementDemo2 implements ApplicationListener {

  private Jet[] jets;
  private Stage stage;

  @Override
  public void create() {
    stage = new Stage();
    final TextureRegion jetTexture = new TextureRegion(new Texture("data/jet.png"));

    jets = new Jet[10];

    // Create/seed our random number for positioning jets randomly
    Random random = new Random();

    // Create 10 Jet objects at random on screen locations
    for (int i = 0; i < 10; i++) {
      jets[i] = new Jet(jetTexture);

      //Assign the position of the jet to a random value within the screen boundaries
      jets[i].setPosition(random.nextInt(Gdx.graphics.getWidth() - (int) jets[i].getWidth())
          , random.nextInt(Gdx.graphics.getHeight() - (int) jets[i].getHeight()));

      // Set the name of the Jet to it's index within the loop
      jets[i].setName(Integer.toString(i));

      // Add them to the stage
      stage.addActor(jets[i]);
    }

    Gdx.input.setInputProcessor(stage);
  }

  @Override
  public void dispose() {
    stage.dispose();
  }

  @Override
  public void render() {
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    stage.act(Gdx.graphics.getDeltaTime());
    stage.draw();
  }

  @Override
  public void resize(int width, int height) {
  }

  @Override
  public void pause() {
  }

  @Override
  public void resume() {
  }


  // Create an Actor "Jet" that displays the TextureRegion passed in
  class Jet extends Actor {
    private TextureRegion _texture;

    public Jet(TextureRegion texture) {
      _texture = texture;
      setBounds(getX(), getY(), _texture.getRegionWidth(), _texture.getRegionHeight());

      this.addListener(new InputListener() {
        public boolean touchDown(InputEvent event, float x, float y, int pointer, int buttons) {
          System.out.println("Touched" + getName());
          setVisible(false);
          return true;
        }
      });
    }

    // Implement the full form of draw() so we can handle rotation and scaling.
    public void draw(Batch batch, float alpha) {
      batch.draw(_texture, getX(), getY(), getOriginX(), getOriginY(), getWidth(), getHeight(),
          getScaleX(), getScaleY(), getRotation());
    }

    // This hit() instead of checking against a bounding box, checks a bounding circle.
    public Actor hit(float x, float y, boolean touchable) {
      // If this Actor is hidden or untouchable, it cant be hit
      if (!this.isVisible() || this.getTouchable() == Touchable.disabled) {
        return null;
      }
      System.out.println(x);
      // Get centerpoint of bounding circle, also known as the center of the rect
      float centerX = getWidth() / 2;
      float centerY = getHeight() / 2;

      // Square roots are bad m'kay. In "real" code, simply square both sides for much speedy fastness
      // This however is the proper, unoptimized and easiest to grok equation for a hit within a circle
      // You could of course use LibGDX's Circle class instead.

      // Calculate radius of circle
      float radius = (float) Math.sqrt(centerX * centerX +
                                       centerY * centerY);

      // And distance of point from the center of the circle
      float distance = (float) Math.sqrt(((centerX - x) * (centerX - x))
                                         + ((centerY - y) * (centerY - y)));

      // If the distance is less than the circle radius, it's a hit
      if (distance <= radius) {
        return this;
      }

      // Otherwise, it isnt
      return null;
    }
  }
}
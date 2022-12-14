package com.mygdx.game.tutorial5;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.scenes.scene2d.actions.RotateToAction;
import com.badlogic.gdx.scenes.scene2d.actions.ScaleToAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;


public class SceneDemo3 implements ApplicationListener {

  private Stage stage;

  @Override
  public void create() {
    stage = new Stage();
    Gdx.input.setInputProcessor(stage);

    final MyActor myActor = new MyActor();

    SequenceAction sequenceAction = new SequenceAction();

    MoveToAction moveAction = new MoveToAction();
    RotateToAction rotateAction = new RotateToAction();
    ScaleToAction scaleAction = new ScaleToAction();

    moveAction.setPosition(300f, 0f);
    moveAction.setDuration(5f);
    rotateAction.setRotation(90f);
    rotateAction.setDuration(5f);
    scaleAction.setScale(0.5f);
    scaleAction.setDuration(5f);

    sequenceAction.addAction(scaleAction);
    sequenceAction.addAction(rotateAction);
    sequenceAction.addAction(moveAction);

    //    myActor.addAction(sequenceAction);
    myActor.addAction(Actions.parallel(scaleAction, rotateAction, moveAction));

    stage.addActor(myActor);
  }

  @Override
  public void dispose() {
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


  public class MyActor extends Actor {
    public boolean started = false;
    Texture texture = new Texture(Gdx.files.internal("data/jet.png"));

    public MyActor() {
      setBounds(getX(), getY(), texture.getWidth(), texture.getHeight());
    }

    @Override
    public void draw(Batch batch, float alpha) {
      batch.draw(texture, this.getX(), getY(), this.getOriginX(), this.getOriginY(), this.getWidth(), this.getHeight(),
          this.getScaleX(), this.getScaleY(), this.getRotation(), 0, 0, texture.getWidth(), texture.getHeight(), false,
          false);
    }
  }
}
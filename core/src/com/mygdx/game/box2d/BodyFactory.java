package com.mygdx.game.box2d;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;

public class BodyFactory {
  public static final int STEEL = 0;
  public static final int WOOD = 1;
  public static final int RUBBER = 2;
  public static final int STONE = 3;
  private static BodyFactory instance;
  private World world;

  private BodyFactory(World world) {
    this.world = world;
  }

  public static BodyFactory getInstance(World world) {
    if (instance == null) {
      instance = new BodyFactory(world);
    }
    return instance;
  }

  static public FixtureDef makeFixture(int material, Shape shape) {
    FixtureDef fixtureDef = new FixtureDef();
    fixtureDef.shape = shape;

    switch (material) {
      case 0:
        fixtureDef.density = 1f;
        fixtureDef.friction = 0.3f;
        fixtureDef.restitution = 0.1f;
        break;
      case 1:
        fixtureDef.density = 0.5f;
        fixtureDef.friction = 0.7f;
        fixtureDef.restitution = 0.3f;
        break;
      case 2:
        fixtureDef.density = 1f;
        fixtureDef.friction = 0f;
        fixtureDef.restitution = 1f;
        break;
      case 3:
        fixtureDef.density = 1f;
        fixtureDef.friction = 0.9f;
        fixtureDef.restitution = 0.01f;
      default:
        fixtureDef.density = 7f;
        fixtureDef.friction = 0.5f;
        fixtureDef.restitution = 0.3f;
    }
    return fixtureDef;
  }

  public Body makeCirclePolyBody(float posx,
                                 float posy,
                                 float radius,
                                 int material,
                                 BodyDef.BodyType bodyType,
                                 boolean fixedRotation) {
    // create a definition
    BodyDef boxBodyDef = new BodyDef();
    boxBodyDef.type = bodyType;
    boxBodyDef.position.x = posx;
    boxBodyDef.position.y = posy;
    boxBodyDef.fixedRotation = fixedRotation;

    //create the body to attach said definition
    Body boxBody = world.createBody(boxBodyDef);
    CircleShape circleShape = new CircleShape();
    circleShape.setRadius(radius / 2);
    boxBody.createFixture(makeFixture(material, circleShape));
    circleShape.dispose();
    return boxBody;
  }

  public Body makeBoxPolyBody(float posx,
                              float posy,
                              float width,
                              float height,
                              int material,
                              BodyDef.BodyType bodyType) {
    return makeBoxPolyBody(posx, posy, width, height, material, bodyType, false);
  }

  public Body makeBoxPolyBody(float posx,
                              float posy,
                              float width,
                              float height,
                              int material,
                              BodyDef.BodyType bodyType,
                              boolean fixedRotation) {
    // create a definition
    BodyDef boxBodyDef = new BodyDef();
    boxBodyDef.type = bodyType;
    boxBodyDef.position.x = posx;
    boxBodyDef.position.y = posy;
    boxBodyDef.fixedRotation = fixedRotation;

    //create the body to attach said definition
    Body boxBody = world.createBody(boxBodyDef);
    PolygonShape poly = new PolygonShape();
    poly.setAsBox(width / 2, height / 2);
    boxBody.createFixture(makeFixture(material, poly));
    poly.dispose();

    return boxBody;
  }

  public Body makePolygonShapeBody(Vector2[] vertices,
                                   float posx,
                                   float posy,
                                   int material,
                                   BodyDef.BodyType bodyType) {
    BodyDef boxBodyDef = new BodyDef();
    boxBodyDef.type = bodyType;
    boxBodyDef.position.x = posx;
    boxBodyDef.position.y = posy;
    Body boxBody = world.createBody(boxBodyDef);

    PolygonShape polygon = new PolygonShape();
    polygon.set(vertices);
    boxBody.createFixture(makeFixture(material, polygon));
    polygon.dispose();

    return boxBody;
  }

}

package com.paladinzzz.game.screens.worldobjects;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.paladinzzz.game.screens.worldobjects.visitor.objectVisitor;
import com.paladinzzz.game.sprites.Mole;
import com.paladinzzz.game.util.Constants;

//Deze klas beschrijft waar de bounce pads worden geplaatst binnen onze wereld
//We roepen uit de TiledMap alle Rectangles op van de bouncyBlock layer, vervolgens spawnen we daar een bouncyBlock

public class bounceObject implements com.paladinzzz.game.screens.worldobjects.IObject {
    private Body body;
    private Mole player;
    private Rectangle rect;
    private FixtureDef fdef;
    private BodyDef bdef;
    private PolygonShape shape;

    public bounceObject() {
        bdef = new BodyDef();
        shape = new PolygonShape();
        fdef = new FixtureDef();
        shape = new PolygonShape();
    }

    @Override
    public void defineObject(World world, TiledMap map) {
        //De grond objecten zijn het 5e object in onze map editor, beginnend bij 0 is dat het 4e object in code
        for (MapObject object : map.getLayers().get(4).getObjects().getByType(RectangleMapObject.class)) {
            this.rect = ((RectangleMapObject) object).getRectangle();
            bdef.type = BodyDef.BodyType.StaticBody;

            //De positie delen we door 2, omdat libgdx begint in het midden van elke vorm.
            bdef.position.set((rect.getX() + rect.getWidth() / 2) / Constants.PPM, (rect.getY() + rect.getHeight() / 2) / Constants.PPM);
            this.body = world.createBody(bdef);
            shape.setAsBox(rect.getWidth() / 2 / Constants.PPM, rect.getHeight() / 2 / Constants.PPM);

            fdef.filter.categoryBits = Constants.BOUNCY_BIT; //De bouncyblocks zijn dus BOUNCY_BITs

            fdef.shape = shape;
            fdef.restitution = (float) 1.2;
            body.createFixture(fdef);
            body.setUserData(this);
        }
    }

    @Override
    public void getType() {
        System.out.println("I am a bounceObject!");
    }

    @Override
    public void visit(objectVisitor visitor) {
    }
}

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

//Deze klas beschrijft waar de dodelijke objecten (water, spikes, etc.) worden geplaatst binnen onze wereld
//We roepen uit de TiledMap alle Rectangles op van de fluidKillable layer, vervolgens spawnen we daar blokken die de speler doden wanneer deze elkaar aanraken

public class fluidKillable implements IObject {

    private Body body;
    private com.paladinzzz.game.sprites.Mole player;
    private Rectangle rect;
    private FixtureDef fdef;
    private BodyDef bdef;
    private PolygonShape shape;

    public fluidKillable() {
        bdef = new BodyDef();
        shape = new PolygonShape();
        fdef = new FixtureDef();
        shape = new PolygonShape();
    }

    @Override
    public void defineObject(World world, TiledMap map) {
        //De water/lava objecten zijn het 7e object in onze map editor, beginnend bij 0 is dat het 5e object in code
        for (MapObject object : map.getLayers().get(5).getObjects().getByType(RectangleMapObject.class)) {
            this.rect = ((RectangleMapObject) object).getRectangle();
            bdef.type = BodyDef.BodyType.StaticBody;

            //De positie delen we door 2, omdat libgdx begint in het midden van elke vorm.
            bdef.position.set((rect.getX() + rect.getWidth() / 2) / com.paladinzzz.game.util.Constants.PPM, (rect.getY() + rect.getHeight() / 2) / com.paladinzzz.game.util.Constants.PPM);
            body = world.createBody(bdef);
            shape.setAsBox(rect.getWidth() / 2 / com.paladinzzz.game.util.Constants.PPM, rect.getHeight() / 2 / com.paladinzzz.game.util.Constants.PPM);

            fdef.filter.categoryBits = com.paladinzzz.game.util.Constants.FLUID_BIT; //Het water/lava is dus een FLUID_BIT

            fdef.shape = shape;
            body.createFixture(fdef);
            body.setUserData(this);
        }
    }

    @Override
    public void getType() {
        System.out.println("I am a fluidKillableObject!");
    }

    @Override
    public void visit(objectVisitor visitor) {
    }
}

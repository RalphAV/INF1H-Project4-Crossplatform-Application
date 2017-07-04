package com.paladinzzz.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.paladinzzz.game.util.Constants;


public class OptionScreen implements Screen{

    private com.paladinzzz.game.CrossplatformApp game;
    private Stage stage;
    private OrthographicCamera camera;
    private Table table;
    private Skin skin;
    private ImageButton backButton;
    private Drawable drawableBack;
    private Texture background, backTexture;
    private Sound click = Gdx.audio.newSound(Gdx.files.internal("Audio/click.wav"));

    public OptionScreen(com.paladinzzz.game.CrossplatformApp game) {
        this.game = game;
        this.camera = new OrthographicCamera();
        this.stage = new Stage(new FillViewport(com.paladinzzz.game.util.Constants.WIDTH, com.paladinzzz.game.util.Constants.HEIGHT, camera));
        this.skin = new Skin(Gdx.files.internal("skin/uiskin.json"));
        this.backTexture = new Texture("Screens/BackButton.png");
        this.background = new Texture("Screens/OptionsScreen/OptionsScreen_InProgress.png");
    }


    @Override
    public void show() {


        //Geef de texture van de backButton mee aan een nieuwe ImageButton
        drawableBack = new TextureRegionDrawable(new TextureRegion(backTexture));
        backButton = new ImageButton(drawableBack);
        backButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new MenuScreen(game));
                MenuScreen.musicHandler.stopMusic();
                click.play(2.0f);
            }
        });

        //Hiermee kunnen elementen nu aan de stage worden toegevoegd
        Gdx.input.setInputProcessor(stage);


        //Een table wordt aangemaakt om de back button toe te voegen.
        table = new Table();
        table.bottom();
        table.setFillParent(true);
        table.add(backButton).padBottom(13).padRight(640);
        stage.addActor(table);
    }

    @Override
    public void render(float delta) {
        game.batch.begin();
        game.batch.draw(background, 0, 0, Constants.WIDTH, Constants.HEIGHT);
        game.batch.end();
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        game.batch.setProjectionMatrix(stage.getCamera().combined);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        stage.dispose();

    }
}

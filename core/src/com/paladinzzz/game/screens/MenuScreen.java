package com.paladinzzz.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.paladinzzz.game.audio.MusicHandler;
import com.paladinzzz.game.util.Constants;
import com.paladinzzz.game.util.TempMS;

import static com.badlogic.gdx.Gdx.input;



public class MenuScreen implements Screen {

    private com.paladinzzz.game.CrossplatformApp game;
    private Stage stage;
    private ImageButton exitButton, playButton, optionsButton, highscoreButton;
    private Texture exitTexture, playTexture, optionsTexture, highscoreTexture, background;
    private Drawable drawableExit, drawablePlay, drawableOptions, drawableHighscore;
    private OrthographicCamera camera;
    public static MusicHandler musicHandler;
    boolean inMenuScreen = true;
    BitmapFont font = new BitmapFont();
    int[] x = new int[255];
    public static boolean inPlayscreen = false;
    private Table table;
    private Sound click = Gdx.audio.newSound(Gdx.files.internal("Audio/click.wav"));

    private int amountbackspacepressed = 0;

    public TempMS tempMS;

    public MenuScreen(com.paladinzzz.game.CrossplatformApp game, MusicHandler musicHandler) {
        this.game = game;
        this.camera = new OrthographicCamera();
        this.stage = new Stage(new FillViewport(com.paladinzzz.game.util.Constants.WIDTH, com.paladinzzz.game.util.Constants.HEIGHT, camera));
        this.exitTexture = new Texture("Screens/TitleScreen/ExitGameButton.png");
        this.playTexture = new Texture("Screens/TitleScreen/LevelButton.png");
        this.optionsTexture = new Texture("Screens/TitleScreen/OptionsButton.png");
        this.highscoreTexture = new Texture("Screens/TitleScreen/HighscoresButton.png");
        this.background = new Texture("Screens/TitleScreen/MainScreen.png");
        this.musicHandler = musicHandler;
        this.tempMS = new TempMS(this);
    }

    @Override
    public void show() {

        //Geef de texture van de exitButton mee aan de ImageButton
        drawableExit = new TextureRegionDrawable(new TextureRegion(exitTexture));
        exitButton = new ImageButton(drawableExit);
        exitButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                click.play(1.0f * Constants.soundLevel);
                Gdx.app.exit();
            }
        });

        //Geef de texture van de playButton mee aan de ImageButton
        drawablePlay = new TextureRegionDrawable(new TextureRegion(playTexture));
        playButton = new ImageButton(drawablePlay);
        playButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                click.play(1.0f * Constants.soundLevel);
                game.setScreen(new LoginScreen(game, tempMS));
                inPlayscreen = true;
            }
        });

        //Geef de texture van de optionsbutton mee aan de ImageButton
        drawableOptions = new TextureRegionDrawable(new TextureRegion(optionsTexture));
        optionsButton = new ImageButton(drawableOptions);
        optionsButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                click.play(1.0f * Constants.soundLevel);
                game.setScreen(new OptionScreen(game, tempMS));
                GameScreen.showtext = true;

            }
        });

        drawableHighscore = new TextureRegionDrawable(new TextureRegion(highscoreTexture));
        highscoreButton = new ImageButton(drawableHighscore);
        highscoreButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                click.play(1.0f * Constants.soundLevel);
                game.setScreen(new HighScoresScreen(game, tempMS));
            }
        });

        //Hiermee kunnen elementen nu aan de stage worden toegevoegd
        input.setInputProcessor(stage);

        //Een table wordt aangemaakt om buttons aan toe te voegen.
        table = new Table();
        table.top();
        table.setFillParent(true);
        table.add(playButton).padTop(59);
        table.row();
        table.add(optionsButton).padTop(5).padRight(5);
        table.row();
        table.add(highscoreButton).padTop(2).padRight(7);
        table.row();
        table.add(exitButton).padTop(5).padRight(10);
        stage.addActor(table);
    }

    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

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
        exitTexture.dispose();
        highscoreTexture.dispose();
        optionsTexture.dispose();
        playTexture.dispose();
        background.dispose();

    }

    public MusicHandler getMusicHandler(){
        return this.musicHandler;
    }
}

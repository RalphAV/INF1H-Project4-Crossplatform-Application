package com.paladinzzz.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.paladinzzz.game.CrossplatformApp;
import com.paladinzzz.game.database.JSONfunctions;
import com.paladinzzz.game.scenes.MenuHUD;
import com.paladinzzz.game.util.Constants;
import com.paladinzzz.game.util.TempMS;
import com.paladinzzz.game.util.playerMemory;

import static com.paladinzzz.game.screens.LoginScreen.playername;

//De class van het wereld selectie scherm

public class LevelScreen implements Screen {

    private OrthographicCamera camera;
    private CrossplatformApp game;
    public Stage levelstage, button2stage, button3stage;
    private Texture background, level1texture, level2texture, level3texture, backbutton, level2textureDeny, level3textureDeny;
    private ImageButton level1, level2, level3, back, level2NOT, level3NOT;
    private Drawable level1drawable, level2drawable, level3drawable, backdrawable, level2deny, level3deny;
    private Table table, table2, table3;
    private Label label2;
    private Label label3;
    private MenuHUD MenuHUD;
    private Sound click = Gdx.audio.newSound(Gdx.files.internal("Audio/click.wav"));
    private Viewport viewport;
    static boolean showtext = true;
    public final JSONfunctions s;

    private TempMS tempMS;

    public LevelScreen(CrossplatformApp game, TempMS tempMS) {
        this.game = game;
        this.camera = new OrthographicCamera();
        viewport = new FitViewport(Constants.WIDTH, Constants.HEIGHT, camera);
        this.levelstage = new Stage(viewport);
        this.background = new Texture("Screens/LevelScreen/LevelSelection.png");
        this.backbutton = new Texture("Screens/BackButton.png");
        this.level1texture = new Texture("Screens/LevelScreen/Button1.png");
        this.level2texture = new Texture("Screens/LevelScreen/Button2.png");
        this.level3texture = new Texture("Screens/LevelScreen/Button3.png");
        this.tempMS = tempMS;
        this.s = new JSONfunctions();
        this.level2textureDeny = new Texture("Screens/LevelScreen/Button2NOT.png");
        this.level3textureDeny = new Texture("Screens/LevelScreen/Button3NOT.png");
        label2 = new Label(("Complete level 1 first!"), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        label3 = new Label(("Complete level 2 first!"), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        this.MenuHUD = new MenuHUD(game.batch);

    }

    @Override
    public void show() {
        int showbutton = s.getHasLevel("haslevel1", playername);
        int showbutton2 = s.getHasLevel("haslevel2", playername);
        level1drawable = new TextureRegionDrawable(new TextureRegion(level1texture));
        level1 = new ImageButton(level1drawable);
        level1.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                click.play(Constants.soundLevel * 1.0f);
                tempMS.menuScreen.musicHandler.stopMusic();
                playerMemory.player.worldAndLevelData.setCurrentLevel(1);
                playerMemory.player.worldAndLevelData.setCurrentWorld(1);
                game.setScreen(new GameScreen(game, tempMS));
                levelstage.dispose();
                click.play(2.0f);
            }
        });

        level2drawable = new TextureRegionDrawable(new TextureRegion(level2texture));
        level2 = new ImageButton(level2drawable);
        level2.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                click.play(1.0f * Constants.soundLevel);
                System.out.println("Level 2 clicked");
                if ((s.getHasLevel("haslevel1", playername) == 1))  {
                    MenuScreen.musicHandler.stopMusic();
                    playerMemory.player.worldAndLevelData.setCurrentWorld(2);
                    playerMemory.player.worldAndLevelData.setCurrentLevel(1);
                    game.setScreen(new GameScreen(game, tempMS));
                    levelstage.dispose();
                } else {
                    System.out.println("Complete World 1 first!");
                }
            }
        });


        level3drawable = new TextureRegionDrawable(new TextureRegion(level3texture));
        level3 = new ImageButton(level3drawable);
        level3.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                click.play(1.0f * Constants.soundLevel);
                System.out.println("Level 3 clicked");
                System.out.println(s.getHasLevel("haslevel2", playerMemory.player.getName()));
                if (s.getHasLevel("haslevel2", playerMemory.player.getName()) == 1) {
                    MenuScreen.musicHandler.stopMusic();
                    playerMemory.player.worldAndLevelData.setCurrentWorld(3);
                    playerMemory.player.worldAndLevelData.setCurrentLevel(1);
                    game.setScreen(new GameScreen(game, tempMS));
                    levelstage.dispose();
                } else {
                    System.out.println("Complete World 2 first!");
                }
            }//
        });

        level2deny = new TextureRegionDrawable(new TextureRegion(level2textureDeny));
        level2NOT = new ImageButton(level2deny);
        level2NOT.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("Finish level 1 first");
                showtext = true;
                click.play(2.0f);
            }
        });

        level3deny = new TextureRegionDrawable(new TextureRegion(level3textureDeny));
        level3NOT = new ImageButton(level3deny);
        level3NOT.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("Finish level 2 first");
                showtext = true;
                click.play(2.0f);
            }
        });

        backdrawable = new TextureRegionDrawable(new TextureRegion(backbutton));
        back = new ImageButton(backdrawable);
        back.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                click.play(1.0f * Constants.soundLevel);
                game.setScreen(new LoginScreen(game, tempMS));

            }
        });

        Gdx.input.setInputProcessor(levelstage);

        table = new Table();
        table.setFillParent(true);
        table.top();
        table.add().expandX();
        table.add(level1).expandX().padTop(190);
        if (showbutton == 1) {
            table.add(level2).expandX().padTop(190);
        } else {
            table.add(level2NOT).expandX().padTop(190);
            if (Gdx.input.isTouched()) {
                MenuHUD.clicked = false;
                if (Gdx.input.isTouched()) {
                    MenuHUD.clicked = true;
                }
            }
        }

        if (showbutton2 == 1) {
            table.add(level3).expandX().padTop(190);
        } else {
            table.add(level3NOT).expandX().padTop(190);
            if (Gdx.input.isTouched()) {
                MenuHUD.clicked = false;
                if (Gdx.input.isTouched()) {
                    MenuHUD.clicked = true;
                }
            }
        }
        table.add().expandX();
        table.add().expandX();
        table.row();
        table.add(back).expandX().padTop(50);

        levelstage.addActor(table);

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.batch.setProjectionMatrix(levelstage.getCamera().combined);

        game.batch.begin();

        if (Gdx.input.isTouched()){
            MenuHUD.removeSpaceText();
        }

        game.batch.draw(background, 0, 0, Constants.WIDTH, Constants.HEIGHT);

        game.batch.end();
        levelstage.draw();

    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
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
        levelstage.dispose();
        background.dispose();
        level1texture.dispose();
        level2texture.dispose();
        level3texture.dispose();
        backbutton.dispose();
        this.button2stage.dispose();
        this.button3stage.dispose();
    }
}


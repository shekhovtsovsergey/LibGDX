package com.mygdx.game.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.MyAtlasAnim;
import com.mygdx.game.MyInputProcessor;
import com.mygdx.game.MyPhysX;

public class GameScreen implements Screen {
    Game game;
    SpriteBatch batch;
    Texture img, coinImg;
    MyAtlasAnim run, stand, tmpA;
    private Music music;
    private Sound sound;
    MyInputProcessor myInputProcessor;
    private  float x,y;
    int dir =0, step =1;
    private OrthographicCamera camera;
    private  MyPhysX myPhysX;
    private Body body;
    private TiledMap map;
    private OrthogonalTiledMapRenderer mapRenderer;
    private Sound sound_jump;


    public GameScreen(Game game) {
        this.game = game;


        //из create перенесено
        map = new TmxMapLoader().load("map/безымянный.tmx");
        mapRenderer = new OrthogonalTiledMapRenderer(map);

        img = new Texture("scene/bg.jpg");
        //прямоугольник
        myPhysX = new MyPhysX();//физика
        BodyDef def = new BodyDef(); //тело
        FixtureDef fixtureDef = new FixtureDef(); //фигура
        PolygonShape polygonShape = new PolygonShape();
        def.gravityScale = 1;//гравитация
        def.type=BodyDef.BodyType.StaticBody;//типа тела двигается/нет



        fixtureDef.shape=polygonShape;
        fixtureDef.density=1; //плотность
        fixtureDef.friction=0; //скользить
        fixtureDef.restitution=1; //прыгучесть


        MapLayer env = map.getLayers().get("env");
        Array<RectangleMapObject> rect = env.getObjects().getByType(RectangleMapObject.class);
        for (int i = 0; i < rect.size; i++) {
            float x = rect.get(i).getRectangle().x;
            float y = rect.get(i).getRectangle().y;
            float w = rect.get(i).getRectangle().width/2;
            float h = rect.get(i).getRectangle().height/2;
            def.position.set(x,y);//где находится
            polygonShape.setAsBox(w,h);
            myPhysX.world.createBody(def).createFixture(fixtureDef).setUserData("Kubik");//создает тело приделывает фикстуру
        }


        def.type=BodyDef.BodyType.DynamicBody;//типа тела двигается/нет
        env = map.getLayers().get("dyn");
        rect = env.getObjects().getByType(RectangleMapObject.class);
        for (int i = 0; i < rect.size; i++) {
            float x = rect.get(i).getRectangle().x;
            float y = rect.get(i).getRectangle().y;
            float w = rect.get(i).getRectangle().width/2;
            float h = rect.get(i).getRectangle().height/2;
            def.position.set(x,y);//где находится
            polygonShape.setAsBox(w,h);
            fixtureDef.density=1; //плотность
            fixtureDef.friction=0; //скользить
            fixtureDef.restitution=1; //прыгучесть
            myPhysX.world.createBody(def).createFixture(fixtureDef).setUserData("Kubik");//создает тело приделывает фикстуру
        }

        env = map.getLayers().get("hero");
        RectangleMapObject hero = (RectangleMapObject) env.getObjects().get("Hero");
        for (int i = 0; i < rect.size; i++) {
            float x = hero.getRectangle().x;
            float y = hero.getRectangle().y;
            float w = hero.getRectangle().width/2;
            float h = hero.getRectangle().height/2;
            def.position.set(x,y);//где находится
            polygonShape.setAsBox(w,h);
            fixtureDef.density=1; //плотность
            fixtureDef.friction=0; //скользить
            fixtureDef.restitution=1; //прыгучесть
            myPhysX.world.createBody(def).createFixture(fixtureDef).setUserData("Kubik");//создает тело приделывает фикстуру
        }



/*      //квадратики
		def.type=BodyDef.BodyType.DynamicBody;//типа тела двигается/нет
		for (int i = 0; i < 100; i++) {
			def.position.set(MathUtils.random(-120,120),MathUtils.random(120,220));//где находится
			polygonShape.setAsBox(10,10);
			fixtureDef.shape=polygonShape;

			fixtureDef.density=1; //плотность
			fixtureDef.friction=0; //скользить
			fixtureDef.restitution=1; //прыгучесть
			myPhysX.world.createBody(def).createFixture(fixtureDef).setUserData("Kubik");//создает тело приделывает фикстуру

		}
*/


        def.position.set(MathUtils.random(-120,120),MathUtils.random(120,220));//где находится
        polygonShape.setAsBox(5,5);
        fixtureDef.shape=polygonShape;
        fixtureDef.density=1; //плотность
        fixtureDef.friction=0; //скользить
        fixtureDef.restitution=1; //прыгучесть
        body = myPhysX.world.createBody(def);
        body.createFixture(fixtureDef).setUserData("Kubik");//создает тело приделывает фикстуру




        polygonShape.dispose();



        myInputProcessor = new MyInputProcessor();
        Gdx.input.setInputProcessor(myInputProcessor);



        /*music=Gdx.audio.newMusic(Gdx.files.internal("sounds/cf0fc01247f4fc1.mp3"));
        music.setVolume(0.00001f);
        music.setLooping(true);
        music.pause();

        sound=Gdx.audio.newSound(Gdx.files.internal("sounds/cf0fc01247f4fc1.mp3"));*/

        batch = new SpriteBatch();
        coinImg = new Texture("g.png");

        stand = new MyAtlasAnim("atlas/unnamed.atlas","stand", 8, true, "sounds/cf0fc01247f4fc1.mp3");
        run = new MyAtlasAnim("atlas/unnamed.atlas","run", 8, true, "sounds/cf0fc01247f4fc1.mp3");
        tmpA = stand;

        camera = new OrthographicCamera(Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        //camera.position.y=Gdx.graphics.getWidth()/2;
        //camera.position.x=Gdx.graphics.getHeight()/2;
        //camera.position.y=Gdx.graphics.getWidth()/2;
        //camera.position.x=Gdx.graphics.getHeight()/2;

		/*camera.position.x=0;
		camera.position.y=0;

		camera.update();
*/

        sound_jump = Gdx.audio.newSound(Gdx.files.internal("sounds/sound_jump.mp3"));

    }



    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

        ScreenUtils.clear(Color.BLACK);

        //это камера
        //camera.position.x=body.getPosition().x / 100;
        //camera.position.y=body.getPosition().y / 100 ;
        camera.position.x = body.getPosition().x * myPhysX.PPM;
        camera.position.y = body.getPosition().y * myPhysX.PPM;
        //camera.zoom=0.125f;
        camera.update();


        //Это карту передаем в камеру
        mapRenderer.setView(camera);
        mapRenderer.render();



        //ScreenUtils.clear(1, 1, 1, 1);


        tmpA = stand;
        dir = 0;
        //float x = Gdx.input.getX() - anim.draw().getRegionWidth()/2.0f;
        //float y = Gdx.graphics.getHeight() - Gdx.input.getY() - anim.draw().getRegionHeight()/2.0f;

        if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) sound.play();


        if (myInputProcessor.getOutString().contains("A")) {
            dir = -1;
            //x--;
            tmpA = run;
            body.applyForceToCenter(new Vector2(-10000, 0f), true);
        }
        if (myInputProcessor.getOutString().contains("D")){
            dir = 1;
            //x++;
            tmpA = run;
            body.applyForceToCenter(new Vector2(10000, 0f), true);
        }
        if (myInputProcessor.getOutString().contains("W")) {
            y++;
            tmpA = run;
        }
        if (myInputProcessor.getOutString().contains("S")) {
            y--;
            tmpA = run;
        }
        if (myInputProcessor.getOutString().contains("Space")) {
            sound_jump.play();
            //x= Gdx.graphics.getWidth()/2 ;
            //y= Gdx.graphics.getHeight()/2 ;
            body.applyForceToCenter(new Vector2(0, 100000f), true);
        }


        if (dir == -1) x-= step;
        if (dir == 1) x+= step;

        TextureRegion tmp = tmpA.draw();
        if (!tmpA.draw().isFlipX() & dir == -1) tmpA.draw().flip(true,false);
        if (tmpA.draw().isFlipX() & dir == 1) tmpA.draw().flip(true,false);


        float x = body.getPosition().x - 2.5f/camera.zoom;
        float y = body.getPosition().y - 2.5f/camera.zoom;

        tmpA.setTime(Gdx.graphics.getDeltaTime());
        //System.out.println(myInputProcessor.getOutString());
        //batch.setProjectionMatrix(camera.combined);//установка камеры
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        batch.draw(img, 0, 0);
        batch.draw(tmpA.draw(), x, y, 5/camera.zoom, 5/camera.zoom);
        batch.end();


        myPhysX.step();//обновление физики
        myPhysX.debugDraw(camera);



    }

    @Override
    public void resize(int width, int height) {
        camera.viewportHeight = height;
        camera.viewportWidth = width;
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
        batch.dispose();
        img.dispose();
        run.dispose();
        stand.dispose();
        music.dispose();
        sound.dispose();
        map.dispose();
        mapRenderer.dispose();

    }






}



package com.zeldiablo.factories;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.zeldiablo.models.traps.Trap;

public class SoundFactory {

    private static SoundFactory instance;

    public static final float VOLUME = 0.3f;

    private static final String SOUND = "sounds/";
    public final Sound attack;
    public final Sound coins;
    public final Sound hurt;
    public final Sound lose;
    public final Sound portal;
    public final Sound touch;

    private static final String MUSIC = SOUND + "music/";
    public final Music misc_game;
    public final Music misc_title;
    public Music win;

    private SoundFactory() {
        this.attack = loadSound("attack.wav");
        this.coins  = loadSound("coins.wav");
        this.hurt   = loadSound("hurt.wav");
        this.lose   = loadSound("lose.wav");
        //this.win = loadSound("win.wav");
        this.portal = loadSound("portal.wav");
        this.touch  = loadSound("touch.wav");

        this.misc_game  = loadMusic("game.mp3");
        this.misc_title = loadMusic("title.wav");
    }

    public static SoundFactory getInstance() {
        if (instance == null) {
            instance = new SoundFactory();
        }

        return instance;
    }

    /**
     * Permet de charger un son
     * @param soundName nom du fichier a charger
     * @return Sound
     */
    private Sound loadSound(String soundName) {
        Sound sound = Gdx.audio.newSound(Gdx.files.internal(SOUND + soundName));
        return sound;
    }

    /**
     * Permet de charger une musique
     * @param musicName nom du fichier
     * @return Music
     */
    private Music loadMusic(String musicName) {
        Music music = Gdx.audio.newMusic(Gdx.files.internal(MUSIC + musicName));
        music.setLooping(true);
        music.setVolume(VOLUME);
        return music;
    }
}

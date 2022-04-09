package ru.maxed.photocleaner.utility;

import java.io.*;

public class Settings implements Serializable {
    private String path;
    private String mainExpansive;
    private String secondaryExpansive;

    public Settings(String path, String mainExpansive, String secondaryExpansive) {
        this.path = path;
        this.mainExpansive = mainExpansive;
        this.secondaryExpansive = secondaryExpansive;
    }
    public Settings(File settings){

        try(ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(settings))) {
            setFields( (Settings) objectInputStream.readObject());
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public String getPath() {
        return path;
    }

    public String getMainExpansive() {
        return mainExpansive;
    }

    public String getSecondaryExpansive() {
        return secondaryExpansive;
    }

    public void setFields(Settings settings) {
        this.path = settings.path;
        this.mainExpansive = settings.mainExpansive;
        this.secondaryExpansive = settings.secondaryExpansive;
    }

    public void save(){
        File settings = new File("settings.cfg");
        try {
            settings.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try(ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(settings))) {
            objectOutputStream.writeObject(this);
        } catch (Exception e){
            e.printStackTrace();
        }

    }

}

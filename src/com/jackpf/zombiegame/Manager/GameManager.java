package com.jackpf.zombiegame.Manager;

public class GameManager
{
    private static GameManager instance = null;
    
    private GameManager()
    {
        // Singleton
    }
    
    public static GameManager getInstance()
    {
        if (instance == null) {
            instance = new GameManager();
        }
        
        return instance;
    }
}
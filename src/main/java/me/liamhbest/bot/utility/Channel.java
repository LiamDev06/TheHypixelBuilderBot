package me.liamhbest.bot.utility;

public enum Channel {

    COMMANDS(868236616001519708L);

    private final long id;
    public long getId(){
        return id;
    }

    Channel(long id){
        this.id = id;
    }

}

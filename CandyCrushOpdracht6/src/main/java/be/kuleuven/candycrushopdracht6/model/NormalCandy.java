package be.kuleuven.candycrushopdracht6.model;

public record NormalCandy(int color) implements Candy{
    public NormalCandy{
        if(color < 0 || color >3){
            throw new IllegalArgumentException("out of range");
        }
    }
}

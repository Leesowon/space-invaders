package org.newdawn.spaceinvaders.entity.item;

import org.newdawn.spaceinvaders.Game;
import org.newdawn.spaceinvaders.entity.Entity;
import org.newdawn.spaceinvaders.entity.ShipEntity;

import java.awt.*;
import java.util.ArrayList;

public class AilenSlowItemEntity extends ItemEntity{
    private Rectangle me = new Rectangle();
    private Rectangle him = new Rectangle();
    private long effectTime = 5000;
    private long startTime = 0;
    private ArrayList ailen = new ArrayList();



    public AilenSlowItemEntity(Game game, int x, int y) {
        super(game, "sprites/AilenSlowItem.png", x, y);
        dx = 0;
        dy = moveSpeed * 0.5;
    }
    @Override
    public boolean collidesWith(Entity other) {
        me.setBounds((int) x, (int) y, sprite.getWidth(), sprite.getHeight());
        him.setBounds(other.getX(), other.getY(), other.getSprite().getWidth(), other.getSprite().getHeight());

        return me.intersects(him);
    }

    public void collidedWith(Entity other) {
        if(other instanceof ShipEntity){
            if(used){return;}
            game.removeEntity(this);
            game.addItem(this);
            used = true;
            startTime = System.currentTimeMillis();
        }
    }

    @Override
    public void move(long delta) {
        super.move(delta);
    }

    @Override
    public void doItemLogic() {
        if(used){
            if(!is_apply){
                ailen = game.getAilen();
                for(int i=0;i< ailen.size();i++){
                    ((Entity)ailen.get(i)).setDx(((Entity)ailen.get(i)).getDx()/10);
                }
                is_apply = true;
            }
            if(System.currentTimeMillis() - startTime > effectTime){
                ailen = game.getAilen();
                for(int i=0;i< ailen.size();i++){
                    ((Entity)ailen.get(i)).setDx(((Entity)ailen.get(i)).getDx()*10);
                }
                game.removeItem(this);
            }
        }
    }
    public void resetItemEffect(){
        for(int i=0;i< ailen.size();i++){
            ((Entity)ailen.get(i)).setDx(((Entity)ailen.get(i)).getDx()*2);
        }
    }

}

package io.github.Farm.Interface;

import io.github.Farm.ui.Other.GreenBar;
import io.github.Farm.ui.Other.RedBar;

public class Heath {

    private float currHp;
    private final float maxHp;
    private RedBar healBar;

    public Heath(float hp){
        this.currHp =hp;
        this.maxHp=hp;
        this.healBar=new RedBar();
    }

    public void damaged(float damage){
        currHp-=damage;
    }

    public void heal(float increase){
        if(currHp+increase<=maxHp){
            this.currHp+=increase;
            return;
        }
        this.currHp=maxHp;
    }

    public boolean isDie() {
        return currHp <=0;
    }

    public float getCurrHp(){
        return currHp;
    }

    public void setCurrHp(float currHp) {
        this.currHp = currHp;
    }

    public RedBar getHealBar() {
        return healBar;
    }

    public float getMaxHp() {
        return maxHp;
    }

    public void dispose() {
        if (healBar != null) {
            healBar.dispose();
        }
    }
}

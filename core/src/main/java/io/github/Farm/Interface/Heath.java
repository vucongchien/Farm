package io.github.Farm.Interface;

public class Heath {

    private float currHp;
    private final float maxHp;

    public Heath(float hp){
        this.currHp =hp;
        this.maxHp=hp;
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

}

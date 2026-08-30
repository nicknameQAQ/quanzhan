package edu.hue.jk2.wxj.domain;

public class EnemyCharacter extends CharacterBean{
    //敌方游戏角色
    String skill;
    boolean defending;

    public EnemyCharacter() {
        super();
    }

    public EnemyCharacter(String name, int HP, int attack, int defense, String skill) {
        super(name, HP, attack, defense);
        this.skill = skill;
    }
    //重写敌方角色受到伤害的方法
    @Override
    public void takeDamage(int amount) {
        //如果处于防御状态，则受到的伤害减少一半
        if (defending) {
            amount = amount / 2 > 1 ? amount / 2 : 1;
            defending = false;
        }
        //调用父类的扣血方法
        super.takeDamage(amount);
    }
}

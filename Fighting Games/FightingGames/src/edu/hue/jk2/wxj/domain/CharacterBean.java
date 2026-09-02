package edu.hue.jk2.wxj.domain;

public class CharacterBean {
    //角色名称由登录而来（name）
    public String name;
    public int HP;
    public int maxHP;
    public int attack;
    public int defense;

    public CharacterBean() {
    }

    //刚创建角色时,血量是满的
    public CharacterBean(String name, int HP, int attack, int defense) {
        this.maxHP = HP;
        this.name = name;
        this.HP = HP;
        this.attack = attack;
        this.defense = defense;
    }
    //判断是否存活
    public boolean isAlive(){
        return HP > 0;
    }
    //恢复血量
    public void heal(int amount){
        HP += amount;
        if (HP > maxHP){
            HP = maxHP;
        }

    }
    //受到伤害
    public void takeDamage(int amount){
        HP -= amount;
        if (HP < 0){
            HP = 0;
        }
    }
    //展示人物属性
    public String show(){
        return name + "[当前生命:" + HP + ",  攻击：" + attack + ",   防御：" + defense + "]";
    }


}

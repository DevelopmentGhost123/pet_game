package edu.dselent.player.spring2024.team02;

import edu.dselent.skill.Skills;
import edu.dselent.skill.skilldata.SkillData;

public class SkillDetails {
    Skills skill;
    private int cooldown;
    private double damage;

    /***
     * Gives the cooldown and damage of a specified skill
     * @param skill
     */
    public SkillDetails(Skills skill) {
        this.skill = skill;
        this.cooldown = 0;
        this.damage = damage;
    }

    /***
     * Decrements the cool-down time by 1
     */
    public void decrementCooldown()
    {
        if(cooldown > 0)
        {
            cooldown--;
        }
    }

    /***
     * Returns whether there is a cool-down or not
     * @return
     */
    public boolean isCooldown()
    {
        if(cooldown >0)
            return true;
        return false;
    }


    public void setCooldown(int cooldown)
    {
        this.cooldown = cooldown;
    }
    public int getCooldown()
    {
        return this.cooldown;
    }

    public Skills getSkill()
    {
        return this.skill;
    }
}

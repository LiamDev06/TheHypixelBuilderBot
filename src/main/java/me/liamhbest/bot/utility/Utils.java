package me.liamhbest.bot.utility;

import net.dv8tion.jda.api.entities.Member;

public class Utils {

    public static boolean hasRole(Member member, String roleName) {
        for (int i=0; i<member.getRoles().size(); i++){
            if (roleName.equals(member.getRoles().get(i).getName())){
                return true;
            }
        }
        return false;
    }

}

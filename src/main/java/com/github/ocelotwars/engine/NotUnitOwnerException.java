package com.github.ocelotwars.engine;

public class NotUnitOwnerException extends RuntimeException {

    private NotUnitOwnerException(String message) {
        super(message);
    }
    
    public static NotUnitOwnerException forPlayerAndUnit(Player player, int unitId) {
        return new NotUnitOwnerException("Unit " + unitId + " is not under control of player " + player.getName());
    }

}

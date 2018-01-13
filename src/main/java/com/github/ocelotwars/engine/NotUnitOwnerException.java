package com.github.ocelotwars.engine;

public class NotUnitOwnerException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private NotUnitOwnerException(String message) {
        super(message);
    }

    public static NotUnitOwnerException forPlayerAndUnit(Player player, int unitId) {
        return new NotUnitOwnerException("Unit " + unitId + " is not under control of player " + player.getName());
    }

}

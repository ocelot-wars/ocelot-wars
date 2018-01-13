package com.github.ocelotwars.engine;

public class NoSuchAssetException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private NoSuchAssetException(String message) {
        super(message);
    }

    public static NoSuchAssetException forUnit(int unitId) {
        return new NoSuchAssetException("Unit " + unitId + " not found.");
    }

    public static NoSuchAssetException forHeadquarter(Player player) {
        return new NoSuchAssetException("Headquarter for " + player.getName() + " not found.");
    }

}

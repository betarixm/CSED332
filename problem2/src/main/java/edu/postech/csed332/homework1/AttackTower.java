package edu.postech.csed332.homework1;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public abstract class AttackTower implements Tower {
    public abstract GameBoard getBoard();
    public abstract boolean isTargetGround();

    @Override
    public Set<Monster> attack() {
        Position pos = getBoard().getPosition(this);
        int p_x = pos.getX();
        int p_y = pos.getY();

        Set<Position> positions = new HashSet<>(Arrays.asList(
                new Position(p_x - 1, p_y), new Position(p_x + 1, p_y),
                new Position(p_x, p_y - 1), new Position(p_x, p_y + 1)
        ));

        return positions.stream()
                .map(p -> getBoard().getUnitsAt(p))
                .flatMap(Collection::stream)
                .filter(u -> isTargetGround() ? u instanceof GroundMob : u instanceof AirMob)
                .map(u -> (Monster) u)
                .collect(Collectors.toSet());
    }
}

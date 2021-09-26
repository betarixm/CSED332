package edu.postech.csed332.homework2;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public abstract class BinaryExp implements Exp {
    protected List<Exp> subexps;

    BinaryExp(List<Exp> subexps) {
        this.subexps = subexps;
    }

    public Set<Integer> vars() {
        return subexps.stream()
                .map(Exp::vars)
                .filter(s -> s.size() != 0)
                .flatMap(Collection::stream)
                .collect(Collectors.toSet());
    }

    public abstract List<Exp> getSubexps();
}

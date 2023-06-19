package org.mg.task;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public class Wall implements Structure{
    private List<Block> blocks;

    @Override
    public Optional<Block> findBlockByColor(String color) {
        return blocks.stream()
                .filter(b -> b.getColor().equals(color))
                .findAny()
                .or(() -> blocks.stream()
                        .filter(b -> b instanceof CompositeBlock)
                        .map(CompositeBlock.class::cast)
                        .flatMap(cb -> cb.getBlocks().stream())
                        .filter(b -> b.getColor().equals(color))
                        .findAny());
    }

    @Override
    public List<Block> findBlocksByMaterial(String material) {
        return null;
    }

    @Override
    public int count() {
        return (int) blocks.stream().
                flatMap(b -> {
                    if (b instanceof CompositeBlock) {
                        return ((CompositeBlock) b).getBlocks().stream();
                    } else {
                        return Stream.of(b);
                    }
                }).count();
    }
}

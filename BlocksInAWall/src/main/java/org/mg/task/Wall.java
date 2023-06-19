package org.mg.task;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public class Wall implements Structure{
    private List<Block> blocks;

    @Override
    public Optional<Block> findBlockByColor(String color) {
        return Optional.empty();
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
                        return Stream.of(((CompositeBlock) b).getBlocks());
                    } else {
                        return Stream.of(b);
                    }
                }).count();
    }
}

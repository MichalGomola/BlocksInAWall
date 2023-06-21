package org.mg.task;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Wall implements Structure{
    private List<Block> blocks;

    @Override
    public Optional<Block> findBlockByColor(String color) {
        return prepareAllBlocks()
                .filter(b -> b.getColor().equals(color))
                .findAny();
    }

    @Override
    public List<Block> findBlocksByMaterial(String material) {
        return prepareAllBlocks().filter(b -> b.getMaterial().equals(material))
                .collect(Collectors.toList());
    }

    @Override
    public int count() {
        return (int) prepareAllBlocks().count();
    }

    private Stream<Block> prepareAllBlocks() {
        return blocks.stream().
                flatMap(b -> {
                    if (b instanceof CompositeBlock) {
                        return Stream.concat(Stream.of(b), prepareAllBlocks(((CompositeBlock) b).getBlocks())) ;
                    } else {
                        return Stream.of(b);
                    }
                });
    }

    private Stream<Block> prepareAllBlocks(List<Block> blocks) {
        return blocks.stream().
                flatMap(b -> {
                    if (b instanceof CompositeBlock) {
                        return Stream.concat(Stream.of(b), prepareAllBlocks(((CompositeBlock) b).getBlocks()));
                    } else {
                        return Stream.of(b);
                    }
                });
    }
}

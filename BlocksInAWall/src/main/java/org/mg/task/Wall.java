package org.mg.task;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Objects.isNull;
import static org.apache.commons.collections4.CollectionUtils.emptyIfNull;

public class Wall implements Structure{
    private List<Block> blocks;

    @Override
    public Optional<Block> findBlockByColor(String color) {
        if(isNull(color)) {return Optional.empty();}
        return prepareAllBlocks()
                .filter(b -> b.getColor().equals(color))
                .findAny();
    }

    @Override
    public List<Block> findBlocksByMaterial(String material) {
        if (isNull(material)) {return new ArrayList<>();}
        return prepareAllBlocks().filter(b -> b.getMaterial().equals(material))
                .collect(Collectors.toList());
    }

    @Override
    public int count() {
        return (int) prepareAllBlocks().count();
    }

    private Stream<Block> prepareAllBlocks() {
        return emptyIfNull(blocks).stream().
                flatMap(b -> {
                    if (b instanceof CompositeBlock) {
                        return Stream.concat(Stream.of(b), prepareAllBlocks(((CompositeBlock) b).getBlocks())) ;
                    } else {
                        return Stream.of(b);
                    }
                });
    }

    private Stream<Block> prepareAllBlocks(List<Block> blocks) {
        return emptyIfNull(blocks).stream().
                flatMap(b -> {
                    if (b instanceof CompositeBlock) {
                        return Stream.concat(Stream.of(b), prepareAllBlocks(((CompositeBlock) b).getBlocks()));
                    } else {
                        return Stream.of(b);
                    }
                });
    }
}

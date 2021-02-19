package io.github.followsclosley.reverse.impl;

import io.github.followsclosley.reverse.Coordinate;

public interface BoardChangedListener {
    void boardChanged(Coordinate coordinate);
}

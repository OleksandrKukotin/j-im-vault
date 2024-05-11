package org.github.oleksandrkukotin.jimvault.image;

import java.util.List;

public interface ImageRepository {

    void save(Image image);

    List<Image> findAll();

    List<Image> findImagesInSizeRange(int from, int to);
}

package org.github.oleksandrkukotin.jimvault.image;

import java.time.LocalDateTime;

public record Image(String name, LocalDateTime creatingTimestamp, String imageKeyOnS3, int size) {
}

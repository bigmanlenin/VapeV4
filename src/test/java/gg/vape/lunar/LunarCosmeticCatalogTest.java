package gg.vape.lunar;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

final class LunarCosmeticCatalogTest {
    @TempDir
    Path home;
    private String originalHome;

    @AfterEach
    void restoreHome() {
        if (originalHome != null) System.setProperty("user.home", originalHome);
        LunarCosmeticCatalog.resetForTests();
    }

    @Test
    void reloadsSparseHighIdsWhenCatalogChanges() throws Exception {
        originalHome = System.getProperty("user.home");
        System.setProperty("user.home", home.toString());
        Path catalog = home.resolve(".lunarclient/textures/assets/lunar/cosmetics.json");
        Files.createDirectories(catalog.getParent());
        Files.write(catalog, "[{\"id\":1},{\"id\":9674},{\"id\":42}]"
                .getBytes(StandardCharsets.UTF_8));
        LunarCosmeticCatalog.resetForTests();
        assertEquals(Arrays.asList(1, 42, 9674), LunarCosmeticCatalog.currentIds());

        Files.write(catalog, "[{\"id\":5},{\"id\":10001}]"
                .getBytes(StandardCharsets.UTF_8));
        assertEquals(Arrays.asList(5, 10001), LunarCosmeticCatalog.currentIds());
    }

    @Test
    void missingCatalogDoesNotFabricateIds() {
        originalHome = System.getProperty("user.home");
        System.setProperty("user.home", home.toString());
        LunarCosmeticCatalog.resetForTests();
        assertTrue(LunarCosmeticCatalog.currentIds().isEmpty());
    }
}

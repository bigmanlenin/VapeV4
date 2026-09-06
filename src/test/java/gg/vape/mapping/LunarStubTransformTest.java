package gg.vape.mapping;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

final class LunarStubTransformTest {
    @Test
    void legacyHooksAreRestrictedToJavaEight() {
        assertTrue(LunarStubTransform.legacyHooksAllowedForSpec("1.8"));
        assertFalse(LunarStubTransform.legacyHooksAllowedForSpec("17"));
        assertFalse(LunarStubTransform.legacyHooksAllowedForSpec("25"));
        assertFalse(LunarStubTransform.legacyHooksAllowedForSpec("unknown"));
    }
}

#include "lunar_unlock_engine.h"

#include <cstdlib>
#include <cstring>
#include <iostream>

namespace {

void require(bool condition, const char* message) {
    if (!condition) {
        std::cerr << "FAIL: " << message << '\n';
        std::exit(1);
    }
}

LunarFeatureResult result(LunarFeatureId feature, LunarFeatureState state,
                          int catalog, int owned) {
    LunarFeatureResult value{};
    value.feature = feature;
    value.state = state;
    value.catalog_count = catalog;
    value.owned_count = owned;
    return value;
}

}

int main() {
    LunarUnlockReport complete{};
    complete.features[0] = result(LUNAR_FEATURE_COSMETICS,
                                  LUNAR_FEATURE_UNLOCKED, 5513, 5513);
    complete.features[1] = result(LUNAR_FEATURE_JAMS,
                                  LUNAR_FEATURE_UNLOCKED, 97, 97);
    complete.features[2] = result(LUNAR_FEATURE_BADGES,
                                  LUNAR_FEATURE_UNAVAILABLE, 0, 0);
    complete.feature_count = 3;
    require(lunar_unlock_report_succeeded(&complete) == 1,
            "optional unavailable features do not fail a verified run");

    LunarUnlockReport partial = complete;
    partial.features[1].state = LUNAR_FEATURE_FAILED;
    require(lunar_unlock_report_succeeded(&partial) == 0,
            "a present failed feature fails the run");
    require(lunar_unlock_counts_match(5513, 5513, 5513) == 1,
            "exact live ownership counts verify");
    require(lunar_unlock_should_repair_manager(5513, 4, 4) == 1,
            "a reconnect replacement manager is repaired");
    require(lunar_unlock_should_repair_manager(5513, 5513, 5513) == 0,
            "a fully owned manager is not rewritten");
    require(std::strcmp(lunar_feature_name(LUNAR_FEATURE_LUNAR_PLUS),
                        "lunar_plus") == 0,
            "feature names remain stable");
    return 0;
}

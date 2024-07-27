import org.jetbrains.kotlin.fir.declarations.builder.buildScript


plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.jetbrains.kotlin.android) apply false
    id("androidx.navigation.safeargs") version "2.7.3" apply false
}
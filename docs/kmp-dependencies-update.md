# KMP AGP 8.x → 9.x Migration + Dependency Update

Migrate a Kotlin Multiplatform project from AGP 8.x to AGP 9.x (`com.android.kotlin.multiplatform.library`) and update all other dependencies to their latest stable versions. Apply ALL steps in order. Read each file before editing it.

**Primary scope — AGP 8.x → 9.x migration:**
The plugin `com.android.library` is replaced by `com.android.kotlin.multiplatform.library`. This requires structural changes to the root `build.gradle.kts`, the Gradle wrapper, and `gradle.properties`. The migration also splits the old KMP+Android-app module into two: a pure `com.android.application` launcher and a KMP library entry point.

**Secondary scope — dependency refresh:**
Beyond the AGP-driven changes, bump all other library and plugin versions in `libs.versions.toml` to their latest stable releases. The table in Step 2a shows the versions used in the `spicy-mayo` reference project as a baseline, but always check for newer releases before applying.

---

## Before you start — read the project structure

Run these before making any changes:
1. Read `settings.gradle.kts` to learn the module names
2. Read `gradle/libs.versions.toml` to see current versions and what exists
3. Read `build.gradle.kts` (root) to understand the current function names and structure
4. Read `gradle.properties` to know what needs adding/removing
5. `ls` the repo root to find the Android app module (named `app`, `androidApp`, or similar)

Use this information to adapt the project-specific steps below.

---

## Step 1 — Gradle wrapper

**File:** `gradle/wrapper/gradle-wrapper.properties`

Change:
```
distributionUrl=https\://services.gradle.org/distributions/gradle-8.x.x-bin.zip
```
To:
```
distributionUrl=https\://services.gradle.org/distributions/gradle-9.4.1-bin.zip
```

---

## Step 2 — libs.versions.toml

**File:** `gradle/libs.versions.toml`

### 2a. Version bumps

The table below shows what was bumped in the `spicy-mayo` reference project. For each entry, apply the listed target version OR the latest stable release if a newer one is available — whichever is higher. Check Maven Central / the library's release page before applying.

| Key | spicy-mayo baseline | spicy-mayo target | Check for newer at |
|-----|--------------------|--------------------|-------------------|
| `agp` | `8.13.1` | `9.1.1` | developer.android.com/build/releases |
| `android-targetSdk` | `36` | `37` | — |
| `kotlin` | `2.2.21` | `2.3.20` | kotlinlang.org/docs/releases.html |
| `detektComposeRules` | `0.4.27` | `0.5.7` | github.com/mrmans0n/compose-rules/releases |
| `androidxActivityCompose` | `1.11.0` | `1.13.0` | developer.android.com/jetpack/androidx/releases/activity |
| `composeMultiplatform` | `1.9.3` | `1.10.3` | github.com/JetBrains/compose-multiplatform/releases |
| `kotlinxSerialization` | `1.9.0` | `1.11.0` | github.com/Kotlin/kotlinx.serialization/releases |
| `ktor` | `3.3.2` | `3.4.2` | ktor.io/changelog |
| `datastore` | `1.1.7` | `1.2.1` | developer.android.com/jetpack/androidx/releases/datastore |
| `koin` | `4.1.1` | `4.2.1` | github.com/InsertKoinIO/koin/releases |
| `navigationCompose` | `2.9.1` | `2.9.2` | developer.android.com/jetpack/androidx/releases/navigation |

### 2b. Add new version entries (if missing)

```toml
composeMaterial3 = "1.9.0"
lifecycle = "2.10.0"
```

For these new entries also check for a newer stable release before applying.

### 2c. Add new library entries (if missing)

```toml
lifecycle-runtime-compose = { group = "org.jetbrains.androidx.lifecycle", name = "lifecycle-runtime-compose", version.ref = "lifecycle" }
lifecycle-viewmodel-compose = { group = "org.jetbrains.androidx.lifecycle", name = "lifecycle-viewmodel-compose", version.ref = "lifecycle" }
compose-runtime = { group = "org.jetbrains.compose.runtime", name = "runtime", version.ref = "composeMultiplatform" }
compose-foundation = { group = "org.jetbrains.compose.foundation", name = "foundation", version.ref = "composeMultiplatform" }
compose-material3 = { group = "org.jetbrains.compose.material3", name = "material3", version.ref = "composeMaterial3" }
compose-ui = { group = "org.jetbrains.compose.ui", name = "ui", version.ref = "composeMultiplatform" }
compose-components-resources = { group = "org.jetbrains.compose.components", name = "components-resources", version.ref = "composeMultiplatform" }
compose-components-ui-tooling-preview = { group = "org.jetbrains.compose.components", name = "components-ui-tooling-preview", version.ref = "composeMultiplatform" }
```

### 2d. Plugin changes

Remove (if present):
```toml
android-library = { id = "com.android.library", version.ref = "agp" }
```

Add (if missing):
```toml
android-kotlin-multiplatform-library = { id = "com.android.kotlin.multiplatform.library", version.ref = "agp" }
kotlin-android = { id = "org.jetbrains.kotlin.android", version.ref = "kotlin" }
```

---

## Step 3 — gradle.properties

**File:** `gradle.properties`

Remove if present:
- `kotlin.mpp.androidSourceSetLayoutVersion=2`
- `kotlin.mpp.enableCInteropCommonization=true`
- `android.builtInKotlin=false`
- `android.newDsl=false`

Add if missing:
```properties
android.uniquePackageNames=false
android.dependency.useConstraints=true
```

---

## Step 4 — Root build.gradle.kts

**File:** `build.gradle.kts` (root)

This file uses extension functions to configure all submodules. The function names may vary slightly across projects but the pattern is the same. Read the current file carefully before editing.

### 4a. Imports

Remove if present:
```kotlin
import com.android.build.api.dsl.ApplicationExtension
import com.android.build.api.dsl.LibraryExtension
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
```

Add if missing:
```kotlin
import com.android.build.api.dsl.KotlinMultiplatformAndroidLibraryExtension
```

### 4b. Plugins block

Replace:
```kotlin
alias(libs.plugins.android.library) apply false
```
With:
```kotlin
alias(libs.plugins.kotlin.android) apply false
alias(libs.plugins.android.kotlin.multiplatform.library) apply false
```

### 4c. Add `plugins()` helper (if the project had separate `androidLibrary()` and `androidApplication()` helpers)

Replace any `androidLibrary()` / `androidApplication()` helper functions with a single `plugins()` helper:
```kotlin
private fun Project.plugins() {
    plugins {
        alias(rootProject.libs.plugins.android.kotlin.multiplatform.library)
        alias(rootProject.libs.plugins.kotlin.multiplatform)
    }
}
```

In the `subprojects` block, replace calls to `androidLibrary()` / `androidApplication()` with `plugins()`.

### 4d. `targets()` function — replace Android target configuration

Remove the old `androidTarget { compilerOptions { jvmTarget.set(...) } }` block. Replace with a typed `extensions.configure<KotlinMultiplatformAndroidLibraryExtension>` call outside the `kotlin<>` block.

```kotlin
private fun Project.targets(targetName: String? = null) {
    if (pluginManager.hasPlugin(rootProject.libs.plugins.android.kotlin.multiplatform.library.get().pluginId)) {
        extensions.configure<KotlinMultiplatformAndroidLibraryExtension> {
            namespace = nameSpace + path.replace(':', '.')
            compileSdk = rootProject.libs.versions.android.targetSdk.get().toInt()
            minSdk = rootProject.libs.versions.android.minSdk.get().toInt()
            androidResources.enable = true
        }
    }

    kotlin<KotlinMultiplatformExtension> {
        jvmToolchain(javaVersion.toInt())
        listOf(
            iosX64(),
            iosArm64(),
            iosSimulatorArm64(),
        ).forEach { iosTarget ->
            targetName?.let {
                iosTarget.binaries.framework {
                    baseName = targetName
                    isStatic = true
                }
            }
        }
    }
}
```

### 4e. `compose()` (or `setupCompose()`) function — replace `ComposePlugin.Dependencies` with version catalog refs

Remove:
```kotlin
val compose = org.jetbrains.compose.ComposePlugin.Dependencies(rootProject)
implementation(compose.runtime)
implementation(compose.foundation)
implementation(compose.material3)
implementation(compose.ui)
implementation(compose.components.resources)
implementation(compose.components.uiToolingPreview)
```

Replace with:
```kotlin
implementation(rootProject.libs.compose.runtime)
implementation(rootProject.libs.compose.foundation)
implementation(rootProject.libs.compose.material3)
implementation(rootProject.libs.compose.ui)
implementation(rootProject.libs.compose.components.resources)
implementation(rootProject.libs.compose.components.ui.tooling.preview)
```

### 4f. `composeApp()` function — remove if present

Remove the entire `composeApp()` function. It was a legacy helper that duplicated compose setup for the old app module. The KMP entry-point module is now handled by `compose()` + `composeFeature()` directly in the `subprojects` branch.

### 4g. `composeFeature()` (or `setupComposeFeature()`) — add lifecycle deps if missing

Ensure these are present in `commonMain.dependencies`:
```kotlin
implementation(rootProject.libs.lifecycle.runtime.compose)
implementation(rootProject.libs.lifecycle.viewmodel.compose)
```

### 4h. Detekt — fix task configuration

Change `tasks.withType<Detekt> {` to `tasks.withType<Detekt>().configureEach {` and add `classpath.setFrom(files())` inside it:
```kotlin
tasks.withType<Detekt>().configureEach {
    // Clearing classpath to avoid variant ambiguity in KMP Android modules
    // Type-safe analysis will be disabled for these tasks as a workaround for resolution issues
    classpath.setFrom(files())
    setSource(files(project.projectDir))
    exclude("**/build/**")
    exclude { it.file.toPath().startsWith(layout.buildDirectory.get().asFile.toPath()) }
}
```

Also add `classpath.setFrom(files())` to the `DetektCreateBaselineTask`:
```kotlin
val detektBaselines by tasks.registering(DetektCreateBaselineTask::class) {
    ...
    classpath.setFrom(files())
    ...
}
```

### 4i. Remove the `android<T>()` typed helper (if present)

Remove:
```kotlin
private inline fun <reified T: com.android.build.api.dsl.CommonExtension<*,*,*,*,*,*>> Project.android(block: T.() -> Unit) {
    the<T>().apply(block)
}
```

---

## Step 5 — KMP library module (the Compose entry point)

**Project-specific:** find the KMP module that was previously the app (now typically named `:app` or `:composeApp`). Read its `build.gradle.kts`.

Replace the old `android { }` block (which contained `packaging`, `buildTypes`, `defaultConfig { versionCode/versionName }`) with nothing — `targets()` in root now handles `namespace`, `compileSdk`, `minSdk`, and `androidResources` for all KMP modules.

Move any `androidMain.dependencies` that were absent (e.g. `androidx.activity.compose`) into the KMP `androidMain` source set:
```kotlin
androidMain.dependencies {
    implementation(libs.androidx.activity.compose)
}
```

---

## Step 6 — Kotlin API migrations

Search the entire codebase for these deprecated usages:

### 6a. `kotlinx.datetime.Clock` → `kotlin.time.Clock`

```bash
grep -r "import kotlinx.datetime.Clock" --include="*.kt"
```

For each file found, replace:
```kotlin
import kotlinx.datetime.Clock
```
with:
```kotlin
import kotlin.time.Clock
```

### 6b. `toDuration(DurationUnit.HOURS)` → `.hours`

```bash
grep -r "toDuration(DurationUnit" --include="*.kt"
```

Replace patterns like:
```kotlin
1L.toDuration(DurationUnit.HOURS)
```
with:
```kotlin
1.hours
```
And ensure `import kotlin.time.Duration.Companion.hours` is present in those files.

### 6c. `org.jetbrains.compose.ui.tooling.preview.Preview` → `androidx.compose.ui.tooling.preview.Preview`

```bash
grep -r "import org.jetbrains.compose.ui.tooling.preview.Preview" --include="*.kt"
```

For each file found, replace:
```kotlin
import org.jetbrains.compose.ui.tooling.preview.Preview
```
with:
```kotlin
import androidx.compose.ui.tooling.preview.Preview
```

This affects `@Preview`-annotated composables. The JetBrains alias was removed in Compose Multiplatform 1.10.x — the canonical annotation is now the AndroidX one.

---

## Step 7 — Sync, build, and run

After all changes, sync the project and verify it compiles and runs correctly. Fix any remaining issues before considering the migration done.

### 7a. Check for compile errors

```bash
./gradlew :<android-launcher-module>:assembleDebug
```

If there are compilation failures: read the full error, identify which file and line, fix it, and re-run. Common issues after this migration:
- Unresolved references to removed AGP types (`LibraryExtension`, `JvmTarget`) — remove the imports and any usages
- `ComposePlugin.Dependencies` references that weren't caught — replace with version catalog refs (see 4e)
- Version conflicts between bumped dependencies — check the error for the conflicting artifact and align versions in `libs.versions.toml`

### 7b. Run the app on a device or emulator

Install the debug APK and exercise the main flows. Watch logcat for `MissingResourceException` (missing compose resources) or any crash on startup.

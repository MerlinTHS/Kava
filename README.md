# Kava
Kotlin Validation DSL

## Installation

### Gradle

In your root build.gradle file

```gradle
allprojects {
  repositories {
    maven { url 'https://jitpack.io' }
  }
}
```
Add dependency to your module build.gradle file

```gradle
implementation 'com.github.MerlinTHS:Kava:1.0.1'
```

## Getting started

Using standard kotlin
```kotlin
import java.util.Optional

fun main() {
    val name = parseName("Hello Kotlin!")

    name.ifPresent(::println)
}

fun parseName(greeting: String): Optional<String> {
    if (greeting.isBlank() or !greeting.endsWith("!")) {
        return Optional.empty();
    }

    val name = "Hello\\s([^!]*)!"
        .toRegex()
        .find(greeting)


    return Optional.ofNullable(
        name?.groupValues?.get(1)
    )
}
```

Using Kava
```kotlin
import com.github.merlinths.kava.ensure
import com.github.merlinths.kava.validation.valid
import com.github.merlinths.kava.validator.optional

fun main() = valid {
    val (name) = parseName("Hello Kotlin!")

    println(name)
}

fun parseName(greeting: String) = optional {
    ensure (greeting) {
        isNotBlank() and endsWith("!")
    }

    val (name) = "Hello\\s([^!]*)!"
        .toRegex()
        .find(greeting)

    name.groupValues[1]
}
```

## Supported platforms
- JVM

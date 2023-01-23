# Kava

Kava is a kotlin library to reduce boilderplate code in the scope of validations.

It's purpose is to provide a flat and easy-to-read DSL for working with optional types.

## Getting started

Kava comes with a few top-level functions for creating optional datatypes.
- ```nullable``` for nullable types
- ```optional``` for types wrapped into ```java.util.Optional```

There are two ways to use Kava in a ```ValidScope```.

### Direct syntax using ```ensure```



### Destructuring syntax



### Example

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

Instead of writing it in the destructuring syntax, you can also use ```ensure (condition)```.
This function uses kotlin contracts to enable the same IDE awareness.

*Note that ```ensure``` is written in the same coding style as if - statements are, because the usage is conceptionally quite the same.*

```kotlin
val name = "Hello\\s([^!]*)!"
        .toRegex()
        .find(greeting)

ensure (name != null)
name.groupValues[1]
```

### Support Custom Datatypes

To automatically handle custom datatypes:
- Create custom ```Validator``` implementation
- Register implementation using ```@Validator```


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

## Supported platforms
- JVM

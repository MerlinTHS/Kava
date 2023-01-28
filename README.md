# Kava ☕

Kava is a kotlin library to reduce boilderplate code in the scope of validations.
It provides a flat and easy-to-read DSL for working with optional types.

## Getting started

Kava comes with a few top-level functions for creating optional data types
- ```nullable``` for nullable types
- ```optional``` for types wrapped into ```java.util.Optional```

As well as a ```validate``` function which returns an ```ValidationResult```. It can be used to execute code in case the scope succeeds
or fails with the extension functions ```onSuccess``` or```onFailure```. If you aren't interessted in checking the result, use ```onlyValidate```.

```kotlin
import com.github.merlinths.kava.validator.*

fun getName() = nullable { "Kava" }

fun main() {
    validate {
        val name by getName()

        println("Hello $name!")
    } onSuccess {
        println("Success is all around us!")
    } onFailure {
        println("Operation failed!")
    }
}
```

The code inside ```validate``` runs in the context of a ```ValidationScope```.
This scope allows you to use **delegated validations**. Each of the validations
either returns a validated instance of its type or fails, which results
in the end of the ```ValidationScope```. In the example above, the scope
would have been left, if ```getName``` had returned ```null```. 
<br />
>To achieve this stop of execution, a ```ValidationException``` is thrown.
So be careful when catching exceptions inside a ```ValidationScope```.

### Validation by Delegation

```kotlin
import com.github.merlinths.kava.validator.*
import java.util.Optional

data class Person(
    val name: String,
    val age: Int
)

fun main() = onlyValidate {
    val person by getPerson()
    
    println("${person.name} is ${person.age} years old!")
}

fun getPerson() = optional {
    Person(name = "Peter", age = 42)
}
```

### Preconditions

Use ```ensure``` to check conditions. It's internal use of *Kotlin Contracts* makes IntelliJ aware of your contract and results in a better IDE assistance.

Given a nullable property ```1```.
Instead of declaring a new property, that's delegated by validation ```2```.
You can use ```ensure``` to treat the old one as a non-nullable type ```3```.

```kotlin
// Nullable Property (1)
val maybeName: String? = "Peter"

// Delegated Validation (2)
val name by maybeName

// Ensured Conditions (3)
ensure (maybeName != null)
```

Or use the trailing lambda syntax to combine multiple conditions for the
same receiver.

```kotlin
ensure (name) {
    isNotBlank() and (length > 2)
}
```

You can also use ```fail``` in combination with
```if```, ```when``` or the *Elvis Operator* ```?:``` as more flexible alternatives.

>```ensure``` is written in the same coding style as ```if``` - statements are, because the usage is conceptually quite the same.
Statements below it will only be executed, if the condition is ```true```.

### The Snowflake ❄

In situations where you don't want to define a new property for checking and unwrapping via delegation,
you can use the *Snowflake* - property instead.
Simply add a snowflake to the expression to validate.

```kotlin
import com.github.merlinths.kava.validator.*
import java.util.Optional

fun main() = onlyValidate {
    val processedMagic = process(getNumber().`*`)

    println("Processed magic number is $processedMagic")
}

fun process(number: Int) =
    number * 10 + 5

fun getNumber() =
    Optional.of(42)
```

### Example

Using standard kotlin
```kotlin
import java.util.Optional

fun main() {
    val name = parseName("Hello Kotlin!")

    name.ifPresentOrElse(
        action = { name ->
            println("Bye $name!")
        },
        emptyAction = {
            println("Unable to parse a name!")
        }
    )
}

fun parseName(greeting: String): Optional<String> {
    if (greeting.isBlank() or !greeting.endsWith("!")) {
        return Optional.empty()
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
import com.github.merlinths.kava.validator.*
import java.util.Optional

fun main() {
    validate {
        val name by parseName("Hello Kotlin!")

        println("Bye $name!")
    } onFailure {
        println("Unable to parse a name!")
    }
}

fun parseName(greeting: String) = optional {
    ensure (greeting) {
        isNotBlank() and endsWith("!")
    }

    val name by "Hello\\s([^!]*)!"
        .toRegex()
        .find(greeting)

    name.groupValues[1]
}
```

## Installation

### Gradle

In your root *build.gradle*

```gradle
allprojects {
  repositories {
    maven { url 'https://jitpack.io' }
  }
}
```
Add dependency to your module *build.gradle*

```gradle
implementation 'com.github.MerlinTHS:Kava:1.0.2'
```

## Supported platforms
- JVM

## TODOs
- Replace exceptional-escaping with compiler-plugin modifications.
- Add Annotation Processor to generate scope function, snowflake extension and validated delegation from custom ```Validator``` implementation.

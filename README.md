# Kava ☕

Kava is a kotlin library to reduce boilerplate code when dealing with validations and optional types.
It provides a flat and easy-to-read DSL.

## Installation

Make sure Maven Central is set as a repository.

```kotlin
repositories {
    mavenCentral()
}
```

Add the core dependency. Refer to [MavenRepository](https://mvnrepository.com/search?q=io.github.merlinths) for the latest versions.

```kotlin
dependencies {
    implementation("io.github.merlinths:kava-core:1.0.0")   
}
```

Kava uses [Context Receivers](https://github.com/Kotlin/KEEP/blob/master/proposals/context-receivers.md),
a feature introduced in [Kotlin 1.6.20](https://kotlinlang.org/docs/whatsnew1620.html),
which is currently JVM-only. Since it's still experimental you
have to add ```-Xcontext-receivers``` as an additional compiler
argument.

For single-platform (JVM) project.

```kotlin
tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xcontext-receivers")
    }
}
```

For a multi-platform project.

```kotlin
kotlin {
    jvm {
        compilations.all {
            kotlinOptions {
                freeCompilerArgs = listOf("-Xcontext-receivers")
            }
        }
    }
}
```

There are also two templates to get started a bit faster.
One for [single-platform JVM]()
and one for [multi-platform]() projects.

## Getting started

*Kava* comes with a few top-level functions which provide
a scope for handling optional data types
- ```nullable``` for nullable types
- ```optional``` for types wrapped into ```java.util.Optional```
- ```validate``` which returns a ```ValidationResult```
- ```kava``` which always returns ```Unit```

```validate``` and ```kava``` can be used to execute code only
in case the scope succeeds
or fails with the extension functions ```onSuccess``` or```onFailure```. If you aren't interested in checking the result, use ```kava```.

```kotlin
import com.github.merlinths.io.validator.*

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
import com.github.merlinths.io.validator.*
import java.util.Optional

data class Person(
    val name: String,
    val age: Int
)

fun main() = kava {
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
import com.github.merlinths.io.validator.*
import java.util.Optional

fun main() = kava {
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

Using _Kava_

```kotlin
import com.github.merlinths.io.validator.*
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

## Custom Types

```kotlin
sealed interface Result<Type> {
    class Failure<Type> : Result<Type>
    
    data class Success<Type>(
        val value: Type
    ) : Result<Type>
}
```

You can add your own scope functions and the corresponding extension function for valid delegation by adding
the ```@GenerateExtensions``` annotation to your custom validator. The validator has to extend _Kava_'s ```Validator``` class.
Otherwise, compilation will fail!

```kotlin
@GenerateExtensions("result")
class ResultValidator<Type> : Validator<Type, Result<Type>> {
    override val invalid =
        Result.Failure<Type>()

    override fun valid(value: Type) =
        Result.Success(value)

    override fun ValidationScope<*>.validate(wrapper: Result<Type>) =
        when (wrapper) {
            is Result.Success -> wrapper.value
            else -> fail()
        }
}
```

To use the annotation and the corresponding processor to generate
extensions
- apply the [KSP Compiler - Plugin](https://kotlinlang.org/docs/ksp-overview.html)
- include the _annotations_ - dependency.
- Add the generated directory to your source sets.

For the sake of simplicity the following code samples show only
JVM single-platform.

```kotlin
plugins {
    id("com.google.devtools.ksp") version "1.8.0-1.0.8"
}

implementation {
    implementation("io.github.merlinths:kava-annotations:1.0.3")
    
    add("ksp", "io.github.merlinths:kava-processor:1.0.0")
    add("kspTest", "io.github.merlinths:kava-processor:1.0.0")
}

kotlin {
    sourceSets {
        main {
            kotlin.srcDir("build/generated/ksp/main/kotlin")
        }

        test {
            kotlin.srcDir("build/generated/ksp/test/kotlin")
        }
    }
}
```

After building the project, you can use the generated scope function ```result```.

```kotlin
fun greet(name: String) = result {
    ensure (name) {
        isNotBlank()
    }
    
    "Hello $name!"
}
```

## Supported platforms
- JVM
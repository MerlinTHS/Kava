<h1 align="center">Kava ☕

[![Maven Central](https://img.shields.io/maven-central/v/io.github.merlinths/kava-core?style=for-the-badge)](https://search.maven.org/artifact/io.github.merlinths/kava-core)
[![GitHub license](https://img.shields.io/badge/license-Apache%20License%202.0-red.svg?style=for-the-badge)](https://www.apache.org/licenses/LICENSE-2.0)
</h1>

Reduce boilerplate code when dealing with validations and optional types!
Kava provides a flat and easy-to-read DSL.

## Quick Start

The easiest way getting started is by using the [Gradle Plugin](https://plugins.gradle.org/plugin/io.github.merlinths.kava).
Apply it to your project and it will include all the necessary dependencies for the type of project you're working with.
If you want to setup all of that manually - please refer to the [Manual Setup](https://github.com/MerlinTHS/Kava/wiki/Manual-Setup) section.

Simply apply it in your *build.gradle.kts*.
```kotlin
plugins {
  id("io.github.merlinths.kava") version "1.0.0"
}
```

*Kava* comes with a few top-level functions which provide
a scope for validation.
- ```nullable<Type>``` Returns ```Type``` or ```null```
- ```optional<Type>``` Returns ```Type``` wrapped into ```java.util.Optional```
- ```validate``` Returns a ```ValidationResult``` ( ```Success``` or ```Failure``` )
- ```kava``` Returns always ```Unit```

```kava``` can be used to execute code only in case the preceding code succeeds, without caring about the overall result of the scope, whereas ```validate``` offers a convenient way to handle failure or success with the extension functions ```onSuccess``` or```onFailure```.

```kotlin
import io.mths.kava.validator.extensions.*

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

fun getName() =
    nullable { "Kava" }
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
Kava provides a delegation for every optional datatype [currently supported](https://github.com/MerlinTHS/Kava/wiki/Supported-Types).
Each of the them either returns a valid instance of its type or fails, which results in the end of the surrounding ```ValidationScope```.
Because they're in the same package, you don't need to import them separately.

```kotlin
import io.mths.kava.validator.extensions.*

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

You can add your own validated delegations by creating a custom validator and annotate it with ```@GenerateExtensions```.
See [Custom Types]().

### Preconditions

Use ```ensure``` to check conditions. It's internal use of *Kotlin Contracts* makes IntelliJ aware of your contract and results in a better IDE assistance.

```kotlin
val maybeName: String? = "Peter"
ensure (maybeName != null)
```

Or use the trailing lambda syntax to combine multiple conditions for the
same receiver.

```kotlin
ensure (name) {
    isNotBlank() and (length > 2)
}
```

To shorten precondition checks, you can replace ```ensure``` with
a special overloaded ```unaryPlus``` operator in the context of a
```ValidationScope```. It looks more like listing all the
preconditions, than ensuring them step by step.

```kotlin
fun save(
    name: String,
    description: String
) = validate {
    + name { isNotBlank() }
    + description { length > 20 }
    
    // ...
}
```

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

## Custom Types

Assume we want to add a new type ```Result``` for modelling optional behavior.

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

    override fun ValidationScope<*>.validate(
        wrapper: Result<Type>
    ) = when (wrapper) {
        is Result.Success -> wrapper.value
        else -> fail()
    }
}
```

After [setting up the Annotation Processor](https://github.com/MerlinTHS/Kava/wiki/Manual-Setup#annotation-processor)
and building the project, you can use the generated scope function ```result```.

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

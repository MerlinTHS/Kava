package io.mths.kava.processor.generator.extensions

import com.squareup.kotlinpoet.TypeVariableName
import com.squareup.kotlinpoet.asTypeName
import io.mths.kava.processor.assertEqualsCode
import io.mths.kava.processor.generator.extensions.info.ExtensionInfo
import io.mths.kava.validator.NullValidator
import kotlin.test.Test

class SnowflakeTest {
    private val type = TypeVariableName("Type")
    private val nullableInfo = ExtensionInfo(
        type = type,
        wrapperType = type.copy(nullable = true),
        scopeTypes = listOf(type),
        implementation = NullValidator::class.asTypeName()
    )

    @Test
    fun `Nullable snowflake extension`() {
        val spec = nullableInfo.snowflake()

        assertEqualsCode(
            actual = spec.toString(),
            expected = """
                context (io.mths.kava.ValidationScope<*>)
                val <Type> Type?.`*`: Type
                    get() = with(this@io.mths.kava.ValidationScope) {
                        io.mths.kava.validator.NullValidator<Type>().run {
                            this@io.mths.kava.ValidationScope.validate(this@`*`)
                    }
                }
            """
        )
    }

    @Test
    fun `Nullable delegation extension`() {
        val spec = nullableInfo.delegation()

        assertEqualsCode(
            actual = spec.toString(),
            expected = """
                context (io.mths.kava.ValidationScope<*>)
                public operator fun <Type> Type?.getValue(
                    thisRef: kotlin.Any?,
                    `property`: kotlin.reflect.KProperty<*>
                ): Type =  with(this@io.mths.kava.ValidationScope) {
                    this@getValue.`*`
                }
            """.trimIndent()
        )
    }
}
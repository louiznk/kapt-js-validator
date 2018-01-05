import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test


internal class DataClassForBeanValidationDataTest {
    @Test
    fun validateOk() {
        val dataClass = DataClassForBeanValidationData("John", "Doe")
        val constraints = dataClass.validate()
        assertTrue(constraints.isEmpty())

    }
    @Test
    fun validateKo() {
        val dataClass = DataClassForBeanValidationData("John", null)
        val constraints = dataClass.validate()
        assertFalse(constraints.isEmpty())

        print(constraints)

    }
}
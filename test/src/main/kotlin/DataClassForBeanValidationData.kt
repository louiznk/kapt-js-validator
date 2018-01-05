import javax.validation.ConstraintViolation
import javax.validation.Validation
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

data class DataClassForBeanValidationData(@get: NotNull(message = "{first_name.required}")
                                          val firstName: String?,
                                          @field: NotNull(message = "{last_name.required}")
                                          @field: Size(min = 2, max = 16)
                                          val lastName: String?) {
    fun validate(): List<ConstraintViolation<DataClassForBeanValidationData>> {
        val factory = Validation.buildDefaultValidatorFactory()
        val validator = factory.validator

        return validator.validate(this).toList()
    }

}


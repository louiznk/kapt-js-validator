import javax.validation.constraints.Max
import javax.validation.constraints.Min
import javax.validation.constraints.NotNull
import javax.validation.constraints.Pattern
import javax.validation.executable.ValidateOnExecution

@ValidateOnExecution
data class DataClass(
    @NotNull
    val name: String,
    @Min(10)
    @Max(300)
    val height: Int,
    @Min(1)
    @Max(300)
    val weight: Int,
    @Pattern(regexp = "^[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,6}$", flags = arrayOf(Pattern.Flag.CASE_INSENSITIVE))
    val email: String
)
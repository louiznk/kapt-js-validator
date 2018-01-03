import javax.validation.constraints.NotNull

class MixedClass(@NotNull
                  val truc: Int, val toto: String) {
    @NotNull
    var what: Int = 0


     fun test() : Unit{
        what = 10
    }
}
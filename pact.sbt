import com.itv.scalapact.plugin.ScalaPactPlugin._

providerStates := Seq(
  ("state666", (key: String) => {

    println(s"executing $key")
    println(" inserting data to database...")

    true
  })
)
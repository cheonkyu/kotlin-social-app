package app.config

import org.yaml.snakeyaml.Yaml
import org.yaml.snakeyaml.representer.Representer
import org.yaml.snakeyaml.DumperOptions
import org.yaml.snakeyaml.LoaderOptions
import org.yaml.snakeyaml.constructor.Constructor

val representer = Representer(DumperOptions())
                    .getPropertyUtils()
                    .setSkipMissingProperties(true)

val loaderOptions = LoaderOptions();
val constructor = Constructor(Config::class.java, loaderOptions);

val yaml = Yaml()
val t = {}.javaClass.classLoader
    .getResourceAsStream("application.yaml")

val _data = yaml.loadAs(t, Map::class.java)
val filteredData = _data.filterKeys { key ->
    listOf("ktorm").contains(key) }

val data: Config = yaml.loadAs(yaml.dump(filteredData), Config::class.java)

val ktorm : Config.Ktorm = data.ktorm

data class Config(
    var ktorm: Ktorm = Ktorm(),
) {
    data class Ktorm(
        var main: DataSource = DataSource(),
    ) {
        data class DataSource(
            var url: String = "",
            var driverClassName: String = "",
            var username: String = "",
            var password: String = ""
        )
    }
}
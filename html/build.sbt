enablePlugins(ScalaJSPlugin)

enablePlugins(ScalaJSBundlerPlugin)

enablePlugins(Example)

Test / requireJsDomEnv := true

libraryDependencies += "org.scalatest" %%% "scalatest" % "3.2.11" % Test

libraryDependencies += "org.scala-js" %%% "scalajs-dom" % "2.1.0"

libraryDependencies += "net.sourceforge.htmlunit" % "neko-htmlunit" % "2.58.0"

libraryDependencies += "com.thoughtworks.dsl" %%% "macros-reset" % "2.0.0-M2+300-ad3b467f"

libraryDependencies += "com.thoughtworks.dsl" %%% "domains-continuation" % "2.0.0-M2+300-ad3b467f"

libraryDependencies += "com.thoughtworks.dsl" %%% "keywords-yield" % "2.0.0-M2+300-ad3b467f"

libraryDependencies += "com.thoughtworks.dsl" %%% "keywords-await" % "2.0.0-M2+300-ad3b467f" % Test
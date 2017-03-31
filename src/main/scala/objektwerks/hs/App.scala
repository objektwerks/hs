package objektwerks.hs

import com.typesafe.config.ConfigFactory
import objektwerks.hs.model.Model
import objektwerks.hs.repository.Repository
import objektwerks.hs.view.View

import scalafx.application.JFXApp

object App extends JFXApp {
  val conf = ConfigFactory.load("app.conf")
  val repository = Repository.newInstance("repository.conf")
  val model = new Model(repository)
  val view = new View(conf, model)
  stage = new JFXApp.PrimaryStage {
    scene = view.sceneGraph
    title = conf.getString("title")
    minHeight = 600
    minWidth = 800
    icons.add(View.appImmage())
  }

  sys.addShutdownHook { repository.close() }
}
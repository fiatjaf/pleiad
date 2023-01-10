import com.raquo.laminar.api._
import com.raquo.waypoint._
import org.scalajs.dom

sealed trait Page
case object HomePage extends Page
case class ArticleViewPage(name: String) extends Page
case class ArticleEditPage(name: String) extends Page

object Main {
  val homeRoute = Route.static(HomePage, root)
  val viewRoute = Route[ArticleViewPage, String](
    encode = v => v.name,
    decode = arg => ArticleViewPage(arg),
    pattern = root / segment[String] / endOfSegments
  )
  val editRoute = Route[ArticleEditPage, String](
    encode = v => v.name,
    decode = arg => ArticleEditPage(arg),
    pattern = root / "edit" / segment[String] / endOfSegments
  )

  val router = new Router[Page](
    routes = List(editRoute, viewRoute, homeRoute),
    getPageTitle = {
      case HomePage              => "Pleiad"
      case ArticleViewPage(name) => s"$name | Pleiad"
      case ArticleEditPage(name) => s"[edit] $name | Pleiad"
    },
    serializePage = _ => "",
    deserializePage = _ => HomePage
  )(
    popStateEvents = L.windowEvents(_.onPopState),
    owner = L.unsafeWindowOwner
  )

  val splitter = SplitRender[Page, L.HtmlElement](router.currentPageSignal)
    .collectSignal[ArticleViewPage](ArticleView.render(_))
    .collectSignal[ArticleEditPage](ArticleEdit.render(_))
    .collectStatic(HomePage) { Home.render() }

  val app = L.mainTag(L.child <-- splitter.signal)

  def main(args: Array[String]): Unit = {
    L.documentEvents(_.onDomContentLoaded)
      .foreach { _ =>
        L.render(dom.document.getElementById("main"), app)
      }(L.unsafeWindowOwner)
  }
}

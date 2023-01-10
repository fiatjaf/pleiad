import com.raquo.laminar.api.L._

object ArticleView {
  def render(s: Signal[ArticleViewPage]): HtmlElement = {
    div(
      "article view ",
      child <-- s.map(_.name)
    )
  }
}

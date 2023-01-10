import com.raquo.laminar.api.L._

object ArticleEdit {
  def render(s: Signal[ArticleEditPage]): HtmlElement = {
    div(
      "article edit ",
      child <-- s.map(_.name)
    )
  }
}

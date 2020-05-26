package concurrent.parallel_collections

object ParallelBalanceParentheses extends App {

  case class Count(left: Int, right: Int) {
    def isBalanced = left >= right
  }

  def parenthesesCount(string: String): Count = string match {
    case "(" => Count(1, 0)
    case ")" => Count(0, 1)
    case _ => Count(0, 0)
  }

  def filterParenthesis(string: String): Boolean = string.equals("(") || string.equals(")")

  def sumTuple(a: Count, b: Count): Count = Count(a.left + b.left, a.right + b.right)

  def parallelBalanceParentheses(value: String): Boolean = {

    val count = value.split("").par
      .filter(filterParenthesis(_))
      .map(parenthesesCount(_))

    // we can change this foldLeft for scanLeft to accumulate a collection of intermediate cumulative results using a start value.
    val aggregatedCount = count
      .foldLeft((true, Count(0, 0))){
        case ((balanced, totalCount), elem) => {
           val count = sumTuple(totalCount, elem)
          (balanced && count.isBalanced, count)
        }
      }

    val (balancedByCount, totalCount) = aggregatedCount
    balancedByCount && (totalCount.left == totalCount.right)
  }

  println(parallelBalanceParentheses("0(1)(2(3))4")) //true

  println(parallelBalanceParentheses("0)2(1(3)")) //false

  println(parallelBalanceParentheses("0((1)2")) //false

}

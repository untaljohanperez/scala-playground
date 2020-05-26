package concurrent.JVMConcurrency

object util {
  var uidCount = 0L
  def getUniqueId() = this.synchronized {
    val freshUid = uidCount + 1
    uidCount = freshUid
    freshUid
  }
}

class Account(val name: String, var money: Int) {
  val uid = util.getUniqueId()
}

object AccountsApp extends App {

  def send(a1: Account, a2: Account, n: Int) {
    def adjust() {
      a1.money -= n
      a2.money += n
    }
    if (a1.uid < a2.uid)
      a1.synchronized { a2.synchronized { adjust() } }
    else a2.synchronized { a1.synchronized { adjust() } }
  }

  def sendAll(accounts: Set[Account], target: Account): Unit = {
    accounts.foreach { origin =>
      origin.synchronized {
        target.synchronized {
          target.money += origin.money
          origin.money = 0
        }
      }
    }
  }

  val a = new Account("Jack", 1000)
  val b = new Account("Jill", 2000)
  //val t1 = thread { for (i<- 0 until 100) send(a, b, 1) }
  //val t2 = thread { for (i<- 0 until 100) send(b, a, 1) }
  //t1.join(); t2.join()
  //log(s"a = ${a.money}, b = ${b.money}")

  val c = new Account("Olivia", 0)

  val t3 = thread { sendAll(Set(a, b), c) }
  val t4 = thread { sendAll(Set(c, a), a) }
  t3.join()
  t4.join()
  log(s"a = ${a.money}, b = ${b.money}, c = ${c.money}")

}

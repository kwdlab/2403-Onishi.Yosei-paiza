import scala.io.StdIn._
import math._
import scala.collection.mutable.ArrayBuffer

object Main extends App {

    class UnionFind(n: Int) {
        val parent = Array.tabulate(n)(i => i)
        
        def get_parent(a: Int): Int = {
            var current = a
            while (current != parent(current)) {
                current = parent(current)
            }
            current
        }

        def unite(a: Int, b: Int): Unit = {
            val rootA = get_parent(a)
            val rootB = get_parent(b)
            parent(rootB) = rootA
        }

        def same(a: Int, b: Int): Boolean = {
            get_parent(a) == get_parent(b)
        }
    }

    def Euclidean(a: Array[Double], b: Array[Double]):Double = {
        return sqrt(pow(a(0)-b(0), 2) + pow(a(1)-b(1), 2))
    }

    def two_approx(before: Int, now: Int, n: Int, mst: Array[ArrayBuffer[Int]], tour: ArrayBuffer[Int]): Unit = {
        tour.append(now)
        for (next <- mst(now)) {
            if (next == before) {

            } else {
                two_approx(now, next, n, mst, tour)
            }
        }
    }

    val n = readLine().trim().toInt
    val points = Array.ofDim[Double](n, 2)
    for (i <- 0 until n) {
        points(i) = readLine().trim().split(" ").map(_.toDouble)
    }

    val edges = ArrayBuffer[(Double, Int, Int)]()
    for (i <- 0 until n; j <- i + 1 until n) {
        edges.append((Euclidean(points(i), points(j)), i, j))
    }
    val sortEdges = edges.sortBy(_._1)
    val uf = new UnionFind(n)
    val mst = Array.fill(n)(ArrayBuffer[Int]())
    for ((d, i, j) <- sortEdges) {
        if (!uf.same(i, j)) {
            mst(i).append(j)
            mst(j).append(i)
            uf.unite(i, j)
        }
    }

    val tour = ArrayBuffer[Int]()
    two_approx(-1, 0, n, mst, tour)

    println(tour.mkString(" "))
}
//解答例使用済み
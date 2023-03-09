package ivypack.memo

fun main() {
    val fibonacciMemoized = Memo1<Int, Int>::fibonacci.memoize()
    println("Fibonacci (memoized): ${fibonacciMemoized(1_000)}")
    println("Fibonacci memoized returns instantly for large 'n'.")

    println()

    println("Fibonacci: ${fibonacci(15)}")
    println("Without memoization you might wait for a while, while we burn your CPU.")
}

// O(2^n) time | O(2^n) space
/*
O(2^n) because for all "n", n >= 2 there are 2 recursive calls: fib(n-1), fib(n-2) that
will recurse until they reach n = 1.
 */
fun fibonacci(n: Int): Int = when (n) {
    0 -> 0
    1 -> 1
    else -> fibonacci(n - 1) + fibonacci(n - 2)
}

// O(n) time | O(n) space
/*
O(n) because this version of the fibonacci algorithm will be memoized:
For all "n", n >= n: recurse(n-1) & recurse(n-2) will return instantly because
their output will be cached.
 */
fun Memo1<Int, Int>.fibonacci(n: Int): Int = when (n) {
    0 -> 0
    1 -> 1
    else -> {
        // WARNING: You must use the supplied "Memo1<Int,Int>#recurse()"
        // to benefit from memoization for recursive calls.
        recurse(n - 1) + recurse(n - 2)
    }
}

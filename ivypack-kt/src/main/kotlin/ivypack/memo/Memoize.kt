package ivypack.memo

interface Memo1<A, R> {
    fun recurse(a: A): R
}

abstract class Memoized1<A, R> {
    private val cache = mutableMapOf<A, R>()
    private val memo = object : Memo1<A, R> {
        override fun recurse(a: A): R = cache.getOrPut(a) { function(a) }
    }

    protected abstract fun Memo1<A, R>.function(a: A): R

    fun execute(a: A): R = memo.recurse(a)
}


// region Memoize extension
fun <A, R> (Memo1<A, R>.(A) -> R).memoize(): (A) -> R {
    val memoized = object : Memoized1<A, R>() {
        override fun Memo1<A, R>.function(a: A): R = this@memoize(a)
    }
    return { a ->
        memoized.execute(a)
    }
}
// endregion
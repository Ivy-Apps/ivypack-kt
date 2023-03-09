package ivypack.memo

// region "Memo" abstraction
interface Memo1<A, R> {
    fun recurse(a: A): R
}

interface Memo2<A, B, R> {
    fun recurse(a: A, b: B): R
}

interface Memo3<A, B, C, R> {
    fun recurse(a: A, b: B, c: C): R
}

interface Memo4<A, B, C, D, R> {
    fun recurse(a: A, b: B, c: C, d: D): R
}

interface Memo5<A, B, C, D, E, R> {
    fun recurse(a: A, b: B, c: C, d: D, e: E): R
}
// endregion


// region Memoization implementation
abstract class Memoized1<A, R> {
    private val cache = mutableMapOf<A, R>()
    private val memo = object : Memo1<A, R> {
        override fun recurse(a: A): R = cache.getOrPut(a) { function(a) }
    }

    protected abstract fun Memo1<A, R>.function(a: A): R

    fun execute(a: A): R = memo.recurse(a)
}

abstract class Memoized2<A, B, R> {
    private data class Input<A, B>(
        val a: A,
        val b: B
    )

    private val cache = mutableMapOf<Input<A, B>, R>()
    private val memo = object : Memo2<A, B, R> {
        override fun recurse(a: A, b: B): R =
            cache.getOrPut(Input(a, b)) { function(a, b) }
    }

    protected abstract fun Memo2<A, B, R>.function(a: A, b: B): R

    fun execute(a: A, b: B): R = memo.recurse(a, b)
}

abstract class Memoized3<A, B, C, R> {
    private data class Input<A, B, C>(
        val a: A,
        val b: B,
        val c: C
    )

    private val cache = mutableMapOf<Input<A, B, C>, R>()
    private val memo = object : Memo3<A, B, C, R> {
        override fun recurse(a: A, b: B, c: C): R =
            cache.getOrPut(Input(a, b, c)) { function(a, b, c) }
    }

    protected abstract fun Memo3<A, B, C, R>.function(a: A, b: B, c: C): R

    fun execute(a: A, b: B, c: C): R = memo.recurse(a, b, c)
}

abstract class Memoized4<A, B, C, D, R> {
    private data class Input<A, B, C, D>(
        val a: A,
        val b: B,
        val c: C,
        val d: D
    )

    private val cache = mutableMapOf<Input<A, B, C, D>, R>()
    private val memo = object : Memo4<A, B, C, D, R> {
        override fun recurse(a: A, b: B, c: C, d: D): R =
            cache.getOrPut(Input(a, b, c, d)) { function(a, b, c, d) }
    }

    protected abstract fun Memo4<A, B, C, D, R>.function(a: A, b: B, c: C, d: D): R

    fun execute(a: A, b: B, c: C, d: D): R = memo.recurse(a, b, c, d)
}

abstract class Memoized5<A, B, C, D, E, R> {
    private data class Input<A, B, C, D, E>(
        val a: A,
        val b: B,
        val c: C,
        val d: D,
        val e: E
    )

    private val cache = mutableMapOf<Input<A, B, C, D, E>, R>()
    private val memo = object : Memo5<A, B, C, D, E, R> {
        override fun recurse(a: A, b: B, c: C, d: D, e: E): R =
            cache.getOrPut(Input(a, b, c, d, e)) { function(a, b, c, d, e) }
    }

    protected abstract fun Memo5<A, B, C, D, E, R>.function(a: A, b: B, c: C, d: D, e: E): R

    fun execute(a: A, b: B, c: C, d: D, e: E): R = memo.recurse(a, b, c, d, e)
}
// endregion


// region ".memoize()" extensions
fun <A, R> (Memo1<A, R>.(A) -> R).memoize(): (A) -> R {
    val memoized = object : Memoized1<A, R>() {
        override fun Memo1<A, R>.function(a: A): R = this@memoize(a)
    }
    return { a ->
        memoized.execute(a)
    }
}

fun <A, B, R> (Memo2<A, B, R>.(A, B) -> R).memoize(): (A, B) -> R {
    val memoized = object : Memoized2<A, B, R>() {
        override fun Memo2<A, B, R>.function(a: A, b: B): R = this@memoize(a, b)
    }
    return { a, b ->
        memoized.execute(a, b)
    }
}

fun <A, B, C, R> (Memo3<A, B, C, R>.(A, B, C) -> R).memoize(): (A, B, C) -> R {
    val memoized = object : Memoized3<A, B, C, R>() {
        override fun Memo3<A, B, C, R>.function(
            a: A, b: B, c: C
        ): R = this@memoize(a, b, c)
    }
    return { a, b, c ->
        memoized.execute(a, b, c)
    }
}

fun <A, B, C, D, R> (Memo4<A, B, C, D, R>.(A, B, C, D) -> R).memoize(): (A, B, C, D) -> R {
    val memoized = object : Memoized4<A, B, C, D, R>() {
        override fun Memo4<A, B, C, D, R>.function(
            a: A, b: B, c: C, d: D
        ): R = this@memoize(a, b, c, d)
    }
    return { a, b, c, d ->
        memoized.execute(a, b, c, d)
    }
}

fun <A, B, C, D, E, R> (Memo5<A, B, C, D, E, R>.(A, B, C, D, E) -> R).memoize(): (A, B, C, D, E) -> R {
    val memoized = object : Memoized5<A, B, C, D, E, R>() {
        override fun Memo5<A, B, C, D, E, R>.function(
            a: A, b: B, c: C, d: D, e: E
        ): R = this@memoize(a, b, c, d, e)
    }
    return { a, b, c, d, e ->
        memoized.execute(a, b, c, d, e)
    }
}
// endregion

// region "liftMemo"
/**
 * **!Important:** Use only for non-recursive functions.
 * If "f" uses recursion use "MemoN<>.f" or you won't benefit from memoization.
 */
fun <A, R> liftMemo(f: (A) -> R): Memo1<A, R>.(A) -> R = { a ->
    f(a)
}

/**
 * **!Important:** Use only for non-recursive functions.
 * If "f" uses recursion use "MemoN<>.f" or you won't benefit from memoization.
 */
fun <A, B, R> liftMemo(f: (A, B) -> R): Memo2<A, B, R>.(A, B) -> R = { a, b ->
    f(a, b)
}

/**
 * **!Important:** Use only for non-recursive functions.
 * If "f" uses recursion use "MemoN<>.f" or you won't benefit from memoization.
 */
fun <A, B, C, R> liftMemo(f: (A, B, C) -> R): Memo3<A, B, C, R>.(A, B, C) -> R = { a, b, c ->
    f(a, b, c)
}

/**
 * **!Important:** Use only for non-recursive functions.
 * If "f" uses recursion use "MemoN<>.f" or you won't benefit from memoization.
 */
fun <A, B, C, D, R> liftMemo(
    f: (A, B, C, D) -> R
): Memo4<A, B, C, D, R>.(A, B, C, D) -> R = { a, b, c, d ->
    f(a, b, c, d)
}

/**
 * **!Important:** Use only for non-recursive functions.
 * If "f" uses recursion use "MemoN<>.f" or you won't benefit from memoization.
 */
fun <A, B, C, D, E, R> liftMemo(
    f: (A, B, C, D, E) -> R
): Memo5<A, B, C, D, E, R>.(A, B, C, D, E) -> R = { a, b, c, d, e ->
    f(a, b, c, d, e)
}
// endregion
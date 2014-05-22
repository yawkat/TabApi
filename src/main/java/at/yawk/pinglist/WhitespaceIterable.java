package at.yawk.pinglist;

import java.util.Iterator;
import java.util.NoSuchElementException;
import javax.annotation.Nullable;

/**
 * This {@link Iterable} has exactly 2<sup>{@link #length} </sup> elements of the type {@link String}. Each of them is
 * composed in a specific pattern: <ol> <li><i>nothing</i></li> <li><code>a</code></li> <li><code>b</code></li>
 * <li><code>aa</code></li> <li><code>ab</code></li> <li><code>ba</code></li> <li><code>bb</code></li>
 * <li><code>aaa</code></li> <li><i>...</i></li> </ol> where <code>a</code> represents {@link #allowedCharacters}[0] and
 * <code>b</code> represents {@link #allowedCharacters}[1].
 *
 * @author Yawkat
 */
class WhitespaceIterable implements Iterable<String> {
    private final static double l2 = Math.log(2);

    private final int length;
    private final char[] allowedCharacters;

    /**
     * @throws IllegalArgumentException if <code>maxLength < 0</code> or <code>allowedCharacters.length != 2</code>
     */
    public WhitespaceIterable(int maxLength, char... allowedCharacters) {
        this.length = maxLength;
        this.allowedCharacters = allowedCharacters;
        if (allowedCharacters.length != 2) {
            throw new IllegalArgumentException("This constructor only allows exactly two allowed characters!");
        }
        if (maxLength < 0) {
            throw new IllegalArgumentException("Maximum length must be >= null!");
        }
    }

    public WhitespaceIterable(char... allowedCharacters) {
        this(Integer.MAX_VALUE, allowedCharacters);
    }

    @Nullable
    @Override
    public Iterator<String> iterator() {
        return new Iterator<String>() {
            private int index = 0;

            @Override
            public boolean hasNext() {
                return get(index) != null;
            }

            @Nullable
            @Override
            public String next() {
                String s = get(index++);
                if (s == null) {
                    throw new NoSuchElementException();
                }
                return s;
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
    }

    @Nullable
    private String get(int id) {
        if (id < 0) {
            throw new IllegalArgumentException();
        }
        int length = id == 0 ? 0 : (int) (Math.log(id + 1) / l2);
        if (length > this.length) {
            return null;
        }
        int pdex = id - (1 << length) + 1;
        char[] characters = new char[length];
        for (int i = 0; i < length; i++) {
            characters[i] = allowedCharacters[(pdex >> i) & 1];
        }
        return new String(characters);
    }
}

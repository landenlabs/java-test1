
/*
 * Copyright (c) 2015 Dennis Lang (LanDen Labs) landenlabs@gmail.com
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and
 * associated documentation files (the "Software"), to deal in the Software without restriction, including
 * without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the
 *  following conditions:
 *
 *  The above copyright notice and this permission notice shall be included in all copies or substantial
 *  portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT
 * LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN
 * NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
 * WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE
 * SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 *  @author Dennis Lang  (3/21/2015)
 *  @see http://landenlabs.com/
 */

import java.util.Arrays;

/**
 * Alternative to Java StringBuilder which delays allocation of final
 * string storage and merging till all strings are appended.
 *
 * This will use less memory if the strings are persistent (ex literals).
 *
 * Created by Dennis Lang on 12/10/16.
 */
public class StringAppender {
    private String[] mArray = null;
    private int mSize = 0;
    private int mLength = 0;

    private char[] m_allParts;
    private String mLastString;


    public StringAppender() {
        ensureCapacityInternal(16);
    }

    public StringAppender(int initialStrCapacity) {
        ensureCapacityInternal(initialStrCapacity);
    }

    /***
     * Start building a string.
     */
    public StringAppender start(String str) {
        clear();
        return append(str);
    }

    /***
     * Append string to list for later merging.
     */
    public StringAppender append(String str) {
        ensureCapacityInternal(mSize + 1);
        mArray[mSize++] = str;
        mLength += str.length();
        return this;
    }

    /**
     * Clear list of strings.
     */
    public void clear() {
        mSize = 0;
        mLength = 0;
        mLastString = null;
    }

    private void ensureCapacityInternal(int minCapacity) {
        if (mArray == null) {
            mArray = new String[minCapacity];
            return;
        }

        if (minCapacity <= mArray.length) {
            return;
        }

        int oldCapacity = mArray.length;
        int newCapacity = oldCapacity * 2 + 2;
        if (newCapacity - minCapacity < 0)
            newCapacity = minCapacity;

        if (true) {
            // Solution similar to StringBuilder
            mArray = Arrays.copyOf(mArray, newCapacity);
        } else {
            //  Alternate solution, merge prior to grow
            String current = toString();
            mSize = 0;
            // Uncomment next line to allow list to grow.
            // mArray = new String[newCapacity];
            mArray[mSize++] = current;
        }
    }

    /***
     * @return length of all parts.
     */
    public int length() {
        return mLength;
    }

    /***
     * @return combination of parts.
     */
    public String toString() {
        if (mLength == 0)
            return "";

        if (mLastString != null)
            return mLastString;
        if (m_allParts == null || m_allParts.length < mLength)
            m_allParts = new char[mLength];

        int pos = 0;
        for (int idx = 0; idx < mSize; idx++) {
            String str = mArray[idx];
            int len = str.length();
            str.getChars(0, len, m_allParts, pos);
            pos += len;
        }

        mLastString =  new String(m_allParts, 0, mLength);
        return mLastString;
    }
}
